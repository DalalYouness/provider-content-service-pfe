package com.dalal.providercontentservicepfe.services;

import com.dalal.providercontentservicepfe.dtos.CategoryRequestDTO;
import com.dalal.providercontentservicepfe.dtos.CategoryResponseDTO;
import com.dalal.providercontentservicepfe.entities.Category;
import com.dalal.providercontentservicepfe.exceptions.CategoryException;
import com.dalal.providercontentservicepfe.mappers.CategoryMapper;
import com.dalal.providercontentservicepfe.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDTO addNewService(CategoryRequestDTO dto) {

        String cleanedName = dto.serviceName().trim().toLowerCase();
        if (categoryRepository.existsByServiceName(cleanedName)) {
            throw new CategoryException("Service dèjà exist");
        }
        Category category = categoryMapper.toEntity(dto);
        category.setServiceName(cleanedName);
        categoryRepository.save(category);

        return new CategoryResponseDTO("service créé avec succès");

    }

    @Override
    public void updateService(CategoryRequestDTO newCategory) {

    }

    @Override
    public void deleteService(CategoryRequestDTO category) {

    }

    @Override
    public Page<Category> findAllCategory(Pageable pageable) {
        return null;
    }
}
