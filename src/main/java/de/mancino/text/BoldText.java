/*
 * BoldText.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.text;


public class BoldText extends StandardText {
    public BoldText(final String text) {
        super(text);
    }

    @Override
    public String getHtmlText() {
        return "<b>" + super.getHtmlText() + "</b>";
    }
}