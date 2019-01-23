package com.labuts.finalproject.entity;

import java.util.Objects;

/**
 * Entity class is used for storing information of an object with id
 */
public class Entity {
    /**
     * Entity id
     */
    private final int ENTITY_ID;

    /**
     * This is a constructor to initialize entity object
     * @param ENTITY_ID entity id
     */
    Entity(int ENTITY_ID) {
        this.ENTITY_ID = ENTITY_ID;
    }

    /**
     * get entity id
     * @return entity id
     */
    public int getEntityId() {
        return ENTITY_ID;
    }

    /**
     * compare Entity objects for equality
     * @param o object to compare with
     * @return if objects are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return ENTITY_ID == entity.ENTITY_ID;
    }

    /**
     * get entity hash code
     * @return object's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(ENTITY_ID);
    }
}