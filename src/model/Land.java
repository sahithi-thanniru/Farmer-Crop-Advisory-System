package model;

public class Land {

    private int landId;
    private int farmerId;
    private double area;
    private String soilType;
    private String irrigation;
    private String waterAvailability;
    private String location;
    private double temperature;

    public Land() {
    }

    public Land(int farmerId, double area, String soilType,
                String irrigation, String waterAvailability,
                String location, double temperature) {

        this.farmerId = farmerId;
        this.area = area;
        this.soilType = soilType;
        this.irrigation = irrigation;
        this.waterAvailability = waterAvailability;
        this.location = location;
        this.temperature = temperature;
    }

    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public String getWaterAvailability() {
        return waterAvailability;
    }

    public void setWaterAvailability(String waterAvailability) {
        this.waterAvailability = waterAvailability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
