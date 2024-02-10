package com.codearti.services;

import com.codearti.model.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    public List<ProductResponseDto> findAll();

    public ProductResponseDto findById(Long id);

}
