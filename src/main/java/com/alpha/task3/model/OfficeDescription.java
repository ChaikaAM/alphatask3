package com.alpha.task3.model;

import lombok.Data;

@Data
public class OfficeDescription {

    private Integer id;

    private String title;

    private Double lon;

    private Double lat;

    private String address;

    private Long distance;

    public void setDistance(Double distance) {
        this.distance = distance.longValue();
    }
}
