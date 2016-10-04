package hu.bartl.repository;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class BoardGameFlatDescription {

    @Id
    private ObjectId id;

    private int bggId;

    private String name;

    private String hungarianName;

    private String yearpublished;

    private String minplayers;

    private String maxplayers;

    private String playingtime;

    private String thumbnail;

    private String image;

    private String rating;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getBggId() {
        return bggId;
    }

    public void setBggId(int bggId) {
        this.bggId = bggId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHungarianName() {
        return hungarianName;
    }

    public void setHungarianName(String hungarianName) {
        this.hungarianName = hungarianName;
    }

    public String getYearpublished() {
        return yearpublished;
    }

    public void setYearpublished(String yearpublished) {
        this.yearpublished = yearpublished;
    }

    public String getMinplayers() {
        return minplayers;
    }

    public void setMinplayers(String minplayers) {
        this.minplayers = minplayers;
    }

    public String getMaxplayers() {
        return maxplayers;
    }

    public void setMaxplayers(String maxplayers) {
        this.maxplayers = maxplayers;
    }

    public String getPlayingtime() {
        return playingtime;
    }

    public void setPlayingtime(String playingtime) {
        this.playingtime = playingtime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
