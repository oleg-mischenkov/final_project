package com.mischenkov.session;

/**
 *  Common session variables.
 */
public interface SessionVariable {

    String SESSION_USER_ATTR = "userSessionAttr";
    String SESSION_WALLET_ATTR = "walletSessionAttr";
    String SESSION_BASKET_ATTR = "basketSessionArrt";
    String SESSION_USER_ORDER ="orderSessionAttr";

    String SESSION_URL_SCOPE = "uslScopeSessionAttr";
    String SESSION_USER_PERMISSION = "userPermissionSessionAttr";
    String SESSION_ROLE_STATUS = "roleStatusSessionAttr";
    String SESSION_INFO_MSG = "infoMsgSessionAttr";
    String SESSION_ERROR_MSG = "infoMsgSessionAttr";
}
