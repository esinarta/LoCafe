package ca.bcit.locafe.data.model;

import java.io.Serializable;

public class Business implements Serializable {
    private String id;
    private String name;
    private String address;
    private long cord_lat, cord_long;
    private String description;

    public Business() {

    }

    public Business(String id, String name, String address, long cord_lat, long cord_long, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cord_lat = cord_lat;
        this.cord_long = cord_long;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCord_lat() {
        return cord_lat;
    }

    public void setCord_lat(long cord_lat) {
        this.cord_lat = cord_lat;
    }

    public long getCord_long() {
        return cord_long;
    }

    public void setCord_long(long cord_long) {
        this.cord_long = cord_long;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
