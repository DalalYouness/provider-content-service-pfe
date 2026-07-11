package com.dalal.providercontentservicepfe.entities;


import java.io.Serializable;


public record ExpertiseId(
        Long providerId,
        Long serviceId
) implements Serializable {
}
