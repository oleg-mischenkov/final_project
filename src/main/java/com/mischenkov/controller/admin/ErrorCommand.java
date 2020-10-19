package com.mischenkov.controller.admin;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 *  The handler accepts all unrecognized commands.
 */
public class ErrorCommand extends AdminAbstractCommand {
    @Override
    public void make() throws IOException, ServletException {
        System.out.println("Error");
    }
}
