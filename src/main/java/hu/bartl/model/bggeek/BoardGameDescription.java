package hu.bartl.model.bggeek;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class BoardGameDescription {

    @XmlAttribute
    private int id;

    @XmlElement
    private NameType name;

    @XmlElement
    private StringValueType yearpublished;

    @XmlElement
    private StringValueType minplayers;

    @XmlElement
    private StringValueType maxplayers;

    @XmlElement
    private StringValueType playingtime;

    @XmlElement
    private String thumbnail;

    @XmlElement
    private String image;

    @XmlElement(name = "statistics")
    private Statistic statistics;


    public int getId() {
        return id;
    }

    public NameType getName() {
        return name;
    }

    public StringValueType getYearpublished() {
        return yearpublished;
    }

    public StringValueType getMinplayers() {
        return minplayers;
    }

    public StringValueType getMaxplayers() {
        return maxplayers;
    }

    public StringValueType getPlayingtime() {
        return playingtime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getImage() {
        return image;
    }

    public Statistic getStatistics() {
        return statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardGameDescription that = (BoardGameDescription) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "BoardGameDescription{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
