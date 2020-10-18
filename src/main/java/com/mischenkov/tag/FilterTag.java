package com.mischenkov.tag;

import com.mischenkov.entity.Service;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class FilterTag extends TagSupport {

    private static final Logger LOG = Logger.getLogger(FilterTag.class);

    private String id;
    private Service service;

    private String optionNatural;
    private String optionPriceHigh;
    private String optionPriceLow;
    private String optionTitleA;
    private String optionTitleZ;

    public FilterTag() {
        id = "";
        service = new Service();
        optionNatural =     "";
        optionPriceHigh =   "";
        optionPriceLow =    "";
        optionTitleA =      "";
        optionTitleZ =      "";
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setOptionNatural(String optionNatural) {
        this.optionNatural = optionNatural;
    }

    public void setOptionPriceHigh(String optionPriceHigh) {
        this.optionPriceHigh = optionPriceHigh;
    }

    public void setOptionPriceLow(String optionPriceLow) {
        this.optionPriceLow = optionPriceLow;
    }

    public void setOptionTitleA(String optionTitleA) {
        this.optionTitleA = optionTitleA;
    }

    public void setOptionTitleZ(String optionTitleZ) {
        this.optionTitleZ = optionTitleZ;
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer
                .append("<form id=\"" + id + "\" action=\"service?service=" + service.getId() + "\" method=\"GET\">")
                .append("<select class=\"form-control form-control-sm\" id=\"lostSort\" name=\"sort-field\">")
                .append("<option id=\"s-1\" value=\"&sort-field=tariff_id&sort-direction=ASC\">" + optionNatural + "</option>")
                .append("<option id=\"s-2\" value=\"&sort-field=price&sort-direction=DESC\" class=\"desc\">" + optionPriceHigh + "</option>")
                .append("<option id=\"s-3\" value=\"&sort-field=price&sort-direction=ASC\">" + optionPriceLow + "</option>")
                .append("<option id=\"s-4\" value=\"&sort-field=title&sort-direction=ASC\">" + optionTitleA + "</option>")
                .append("<option id=\"s-5\" value=\"&sort-field=title&sort-direction=DESC\" class=\"desc\">" + optionTitleZ + "</option>")
                .append("</select>")
                .append("</form>");

        try {
            pageContext.getOut().write( stringBuffer.toString() );
        } catch (IOException e) {
            LOG.warn("Cant build a SortTag.", e);
            throw  new JspException("Cant build a SortTag.", e);
        }

        return SKIP_BODY;
    }
}
