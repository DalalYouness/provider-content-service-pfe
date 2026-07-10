package com.dalal.providercontentservicepfe.entities;


import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", nullable = false, unique = true, length = 100)
    private String serviceName;

    @Column(columnDefinition = "TEXT")
    private String description;

}