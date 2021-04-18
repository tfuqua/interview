package com.interview.unit;

import com.interview.controller.dto.InsertProductDto;
import com.interview.controller.dto.UpdateProductDto;
import com.interview.entity.Product;
import com.interview.exception.BusinessRuleViolationException;
import com.interview.exception.ResourceNotFoundException;
import com.interview.repository.ProductRepository;
import com.interview.repository.ProductSearchRepository;
import com.interview.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductSearchRepository searchRepository;

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    public void testSearchProduct() {
        Product p1 = new Product("name1","category1", "subcategory1", "image1", "product1", "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Product p2 = new Product("name2","category2", "subcategory2", "image2", "product2", "manufacturer2", "desc2", BigDecimal.valueOf(2));
        List<Product> productList = Arrays.asList(p1, p2);
        PageRequest pageRequest = PageRequest.of(0, 10);
        final PageImpl<Product> products = new PageImpl<>(productList);
        final String product = "product";
        when(searchRepository.searchProducts(product, pageRequest)).thenReturn(products);
        Page<Product> searchResults = service.search(product, pageRequest);
        verify(searchRepository).searchProducts(product, pageRequest);
        assertEquals(searchResults.stream().collect(Collectors.toList()), productList);
    }

    @Test
    public void testCreateProduct() {
        InsertProductDto product = new InsertProductDto("name","category", "subCategory", "iUrl", "pUrl", "manufacturer", "desc", BigDecimal.valueOf(1));
        when(repository.existsByNameAndCategoryAndSubCategoryIgnoreCase("name", "category", "subCategory")).thenReturn(false);
        service.create(product);
        ArgumentCaptor<Product> productA = ArgumentCaptor.forClass(Product.class);
        verify(repository).save(productA.capture());
        assertProductFields(productA.getValue(),"name","category","subCategory","iUrl","pUrl","manufacturer","desc",BigDecimal.valueOf(1));
    }

    @Test
    public void whenProductAlreadyExistsInCreate_thenThrowsBusinessRuleViolationException() {
        InsertProductDto productDto = new InsertProductDto("name","category", "subcategory", null, null, "manufacturer1", "desc1", BigDecimal.valueOf(1));
        when(repository.existsByNameAndCategoryAndSubCategoryIgnoreCase("name", "category", "subcategory")).thenReturn(true);
        assertThrows(BusinessRuleViolationException.class, () -> {
            service.create(productDto);
        });
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product("name","category", "subCategory", "iUrl", "pUrl", "manufacturer", "desc", BigDecimal.valueOf(1));
        product.setId(1L);
        when(repository.findByNameAndCategoryAndSubCategoryIgnoreCase("uName", "uCategory", "uSubCategory")).thenReturn(Optional.empty());
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        UpdateProductDto dto = createUpdateProductDto();
        service.update(1L, dto);
        assertProductFields(product,"uName","uCategory","uSubCategory","uIUrl","uPUrl", "uManufacturer","uDesc", BigDecimal.valueOf(1));
        verify(repository).save(product);
    }

    @Test
    public void whenSameCategoryNameAndSubCategoryAlreadyExists_thenThrowException() {
        Product product = new Product("name","category", "subCategory", "iUrl", "pUrl", "manufacturer", "desc", BigDecimal.valueOf(1));
        product.setId(1L);
        Product product2 = new Product("uName","uCategory", "uSubCategory", "iUrl", "pUrl", "manufacturer", "desc", BigDecimal.valueOf(1));
        product.setId(2L);
        when(repository.findByNameAndCategoryAndSubCategoryIgnoreCase("uName", "uCategory", "uSubCategory")).thenReturn(Optional.of(product2));
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        UpdateProductDto dto = createUpdateProductDto();
        assertThrows(BusinessRuleViolationException.class, () -> service.update(1L, dto));
    }

    @Test
    public void whenSubmitWithoutAnyUpdate_thenReturnOk() {
        Product product = new Product("name","category", "subCategory", "iUrl", "pUrl", "manufacturer", "desc", BigDecimal.valueOf(1));
        product.setId(1L);
        when(repository.findByNameAndCategoryAndSubCategoryIgnoreCase("name", "category", "subCategory")).thenReturn(Optional.of(product));
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        UpdateProductDto dto = new UpdateProductDto("name","category", "subCategory", "iUrl", "pUrl", "manufacturer", "desc", BigDecimal.valueOf(1));
        service.update(1L, dto);
        verify(repository).save(product);
    }

    @Test
    public void whenProductNotExist_thenThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        UpdateProductDto dto = createUpdateProductDto();
        assertThrows(ResourceNotFoundException.class, () -> {
            service.update(1L, dto);
        });
    }

    @Test
    public void whenProductNotExistInGetOperation_thenThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(1L);
        });
    }

    @Test
    public void whenProductNotExistInDeleteOperation_thenThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(1L);
        });
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product();
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        service.delete(1l);
        verify(repository).delete(product);
    }

    private UpdateProductDto createUpdateProductDto() {
        return new UpdateProductDto("uName","uCategory", "uSubCategory", "uIUrl", "uPUrl", "uManufacturer", "uDesc", BigDecimal.valueOf(1));
    }

    private void assertProductFields(Product product, String name, String category, String subCategory, String iUrl, String pUrl, String manufacturer, String desc, BigDecimal price) {
        assertEquals(name, product.getName());
        assertEquals(category, product.getCategory());
        assertEquals(subCategory, product.getSubCategory());
        assertEquals(iUrl, product.getImageUrl());
        assertEquals(pUrl, product.getProductUrl());
        assertEquals(desc, product.getDescription());
        assertEquals(manufacturer, product.getManufacturer());
        assertEquals(price, product.getPrice());
    }
}
