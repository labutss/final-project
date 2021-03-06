package com.labuts.finalproject.command;

/**
 * Command type enumeration
 */
public enum CommandType {
    LOGIN_COMMAND, LOGOUT_COMMAND, REGISTRATION_COMMAND, CHANGE_LANGUAGE_COMMAND, GO_TO_PREVIOUS_PAGE_COMMAND,

    GO_TO_ACCOMMODATION_COMMAND, GO_TO_EDIT_ACCOMMODATION_COMMAND, ADD_ACCOMMODATION_COMMAND, EDIT_ACCOMMODATION_COMMAND,
    VIEW_ALL_USERS_COMMAND, CHANGE_ACCOMMODATION_AVAILABILITY_COMMAND, ADMIN_VIEW_ALL_ACCOMMODATIONS_COMMAND,
    ADMIN_VIEW_ALL_REQUESTS_COMMAND, ADMIN_VIEW_NEW_REQUESTS_COMMAND, ADMIN_VIEW_CLIENTS_REQUESTS_COMMAND,
    SET_REQUEST_BILL_AND_DATE_COMMAND,

    GO_TO_ADD_REQUEST_COMMAND, ADD_REQUEST_COMMAND, CLIENT_VIEW_ALL_ACCOMMODATIONS_COMMAND,
    CLIENT_VIEW_ALL_REQUESTS_COMMAND, TOP_UP_BALANCE_COMMAND, PAY_REQUEST_BILL_COMMAND, RATE_ACCOMMODATION_COMMAND
}
