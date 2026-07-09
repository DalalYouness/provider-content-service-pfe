package com.dalal.providercontentservicepfe.services;

import com.dalal.providercontentservicepfe.dtos.CategoryRequestDTO;
import com.dalal.providercontentservicepfe.dtos.AddCategoryResponseDTO;
import com.dalal.providercontentservicepfe.entities.Category;
import com.dalal.providercontentservicepfe.exceptions.CategoryException;
import com.dalal.providercontentservicepfe.mappers.CategoryMapper;
import com.dalal.providercontentservicepfe.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    void addNewService_ShouldCreateCategorySuccessfully_WhenCategoryDoesNotExist() {

        CategoryRequestDTO requestDto = new CategoryRequestDTO("  Plomberie  ", "Service de plomberie");
        String cleanedName = requestDto.serviceName().trim().toLowerCase();

        Category mockCategory = new Category();
        mockCategory.setServiceName(requestDto.serviceName());
        mockCategory.setDescription(requestDto.description());

        Mockito.when(categoryRepository.existsByServiceName(cleanedName)).thenReturn(false);
        Mockito.when(categoryMapper.toEntity(requestDto)).thenReturn(mockCategory);
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(mockCategory);

        AddCategoryResponseDTO response = categoryService.addNewService(requestDto);

        assertNotNull(response);
        assertEquals("service créé avec succès", response.message());
        assertEquals("plomberie", mockCategory.getServiceName());

        Mockito.verify(categoryRepository, Mockito.times(1)).save(mockCategory);
    }

    @Test
    void addNewService_ShouldThrowCategoryException_WhenCategoryAlreadyExists() {

        CategoryRequestDTO requestDto = new CategoryRequestDTO("Plomberie", "Service de plomberie");
        String cleanedName = "plomberie";

        Mockito.when(categoryRepository.existsByServiceName(cleanedName)).thenReturn(true);

        CategoryException exception = assertThrows(CategoryException.class, () -> {
            categoryService.addNewService(requestDto);
        });

        assertEquals("Service dèjà exist", exception.getMessage());


        Mockito.verify(categoryMapper, Mockito.never()).toEntity(any());
        Mockito.verify(categoryRepository, Mockito.never()).save(any());
    }
}