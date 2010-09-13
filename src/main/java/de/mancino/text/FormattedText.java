/*
 * FormattedText.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.text;

import java.net.URL;


public abstract class FormattedText {
    private FormattedText appendedText = null;

    @Override
    public final String toString() {
        if(appendedText!=null) {
            return getPlainText() + appendedText.toString();
        } else {
            return getPlainText();
        }
    }

    public final String toHtml() {
        if(appendedText!=null) {
            return getHtmlText() + appendedText.toHtml();
        } else {
            return getHtmlText();
        }
    }

    protected abstract String getPlainText();

    protected abstract String getHtmlText();

    public FormattedText append(final FormattedText textToAppend) {
        if(this.appendedText==null) {
            this.appendedText = textToAppend;
        } else {
            FormattedText oldAppendedText = this.appendedText;
            this.appendedText = textToAppend;
            this.appendedText.append(oldAppendedText);
        }
        return this;
    }

    public FormattedText text(final String text) {
        return append(new StandardText(text));
    }

    public FormattedText bold(final String text) {
        return append(new BoldText(text));
    }

    public FormattedText italic(final String text) {
        return append(new ItalicText(text));
    }

    public FormattedText link(final URL url, final String text) {
        return append(new LinkedText(url, text));
    }
    public static FormattedText create() {
        // TODO Auto-generated method stub
        return new StandardText("");
    }
}
