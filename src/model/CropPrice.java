package model;

import java.sql.Date;

public class CropPrice {

    private int priceId;
    private int marketId;
    private int cropId;
    private double pricePerUnit;
    private String unit;
    private Date dateUpdated;

    public CropPrice() {
    }

    public CropPrice(int marketId, int cropId,
                     double pricePerUnit, String unit,
                     Date dateUpdated) {

        this.marketId = marketId;
        this.cropId = cropId;
        this.pricePerUnit = pricePerUnit;
        this.unit = unit;
        this.dateUpdated = dateUpdated;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}