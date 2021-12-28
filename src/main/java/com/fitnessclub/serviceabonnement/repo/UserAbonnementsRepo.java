package com.fitnessclub.serviceabonnement.repo;

import com.fitnessclub.serviceabonnement.repo.model.UserAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAbonnementsRepo extends JpaRepository<UserAbonnement, Long> {
    List<UserAbonnement> findAllByUserId(Long id);

    Optional<UserAbonnement> findByBarCode(String barCode);

    void deleteByBarCode(String barCode);
}
