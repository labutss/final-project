package com.labuts.finalproject.command;

import com.labuts.finalproject.command.impl.admin.*;
import com.labuts.finalproject.command.impl.base.*;
import com.labuts.finalproject.command.impl.client.*;
import com.labuts.finalproject.service.EnumConverter;

import java.util.EnumMap;

/**
 * CommandMap class is used to define commands
 */
public class CommandMap {
    /**
     * empty command
     */
    private static Command emptyCommand = new EmptyCommand();
    /**
     * command map
     */
    private EnumMap<CommandType, Command> commandMap = new EnumMap<CommandType, Command>(CommandType.class){
        {
            this.put(CommandType.LOGIN_COMMAND, new LoginCommand());
            this.put(CommandType.LOGOUT_COMMAND, new LogoutCommand());
            this.put(CommandType.REGISTRATION_COMMAND, new RegistrationCommand());
            this.put(CommandType.GO_TO_ACCOMMODATION_COMMAND, new GoToAccommodationCommand());
            this.put(CommandType.GO_TO_EDIT_ACCOMMODATION_COMMAND, new GoToEditAccommodationCommand());
            this.put(CommandType.ADD_ACCOMMODATION_COMMAND, new AddAccommodationCommand());
            this.put(CommandType.EDIT_ACCOMMODATION_COMMAND, new EditAccommodationCommand());
            this.put(CommandType.GO_TO_ADD_REQUEST_COMMAND, new GoToAddRequestCommand());
            this.put(CommandType.VIEW_ALL_USERS_COMMAND, new ViewAllUsersCommand());
            this.put(CommandType.CHANGE_ACCOMMODATION_AVAILABILITY_COMMAND, new ChangeAccommodationAvailabilityCommand());
            this.put(CommandType.ADMIN_VIEW_ALL_ACCOMMODATIONS_COMMAND, new AdminViewAllAccommodationsCommand());
            this.put(CommandType.ADD_REQUEST_COMMAND, new AddRequestCommand());
            this.put(CommandType.CLIENT_VIEW_ALL_ACCOMMODATIONS_COMMAND, new ClientViewAllAccommodationsCommand());
            this.put(CommandType.CLIENT_VIEW_ALL_REQUESTS_COMMAND, new ClientViewAllRequestsCommand());
            this.put(CommandType.ADMIN_VIEW_ALL_REQUESTS_COMMAND, new AdminViewAllRequestsCommand());
            this.put(CommandType.ADMIN_VIEW_NEW_REQUESTS_COMMAND, new AdminViewNewRequestsCommand());
            this.put(CommandType.ADMIN_VIEW_CLIENTS_REQUESTS_COMMAND, new AdminViewClientsRequestsCommand());
            this.put(CommandType.CHANGE_LANGUAGE_COMMAND, new ChangeLanguageCommand());
            this.put(CommandType.TOP_UP_BALANCE_COMMAND, new TopUpBalanceCommand());
            this.put(CommandType.SET_REQUEST_BILL_AND_DATE_COMMAND, new SetRequestBillAndDateCommand());
            this.put(CommandType.PAY_REQUEST_BILL_COMMAND, new PayRequestBillCommand());
            this.put(CommandType.RATE_ACCOMMODATION_COMMAND, new RateAccommodationCommand());
            this.put(CommandType.GO_TO_PREVIOUS_PAGE_COMMAND, new GoToPreviousPageCommand());
        }
    };

    /**
     * command map instance
     */
    private static CommandMap instance = new CommandMap();

    /**
     * private constructor
     */
    private CommandMap(){}

    /**
     * get instance
     * @return command map instance
     */
    public static CommandMap getInstance(){ return instance;}

    /**
     * get command by command name
     * @param commandName command name
     * @return command
     */
    public Command get(String commandName) {
        Command command;
        if (commandName == null) {
            command = emptyCommand;
        }else {
            try {
                CommandType key = CommandType.valueOf(EnumConverter.convert(commandName));
                command = commandMap.getOrDefault(key, emptyCommand);
            }catch (IllegalArgumentException e){
                command = emptyCommand;
            }
        }
        return command;
    }
}
