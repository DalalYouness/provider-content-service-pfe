package com.dalal.providercontentservicepfe.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "portfolio_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(length = 255)
    private String description;

    /*logical fk*/
    @Column(name = "id_provider")
    private Long idProvider;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @PrePersist
    public void onCreate () {
        this.createdAt = LocalDateTime.now();
    }
}
