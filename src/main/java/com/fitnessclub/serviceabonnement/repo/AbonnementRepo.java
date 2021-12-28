package com.fitnessclub.serviceabonnement.repo;

import com.fitnessclub.serviceabonnement.repo.model.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbonnementRepo extends JpaRepository<Abonnement, Long> {

}
