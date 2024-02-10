package com.codearti.services;

import com.codearti.client.ProductClientRest;
import com.codearti.model.dto.ProductResponseDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Primary
public class ProductServiceFeignImpl implements ProductService {

    private final ProductClientRest clientRest;

    @Override
    public List<ProductResponseDto> findAll() {
        return clientRest.findAll();
    }

    @Override
    public ProductResponseDto findById(Long id) {
        try {
            return clientRest.findById(id);
        }catch (FeignException e){
            if(e.status() == HttpStatus.NOT_FOUND.value()){
                return null;
            }else {
                throw e;
            }
        }
    }
}
