package com.dalal.providercontentservicepfe.web;

import com.dalal.providercontentservicepfe.dtos.CategoryRequestDTO;
import com.dalal.providercontentservicepfe.dtos.CategoryResponseDTO;
import com.dalal.providercontentservicepfe.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/service")
public class CategoryController {
    private final CategoryService categoryService;

    //@ResponseStatus(HttpStatus.CREATED) if we want to
    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") the same but if we use this we have to add ROLE_ , has Role Spring security add automatically ROLE_
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> addCategory(
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ){
        CategoryResponseDTO categoryResponseDTO = categoryService.addNewService(categoryRequestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }

}
