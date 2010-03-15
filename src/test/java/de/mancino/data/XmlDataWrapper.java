/*
 * XmlDataWrapper.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.data;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XmlDataWrapper {

    private final Document xmlCharSheet;

    public XmlDataWrapper(Document xmlCharSheet) {
        this.xmlCharSheet = xmlCharSheet;
    }

    protected String getTextContent(String xPath) {
        // return search(xmlCharSheet.getFirstChild(), xPath, false).getTextContent();
        Node node = search(xmlCharSheet.getFirstChild(), xPath, true);
        if (node != null) {
            return node.getTextContent();
        } else {
            return null;
        }
    }

    private Node search(Node node, String xPath, boolean nullAllowed) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            Node returnNode = (Node) xpath.evaluate(xPath, node, XPathConstants.NODE);
            if(returnNode == null && !nullAllowed) {
                throw new RuntimeException("No Results for: " + xPath);
            }
            return returnNode;
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Invalid XPath used: " + xPath, e);
        }
    }
}
