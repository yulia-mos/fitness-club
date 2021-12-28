package com.fitnessclub.serviceabonnement.api;

import com.fitnessclub.serviceabonnement.repo.model.UserAbonnement;
import com.fitnessclub.serviceabonnement.service.UserAbonnementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/user-abonnements")
public class UserAbonnementController {
    private final UserAbonnementService userAbonnementService;
    @GetMapping
    public ResponseEntity<List<UserAbonnement>> index(){
        final List<UserAbonnement> userAbonnements= userAbonnementService.fetchAll();
        return ResponseEntity.ok(userAbonnements);
    }

    @GetMapping("/{barCode}")
    public ResponseEntity<UserAbonnement> show(@PathVariable String barCode) {
        try {
            final UserAbonnement userAbonnement = userAbonnementService.fetchByBarCode(barCode);
            return ResponseEntity.ok(userAbonnement);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserAbonnement userAbonnement){
        final Long userId=userAbonnement.getUserId();
        final Long abonnementId=userAbonnement.getAbonnementId();
        final String barCode=userAbonnement.getBarCode();
        final Integer numOfClassRemain = userAbonnement.getNumOfClassRemain();
        final Integer numOfFreezeRemain=userAbonnement.getNumOfFreezeRemain();
        final  Boolean active=userAbonnement.getActive();
        try {
            userAbonnementService.create(userId, abonnementId, barCode,
                    numOfClassRemain, numOfFreezeRemain, active);
            final String location = String.format("/user-abonnements/%s", barCode);
            return ResponseEntity.created((URI.create(location))).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/{barCode}/cancel-registration")
    public ResponseEntity<Void> cancel(@PathVariable String barCode, @RequestParam Long classId){
        try {
            userAbonnementService.cancel(barCode, classId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return  ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/{barCode}/register")
    public ResponseEntity<Void> register(@PathVariable String barCode, @RequestParam Long classId){
        try {
            userAbonnementService.register(barCode, classId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return  ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{barCode}")
    public ResponseEntity<Void> update(@PathVariable String barCode, @RequestBody UserAbonnement userAbonnement){
        final Long userId=userAbonnement.getUserId();
        final Long abonnementId=userAbonnement.getAbonnementId();
        final Integer numOfClassRemain = userAbonnement.getNumOfClassRemain();
        final Integer numOfFreezeRemain=userAbonnement.getNumOfFreezeRemain();
        final  Boolean active=userAbonnement.getActive();

        try{
            userAbonnementService.update(userId, abonnementId,barCode, numOfClassRemain,
                    numOfFreezeRemain, active);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{barCode}")
    public ResponseEntity<Void> delete(@PathVariable String barCode) {
        userAbonnementService.deleteByBarCode(barCode);
        return ResponseEntity.noContent().build();
    }
}
