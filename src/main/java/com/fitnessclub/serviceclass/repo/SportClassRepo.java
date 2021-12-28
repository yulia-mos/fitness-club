package com.fitnessclub.serviceclass.repo;

import com.fitnessclub.serviceclass.repo.model.SportClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportClassRepo extends JpaRepository<SportClass, Long> {
}
