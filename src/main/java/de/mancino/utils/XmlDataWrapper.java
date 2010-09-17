/*
 * XmlDataWrapper.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.utils;



import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Parent;
import org.jdom.xpath.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class XmlDataWrapper {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(XmlDataWrapper.class);

    private final Document xmlData;

    public XmlDataWrapper(final Document xmlData) {
        this.xmlData = xmlData;

    }

    protected String getTextContent(String xPath) {
        return getTextContent(xPath, xmlData);
    }

    protected String getTextContent(String xPath, Parent context) {
        // return search(xmlCharSheet.getFirstChild(), xPath, false).getTextContent();
        Object searchResult = search(xPath, context);
        String text = null;
        if (searchResult instanceof Element) {
            return ((Element)searchResult).getText();
        } else if (searchResult instanceof Attribute) {
            return ((Attribute)searchResult).getValue();
        }
        return text;
    }

    protected Object search(String xPath) {
        return search(xPath, xmlData);
    }

    protected Object search(String xPath, Parent context) {
        try {
            XPath x = XPath.newInstance(xPath);
            return x.selectSingleNode(context);
        } catch (JDOMException e) {
            throw new RuntimeException("Invalid XPath used: " + xPath, e);
        }
    }

    @SuppressWarnings("unchecked")
    protected List<Element> searchAll(final String xPath) {
        try {
            XPath x = XPath.newInstance(xPath);
            return x.selectNodes(xmlData);
        } catch (JDOMException e) {
            throw new RuntimeException("Invalid XPath used: " + xPath, e);
        }
    }
}
