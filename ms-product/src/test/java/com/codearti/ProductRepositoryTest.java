package com.codearti;

import com.codearti.model.entity.CategoryEntity;
import com.codearti.model.entity.DeleteProduct;
import com.codearti.model.entity.ProductEntity;
import com.codearti.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.security.cert.CertificateRevokedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void whenGetAll_ThenReturnAllProduct(){
        var list = productRepository.findAllByStatus(null);
        assertNotEquals(4, list.size());
    }

    @Test
    void whenValidSave_ThenReturnProduct(){
        var productCategory=  ProductEntity.builder()
                .name("Teclado")
                .stock(Double.valueOf(12))
                .category(CategoryEntity.builder().id(1L).build())
                .build();

        productRepository.save(productCategory);

        var list = productRepository.findByCategoryAndDeleted(productCategory.getCategory(), DeleteProduct.DELETED);
        assertEquals(1, list.size());

    }

}
