package com.mischenkov.controller.admin;

import javax.servlet.ServletException;
import java.io.IOException;

public class IndexCommand extends AdminAbstractCommand {
    @Override
    public void make() throws IOException, ServletException {
        forward("admin");
    }
}
