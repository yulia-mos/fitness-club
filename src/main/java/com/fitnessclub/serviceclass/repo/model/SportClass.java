package com.fitnessclub.serviceclass.repo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@Table(name = "classes")
public final class SportClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private long id;
    @Getter
    @Setter
    private Long trainerId;
    @Getter
    @Setter
    private String kindOfSport;
    @Getter
    @Setter
    private Integer numOfPeople;
    @Getter
    @Setter
    private String sportHall;
    @Getter
    @Setter
    private Integer numOfRegistered;
    @Getter
    @Setter
    private Date date;

    public SportClass(Long trainerId, String kindOfSport, Integer numOfPeople, String sportHall, Integer numOfRegistered, Date date) {
        this.trainerId = trainerId;
        this.kindOfSport = kindOfSport;
        this.numOfPeople = numOfPeople;
        this.sportHall = sportHall;
        this.numOfRegistered=numOfRegistered;
        this.date = date;
    }
}
