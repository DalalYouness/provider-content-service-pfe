package com.dalal.providercontentservicepfe.services;

import com.dalal.providercontentservicepfe.dtos.CategoryRequestDTO;
import com.dalal.providercontentservicepfe.dtos.AddCategoryResponseDTO;
import com.dalal.providercontentservicepfe.dtos.CategoryResponseDTO;
import com.dalal.providercontentservicepfe.entities.Category;
import com.dalal.providercontentservicepfe.exceptions.CategoryException;
import com.dalal.providercontentservicepfe.exceptions.ServiceAlreadyExistsException;
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
    public CategoryResponseDTO updateService(Long id, CategoryRequestDTO categoryRequestDTO) {

        // 1 - On vérifie d'abord si le service existe bien pour faire la mise a jour (Sinon: Exception)
        Category existingService = categoryRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service introuvable"));

        // Normalisation du nom (Suppression des espaces et passage en minuscules)
        String newServiceName = categoryRequestDTO.serviceName().trim().toLowerCase();

        // 2 - On vérifie si le nouveau nom existe deja chez un AUTRE service (On exclut l'ID actuel)
        // Cela évite de bloquer l'Admin s'il veut modifier uniquement la description sans changer le nom
        boolean nameExists = categoryRepository.existsByServiceNameAndIdNot(newServiceName, id);
        if (nameExists) {
            throw new ServiceAlreadyExistsException("Un autre service avec le nom '" + categoryRequestDTO.serviceName() + "' existe deja.");
        }

        // 3 - Si tout est correct, on applique les modifications et on sauvegarde
        existingService.setServiceName(newServiceName);
        existingService.setDescription(categoryRequestDTO.description());

        Category updatedService = categoryRepository.save(existingService);

        // 4 - Retour du résultat sous forme de DTO
        return categoryMapper.toCategoryResponseDTO(updatedService);
    }

    @Override
    public Page<CategoryResponseDTO> searchServices(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Category> categories = categoryRepository.findByServiceNameContainingIgnoreCase(keyword.trim(), pageable);
        return categories.map(categoryMapper::toCategoryResponseDTO);
    }


}
