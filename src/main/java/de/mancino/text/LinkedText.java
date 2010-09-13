/*
 * LinkedText.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.text;

import java.net.URL;

public class LinkedText extends StandardText {
    protected final URL url;
    public LinkedText(final URL url, final String text) {
        super(text);
        this.url = url;
    }

    @Override
    public String getHtmlText() {
        return "<a href=\"" + url + "\">" + super.getHtmlText() + "</i>";
    }
}
