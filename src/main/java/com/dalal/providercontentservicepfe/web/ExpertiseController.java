package com.dalal.providercontentservicepfe.web;

import com.dalal.providercontentservicepfe.dtos.AddServiceReqProviderDTO;
import com.dalal.providercontentservicepfe.entities.Expertise;
import com.dalal.providercontentservicepfe.security.UserPrincipale;
import com.dalal.providercontentservicepfe.services.ExpertiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expertise")
public class ExpertiseController {

    private final ExpertiseService expertiseService;

    @PostMapping("/select-services")
    @PreAuthorize("hasRole('PRESTATAIRE')")
    public ResponseEntity<Map<String, String>> selectServices(
            @AuthenticationPrincipal UserPrincipale userPrincipale,
            @RequestBody Set<Long> serviceIds
    ) {
        Long providerId = userPrincipale.id();
        expertiseService.selectServices(providerId, serviceIds);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Services sélectionnés avec succès.");
        response.put("status", "SUCCESS");
        return ResponseEntity.ok(response);
    }

    @PostMapping("add-more-service")
    @PreAuthorize("hasRole('PRESTATAIRE')")
    public ResponseEntity<Map<String, String>> addService (@AuthenticationPrincipal UserPrincipale userPrincipale, @RequestBody AddServiceReqProviderDTO serviceReqProviderDTO) {
        Long providerId = userPrincipale.id();
        expertiseService.addService(providerId, serviceReqProviderDTO.serviceId());
        return ResponseEntity.ok(Map.of("message","service ajouté avec succès."));
    }

    @DeleteMapping("remove-service/{serviceId}")
    @PreAuthorize("hasRole('PRESTATAIRE')")
    public ResponseEntity<Map<String,String>> deleteService(@AuthenticationPrincipal UserPrincipale userPrincipale,@PathVariable Long serviceId){
        Long providerId = userPrincipale.id();
        expertiseService.deleteService(providerId, serviceId);
        return ResponseEntity.ok(Map.of("message","service supprimé avec succès."));
    }
}