package ca.bcit.locafe.data.model;

import java.util.Date;

public class Booking {
    private String bookingId;

    private String tableId;
    private String userId;

//    private Table table;
//    private User user;

    private Date start;
    private Date end;
    private int numPeople;

    public Booking(String bookingId, String tableId, String userId, Date start, Date end, int numPeople) {
        this.bookingId = bookingId;
        this.tableId = tableId;
        this.userId = userId;
//        this.table = table;
//        this.user = user;
        this.start = start;
        this.end = end;
        this.numPeople = numPeople;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }
//
//    public Table getTable() {
//        return table;
//    }
//
//    public void setTable(Table table) {
//        this.table = table;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
