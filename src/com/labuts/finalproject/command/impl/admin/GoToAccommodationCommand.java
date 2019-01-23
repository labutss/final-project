package com.labuts.finalproject.command.impl.admin;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.service.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Create GoToAccommodationCommand from Command
 */
public class GoToAccommodationCommand extends Command {
    private static final String ACCOMMODATION_PAGE = "path.page.accommodation";

    private static final String COMMAND_ATTRIBUTE = "command";
    private static final String COMMAND_VALUE = "add-accommodation-command";

    public GoToAccommodationCommand(){}

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(COMMAND_ATTRIBUTE, COMMAND_VALUE);
        return ConfigurationManager.getProperty(ACCOMMODATION_PAGE);
    }
}
