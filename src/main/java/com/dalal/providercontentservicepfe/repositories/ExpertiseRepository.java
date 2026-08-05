package com.dalal.providercontentservicepfe.repositories;

import com.dalal.providercontentservicepfe.entities.Expertise;
import com.dalal.providercontentservicepfe.entities.ExpertiseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpertiseRepository extends JpaRepository<Expertise, ExpertiseId> {
    void deleteByProviderId(Long providerId);
    List<Expertise> findByServiceId(Long serviceId);
}
