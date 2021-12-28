package com.fitnessclub.serviceabonnement.api;

import com.fitnessclub.serviceabonnement.dto.AbonnementDto;
import com.fitnessclub.serviceabonnement.dto.FullAbonnementDto;
import com.fitnessclub.serviceabonnement.dto.UserDto;
import com.fitnessclub.serviceabonnement.repo.model.Abonnement;
import com.fitnessclub.serviceabonnement.service.AbonnementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/abonnements")
public class AbonnementController {
    private final AbonnementService abonnementService;
    @GetMapping
    public ResponseEntity<List<Abonnement>> index(){
        final List<Abonnement> abonnements=abonnementService.fetchAll();
        return ResponseEntity.ok(abonnements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Abonnement> show(@PathVariable long id) {
        try {
            final Abonnement abonnement = abonnementService.fetchById(id);
            return ResponseEntity.ok(abonnement);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<FullAbonnementDto>> showUserAbonnements(@PathVariable long id) {
        try {
            final List<FullAbonnementDto> abonnements = abonnementService.fetchAllByUserId(id);
            return ResponseEntity.ok(abonnements);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserByBarCode(@RequestParam(name="barCode") String barCode){
        try {
            final UserDto userDto = abonnementService.fetchUserByBarCode(barCode);

            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AbonnementDto abonnement){
        final String type = abonnement.getType();
        final Integer duration=abonnement.getDuration();
        final Integer numOfClass=abonnement.getNumOfClass();
        final Boolean gymVisiting=abonnement.getGymVisiting();
        final Float cost=abonnement.getCost();
        final long id=abonnementService.create(type, duration,
                numOfClass, gymVisiting, cost);
        final String location= String.format("/abonnements/%d", id);
        return ResponseEntity.created((URI.create(location))).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody AbonnementDto abonnement){
        final String type = abonnement.getType();
        final Integer duration=abonnement.getDuration();
        final Integer numOfClass=abonnement.getNumOfClass();
        final Boolean gymVisiting=abonnement.getGymVisiting();
        final Float cost=abonnement.getCost();

        try{
            abonnementService.update(id, type, duration,
                    numOfClass, gymVisiting, cost);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        abonnementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
