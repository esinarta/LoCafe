package ca.bcit.locafe.data.model;

public class Table {
    private String tableId;

    private String businessId;
//    private Business business;

    private int numSeats;
    private boolean outdoor;

    public Table(String tableId, String businessId, int numSeats, boolean outdoor) {
        this.tableId = tableId;
        this.businessId = businessId;
//        this.business = business;
        this.numSeats = numSeats;
        this.outdoor = outdoor;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
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

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public boolean isOutdoor() {
        return outdoor;
    }

    public void setOutdoor(boolean outdoor) {
        this.outdoor = outdoor;
    }
}
