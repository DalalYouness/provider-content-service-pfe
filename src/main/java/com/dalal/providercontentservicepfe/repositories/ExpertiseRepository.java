package com.dalal.providercontentservicepfe.repositories;

import com.dalal.providercontentservicepfe.entities.Expertise;
import com.dalal.providercontentservicepfe.entities.ExpertiseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertiseRepository extends JpaRepository<Expertise, ExpertiseId> {
    void deleteByProviderId(Long providerId);
}
