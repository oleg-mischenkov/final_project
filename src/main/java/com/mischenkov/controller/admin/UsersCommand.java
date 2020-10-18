package com.mischenkov.controller.admin;

import com.mischenkov.controller.CommonControllerValues;
import com.mischenkov.controller.RegisterController;
import com.mischenkov.entity.Paginator;
import com.mischenkov.entity.User;
import com.mischenkov.model.dbservice.MySqlUserDbService;
import com.mischenkov.model.dbservice.UserDbService;
import com.mischenkov.model.exception.DBException;
import com.mischenkov.model.validation.ValidPattern;
import com.mischenkov.model.validation.Validator;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

import static com.mischenkov.model.validation.Validator.*;

public class UsersCommand extends AdminAbstractCommand {

    private static final int DEFAULT_ROW_COUNT = CommonControllerValues.DEFAULT_ROW_COUNT;
    private static final int DEFAULT_START_POSITION = CommonControllerValues.DEFAULT_START_POSITION;
    // request parameters
    private static final String REQ_PARAM_COUNT = CommonControllerValues.REQ_PARAM_COUNT;
    private static final String REQ_PARAM_POSITION = CommonControllerValues.REQ_PARAM_POSITION;
    private static final String REQ_PARAM_USER_ID = "user";
    private static final String REQ_PARAM_USER_ACTIVE = "active";
    // for POST
    public static final String REQ_PARAM_LOGIN = RegisterController.REQ_PARAM_LOGIN;
    public static final String REQ_PARAM_FIRST_NAME = RegisterController.REQ_PARAM_FIRST_NAME;
    public static final String REQ_PARAM_LAST_NAME = RegisterController.REQ_PARAM_LAST_NAME;
    public static final String REQ_PARAM_EMAIL = RegisterController.REQ_PARAM_EMAIL;
    public static final String REQ_PARAM_GENDER = RegisterController.REQ_PARAM_GENDER;
    public static final String REQ_PARAM_PASSWORD = RegisterController.REQ_PARAM_PASSWORD;

    public static final String REQ_ATR_USER_MAP = "userMap";
    public static final String REQ_ATR_START_POSITION = "position";
    public static final String REQ_ATR_COUNT = "currentCount";
    public static final String REQ_ATR_PAGINATION_URLS = CommonControllerValues.REQ_ATR_PAGINATION_URLS;

    public static final String REQ_ATR_PATTERN_ERROR_MSG = CommonControllerValues.REQ_ATR_PATTERN_ERROR_MSG;
    public static final String REQ_ATR_PATTERN_ERROR_FIELD = CommonControllerValues.REQ_ATR_PATTERN_ERROR_FIELD;
    public static final String REQ_ATR_USER_SAVE_ERROR = "userSaveError";

    @Override
    public void postMake() throws IOException, ServletException {

        String login = req.getParameter(REQ_PARAM_LOGIN);
        reqParamValidate(login, ValidPattern.LOGIN, REQ_PARAM_LOGIN);

        String firstName = req.getParameter(REQ_PARAM_FIRST_NAME);
        reqParamValidate(firstName, ValidPattern.NAME, REQ_PARAM_FIRST_NAME);

        String lastName = req.getParameter(REQ_PARAM_LAST_NAME);
        reqParamValidate(lastName, ValidPattern.NAME, REQ_PARAM_LAST_NAME);

        String email = req.getParameter(REQ_PARAM_EMAIL);
        reqParamValidate(email, ValidPattern.EMAIL, REQ_PARAM_EMAIL);

        String gender = req.getParameter( REQ_PARAM_GENDER );
        reqParamValidate(gender, "^[mMfF]$", REQ_PARAM_GENDER);

        String password = req.getParameter( REQ_PARAM_PASSWORD );
        reqParamValidate(password, ValidPattern.PASSWORD, REQ_PARAM_PASSWORD);

        User user = new User();
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setGender(gender);

        try {
            UserDbService service = new MySqlUserDbService();
            service.saveUser(user);
        } catch (DBException e) {
            req.setAttribute(REQ_ATR_USER_SAVE_ERROR, "");
            req.setAttribute(REQ_ATR_PATTERN_ERROR_MSG, "System has been same User");
            collectUserAttr();
        }

        getMake();
    }

    private void reqParamValidate(String reqParam, String pattern, String fieldName) {
        req.setAttribute(REQ_ATR_PATTERN_ERROR_FIELD, fieldName);

        Validator validator = new Validator();
        validator
                .append( !isNull(reqParam) )
                .append( match(pattern, reqParam) )
                .ifInvalidate( invalidateCommand );

        req.setAttribute(REQ_ATR_PATTERN_ERROR_FIELD, null);
    }

    private Validator.Command invalidateCommand = ()-> {
        collectUserAttr();
        req.setAttribute( REQ_ATR_PATTERN_ERROR_MSG, "string pattern exception" );

        getMake();
    };

    private void collectUserAttr() {
        req.setAttribute( REQ_PARAM_LOGIN, req.getParameter(REQ_PARAM_LOGIN) );
        req.setAttribute( REQ_PARAM_FIRST_NAME, req.getParameter(REQ_PARAM_FIRST_NAME) );
        req.setAttribute( REQ_PARAM_LAST_NAME, req.getParameter(REQ_PARAM_LAST_NAME) );
        req.setAttribute( REQ_PARAM_EMAIL, req.getParameter(REQ_PARAM_EMAIL) );
        req.setAttribute( REQ_PARAM_PASSWORD, req.getParameter(REQ_PARAM_PASSWORD) );
    }

    @Override
    public void getMake() throws IOException, ServletException {
        String preCount = req.getParameter(REQ_PARAM_COUNT);
        String prePosition = req.getParameter(REQ_PARAM_POSITION);
        int count;
        int position;
        int totalUser = -1;

        if ( (preCount != null) && (prePosition != null) ) {
            numberValidation(preCount, prePosition);
            count = Integer.valueOf(preCount);
            position = Integer.valueOf(prePosition);
        } else {
            count = DEFAULT_ROW_COUNT;
            position = DEFAULT_START_POSITION;
        }

        UserDbService dbService = new MySqlUserDbService();

        String preUserId = req.getParameter(REQ_PARAM_USER_ID);
        String preActive = req.getParameter(REQ_PARAM_USER_ACTIVE);
        if ( (preUserId != null) && (preActive != null)) {
            numberValidation(preUserId, preActive);

            int userId = Integer.valueOf(preUserId);
            int active = Integer.valueOf(preActive);

            try {
                dbService.updateUserActive(userId, active);
            } catch (DBException e) {
                e.printStackTrace();
                forward("error");
            }
        }

        Map<User, String> map = null;

        try {
            map = dbService.getUserRoleNameMap(position, count);
            totalUser = dbService.count();
        } catch (DBException e) {
            e.printStackTrace();
            forward("error");
        }

        req.setAttribute(REQ_ATR_USER_MAP, map);
        req.setAttribute(REQ_ATR_START_POSITION, position);
        req.setAttribute(REQ_ATR_COUNT, count);
        req.setAttribute(REQ_ATR_PAGINATION_URLS, Paginator.getPages(count, totalUser));

        forward("users");
    }

    private void numberValidation(String... param) {
        Validator validator = new Validator();

        for (String par: param) {
            validator.append( par, isNumber );
        }

        validator.ifInvalidate(
                ()-> forward("error")
        );
    }

}