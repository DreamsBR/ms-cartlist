package com.codearti;

import com.codearti.model.entity.CategoryEntity;
import com.codearti.model.entity.DeleteProduct;
import com.codearti.model.entity.ProductEntity;
import com.codearti.model.mapper.ProductMapper;
import com.codearti.repository.ProductRepository;
import com.codearti.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void setUp(){
        ProductEntity productEntity = new ProductEntity().builder()
                .id(1L)
                .name("Teclado2")
                .stock(Double.valueOf(110))
                .price(BigDecimal.valueOf(10))
                .category(CategoryEntity.builder().id(1L).build())
                .deleted(DeleteProduct.CREATED)
                .build();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(productEntity));
    }
    @Test
    public void whenValidGetId_thenReturnProduct(){
        var prod = productService.findProductById(1L, 10);
        assertEquals("Teclado2", prod.getName());
    }






}
