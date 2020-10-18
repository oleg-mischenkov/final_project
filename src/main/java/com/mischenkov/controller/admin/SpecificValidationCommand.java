package com.mischenkov.controller.admin;

import com.mischenkov.model.validation.ValidPattern;
import com.mischenkov.model.validation.Validator;
import org.apache.log4j.Logger;

import java.util.Enumeration;

import static com.mischenkov.model.validation.Validator.isNull;
import static com.mischenkov.model.validation.Validator.match;

public abstract class SpecificValidationCommand extends AdminAbstractCommand {

    private static final Logger LOG = Logger.getLogger(SpecificValidationCommand.class);

    // request attribute
    public static final String REQ_ATR_PATTERN_ERROR_MSG = UsersCommand.REQ_ATR_PATTERN_ERROR_MSG;

    protected String obtainRequestParam(String searchPatternTitle, int languageId) {
        Enumeration<String> paramNamesEnumeration = req.getParameterNames();
        String result = "";

        while ( paramNamesEnumeration.hasMoreElements() ) {
            String parName = paramNamesEnumeration.nextElement();

            String expectedName = String.format(searchPatternTitle, languageId);

            if (parName.equals(expectedName)) {
                result = req.getParameter(parName);
                break;
            }
        }

        return result;
    }

    protected int validateNumber(String number) {
        int result = -1;
        Validator validator = new Validator();

        boolean flag = validator
                .append( !isNull(number) )
                .append( match(ValidPattern.NUMBER, number) )
                .validate();

        if ( flag ) {
            result = Integer.valueOf(number);
        }

        return result;
    }

    protected void reqParamValidate(String reqParam, String pattern) {

        LOG.debug("validate " + reqParam + " - START");

        Validator validator = new Validator();
        validator
                .append( !isNull(reqParam) )
                .append( match(pattern, reqParam) )
                .ifInvalidate( invalidateCommand );

        LOG.debug("validate " + reqParam + " - END");
    }

    protected Validator.Command invalidateCommand = ()-> {
        req.setAttribute( REQ_ATR_PATTERN_ERROR_MSG, "Some parameter was wrong" );
        getMake();
    };

}
