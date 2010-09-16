/*
 * TabInfo.java 16.09.2010
 *
 * Copyright (c) 2010 1&1 Internet AG. All rights reserved.
 *
 * $Id$
 */
package de.mancino.armory.xml.tabinfo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Example Snippet:
 * <tabInfo subTab="profile" tab="character" tabGroup="character" tabUrl="r=Forscherliga&amp;cn=H%C3%A9lios"/>
 * @author mmancino
 */
@XmlRootElement(name = "tabInfo")
public class TabInfo {
    @XmlAttribute
    public String subTab;
    @XmlAttribute
    public String tab;
    @XmlAttribute
    public String tabGroup;
    @XmlAttribute
    public String tabUrl;
}
