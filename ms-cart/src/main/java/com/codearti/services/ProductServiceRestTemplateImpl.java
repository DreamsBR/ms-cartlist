package com.codearti.services;

import com.codearti.model.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceRestTemplateImpl implements ProductService {

    private final RestTemplate restTemplate;

    private String urlBase = "http://localhost:8001/v1";
    private String urlId = urlBase + "/{id}";

    @Override
    public List<ProductResponseDto> findAll() {
        ResponseEntity<ProductResponseDto[]> responseEntity= restTemplate.getForEntity(urlBase, ProductResponseDto[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    @Override
    public ProductResponseDto findById(Long id) {
        Map<String, String> pathVariable = new HashMap<>();
        try {
            pathVariable.put("id", id.toString());
            ResponseEntity<ProductResponseDto> responseEntity = restTemplate.getForEntity(urlId, ProductResponseDto.class, pathVariable);
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }else {
                throw e;
            }
        }

    }
}
