package ca.bcit.locafe.ui.favourites;

public class FavouriteItem {
    private String id;
    private String name;
    private String address;
    private String key;

    public FavouriteItem() {}

    public FavouriteItem(String id, String name, String address, String key) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.key = key;
    }

    public String getId() { return id; }

    public String getName() {
        return name;
    }

    public String getAddress() { return address; }

    public String getKey() { return key; }

}
