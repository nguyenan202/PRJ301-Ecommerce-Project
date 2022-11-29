package model;

public class Banner {
    private int id;
    private String suppilerName,image;

    public Banner() {
    }

    public Banner(int id, String suppilerName, String image) {
        this.id = id;
        this.suppilerName = suppilerName;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuppilerName() {
        return suppilerName;
    }

    public void setSuppilerName(String suppilerName) {
        this.suppilerName = suppilerName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
