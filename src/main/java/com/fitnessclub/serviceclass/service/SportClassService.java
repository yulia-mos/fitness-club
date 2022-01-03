package com.fitnessclub.serviceclass.service;

import com.fitnessclub.serviceclass.dto.UserDto;
import com.fitnessclub.serviceclass.repo.SportClassRepo;
import com.fitnessclub.serviceclass.repo.model.SportClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class SportClassService {
    private final SportClassRepo sportClassRepo;
    //private final String userURL="http://localhost:8081/users";
    public List<SportClass> fetchAll(){
        return sportClassRepo.findAll();
    }
    public SportClass fetchById(long id)throws IllegalArgumentException{
        final Optional<SportClass> maybeClass= sportClassRepo.findById(id);
        if (maybeClass.isEmpty()) throw  new IllegalArgumentException("Class not found");
        else return maybeClass.get();
    }

    public long create(Long trainerId, String kindOfSport, Integer numOfPeople, String sportHall, Integer numOfRegistered, Date date) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.getForEntity(System.getenv("USER_URL") + "/" + trainerId, UserDto.class);
            final SportClass sportClass = new SportClass(trainerId, kindOfSport, numOfPeople, sportHall, numOfRegistered, date);
            SportClass savedSportClass = sportClassRepo.save(sportClass);
            return savedSportClass.getId();
        } catch (Exception e){
            throw new IllegalArgumentException();
        }
    }

    public void update(long id, Long trainerId, String kindOfSport, Integer numOfPeople, String sportHall, Integer numOfRegistered, Date date) {
        final Optional<SportClass> maybeClass = sportClassRepo.findById(id);
        if (maybeClass.isEmpty()) throw  new IllegalArgumentException("Class not found");
        SportClass sportClass=maybeClass.get();
        if (trainerId!=null) {
            try{
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getForEntity(System.getenv("USER_URL") + "/" + trainerId, UserDto.class);
                sportClass.setTrainerId(trainerId);
            } catch (Exception e) {throw new IllegalArgumentException();}
        }
        if (kindOfSport!=null && !kindOfSport.isBlank()) sportClass.setKindOfSport(kindOfSport);
        if (numOfPeople!=null) sportClass.setNumOfPeople(numOfPeople);
        if (sportHall!=null && !sportHall.isBlank()) sportClass.setSportHall(sportHall);
        if (numOfRegistered!=null) sportClass.setNumOfRegistered(numOfRegistered);
        if (date!=null) sportClass.setDate(date);
        sportClassRepo.save(sportClass);

    }

    public void deleteById(long id) {
        sportClassRepo.deleteById(id);
    }

    public UserDto fetchTrainerById(long id) {
        final Optional<SportClass> maybeClass = sportClassRepo.findById(id);
        if (maybeClass.isEmpty()) throw  new IllegalArgumentException("Class not found");
        final SportClass sportClass=maybeClass.get();
        final long trainerId = sportClass.getTrainerId();
        RestTemplate restTemplate = new RestTemplate();
        final UserDto trainer = restTemplate.getForObject(System.getenv("USER_URL") + "/" + trainerId, UserDto.class);
        return trainer;
    }
}
