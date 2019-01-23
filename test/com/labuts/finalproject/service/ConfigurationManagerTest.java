package com.labuts.finalproject.service;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ConfigurationManagerTest {
    @DataProvider
    public Object[][] managerData(){
        return new Object[][]{
                {"path.page.error", "/jsp/errorpage.jsp"},
                {"path.page.client.main", "/jsp/client/mainpage.jsp"},
                {"path.page.admin.main", "/jsp/admin/mainpage.jsp"},
                {"path.page.login", "/jsp/login.jsp"},
                {"path.page.accommodation", "/jsp/admin/accommodation.jsp"},
                {"path.page.request", "/jsp/client/request.jsp"},
                {"path.page.first.page", "/jsp/firstpage.jsp"},
                {"path.page.all.users", "/jsp/admin/allusers.jsp"},
                {"path.page.client.requests", "/jsp/client/usersrequests.jsp"},
                {"path.page.admin.requests", "/jsp/admin/requests.jsp"}
        };
    }

    @Test(dataProvider = "managerData")
    public void testGetProperty(String string, String expected){
        String actual = ConfigurationManager.getProperty(string);
        Assert.assertEquals(actual, expected);
    }
}
