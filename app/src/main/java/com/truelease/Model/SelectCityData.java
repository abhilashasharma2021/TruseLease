package com.truelease.Model;

public class SelectCityData {

    private final String cityId;
    private final String cityName;
    private final String cityImage;
    private final String cityPath;

    public SelectCityData(String cityId, String cityName, String cityImage, String cityPath) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityImage = cityImage;
        this.cityPath = cityPath;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityImage() {
        return cityImage;
    }

    public String getCityPath() {
        return cityPath;
    }


}
