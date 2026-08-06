package com.dalal.providercontentservicepfe.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "IDENTITY-SERVICE-PFE")
public interface IdentityClient {
}
