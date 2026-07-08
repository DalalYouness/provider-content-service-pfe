package com.dalal.providercontentservicepfe.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank(message = "Le nom du service est requis")
        @Size(max = 50, message = "Name too long")
        String serviceName,
        String description
) {}
