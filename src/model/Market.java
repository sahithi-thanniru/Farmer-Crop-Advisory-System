
package model;

public class Market {

    private int marketId;
    private String marketName;
    private String location;
    private String contact;

    public Market() {
    }

    public Market(String marketName, String location,
                  String contact) {

        this.marketName = marketName;
        this.location = location;
        this.contact = contact;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}