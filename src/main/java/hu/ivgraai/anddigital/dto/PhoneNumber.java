package hu.ivgraai.anddigital.dto;

import java.io.Serializable;

/**
 * @author Gergo Ivan
 */
public class PhoneNumber implements Serializable {

    private String value;
    private Boolean active;

    public PhoneNumber() {
        // empty method
    }

    public PhoneNumber(String value, Boolean active) {
        this.value = value;
        this.active = active;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" + "value=" + value + ", active=" + active + '}';
    }

}
