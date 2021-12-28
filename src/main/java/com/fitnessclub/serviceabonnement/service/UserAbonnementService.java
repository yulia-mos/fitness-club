package com.fitnessclub.serviceabonnement.service;

import com.fitnessclub.serviceabonnement.dto.ClassDto;
import com.fitnessclub.serviceabonnement.dto.UserDto;
import com.fitnessclub.serviceabonnement.repo.AbonnementRepo;
import com.fitnessclub.serviceabonnement.repo.UserAbonnementsRepo;
import com.fitnessclub.serviceabonnement.repo.model.UserAbonnement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserAbonnementService {
    private final UserAbonnementsRepo userAbonnementsRepo;
    private final AbonnementRepo abonnementRepo;
    private final String userURL="http://localhost:8081/users";
    private final String classURL="http://localhost:8082/classes";
    public List<UserAbonnement> fetchAll() {
        return userAbonnementsRepo.findAll();
    }

    public UserAbonnement fetchByBarCode(String barCode) {
        final Optional<UserAbonnement> maybeUserAbonnement=userAbonnementsRepo.findByBarCode(barCode);
        if (maybeUserAbonnement.isEmpty()) throw  new IllegalArgumentException("Incorrect input");
        else return maybeUserAbonnement.get();
    }

    public void create(Long userId, Long abonnementId, String barCode,
                       Integer numOfClassRemain, Integer numOfFreezeRemain, Boolean active) throws IllegalArgumentException {
        RestTemplate restTemplate = new RestTemplate();
        if (abonnementRepo.findById(abonnementId).isEmpty()) throw new IllegalArgumentException("Abonnement not found");
        try {
            restTemplate.getForEntity(userURL + "/" + userId, UserDto.class);
            final UserAbonnement userAbonnement = new UserAbonnement(userId, abonnementId, barCode, numOfClassRemain,
                    numOfFreezeRemain, active);
            userAbonnementsRepo.save(userAbonnement);
        } catch (Exception e){
            throw new IllegalArgumentException();
        }
    }

    public void update(Long userId, Long abonnementId, String barCode, Integer numOfClassRemain,
                       Integer numOfFreezeRemain, Boolean active) {
        Optional<UserAbonnement> maybeUserAbonnement=userAbonnementsRepo.findByBarCode(barCode);
        if (maybeUserAbonnement.isEmpty()) throw new IllegalArgumentException("Incorrect input");
        final UserAbonnement userAbonnement=maybeUserAbonnement.get();
        if (abonnementId!=null) {
            if (abonnementRepo.findById(abonnementId).isEmpty())
                throw new IllegalArgumentException("Abonnement not found");
            userAbonnement.setAbonnementId(abonnementId);
        }
        if (userId!=null) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getForEntity(userURL + "/" + userId, UserDto.class);
                userAbonnement.setUserId(userId);
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
        }
        if (numOfClassRemain!=null) userAbonnement.setNumOfClassRemain(numOfClassRemain);
        if (numOfFreezeRemain!=null) userAbonnement.setNumOfFreezeRemain(numOfFreezeRemain);
        if (active!=null) userAbonnement.setActive(active);
        userAbonnementsRepo.save(userAbonnement);
    }

    public void deleteByBarCode(String barCode) {
        userAbonnementsRepo.deleteByBarCode(barCode);

    }

    public void register(String barCode, Long classId) {
        Optional<UserAbonnement> maybeUserAbonnement = userAbonnementsRepo.findByBarCode(barCode);
        if (maybeUserAbonnement.isEmpty()) throw new IllegalArgumentException();
        UserAbonnement userAbonnement = maybeUserAbonnement.get();
        try {
            RestTemplate restTemplate = new RestTemplate();
            ClassDto sportClass = restTemplate.getForObject(classURL + "/" + classId, ClassDto.class);
            final int numOfPeople = Objects.requireNonNull(sportClass).getNumOfPeople();
            final int numOfRegistered = sportClass.getNumOfRegistered();
            if (numOfPeople - numOfRegistered > 0) {
                userAbonnement.setNumOfClassRemain(userAbonnement.getNumOfClassRemain() - 1);
                sportClass.setNumOfRegistered(numOfRegistered + 1);
                ResponseEntity<Void> responseEntity = restTemplate.exchange(classURL + "/" + classId,
                        HttpMethod.PUT, new HttpEntity<>(sportClass), Void.class);
            }
        } catch (Exception e){
            throw new IllegalArgumentException();
        }
    }

    public void cancel(String barCode, Long classId) {
        Optional<UserAbonnement> maybeUserAbonnement = userAbonnementsRepo.findByBarCode(barCode);
        if (maybeUserAbonnement.isEmpty()) throw new IllegalArgumentException();
        UserAbonnement userAbonnement = maybeUserAbonnement.get();
        try {
            RestTemplate restTemplate = new RestTemplate();
            ClassDto sportClass = restTemplate.getForObject(classURL + "/" + classId, ClassDto.class);
            final int numOfRegistered = Objects.requireNonNull(sportClass).getNumOfRegistered();
            if (numOfRegistered > 0) {
                userAbonnement.setNumOfClassRemain(userAbonnement.getNumOfClassRemain() + 1);
                sportClass.setNumOfRegistered(numOfRegistered - 1);
                ResponseEntity<Void> responseEntity = restTemplate.exchange(classURL + "/" + classId,
                        HttpMethod.PUT, new HttpEntity<>(sportClass), Void.class);
            }
        } catch (Exception e){
            throw new IllegalArgumentException();
        }
    }
}
