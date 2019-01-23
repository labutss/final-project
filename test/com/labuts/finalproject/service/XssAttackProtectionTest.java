package com.labuts.finalproject.service;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class XssAttackProtectionTest {
    @DataProvider
    public Object[][] data(){
        return new Object[][]{
                {"<script>something inside</script>", "something inside"},
                {"<script>lalala", "lalala"},
                {"here everything is okay", "here everything is okay"},
                {"something<script> more something</script>", "something more something"},
                {"<script kill me /script>", "<script kill me /script>"},
                {"lalala </script>", "lalala "}
        };
    }

    @Test(dataProvider = "data")
    public void protectTest(String string, String expected){
        String actual = XssAttackProtection.preventAttack(string);
        Assert.assertEquals(actual, expected);
    }
}
