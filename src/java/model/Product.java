package model;

public class Product {
    private int id,supplierId,unitInStock,unitOnOrder,catId;
    private String name,image;
    private double unitPrice;

    public Product() {
    }

    public Product(int id, int supplierId, int unitInStock, int unitOnOrder, int catId, String name, String image, double unitPrice) {
        this.id = id;
        this.supplierId = supplierId;
        this.unitInStock = unitInStock;
        this.unitOnOrder = unitOnOrder;
        this.catId = catId;
        this.name = name;
        this.image = image;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(int unitInStock) {
        this.unitInStock = unitInStock;
    }

    public int getUnitOnOrder() {
        return unitOnOrder;
    }

    public void setUnitOnOrder(int unitOnOrder) {
        this.unitOnOrder = unitOnOrder;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
