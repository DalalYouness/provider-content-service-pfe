package com.dalal.providercontentservicepfe.feign;

import com.dalal.providercontentservicepfe.dtos.client.PrestataireMinResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "IDENTITY-SERVICE-PFE")
public interface IdentityClient {
    @PostMapping("/api/v1/auth/public-profils/batch")
    List<PrestataireMinResponseDto> getAllPrestatairesByIds(@RequestBody List<Long> ids);
}
