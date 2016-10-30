package hu.bartl.model.bggeek;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class BoardGameDescriptionContainer {

    @XmlElement(name = "item")
    private List<BoardGameDescription> boardGameDescriptions;

    public List<BoardGameDescription> getBoardGameDescriptions() {
        return boardGameDescriptions;
    }
}
