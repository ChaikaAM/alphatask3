package com.alpha.task3.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class OfficeDescription {

    private Integer id;

    private String title;

    private Double lon;

    private Double lat;

    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long distance;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer dayOfWeek;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer hourOfDay;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer predicting;

    public void setDistance(Double distance) {
        this.distance = distance.longValue();
    }
}
