package com.fitnessclub.serviceclass.api;

import com.fitnessclub.serviceclass.dto.SportClassDto;
import com.fitnessclub.serviceclass.dto.UserDto;
import com.fitnessclub.serviceclass.repo.model.SportClass;
import com.fitnessclub.serviceclass.service.SportClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.Date;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/classes")
public class SportClassController {
    private final SportClassService sportClassService;
    @GetMapping
    public ResponseEntity<List<SportClass>> index(){
        final List<SportClass> userAbonnements= sportClassService.fetchAll();
        return ResponseEntity.ok(userAbonnements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportClass> show(@PathVariable long id) {
        try {
            final SportClass sportClass = sportClassService.fetchById(id);
            return ResponseEntity.ok(sportClass);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/trainer")
    public ResponseEntity<UserDto> showTrainer(@PathVariable long id) {
        try {
            final UserDto trainer = sportClassService.fetchTrainerById(id);
            return ResponseEntity.ok(trainer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody SportClassDto sportClass){
        final Long trainerId=sportClass.getTrainerId();
        final String kindOfSport = sportClass.getKindOfSport();
        final Integer numOfPeople = sportClass.getNumOfPeople();
        final String sportHall = sportClass.getSportHall();
        final Integer numOfRegistered=sportClass.getNumOfRegistered();
        final Date date = sportClass.getDate();
        try {
            final long id = sportClassService.create(trainerId, kindOfSport, numOfPeople, sportHall, numOfRegistered, date);
            final String location = String.format("/classes/%d", id);
            return ResponseEntity.created((URI.create(location))).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody SportClassDto sportClass){
        return update(id, sportClass);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody SportClassDto sportClass){
        final Long trainerId=sportClass.getTrainerId();
        final String kindOfSport = sportClass.getKindOfSport();
        final Integer numOfPeople = sportClass.getNumOfPeople();
        final String sportHall = sportClass.getSportHall();
        final Integer numOfRegistered=sportClass.getNumOfRegistered();
        final Date date = sportClass.getDate();
        try{
            sportClassService.update(id, trainerId, kindOfSport, numOfPeople, sportHall, numOfRegistered, date);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        sportClassService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
