package hu.bartl.model;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "boardgame")
public class BoardGameDescription {

    @Expose
    private int id;

    @XmlAttribute
    private boolean subtypemismatch = false;

    @XmlElement
    @Expose
    private String name;

    @XmlElement
    @Expose
    private String yearpublished;

    @XmlElement
    @Expose
    private String minplayers;

    @XmlElement
    @Expose
    private String maxplayers;

    @XmlElement
    @Expose
    private String playingtime;

    @XmlElement
    @Expose
    private String thumbnail;

    @XmlElement
    @Expose
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSubtypemismatch() {
        return subtypemismatch;
    }

    public String getName() {
        return name;
    }

    public String getYearpublished() {
        return yearpublished;
    }

    public String getMinplayers() {
        return minplayers;
    }

    public String getMaxplayers() {
        return maxplayers;
    }

    public String getPlayingtime() {
        return playingtime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardGameDescription that = (BoardGameDescription) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return yearpublished != null ? yearpublished.equals(that.yearpublished) : that.yearpublished == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (yearpublished != null ? yearpublished.hashCode() : 0);
        return result;
    }
}
