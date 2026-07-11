package com.dalal.providercontentservicepfe.services;

import java.util.Set;

public interface ExpertiseService {
    void selectServices(Long id, Set<Long> serviceIds);
    void addService(Long id , Long serviceId);
}
