/*
 * StandardText.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.text;

import org.apache.commons.lang.StringEscapeUtils;

public class StandardText extends FormattedText {
    protected final String text;

    public StandardText(final String text) {
        this.text = text;
    }

    @Override
    public String getPlainText() {
        return text;
    }

    @Override
    public String getHtmlText() {
        return StringEscapeUtils.escapeXml(text).replace("\n", "</br>").replace(" ", "&nbsp;");
    }
}
