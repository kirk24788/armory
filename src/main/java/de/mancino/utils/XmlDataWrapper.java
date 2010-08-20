/*
 * XmlDataWrapper.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.utils;



import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;


public class XmlDataWrapper {
    /**
     * Logger instance of this class.
     */
    private static final Log LOG = LogFactory.getLog(XmlDataWrapper.class);

    private final Document xmlData;

    public XmlDataWrapper(final Document xmlData) {
        this.xmlData = xmlData;

    }

    protected String getTextContent(String xPath) {
        // return search(xmlCharSheet.getFirstChild(), xPath, false).getTextContent();
        Element node = search(xPath, true);
        if (node != null) {
            return node.getText();
        } else {
            return null;
        }
    }

    protected Element search(String xPath, boolean nullAllowed) {
        try {
            XPath x = XPath.newInstance(xPath);
            Element element = (Element)x.selectSingleNode(xmlData);

            if(element == null && !nullAllowed) {
                throw new RuntimeException("No Results for: " + xPath);
            }
            return element;
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
