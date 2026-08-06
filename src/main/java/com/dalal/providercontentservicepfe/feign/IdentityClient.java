package com.dalal.providercontentservicepfe.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "IDENTITY-SERVICE-PFE")
public interface IdentityClient {
    @PostMapping("/api/v1/auth/public-profils/batch")
    public List<PrestataireMinResponseDto> getPrestatairesPublicProfilesByIds(List<Long> ids) {

    }
}
