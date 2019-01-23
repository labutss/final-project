package com.labuts.finalproject.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConnectionPool class is used to manage connections to database
 */
public class ConnectionPool {
    /**
     * connection pool instance
     */
    private static ConnectionPool instance;
    /**
     * available connections
     */
    private BlockingQueue<ProxyConnection> availableConnections;
    /**
     * used connections
     */
    private BlockingQueue<ProxyConnection> usedConnections;
    /**
     * lock
     */
    private static ReentrantLock lock = new ReentrantLock();
    /**
     * is pool created
     */
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    /**
     * pool size
     */
    private static final int INITIAL_POOL_SIZE = 10;
    /**
     * logger
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Constructor is used to initialize connection pool
     */
    private ConnectionPool(){
        try {
            Class.forName(PoolDataManager.getClassName());
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "Can't download MySQLDriver", e);
            throw new RuntimeException("Can't download MySQLDriver", e);
        }
        init();
    }

    /**
     * Additional initialization
     */
    private void init(){
        availableConnections = new LinkedBlockingDeque<>(INITIAL_POOL_SIZE);
        usedConnections = new LinkedBlockingDeque<>(INITIAL_POOL_SIZE);
        for(int i = 0; i < INITIAL_POOL_SIZE; i++){
            try {
                Connection connection = DriverManager.getConnection(PoolDataManager.getConnectionUrl(),
                        PoolDataManager.getUsername(), PoolDataManager.getPassword());
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                availableConnections.put(proxyConnection);
            } catch (SQLException e) {
                logger.log(Level.INFO, e);
                e.printStackTrace();
            } catch (InterruptedException e) {
                logger.log(Level.INFO, e);
            }
        }
        if(availableConnections.isEmpty()){
            logger.log(Level.FATAL, "No available connections");
            throw new RuntimeException();
        }
    }

    /**
     * get instance
     * @return connectionPool instance
     */
    public static ConnectionPool getInstance(){
        if(!isCreated.get()){
            try{
                lock.lock();
                if(instance == null){
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            }finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * take connection
     * @return connection
     */
    public Connection takeConnection(){
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            usedConnections.add(connection);
        }catch (InterruptedException e){
            logger.log(Level.ERROR, "Can't take connection", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * release connection
     * @param connection connection
     */
    public void releaseConnection(Connection connection){
        if(connection.getClass() == ProxyConnection.class){
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            try {
                usedConnections.remove(proxyConnection);
                availableConnections.put(proxyConnection);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "Can't release connection", e);
            }
        }
    }

    /**
     * close pool
     */
    public void closePool() {
        for(int i = 0; i < INITIAL_POOL_SIZE; i++){
            try {
                availableConnections.take().realClose();
            } catch (SQLException e) {
                e.printStackTrace();
            }catch (InterruptedException e) {
                logger.log(Level.ERROR, e);
            }
        }
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        }
    }
}
