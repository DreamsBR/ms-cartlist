package com.codearti.controller;

import com.codearti.model.dto.ProductCreateRequestDto;
import com.codearti.model.dto.ProductResponseDto;
import com.codearti.model.dto.ProductUpdateRequestDto;
import com.codearti.model.dto.ProductUpdateStockRequestDto;
import com.codearti.model.entity.ProductStatus;
import com.codearti.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "v1")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    private final ServletWebServerApplicationContext webServerAppCtxt;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<List<ProductResponseDto>> findAll(@RequestParam(required = false) ProductStatus status){
        var port = webServerAppCtxt.getWebServer().getPort();
        var result = productService.findall(status, port);
        if(result.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<ProductResponseDto> findById(@PathVariable Long id){
        var port = webServerAppCtxt.getWebServer().getPort();
        var result = productService.findProductById(id, port);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "category/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<List<ProductResponseDto>> findByCategory(@PathVariable Long id){
        var port = webServerAppCtxt.getWebServer().getPort();
        var result = productService.findProductByCateogry(id, port);
        return ResponseEntity.ok(result);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<ProductResponseDto> saveProduct(@Valid @RequestBody ProductCreateRequestDto product){
        var port = webServerAppCtxt.getWebServer().getPort();
        var result = productService.createProduct(product, port);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<ProductResponseDto> UpdateProduct(@PathVariable Long id,@Valid  @RequestBody ProductUpdateRequestDto updateRequest){
        var port = webServerAppCtxt.getWebServer().getPort();
        var result = productService.update(id,updateRequest,port);
        return ResponseEntity.ok(result);
    }

    @PatchMapping(path = "/{id}/stock", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE})
    public ResponseEntity<ProductResponseDto> UpdateStockProduct(@PathVariable Long id,@Valid  @RequestBody ProductUpdateStockRequestDto updateRequest){
        var port = webServerAppCtxt.getWebServer().getPort();
        var result = productService.updateStock(id,updateRequest,port);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        var port = webServerAppCtxt.getWebServer().getPort();
        productService.deleteById(id, port);
        return ResponseEntity.noContent().build();
    }









}
