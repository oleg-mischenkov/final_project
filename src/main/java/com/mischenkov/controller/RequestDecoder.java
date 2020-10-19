package com.mischenkov.controller;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 *  Interface - utility for converting a string from ASCII to UTP-8.
 */
public interface RequestDecoder {

    Logger LOG = Logger.getLogger(RequestDecoder.class);

    /**
     *  The method recodes the request from the site into the UTF-8 encoding.
     *
     * @param requestString - the string to recode
     * @return  - string in UTF-8 format
     */
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
