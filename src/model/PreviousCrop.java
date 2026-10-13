
package model;

public class PreviousCrop {

    private int recordId;
    private int farmerId;
    private int landId;
    private int cropId;
    private String season;
    private int year;
    private double yield;

    public PreviousCrop() {
    }

    public PreviousCrop(int farmerId, int landId, int cropId,
                        String season, int year, double yield) {

        this.farmerId = farmerId;
        this.landId = landId;
        this.cropId = cropId;
        this.season = season;
        this.year = year;
        this.yield = yield;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }
}