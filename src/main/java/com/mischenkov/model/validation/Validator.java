package com.mischenkov.model.validation;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Validator {

    private static final Logger LOG = Logger.getLogger(Validator.class);

    private List<Boolean> checkList;

    public Validator() {
        checkList = new ArrayList<>();
    }

    public Validator append(Boolean item) {
        checkList.add(item);
        return this;
    }

    public Validator append(Supplier<Boolean> item) {
        Objects.requireNonNull(item, "append(Supplier<Boolean> item), \"item\" is null");
        checkList.add( item.get() );
        return this;
    }

    public Validator append(Integer i,Function<Integer, Boolean> item) {
        Objects.requireNonNull(item, "append(Integer i,Function<Integer, Boolean> item), \"item\" is null");
        checkList.add( item.apply(i) );
        return this;
    }

    public Validator append(String s,Function<String, Boolean> item) {
        Objects.requireNonNull(item, "append(String s,Function<String, Boolean> item), \"item\" is null");
        checkList.add( item.apply(s) );
        return this;
    }

    public boolean validate() {
        for (boolean element: checkList) {
            if (!element) {
                checkList.clear();
                return false;
            }
        }

        checkList.clear();
        return true;
    }

    public void ifInvalidate(Command command) {
        for (boolean element: checkList) {
            if (!element) {
                checkList.clear();
                try {
                    command.run();
                } catch (Exception e) {
                    LOG.warn("ifInvalidate has some problem.", e);
                }
            }
        }
    }

    public static boolean match(String regPattern, String text) {
        Objects.requireNonNull(regPattern, "match(String text, String regPattern), \"regPattern\" is null.");
        if (text == null) {
            return false;
        }

        return Pattern.matches(regPattern, text);
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static Function<String, Boolean> isNumber = s -> {
        try {
            Integer.valueOf(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    };

    public static Function<String, Boolean> isNotNumber = s -> {
        try {
            Integer.valueOf(s);
            return false;
        } catch (Exception e) {
            return true;
        }
    };

    public interface Command {
        void run() throws ServletException, IOException;
    }
}
