package hu.bartl.model.geekdo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class AlternateNameWrapper {

    public AlternateNameWrapper() {
    }

    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
