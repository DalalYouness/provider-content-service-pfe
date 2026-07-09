package com.dalal.providercontentservicepfe.mappers;


import com.dalal.providercontentservicepfe.dtos.CategoryRequestDTO;
import com.dalal.providercontentservicepfe.dtos.CategoryResponseDTO;
import com.dalal.providercontentservicepfe.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequestDTO categoryRequestDTO);
    @Mapping(source = "serviceName", target = "name")
    CategoryResponseDTO toCategoryResponseDTO(Category category);
}
