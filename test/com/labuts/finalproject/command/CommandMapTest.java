package com.labuts.finalproject.command;

import com.labuts.finalproject.command.impl.admin.*;
import com.labuts.finalproject.command.impl.base.*;
import com.labuts.finalproject.command.impl.client.AddRequestCommand;
import com.labuts.finalproject.command.impl.client.ClientViewAllAccommodationsCommand;
import com.labuts.finalproject.command.impl.client.GoToAddRequestCommand;
import com.labuts.finalproject.command.impl.client.TopUpBalanceCommand;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CommandMapTest {
    @DataProvider
    public Object[][] commandData(){
        return new Object[][]{
                {"ahahah", new EmptyCommand()},
                {"change-accommodation-availability-command", new ChangeAccommodationAvailabilityCommand()},
                {"add-request-command", new AddRequestCommand()},
                {"login-command", new LoginCommand()},
                {"registration-command", new RegistrationCommand()},
                {"admin-view-new-requests-command", new AdminViewNewRequestsCommand()},
                {"view-all-users-command", new ViewAllUsersCommand()},
                {"go-to-accommodation-command", new GoToAccommodationCommand()},
                {"client-view-all-accommodations-command", new ClientViewAllAccommodationsCommand()},
                {"top-up-balance-command", new TopUpBalanceCommand()},
                {"go-to-add-request-command", new GoToAddRequestCommand()},
                {"set-request-bill-and-date-command", new SetRequestBillAndDateCommand()},
                {"logout-command", new LogoutCommand()},
                {"change-language-command", new ChangeLanguageCommand()},
                {"fpfpfpfp", new EmptyCommand()}
        };
    }

    @Test(dataProvider = "commandData")
    public void testGettingCommand(String commandName, Command commandExpected){
        Command command = CommandMap.getInstance().get(commandName);
        Assert.assertEquals(command, commandExpected);
    }
}
