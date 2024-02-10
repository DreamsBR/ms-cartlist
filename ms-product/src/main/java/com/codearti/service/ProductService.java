package com.codearti.service;

import com.codearti.configuration.error.ResourceNotFoundException;
import com.codearti.model.dto.ProductCreateRequestDto;
import com.codearti.model.dto.ProductResponseDto;
import com.codearti.model.dto.ProductUpdateRequestDto;
import com.codearti.model.dto.ProductUpdateStockRequestDto;
import com.codearti.model.entity.CategoryEntity;
import com.codearti.model.entity.DeleteProduct;
import com.codearti.model.entity.ProductEntity;
import com.codearti.model.entity.ProductStatus;
import com.codearti.model.mapper.ProductMapper;
import com.codearti.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findall(ProductStatus status, int port){
        log.info("findAll");
        var list = productRepository.findAllByStatus(status);
        log.info("End FindAll");
        return list.stream().map(p -> mapper.entityToResponse(p, port))
                .collect(Collectors.toList());
    };
    @Transactional
    public ProductResponseDto findProductById(Long id, int port){
        log.info("findProductById");
        return  productRepository.findById(id)
                .filter(p->p.getDeleted() == DeleteProduct.CREATED)
                .map(p -> mapper.entityToResponse(p, port))
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Fount", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findProductByCateogry(Long id, int port){
        log.debug("findProductByCategory");
        var lista = productRepository.findByCategoryAndDeleted(CategoryEntity.builder().id(id).build(), DeleteProduct.CREATED);
        return lista.stream()
                .map( p -> mapper.entityToResponse(p , port))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    public ProductResponseDto createProduct(ProductCreateRequestDto product, int port){
        log.info("Create Product");
        var productEntity = mapper.requestToEntity(product);
        productRepository.save(productEntity);
        log.info("saved");
        return mapper.entityToResponse(productEntity , port);
    };

    public ProductResponseDto update(Long id, ProductUpdateRequestDto product, int port){
        var productEntity = getProductEntity(id);
        BeanUtils.copyProperties(product, productEntity);
        productEntity.setStock(productEntity.getStock() + product.getStock());
        productEntity.setCategory(CategoryEntity.builder().id(product.getCategoryId()).build());
        productRepository.save(productEntity);
        log.info("Updated product entity");
        return mapper.entityToResponse(productEntity, port);
    }

    @Transactional
    public ProductResponseDto updateStock(Long id, ProductUpdateStockRequestDto stockUpdate, int port){
        var productEntity = getProductEntity(id);
        productEntity.setStock(productEntity.getStock() + stockUpdate.getStock());
        productRepository.save(productEntity);
        log.info("Updated product entity");
        return mapper.entityToResponse(productEntity, port);

    }

    @Transactional
    public void deleteById(Long id, int port){
        log.info("Delete productentity");
        var productEntity = getProductEntity(id);
        productEntity.setDeleted(DeleteProduct.DELETED);
        productRepository.save(productEntity);
        log.info("Product deleted");
    }


    @Transactional
    public ProductEntity getProductEntity(Long id) {
        log.info("updating product");
        var producEntityOp = productRepository.findById(id).filter(p -> p.getDeleted() == DeleteProduct.CREATED);
        if (!producEntityOp.isPresent()){
            throw  new ResourceNotFoundException("Product Not Found", HttpStatus.NOT_FOUND);
        }
        var productEntity = producEntityOp.get();
        return productEntity;
    }

}
