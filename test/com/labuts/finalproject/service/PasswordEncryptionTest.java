package com.labuts.finalproject.service;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PasswordEncryptionTest {
    @DataProvider
    public Object[][] passwordData() {
        return new Object[][]{
                {"123456789098765", "4d54497a4e4455324e7a67354d446b344e7a5931"},
                {"zzzzzzzzzzzzzzz", "656e7036656e7036656e7036656e7036656e7036"},
                {"PERFECTPAS5WORD", "55455653526b564456464242557a565854314a45"},
                {"1234", "4d54497a4e413d3d"},
                {"lemon18081997a", "62475674623234784f4441344d546b354e32453d"},
                {"AbCdEfGhIJ99", "51574a445a45566d5232684a536a6b35"},
                {"05061999", "4d4455774e6a45354f546b3d"},
                {"ilovejava", "61577876646d567159585a68"},
                {"1", "4d513d3d"},
                {"321a", "4d7a497859513d3d"},
                {"ahahahAH", "595768686147466f5155673d"},
                {"!!!!!", "495345684953453d"},
                {"agent006", "5957646c626e51774d44593d"},
                {"music80", "6258567a61574d344d413d3d"},
                {"r1a2i3N", "636a46684d6d6b7a54673d3d"},
                {"151515151515150", "4d5455784e5445314d5455784e5445314d545577"}
        };
    }

    @Test(dataProvider = "passwordData")
    public void testPassword(String password, String expected){
        String actual = PasswordEncryption.encryptPassword(password);
        Assert.assertEquals(actual, expected);
    }
}
