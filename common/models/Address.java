package common.models;

import java.io.Serializable;

public class Address implements Serializable {

    private String street; //Поле может быть null
    private Location town; //Поле может быть null

    public Address(){}
    public Address(String street){
        this.street = street;
    }
    public Address(Location town){
        this.town = town;
    }
    public Address(String street, Location town){
        this.street = street;
        this.town = town;
    }

    public void setStreet(String street){
        this.street = street;
    }
    public void setTown(Location town){
        this.town = town;
    }

    public String getStreet(){
        return street;
    }
    public Location getTown() {
        return town;
    }

    public boolean isLowerThan(Address other){
        return (this.street != null && other.street != null &&
                (this.street.length() < other.street.length() ||
                        this.town.isLowerThan(other.town)
                )
        );
    }

}
