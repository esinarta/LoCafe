package ca.bcit.locafe.data.model;

public class Table {
    private String id;

    private String businessId;
//    private Business business;

    private String seats;
    private boolean outdoor;

    public Table(){}

    public Table(String tableId, String businessId, String seats, boolean outdoor) {
        this.id = tableId;
        this.businessId = businessId;
//        this.business = business;
        this.seats = seats;
        this.outdoor = outdoor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

//    public Business getBusiness() {
//        return business;
//    }
//
//    public void setBusiness(Business business) {
//        this.business = business;
//    }

    public int getSeats() {
        return Integer.parseInt(seats);
    }


    public void setSeats(String seats) {
        this.seats = seats;
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
    }
}
