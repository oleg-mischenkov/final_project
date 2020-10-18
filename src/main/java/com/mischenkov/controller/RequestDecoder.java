package com.mischenkov.controller;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public interface RequestDecoder {

    Logger LOG = Logger.getLogger(RequestDecoder.class);

    static String decodeRequest(String requestString) {
        String result = requestString;
        try {
            result = new String(requestString.getBytes("ISO-8859-1"),"UTF8");
        } catch (UnsupportedEncodingException e) {
            LOG.warn("Cant decoding some string");
        }
        return result;
    }
}
