package com.alpha.task3.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "queue_log")
@Data
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date startTimeOfWait;

    private Date endTimeOfWait;

    private Date endTimeOfService;

    private Integer branchesId;

}
