package com.labuts.finalproject.validation;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BaseValidatorTest {
    private BaseValidator baseValidator = new BaseValidator();

    @DataProvider
    public Object[][] dateInformation(){
        return new Object[][]{
                {"05", false},
                {"01/02/2018", true},
                {"12/25/2019", true},
                {"13/11/1999", false},
                {"10/09/199", false},
                {"05/06/2007", true},
                {"02/30/2003", false},
                {"19/19/1789", false},
                {"12/56/2003", false},
                {"05/32/1999", false},
                {"05/06/1999", true},
                {"08/27/2019", true},
                {"01/01/2017", true},
                {"08/18/1977", true},
                {"31/2/2018", false},
                {"akka", false}
        };
    }

    @Test(dataProvider = "dateInformation")
    public void testAccommodationCost(String date, boolean expected){
        boolean actual = baseValidator.isValidDate(date);
        Assert.assertEquals(actual, expected);
    }
}
