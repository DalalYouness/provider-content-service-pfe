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

    @Id
    @Column(name = "id_provider")
    private Long providerId;

    @Id
    @Column(name = "id_service")
    private Long serviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    // it's very important to do insertable and updatable false because at that cas hibernate will insert to service id
    @JoinColumn(name = "id_service", insertable = false, updatable = false)
    private Category service;
}