package com.fitnessclub.serviceabonnement.service;

import com.fitnessclub.serviceabonnement.dto.FullAbonnementDto;
import com.fitnessclub.serviceabonnement.dto.UserDto;
import com.fitnessclub.serviceabonnement.repo.AbonnementRepo;
import com.fitnessclub.serviceabonnement.repo.UserAbonnementsRepo;
import com.fitnessclub.serviceabonnement.repo.model.Abonnement;
import com.fitnessclub.serviceabonnement.repo.model.UserAbonnement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class AbonnementService {
    private final AbonnementRepo abonnementRepo;
    private final UserAbonnementsRepo userAbonnementsRepo;
    private final String userURL="http://localhost:8081/users";

    public List<Abonnement> fetchAll(){
        return abonnementRepo.findAll();
    }
    public Abonnement fetchById(long id)throws IllegalArgumentException{
        final Optional<Abonnement> maybeAbonnement=abonnementRepo.findById(id);
        if (maybeAbonnement.isEmpty()) throw  new IllegalArgumentException("Abonnement not found");
        else return maybeAbonnement.get();
    }
    public long create(String type, Integer duration,
                       Integer numOfClass, Boolean gymVisiting,
                       Float cost){
        final Abonnement abonnement = new Abonnement(type, duration,
                numOfClass, gymVisiting,cost);
        final Abonnement savedAbonnement=abonnementRepo.save(abonnement);
        return savedAbonnement.getId();
    }
    public void update(long id,String type, Integer duration,
                       Integer numOfClass, Boolean gymVisiting,
                       Float cost)throws IllegalArgumentException{
        final Optional<Abonnement> maybeAbonnement = abonnementRepo.findById(id);
        if (maybeAbonnement.isEmpty()) throw  new IllegalArgumentException("Abonnement not found");
        Abonnement abonnement=maybeAbonnement.get();
        if (type!=null && !type.isBlank()) abonnement.setType(type);
        if (duration!=null) abonnement.setDuration(duration);
        if (numOfClass!=null) abonnement.setNumOfClass(numOfClass);
        if (gymVisiting!=null) abonnement.setGymVisiting(gymVisiting);
        if (cost!=null) abonnement.setCost(cost);
        abonnementRepo.save(abonnement);


    }
    public void deleteById(long id){
        abonnementRepo.deleteById(id);
    }

    public List<FullAbonnementDto> fetchAllByUserId(long id) {
        List<UserAbonnement> userAbonnements = userAbonnementsRepo.findAllByUserId(id);
        List<FullAbonnementDto> abonnements = new ArrayList<FullAbonnementDto>();
        for (UserAbonnement ua : userAbonnements) {
            Long abonnementId = ua.getAbonnementId();
            Abonnement abonnement = abonnementRepo.findById(abonnementId).orElseThrow(IllegalArgumentException::new);
            abonnements.add(new FullAbonnementDto(abonnement.getType(), abonnement.getDuration(),
                    abonnement.getNumOfClass(), abonnement.getGymVisiting(), abonnement.getCost(),
                    ua.getBarCode(), ua.getNumOfClassRemain(), ua.getNumOfFreezeRemain(), ua.getActive()));
        }
        return abonnements;
    }

    public UserDto fetchUserByBarCode(String barCode) {
        final Optional<UserAbonnement> maybeUserAbonnement=userAbonnementsRepo.findByBarCode(barCode);
        if (maybeUserAbonnement.isEmpty()) throw  new IllegalArgumentException("Abonnement not found");
        long userId = maybeUserAbonnement.get().getUserId();
        final RestTemplate restTemplate= new RestTemplate();
        return restTemplate
                .getForObject(userURL + "/" +userId, UserDto.class);
    }
}
