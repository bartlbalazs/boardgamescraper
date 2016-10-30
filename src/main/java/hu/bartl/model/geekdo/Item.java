package hu.bartl.model.geekdo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect
public class Item {

    public Item() {
    }

    private List<AlternateName> alternatenames;

    public List<AlternateName> getAlternatenames() {
        return alternatenames;
    }

    public void setAlternatenames(List<AlternateName> alternatenames) {
        this.alternatenames = alternatenames;
    }
}