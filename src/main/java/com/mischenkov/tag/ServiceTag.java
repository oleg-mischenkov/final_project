package com.mischenkov.tag;

import com.mischenkov.entity.Service;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class ServiceTag extends TagSupport {

    private static final Logger LOG = Logger.getLogger(ServiceTag.class);

    private List<Service> services;

    public void setServices(List<Service> serviceList) {
        services = serviceList;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuffer stringBuffer = new StringBuffer();

        for (Service service: services) {
            stringBuffer
                    .append("<div class=\"col-4 mb-4 general-menu\">")
                    .append("<div class=\"card text-white bg-info\">")
                    .append("<a href=\"service?service=" + service.getId() + "\">")
                    .append("<div class=\"card-body\" style=\"text-align: center\">")
                    .append(service.getTitle())
                    .append("</div>")
                    .append("</a>")
                    .append("</div>")
                    .append("</div>");
        }

        try {
            pageContext.getOut().write( stringBuffer.toString() );
        } catch (IOException e) {
            LOG.warn("Cant build a ServiceTag.", e);
            throw  new JspException("Cant build a ServiceTag.", e);
        }

        return SKIP_BODY;
    }
}
