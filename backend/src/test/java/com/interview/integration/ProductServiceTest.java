package com.interview.integration;

import com.interview.controller.dto.InsertProductDto;
import com.interview.controller.dto.UpdateProductDto;
import com.interview.entity.Product;
import com.interview.repository.ProductRepository;
import com.interview.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService service;

    @Autowired
    ProductRepository repository;

    @Test
    @Transactional
    public void testSearch() {
        InsertProductDto p1 = new InsertProductDto("name1","category1", "subcategory1", "image1", "product1", "manufacturer1", "desc1", BigDecimal.valueOf(1));
        InsertProductDto p2 = new InsertProductDto("name2","category2", "subcategory2", "image2", "product2", "manufacturer2", "desc2", BigDecimal.valueOf(2));
        InsertProductDto p3 = new InsertProductDto("denizToy","category3", "subcategory3", "image3", "product3", "manufacturer3", "desc3", BigDecimal.valueOf(3));
        service.create(p1);
        service.create(p2);
        service.create(p3);
        assertEquals(3,repository.findAll().size());
        Page<Product> result = service.search("Ame", PageRequest.of(0, 10));
        assertEquals(2,result.getNumberOfElements());
    }

    @Test
    @Transactional
    public void whenDoesNotMatch_thenReturnEmptyList() {
        InsertProductDto p1 = new InsertProductDto("name1","category1", "subcategory1", "image1", "product1", "manufacturer1", "desc1", BigDecimal.valueOf(1));
        InsertProductDto p2 = new InsertProductDto("name2","category2", "subcategory2", "image2", "product2", "manufacturer2", "desc2", BigDecimal.valueOf(2));
        InsertProductDto p3 = new InsertProductDto("denizToy","category3", "subcategory3", "image3", "product3", "manufacturer3", "desc3", BigDecimal.valueOf(3));
        service.create(p1);
        service.create(p2);
        service.create(p3);
        assertEquals(3,repository.findAll().size());
        Page<Product> result = service.search("Mehmet", PageRequest.of(0, 10));
        assertEquals(0,result.getNumberOfElements());
    }

    @Test
    @Transactional
    public void whenSearchWithoutTypingAnything_thenReturnEmptyList() {
        InsertProductDto p1 = new InsertProductDto("name1","category1", "subcategory1", "image1", "product1", "manufacturer1", "desc1", BigDecimal.valueOf(1));
        InsertProductDto p2 = new InsertProductDto("name2","category2", "subcategory2", "image2", "product2", "manufacturer2", "desc2", BigDecimal.valueOf(2));
        InsertProductDto p3 = new InsertProductDto("denizToy","category3", "subcategory3", "image3", "product3", "manufacturer3", "desc3", BigDecimal.valueOf(3));
        service.create(p1);
        service.create(p2);
        service.create(p3);
        assertEquals(3,repository.findAll().size());
        Page<Product> result = service.search(null, PageRequest.of(0, 10));
        assertEquals(3,result.getNumberOfElements());
    }

    @Test
    @Transactional
    public void testCreate() {
        InsertProductDto product = new InsertProductDto("name1","category1", "subcategory1", "image1", "product1", "manufacturer1", "desc1", BigDecimal.valueOf(1));
        service.create(product);
        assertEquals(1,repository.findAll().size());
    }

    @Test
    @Transactional
    public void testUpdate() {
        InsertProductDto p = new InsertProductDto("name1","category1", "subcategory1", "image1", "product1", "manufacturer1", "desc1", BigDecimal.valueOf(1));
        UpdateProductDto uP = new UpdateProductDto("name2","category2", "subcategory2", "image1", "product1", "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Product product = service.create(p);
        Product uProduct = service.update(product.getId(), uP);
        assertEquals(uProduct.getName(),"name2");
        assertEquals(uProduct.getCategory(),"category2");
        assertEquals(uProduct.getSubCategory(),"subcategory2");

    }

    @Test
    @Transactional
    public void testDelete() {
        InsertProductDto p = new InsertProductDto("name1","category1", "subcategory1", "image1", "product1", "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Product product = service.create(p);
        assertEquals(1,repository.findAll().size());
        service.delete(product.getId());
        assertEquals(0,repository.findAll().size());
    }

    @Test
    @Transactional
    public void testGet() {
        InsertProductDto p = new InsertProductDto("name1","category1", "subcategory1", "image1", "product1", "manufacturer1", "desc1", BigDecimal.valueOf(1));
        Product product = service.create(p);
        Product sameProduct = service.findById(product.getId());
        assertEquals(sameProduct.getId(), product.getId());
    }

}
