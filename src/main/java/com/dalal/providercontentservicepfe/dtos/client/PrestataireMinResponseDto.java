package com.dalal.providercontentservicepfe.dtos.client;

public record PrestataireMinResponseDto(
        Long id,
        String firstName,
        String lastName,
        String gender, // madrtoch enum tafadiyan l sda3
        String city,
        String country,
        String imgUrl
) {
}

