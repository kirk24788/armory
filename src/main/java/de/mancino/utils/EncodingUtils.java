/*
 * EncodingUtils.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EncodingUtils {
    public static String urlEncode(final String s, final String enc) {
        try {
            return URLEncoder.encode(s, enc);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
