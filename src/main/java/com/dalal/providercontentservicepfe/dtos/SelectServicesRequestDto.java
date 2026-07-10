package com.dalal.providercontentservicepfe.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record SelectServicesRequestDto(
        @NotEmpty(message = "La liste des services ne doit pas être vide.")
        Set<Long> serviceIds
) {
}
