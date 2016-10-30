package hu.bartl.model.bggeek;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ratings")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rating {

    @XmlElement(name = "average")
    private StringValueType average;

    public StringValueType getAverage() {
        return average;
    }
}
