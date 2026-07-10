package com.dalal.providercontentservicepfe.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "expertise")
@IdClass(ExpertiseId.class)
public class Expertise {
    @Column(name = "id_provider")
    @Id
    private Long providerId;

    @Id
    @Column
    private Long serviceId;

    @JoinColumn(name = "service_id")
    @ManyToOne
    private Category service;
}
