package com.dalal.providercontentservicepfe.security;

import lombok.Builder;

@Builder
public record UserPrincipale(
        Long id,
        String email
) {
}
