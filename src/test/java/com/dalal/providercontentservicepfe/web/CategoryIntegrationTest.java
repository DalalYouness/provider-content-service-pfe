package com.dalal.providercontentservicepfe.web;

import com.dalal.providercontentservicepfe.dtos.CategoryRequestDTO;
import com.dalal.providercontentservicepfe.entities.Category;
import com.dalal.providercontentservicepfe.repositories.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // for run the whole app because it is a integration test
@AutoConfigureMockMvc // mock mvc our client , create a mockMvc object
class CategoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository; // we use autowired because our test works while the app is running

    @Autowired
    private ObjectMapper objectMapper; // object who is responsable for json serialization

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void integration_AddNewService_Success() throws Exception {

        CategoryRequestDTO requestDto = new CategoryRequestDTO("  Plomberie  ", "Service de plomberie");

        mockMvc.perform(post("/api/v1/service/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("service créé avec succès"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void integration_AddNewService_ShouldFail_WhenNameIsBlank() throws Exception {

        CategoryRequestDTO invalidDto = new CategoryRequestDTO("", "Description");
        mockMvc.perform(post("/api/v1/service/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void integration_AddNewService_ShouldFail_WhenCategoryAlreadyExists() throws Exception {

        Category existingCategory = new Category();
        existingCategory.setServiceName("plomberie");
        existingCategory.setDescription("Ancien service");
        categoryRepository.save(existingCategory);

        CategoryRequestDTO duplicateDto = new CategoryRequestDTO("  Plomberie  ", "Nouveau service");
        mockMvc.perform(post("/api/v1/service/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Service dèjà exist"));

        long count = categoryRepository.count();
        assertEquals(1, count, "Il ne doit pas y avoir de doublon dans la base de données !");
    }
}