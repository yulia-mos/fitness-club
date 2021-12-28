package com.fitnessclub.serviceabonnement.repo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "user_abonnements")
public class UserAbonnement {
    public UserAbonnement(Long userId, Long abonnementId, String barCode,
                          Integer numOfClassRemain, Integer numOfFreezeRemain, Boolean active) {
        this.userId = userId;
        this.abonnementId = abonnementId;
        this.barCode = barCode;
        this.numOfClassRemain = numOfClassRemain;
        this.numOfFreezeRemain = numOfFreezeRemain;
        this.active = active;
    }

    @Getter
    @Setter
    private Long userId;
    @Getter
    @Setter
    private Long abonnementId;

    @Getter
    @Setter
    @Id
    private String barCode;

    @Getter
    @Setter
    private Integer numOfClassRemain;
    @Getter
    @Setter
    private Integer numOfFreezeRemain;

    @Getter
    @Setter
    private Boolean active;



}
