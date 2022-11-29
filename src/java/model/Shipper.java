package model;

public class Shipper {
    private int id;
    private String name,address,phone,shipTime;
    private double shipPrice;

    public Shipper() {
    }

    public Shipper(int id, String name, String address, String phone, String shipTime, double shipPrice) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.shipTime = shipTime;
        this.shipPrice = shipPrice;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public double getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(double shipPrice) {
        this.shipPrice = shipPrice;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}
