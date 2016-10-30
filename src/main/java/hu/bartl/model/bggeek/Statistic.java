package hu.bartl.model.bggeek;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "statistics")
@XmlAccessorType(XmlAccessType.FIELD)
public class Statistic {

    @XmlElement(name = "ratings")
    private Rating rating;

    public Rating getRating() {
        return rating;
    }
}
