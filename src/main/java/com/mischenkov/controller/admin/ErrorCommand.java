package com.mischenkov.controller.admin;

import javax.servlet.ServletException;
import java.io.IOException;

public class ErrorCommand extends AdminAbstractCommand {
    @Override
    public void make() throws IOException, ServletException {
        System.out.println("Error");
    }
}
