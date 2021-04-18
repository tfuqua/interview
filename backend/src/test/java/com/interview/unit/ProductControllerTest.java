package com.interview.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.controller.ProductController;
import com.interview.controller.dto.InsertProductDto;
import com.interview.controller.dto.UpdateProductDto;
import com.interview.entity.Product;
import com.interview.exception.BusinessRuleViolationException;
import com.interview.exception.ResourceNotFoundException;
import com.interview.service.MessageBundle;
import com.interview.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ProductService service;

    @Test
    public void testSearch() throws Exception {
        Product p1 = new Product("name1","category1", "subcategory1", "image1", "product1", "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Product p2 = new Product("name2","category2", "subcategory2", "image2", "product2", "manufacturer2", "desc2", BigDecimal.valueOf(2));
        List<Product> productList = Arrays.asList(p1, p2);

        Mockito.when(service.search(null, PageRequest.of(0, 10))).thenReturn(new PageImpl<>(productList));

        mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    public void whenProductNotFound_thenReturnsStatusNotFoundHttpStatus() throws Exception {

        Mockito.when(service.findById(2L)).thenThrow(new ResourceNotFoundException(Product.class, 2L));

        mockMvc.perform(get("/api/products/{id}", 2L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.is("product.not.found")));
    }

    @Test
    public void whenProductAlreadyExistsInCreate_thenReturnsUnprocessableEntityHttpStatus() throws Exception {

        InsertProductDto p1 = new InsertProductDto("name1","category1", "subcategory1", null, null, "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Mockito.when(service.create(p1)).thenThrow(new BusinessRuleViolationException(MessageBundle.SHOULD_BE_UNIQUE,"name","category","subCategory"));

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(p1)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", Matchers.is("should.be.unique")));
    }

    @Test
    public void whenProductAlreadyExistsInUpdate_thenReturnsUnprocessableEntityHttpStatus() throws Exception {

        UpdateProductDto p1 = new UpdateProductDto("name1","category1", "subcategory1", null, null, "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Mockito.when(service.update(1L, p1)).thenThrow(new BusinessRuleViolationException(MessageBundle.SHOULD_BE_UNIQUE_AFTER_UPDATE,"name","category","subCategory"));

        mockMvc.perform(put("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(p1)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code", Matchers.is("should.be.unique.after.update")));
    }

    @Test
    public void whenProductFieldIsNotValidInUpdate_thenReturnsBadRequestHttpStatus() throws Exception {

        UpdateProductDto p1 = new UpdateProductDto("name1","category1", "subcategory1", "deneme", null, "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Mockito.when(service.update(1L, p1)).thenThrow(new BusinessRuleViolationException(MessageBundle.SHOULD_BE_UNIQUE_AFTER_UPDATE,"name","category","subCategory"));

        mockMvc.perform(put("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(p1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is("validation.failed")))
                .andExpect(jsonPath("$.message[0]", Matchers.is("imageUrl field must be a valid URL")));
    }

    @Test
    public void whenProductFieldIsNotValidInCreate_thenReturnsBadRequestHttpStatus() throws Exception {

        InsertProductDto p1 = new InsertProductDto("name1","category1", "subcategory1", "deneme", null, "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Mockito.when(service.create(p1)).thenThrow(new BusinessRuleViolationException(MessageBundle.SHOULD_BE_UNIQUE_AFTER_UPDATE,"name","category","subCategory"));

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(p1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", Matchers.is("validation.failed")))
                .andExpect(jsonPath("$.message[0]", Matchers.is("imageUrl field must be a valid URL")));
    }

    @Test
    public void whenProductFieldsValidInUpdate_thenReturnsOkHttpStatus() throws Exception {

        UpdateProductDto p1 = new UpdateProductDto("name1","category1", "subcategory1", null, null, "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Mockito.when(service.update(1L, p1)).thenReturn(new Product());

        mockMvc.perform(put("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(p1)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void whenProductFieldsValidInCreation_thenReturnsOkHttpStatus() throws Exception {

        InsertProductDto p1 = new InsertProductDto("name1","category1", "subcategory1", null, null, "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Mockito.when(service.create(p1)).thenReturn(new Product());

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(p1)))
                .andExpect(status().is2xxSuccessful());
    }
}