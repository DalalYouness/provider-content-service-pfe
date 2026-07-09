package com.dalal.providercontentservicepfe.services;

import com.dalal.providercontentservicepfe.dtos.CategoryRequestDTO;
import com.dalal.providercontentservicepfe.dtos.AddCategoryResponseDTO;
import com.dalal.providercontentservicepfe.dtos.CategoryResponseDTO;
import com.dalal.providercontentservicepfe.entities.Category;
import com.dalal.providercontentservicepfe.exceptions.CategoryException;
import com.dalal.providercontentservicepfe.exceptions.ServiceNotFoundException;
import com.dalal.providercontentservicepfe.mappers.CategoryMapper;
import com.dalal.providercontentservicepfe.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public AddCategoryResponseDTO addNewService(CategoryRequestDTO dto) {

        String cleanedName = dto.serviceName().trim().toLowerCase();
        if (categoryRepository.existsByServiceName(cleanedName)) {
            throw new CategoryException("Service dèjà exist");
        }
        Category category = categoryMapper.toEntity(dto);
        category.setServiceName(cleanedName);
        categoryRepository.save(category);

        return new AddCategoryResponseDTO("service créé avec succès");

    }

    @Override
    public Page<CategoryResponseDTO> getAllServices(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(categoryMapper::toCategoryResponseDTO);
    }

    @Override
    public void deleteService(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ServiceNotFoundException("Impossible de supprimer. Service introuvable.");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public void updateService(CategoryRequestDTO newCategory) {

    }



}
