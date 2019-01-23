package com.labuts.finalproject.service;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EnumConverterTest {
    @DataProvider
    public Object[][] enumData(){
        return new Object[][]{
                {"add-request-command", "ADD_REQUEST_COMMAND"},
                {"login-command", "LOGIN_COMMAND"},
                {"ReAd-DATA", "READ_DATA"},
                {"RegisTratioN", "REGISTRATION"},
                {"-", "_"},
                {"logout_commanD", "LOGOUT_COMMAND"}
        };
    }

    @Test(dataProvider = "enumData")
    public void convertTest(String string, String expected){
        String actual = EnumConverter.convert(string);
        Assert.assertEquals(actual, expected);
    }
}
