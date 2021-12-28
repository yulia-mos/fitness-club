package com.fitnessclub.serviceabonnement.repo.model;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="abonnements")
@NoArgsConstructor
public final class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private long id;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private Integer duration;
    @Getter
    @Setter
    private Integer numOfClass;
    @Getter
    @Setter
    private Boolean gymVisiting;
    @Getter
    @Setter
    private Float cost;

    public Abonnement(String type, Integer duration, Integer numOfClass,
                      Boolean gymVisiting, Float cost) {
        this.type = type;
        this.duration = duration;
        this.numOfClass = numOfClass;
        this.gymVisiting = gymVisiting;
        this.cost = cost;
    }
}
