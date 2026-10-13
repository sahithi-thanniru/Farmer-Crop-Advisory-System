package model;

public class Cultivation {

    private int cultivationId;
    private int cropId;
    private String cultivationMethod;
    private String pestControl;
    private int harvestDuration;

    public Cultivation() {
    }

    public Cultivation(int cropId, String cultivationMethod,
                       String pestControl, int harvestDuration) {

        this.cropId = cropId;
        this.cultivationMethod = cultivationMethod;
        this.pestControl = pestControl;
        this.harvestDuration = harvestDuration;
    }

    public int getCultivationId() {
        return cultivationId;
    }

    public void setCultivationId(int cultivationId) {
        this.cultivationId = cultivationId;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public String getCultivationMethod() {
        return cultivationMethod;
    }

    public void setCultivationMethod(String cultivationMethod) {
        this.cultivationMethod = cultivationMethod;
    }

    public String getPestControl() {
        return pestControl;
    }

    public void setPestControl(String pestControl) {
        this.pestControl = pestControl;
    }

    public int getHarvestDuration() {
        return harvestDuration;
    }

    public void setHarvestDuration(int harvestDuration) {
        this.harvestDuration = harvestDuration;
    }
}