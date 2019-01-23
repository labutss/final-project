package com.labuts.finalproject.validation;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserValidatorTest {
    private UserValidator userValidator = new UserValidator();

    @DataProvider
    public Object[][] usernameData(){
        return new Object[][]{
                {"", false},
                {"a8", false},
                {"ajsslajsaljasjllsjasjlaljsljsljsaljsljasljsaljjslajsl", false},
                {"i n v a l i d", false},
                {"---", false},
                {"ajajaj))))", false},
                {"111", false},
                {"@@@GGGG", false},
                {"AAA", false},
                {"\n", false},
                {"itistoolongtobesomeonesusername", false},
                {"useless", true},
                {"Arina123", true},
                {"username", true},
                {"1234user", true},
                {"AAAAAAAAAA", true},
                {"labutss", true}
        };
    }

    @Test(dataProvider = "usernameData")
    public void testUsername(String username, boolean expected){
        boolean actual = userValidator.isValidUsername(username);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] passwordData(){
        return new Object[][]{
                {"", false},
                {"1234-+", false},
                {"ajsslajsaljasjllsjasjlaljsljsljsaljslja", false},
                {"i n v a l i d", false},
                {"---", false},
                {"ajajaj))))", false},
                {"111", false},
                {"12345", true},
                {"AAAA", true},
                {"\n", false},
                {"nIcEpAsSwOrD", true},
                {"CoolPassword11", true},
                {"Arina123", true},
                {".a.a.a.a.a.", false},
                {"FFF!", false}
        };
    }

    @Test(dataProvider = "passwordData")
    public void testPassword(String password, boolean expected){
        boolean actual = userValidator.isValidPassword(password);
        Assert.assertEquals(actual, expected);
    }
}
