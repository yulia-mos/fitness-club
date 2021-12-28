package com.fitnessclub.serviceabonnement.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FullAbonnementDto {
    private String type;
    private Integer duration;
    private Integer numOfClass;
    private Boolean gymVisiting;
    private Float cost;
    private String barCode;

    private Integer numOfClassRemain;

    private Integer numOfFreezeRemain;

    private Boolean active;
}
