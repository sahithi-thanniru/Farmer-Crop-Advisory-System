
package model;

public class Fertilizer {

    private int fertilizerId;
    private String fertilizerName;
    private String quantity;
    private String applicationStage;
    private int cropId;

    public Fertilizer() {
    }

    public Fertilizer(String fertilizerName, String quantity,
                      String applicationStage, int cropId) {

        this.fertilizerName = fertilizerName;
        this.quantity = quantity;
        this.applicationStage = applicationStage;
        this.cropId = cropId;
    }

    public int getFertilizerId() {
        return fertilizerId;
    }

    public void setFertilizerId(int fertilizerId) {
        this.fertilizerId = fertilizerId;
    }

    public String getFertilizerName() {
        return fertilizerName;
    }

    public void setFertilizerName(String fertilizerName) {
        this.fertilizerName = fertilizerName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getApplicationStage() {
        return applicationStage;
    }

    public void setApplicationStage(String applicationStage) {
        this.applicationStage = applicationStage;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }
}