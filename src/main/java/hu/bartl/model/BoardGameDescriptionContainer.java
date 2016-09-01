package hu.bartl.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "boardgames")
public class BoardGameDescriptionContainer {

    @XmlElement(name = "boardgame")
    private BoardGameDescription boardGameDescription;

    public BoardGameDescription getBoardGameDescription() {
        return boardGameDescription;
    }
}
