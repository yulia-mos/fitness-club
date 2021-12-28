package com.fitnessclub.serviceclass.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@NoArgsConstructor
public class SportClassDto {
    private Long trainerId;
    private String kindOfSport;
    private Integer numOfPeople;
    private String sportHall;
    private Integer numOfRegistered;
    private Date date;
}
