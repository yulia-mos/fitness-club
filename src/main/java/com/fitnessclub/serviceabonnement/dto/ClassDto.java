package com.fitnessclub.serviceabonnement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {
    private Integer numOfPeople;
    private Integer numOfRegistered;
}
