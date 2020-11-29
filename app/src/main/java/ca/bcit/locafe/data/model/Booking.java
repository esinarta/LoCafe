package ca.bcit.locafe.data.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Booking {
    static public final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String bookingId;
    private String businessId;

    private String tableId;
    private String userId;

    private String startString;
    private String endString;

    private int numPeople;

    public Booking() {}

    public Booking(String bookingId, String userId, String businessId, String tableId, String startString, String endString, int numPeople){
        this.bookingId = bookingId;

        this.userId = userId;
        this.businessId = businessId;

        this.numPeople = numPeople;
        this.startString = startString;
        this.endString = endString;

        this.tableId = tableId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getStartString() {
        return startString;
    }

    public Date getStartDate() throws ParseException {
        return format.parse(startString);
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public String getEndString() {
        return endString;
    }

    public Date getEndDate() throws ParseException {
        return format.parse(endString);
    }

    public void setEndString(String endString) {
        this.endString = endString;
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

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public void setBusinessId(String businessId) { this.businessId = businessId; }
}
