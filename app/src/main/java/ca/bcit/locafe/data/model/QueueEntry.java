package ca.bcit.locafe.data.model;

public class QueueEntry{
    private String queueId;

    private String businessId;
    private String userId;

    private Business business;
    private User user;

    private long duration;
    private int numPeople;

    public QueueEntry(String queueId, String businessId, String userId, long duration, int numPeople) {
        this.queueId = queueId;
        this.businessId = businessId;
        this.userId = userId;
//        this.user = user;
//        this.business = business;
        this.duration = duration;
        this.numPeople = numPeople;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public Business getBusiness() {
//        return business;
//    }
//
//    public void setBusiness(Business business) {
//        this.business = business;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }
}
