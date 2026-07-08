package com.dalal.providercontentservicepfe.services;


import com.dalal.providercontentservicepfe.dtos.CategoryRequestDTO;
import com.dalal.providercontentservicepfe.dtos.CategoryResponseDTO;
import com.dalal.providercontentservicepfe.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    // admin operations
    CategoryResponseDTO addNewService(CategoryRequestDTO category);
    void updateService(CategoryRequestDTO newCategory);
    void deleteService(CategoryRequestDTO category);
    Page<Category> findAllCategory(Pageable pageable);
}
