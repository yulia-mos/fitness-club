package com.fitnessclub.serviceidentity.repo;

import com.fitnessclub.serviceidentity.repo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
