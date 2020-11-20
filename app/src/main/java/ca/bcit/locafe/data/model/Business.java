package ca.bcit.locafe.data.model;

public class Business {
    private String businessId;
    private String name;
    private String address;
    private long cord_lat, cord_long;
    private String description;

    public Business() {

    }

    public Business(String businessId, String name, String address, long cord_lat, long cord_long, String description) {
        this.businessId = businessId;
        this.name = name;
        this.address = address;
        this.cord_lat = cord_lat;
        this.cord_long = cord_long;
        this.description = description;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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
