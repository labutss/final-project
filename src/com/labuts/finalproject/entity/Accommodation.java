package com.labuts.finalproject.entity;


import java.util.Objects;

/**
 * Create class Accommodation from Entity
 * Accommodation class is used for storing accommodation data
 */
public class Accommodation extends Entity {
    /**
     * accommodation name
     */
    private String accommodationName;
    /**
     * accommodation description
     */
    private String accommodationDescription;
    /**
     * accommodation cost
     */
    private double accommodationCost;
    /**
     * accommodation availability
     */
    private boolean isAvailable;
    /**
     * mark for accommodation
     */
    private double accommodationMark;

    /**
     * Constructor to initialize Accommodation object
     * @param entityId entity id
     * @param accommodationName initial accommodation name
     * @param accommodationDescription initial accommodation description
     * @param accommodationCost initial accommodation cost
     * @param isAvailable initial accommodation availability
     * @param accommodationMark initial mark for accommodation
     */
    public Accommodation(int entityId, String accommodationName,
                         String accommodationDescription, double accommodationCost,
                         boolean isAvailable, double accommodationMark) {
        super(entityId);
        this.accommodationName = accommodationName;
        this.accommodationDescription = accommodationDescription;
        this.accommodationCost = accommodationCost;
        this.isAvailable = isAvailable;
        this.accommodationMark = accommodationMark;
    }

    /**
     * Constructor to initialize Accommodation object
     * @param entityId entity id
     */
    public Accommodation(int entityId){
        super(entityId);
    }

    /**
     * get accommodation name
     * @return accommodation name
     */
    public String getAccommodationName() {
        return accommodationName;
    }

    /**
     * set accommodation name
     * @param accommodationName new accommodation name
     */
    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    /**
     * get accommodation description
     * @return accommodation description
     */
    public String getAccommodationDescription() {
        return accommodationDescription;
    }

    /**
     * set accommodation description
     * @param accommodationDescription new accommodation description
     */
    public void setAccommodationDescription(String accommodationDescription) {
        this.accommodationDescription = accommodationDescription;
    }

    /**
     * get accommodation cost
     * @return accommodation cost
     */
    public double getAccommodationCost() {
        return accommodationCost;
    }

    /**
     * set accommodation cost
     * @param accommodationCost new accommodation cost
     */
    public void setAccommodationCost(double accommodationCost) {
        this.accommodationCost = accommodationCost;
    }

    /**
     * get accommodation availability
     * @return accommodation availability
     */
    public boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * set accommodation availability
     * @param available new accommodation availability
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * get mark for accommodation
     * @return mark for accommodation
     */
    public double getAccommodationMark() {
        return accommodationMark;
    }

    /**
     * set mark for accommodation
     * @param accommodationMark new mark for accommodation
     */
    public void setAccommodationMark(double accommodationMark) {
        this.accommodationMark = accommodationMark;
    }

    /**
     * compare Accommodation objects for equality
     * @param o object to compare with
     * @return if objects are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Accommodation that = (Accommodation) o;
        return Double.compare(that.accommodationCost, accommodationCost) == 0 &&
                isAvailable == that.isAvailable &&
                Double.compare(that.accommodationMark, accommodationMark) == 0 &&
                Objects.equals(accommodationName, that.accommodationName) &&
                Objects.equals(accommodationDescription, that.accommodationDescription);
    }

    /**
     * get accommodation's hash code
     * @return object's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accommodationName, accommodationDescription, accommodationCost, isAvailable, accommodationMark);
    }
}

