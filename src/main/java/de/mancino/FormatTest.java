/*
 * FormatTest.java 13.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino;

import java.io.IOException;

import de.mancino.text.FormattedText;

public class FormatTest {

    public static void main(String[] args) throws IOException {
        FormattedText ft = FormattedText.create().text("te&eśxt").bold("fett").text("normal");
        System.err.println(ft.toString());
        System.err.println(ft.toHtml());
    }
}
