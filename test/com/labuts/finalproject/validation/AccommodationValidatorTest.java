package com.labuts.finalproject.validation;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AccommodationValidatorTest {
    private AccommodationValidator accommodationValidator = new AccommodationValidator();

    @DataProvider
    public Object[][] accommodationNameData(){
        return new Object[][]{
                {"", false},
                {"a8", false},
                {"ajsslajsaljasjllsjasjlaljsljsljsaljsljasljsaljjslajsl", false},
                {"i n v a l i d", true},
                {"---", false},
                {"ajajaj))))", false},
                {"1111 ", false},
                {"@@@GGGG", false},
                {"AAA", false},
                {"\n", false},
                {"Accommodation\nname", true},
                {"great", true},
                {"slddsald;jasjd;ajda;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;", false},
                {"user, name", false},
                {"1234 accommodation", false},
                {"AAAAAAAAAA", true},
                {"Hair-cut", true},
                {"Best accommodation", true},
                {"First accommodation", true}
        };
    }

    @Test(dataProvider = "accommodationNameData")
    public void testAccommodationName(String accommodationName, boolean expected){
        boolean actual = accommodationValidator.isValidAccommodationName(accommodationName);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] accommodationDescriptionData(){
        return new Object[][]{
                {"Accommodation description" , true},
                {"Too short", false},
                {"We should test accommodations descriptions. Very carefully", true},
                {"I LOVE MY LIFE", true},
                {"ggg", false},
                {"", false},
                {"Accommodation description, should be from (10 to 100) sym-bols", true},
                {"Akaka \n ahahaha", true}
        };
    }

    @Test(dataProvider = "accommodationDescriptionData")
    public void testAccommodationDescription(String accommodationDescription, boolean expected){
        boolean actual = accommodationValidator.isValidAccommodationDescription(accommodationDescription);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] accommodationCostData(){
        return new Object[][]{
                {"-1,0", false},
                {"ajjaja", false},
                {"-4.5", false},
                {"70", true},
                {"123.67", true},
                {"11222222.999", false},
                {"111111111111111111111111111", false},
                {"6.992929299292", false},
                {"0.8", true},
                {"-2222", false},
                {"123.62a", false},
                {"56.8", true},
                {"12552525252.9", false},
                {"11.55", true},
                {"172.999", false},
                {"-1.99", false},
                {"1.99", true},
                {"0.0", false},
                {"0.77", true},
                {"0", false},
                {"11.9", true},
                {".9", false},
                {"0.09", true},
                {"1.0", true}
        };
    }

    @Test(dataProvider = "accommodationCostData")
    public void testAccommodationCost(String cost, boolean expected){
        boolean actual = accommodationValidator.isValidAccommodationCost(cost);
        Assert.assertEquals(actual, expected);
    }
}
