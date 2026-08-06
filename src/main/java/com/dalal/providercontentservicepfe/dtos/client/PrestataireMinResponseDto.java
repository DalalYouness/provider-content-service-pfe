package com.dalal.providercontentservicepfe.dtos.client;

public record PrestataireMinResponseDto(
        Long id,
        String firstName,
        String lastName,
        String gender,
        String city,
        String country,
        String imgUrl
) {
}

