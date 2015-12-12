package com.main.envibus.data;

/**
 * Created by KraKk on 15/11/2015.
 */
public class Stop
{
    private String code;
    private Double id;
    private String name;
    private Double category;
    private Float latitude;
    private Float longitude;
    private Double localityId;
    private String localityName;
    private String numRoad;
    private Double roadLength;
    private int type;

    public Stop(int type, Double roadLength, String numRoad, Double localityId, String localityName, Float longitude, Float latitude, Double category, String name, Double id, String code) {
        this.type = type;
        this.roadLength = roadLength;
        this.numRoad = numRoad;
        this.localityId = localityId;
        this.localityName = localityName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
        this.name = name;
        this.id = id;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCategory() {
        return category;
    }

    public void setCategory(Double category) {
        this.category = category;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Double getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Double localityId) {
        this.localityId = localityId;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public String getNumRoad() {
        return numRoad;
    }

    public void setNumRoad(String numRoad) {
        this.numRoad = numRoad;
    }

    public Double getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(Double roadLength) {
        this.roadLength = roadLength;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
