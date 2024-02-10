package com.codearti.services;

import com.codearti.configuration.error.ResourceNotFoundException;
import com.codearti.model.dto.*;
import com.codearti.model.entity.CartEntity;
import com.codearti.model.entity.CartItemEntity;
import com.codearti.model.mapper.CartMapper;
import com.codearti.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    private final  CartMapper mapper;

    private final ProductService productService;

    @Transactional
    public CartEntityResponseDto addItem(Long customerId, CartRequestDto cartRequestDto) {
        Optional<CartEntity> cartOptional = cartRepository.findByCustomerId(customerId);
        if(cartOptional.isEmpty()){
            throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
        }
        CartEntity cartEntity = cartOptional.get();
        cartRequestDto.getItems().forEach(p -> {
            Optional<CartItemEntity> cartItemOptional =  cartEntity.getItems().stream().filter(pt -> pt.getProductId() == p.getProductId()).findFirst();
            if(!cartItemOptional.isPresent()){
                ProductResponseDto product = productService.findById(p.getProductId());
                if(product == null){
                    throw new ResourceNotFoundException("Product not found with id: " + p.getProductId(), HttpStatus.NOT_FOUND);
                }else{
                    log.info("ms-product port:" + product.getPort());
                    cartEntity.getItems().add(mapper.responseToEntity(product,p.getQuantity()));
                }
            }
        });
        cartRepository.save(cartEntity);
        return mapper.entityToResponse(cartEntity);
    }

    @Transactional
    public CartEntityResponseDto findAll(Long customerId){
        log.info("findById");;
        return cartRepository.findByCustomerId(customerId)
                .map(mapper::entityToResponse)
                .orElseThrow(()-> new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND));
    }

    public CartEntityResponseDto deleteItem(Long customerId, CartRequestDeleteDto cartRequestDeleteDto){
        log.info("findById");
        Optional<CartEntity> cartOtional = cartRepository.findByCustomerId(customerId);
        if(cartOtional.isEmpty()){
            throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
        }

        CartEntity cart = cartOtional.get();

        cartRequestDeleteDto.getItem().forEach( p -> {
            cart.getItems().removeIf( pt-> pt.getProductId() == p.getProductId());
        });

        cartRepository.save(cart);

        return mapper.entityToResponse(cart);
    }

}
