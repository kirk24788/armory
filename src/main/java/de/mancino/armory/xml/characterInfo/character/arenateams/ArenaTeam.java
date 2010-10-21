package de.mancino.armory.xml.characterInfo.character.arenateams;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Example Snippet:
        <arenaTeam
         battleGroup="Embuscade / Hinterhalt"
         created="1257638400000"
         faction="Alliance"
         factionId="0"
         gamesPlayed="0"
         gamesWon="0"
         lastSeasonRanking="0"
         name="Smarter than you"
         ranking="0"
         rating="0"
         realm="Forscherliga"
         realmUrl="b=Embuscade+%2F+Hinterhalt&amp;r=Forscherliga&amp;ts=2&amp;t=Smarter+than+you&amp;ff=realm&amp;fv=Forscherliga&amp;select=Smarter+than+you" season="0" seasonGamesPlayed="0" seasonGamesWon="0" size="2" teamSize="2" teamUrl="r=Forscherliga&amp;ts=2&amp;t=Smarter+than+you&amp;select=Smarter+than+you" url="r=Forscherliga&amp;ts=2&amp;t=Smarter+than+you&amp;select=Smarter+than+you">
          <emblem />
          <members>
            <character />
            ...
            <character />
          </members>
        </arenaTeam>
 * @author mmancino
 */
@XmlRootElement(name = "character")
public class ArenaTeam {
    @XmlAttribute
    public String battleGroup;
    @XmlAttribute
    public long created;
    @XmlAttribute
    public String faction;
    @XmlAttribute
    public int factionId;
    @XmlAttribute
    public int gamesPlayed;
    @XmlAttribute
    public int gamesWon;
    @XmlAttribute
    public int lastSeasonRanking;
    @XmlAttribute
    public String name;
    @XmlAttribute
    public int ranking;
    @XmlAttribute
    public int rating;
    @XmlAttribute
    public String realm;
    @XmlAttribute
    public String realmUrl;
    public Emblem emblem;
    @XmlElement(name = "character")
    @XmlElementWrapper(name="members")
    public List<Character> members;
}