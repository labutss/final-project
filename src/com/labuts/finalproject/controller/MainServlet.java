package com.labuts.finalproject.controller;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.command.CommandMap;
import com.labuts.finalproject.pool.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *MainServlet class is used to run application
 */
@WebServlet("/controller")
public class MainServlet extends HttpServlet {
    /**
     * command parameter
     */
    private static final String COMMAND_PARAMETER = "command";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * process request
     * @param request httpServletRequest
     * @param response httpServletResponse
     * @throws ServletException when something goes wrong
     * @throws IOException when something goes wrong
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND_PARAMETER);
        Command command = CommandMap.getInstance().get(commandName);
        String page = command.execute(request);
        command.storeData(request, page);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().closePool();
        super.destroy();
    }
}
