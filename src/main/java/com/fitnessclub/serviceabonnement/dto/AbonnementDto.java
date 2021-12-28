package com.fitnessclub.serviceabonnement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class AbonnementDto {
    private String type;
    private Integer duration;
    private Integer numOfClass;
    private Boolean gymVisiting;
    private Float cost;
}
