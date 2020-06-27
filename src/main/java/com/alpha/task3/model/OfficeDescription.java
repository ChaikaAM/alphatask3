package com.alpha.task3.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfficeDescription {

    private Integer id;

    private String title;

    private BigDecimal lon;

    private BigDecimal lat;

    private String address;
}
