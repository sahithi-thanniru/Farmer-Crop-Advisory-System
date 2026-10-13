package model;

public class Farmer {

    private int farmerId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String village;
    private String district;
    private String landOwnership;

    public Farmer() {
    }

    public Farmer(String name, int age, String gender, String phone,
                  String village, String district, String landOwnership) {

        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.village = village;
        this.district = district;
        this.landOwnership = landOwnership;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLandOwnership() {
        return landOwnership;
    }

    public void setLandOwnership(String landOwnership) {
        this.landOwnership = landOwnership;
    }
}