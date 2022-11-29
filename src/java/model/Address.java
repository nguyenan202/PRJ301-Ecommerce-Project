package model;

public class Address {
    private int id;
    private String addressLine;
    private int userId;

    public Address() {
    }

    public Address(int id, String addressLine, int userId) {
        this.id = id;
        this.addressLine = addressLine;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
}
