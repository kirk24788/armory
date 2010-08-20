/*
 * XmlDataWrapper.java 15.03.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import de.mancino.exceptions.ArmoryConnectionException;

public class XmlDataWrapper {

    private final Document xmlData;

    public XmlDataWrapper(Document xmlData) {
        this.xmlData = xmlData;
    }

    protected static Document executeXmlQuery(final String armoryUrl) throws ArmoryConnectionException {
        final GetMethod armoryRequest = new GetMethod("http://eu.wowarmory.com/" + armoryUrl);
        final HttpClient httpClient = new HttpClient();
        try {
            httpClient.executeMethod(armoryRequest);
            return DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(armoryRequest.getResponseBodyAsStream());
        } catch (Exception e) {
            throw new ArmoryConnectionException(e);
        } finally {
            armoryRequest.releaseConnection();
        }
    }

    protected String getTextContent(String xPath) {
        // return search(xmlCharSheet.getFirstChild(), xPath, false).getTextContent();
        Node node = search(xmlData.getFirstChild(), xPath, true);
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
