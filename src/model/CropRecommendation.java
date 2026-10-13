package model;

import java.sql.Date;

public class CropRecommendation {

    private int recommendationId;
    private int farmerId;
    private int landId;
    private int cropId;
    private String suitability;
    private String reason;
    private Date recommendationDate;

    public CropRecommendation() {
    }

    public CropRecommendation(int farmerId, int landId, int cropId,
                               String suitability, String reason,
                               Date recommendationDate) {

        this.farmerId = farmerId;
        this.landId = landId;
        this.cropId = cropId;
        this.suitability = suitability;
        this.reason = reason;
        this.recommendationDate = recommendationDate;
    }

    public int getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(int recommendationId) {
        this.recommendationId = recommendationId;
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

    public String getSuitability() {
        return suitability;
    }

    public void setSuitability(String suitability) {
        this.suitability = suitability;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getRecommendationDate() {
        return recommendationDate;
    }

    public void setRecommendationDate(Date recommendationDate) {
        this.recommendationDate = recommendationDate;
    }
}