package common.models;

import client.utils.ValidationError;
import client.utils.WorkerValidator;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Float x; //Поле не может быть null
    private Integer y;

    public Coordinates() {}

    public Coordinates(Float x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }


    @Override
    public String toString() {
        return "(" + x +  ", " + y + ")";
    }

    public boolean isValid() {
        try {
            WorkerValidator.validateX(this.x);
            return true;
        } catch (ValidationError e) {
            return false;
        }
    }
    public boolean isLowerThan(Coordinates other){
        return (this.isValid() && other.isValid()) && (this.x < other.x || this.y < other.y);
    }
}
