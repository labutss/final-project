package com.labuts.finalproject.command.impl.base;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.service.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Create EmptyCommand from Command
 */
public class EmptyCommand extends Command {
    private static final String ERROR_PAGE = "path.page.error";

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getProperty(ERROR_PAGE);
    }

    @Override
    public void storeData(HttpServletRequest request, String page) {}
}
