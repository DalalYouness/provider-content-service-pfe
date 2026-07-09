package com.dalal.providercontentservicepfe.web;

import com.dalal.providercontentservicepfe.dtos.CategoryRequestDTO;
import com.dalal.providercontentservicepfe.dtos.AddCategoryResponseDTO;
import com.dalal.providercontentservicepfe.dtos.CategoryResponseDTO;
import com.dalal.providercontentservicepfe.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/service")
public class CategoryController {
    private final CategoryService categoryService;

    //@ResponseStatus(HttpStatus.CREATED) if we want to
    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") the same but if we use this we have to add ROLE_ , has Role Spring security add automatically ROLE_
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AddCategoryResponseDTO> addCategory(
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ){
        AddCategoryResponseDTO categoryResponseDTO = categoryService.addNewService(categoryRequestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @RequestParam (defaultValue = "0")int page,
            @RequestParam(defaultValue = "10") int size){
        Page<CategoryResponseDTO> allServices =  categoryService.getAllServices(page, size);
        return new ResponseEntity<>(allServices, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Map<String,String>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteService(id);
        Map<String,String> response = new HashMap<>();
        response.put("message", "Service supprimée avec succès");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO categoryResponseDTO = categoryService.updateService(id, categoryRequestDTO);
        return ResponseEntity.ok(categoryResponseDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategoryResponseDTO>> searchServices(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<CategoryResponseDTO> result = categoryService.searchServices(keyword, page, size);
        return ResponseEntity.ok(result);
    }

}
