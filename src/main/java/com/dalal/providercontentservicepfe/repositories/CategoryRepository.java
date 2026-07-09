package com.dalal.providercontentservicepfe.repositories;

import com.dalal.providercontentservicepfe.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByServiceName(String name);
    boolean existsByServiceNameAndIdNot(String newServiceName, Long id);
    // spring data jpa by ignore case he would automatically convert the keyword to lower case
    Page<Category> findByServiceNameContainingIgnoreCase(String keyword, Pageable pageable);
}
