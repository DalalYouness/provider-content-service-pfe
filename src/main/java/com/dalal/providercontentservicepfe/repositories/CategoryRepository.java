package com.dalal.providercontentservicepfe.repositories;

import com.dalal.providercontentservicepfe.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByServiceName(String name);
}
