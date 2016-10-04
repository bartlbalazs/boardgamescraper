package hu.bartl.repository;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "boardgame")
public class BoardGameFlatDescription {

    @Id
    private ObjectId id;

    private int bggId;

    private String name;

    private String hungarianName;

    private String yearPublished;

    private String minPlayers;

    private String maxPlayers;

    private String playingTime;

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

    public String getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(String yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(String minPlayers) {
        this.minPlayers = minPlayers;
    }

    public String getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(String maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(String playingTime) {
        this.playingTime = playingTime;
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
