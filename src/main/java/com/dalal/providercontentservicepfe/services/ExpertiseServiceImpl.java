package com.dalal.providercontentservicepfe.services;

import com.dalal.providercontentservicepfe.entities.Category;
import com.dalal.providercontentservicepfe.entities.Expertise;
import com.dalal.providercontentservicepfe.entities.ExpertiseId;
import com.dalal.providercontentservicepfe.exceptions.ServiceAlreadyAssignedException;
import com.dalal.providercontentservicepfe.exceptions.ServiceNotFoundException;
import com.dalal.providercontentservicepfe.repositories.CategoryRepository;
import com.dalal.providercontentservicepfe.repositories.ExpertiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpertiseServiceImpl implements ExpertiseService {

    private final CategoryRepository categoryRepository;
    private final ExpertiseRepository expertiseRepository;

    //check provider exist (yet)
    @Override
    public void selectServices (Long providerId, Set<Long> serviceIds) {
       // it's vital to do that step
        expertiseRepository.deleteByProviderId(providerId); //
        List<Expertise> expertisesToSave = new ArrayList<>();

        serviceIds.forEach(serviceId -> {

            Category category = categoryRepository.findById(serviceId)
                    .orElseThrow(() -> new ServiceNotFoundException("Service avec ID " + serviceId + " introuvable."));
            Expertise expertise = Expertise.builder()
                    .providerId(providerId)
                    .serviceId(serviceId)
                    .service(category)
                    .build();

            expertisesToSave.add(expertise);
        });

        expertiseRepository.saveAll(expertisesToSave);
    }

    //check provider exist (yet)
    public void addService(Long providerId, Long serviceId) {

        // awal verification
        Category category = categoryRepository.findById(serviceId)
                    .orElseThrow(() -> new ServiceNotFoundException("Service introuvable."));

        // creation dyal composite id
        ExpertiseId expertiseId = new ExpertiseId(providerId, serviceId);

        // verification if that provider already associated to that service id
        if (expertiseRepository.findById(expertiseId).isPresent()) {
            throw new ServiceAlreadyAssignedException("Ce service est déjà associé à ce prestataire.");
        }

        // if not i will create an new expertise object to save it in DB
        Expertise expertise = Expertise.builder()
                .providerId(providerId)
                .serviceId(serviceId)
                .service(category)
                .build();

        expertiseRepository.save(expertise);
    }

}