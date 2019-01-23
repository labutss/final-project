package com.labuts.finalproject.command.impl.base;

import com.labuts.finalproject.command.Command;
import com.labuts.finalproject.service.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Create LogoutCommand from Command
 */
public class LogoutCommand extends Command {
    private static final String FIRST_PAGE = "path.page.first.page";

    @Override
    public String execute(HttpServletRequest request){
        String page = ConfigurationManager.getProperty(FIRST_PAGE);
        request.getSession().invalidate();
        return page;
    }
}
