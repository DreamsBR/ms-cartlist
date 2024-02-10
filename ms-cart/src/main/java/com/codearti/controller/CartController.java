package com.codearti.controller;


import com.codearti.model.dto.CartEntityResponseDto;
import com.codearti.model.dto.CartItemResponseDto;
import com.codearti.model.dto.CartRequestDeleteDto;
import com.codearti.model.dto.CartRequestDto;
import com.codearti.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("v1")
public class CartController {


    private final CartService cartService;

    @GetMapping(value = "/{customerId}",
            produces = {MediaType.APPLICATION_PROBLEM_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CartEntityResponseDto> findByCustomer(@PathVariable Long customerId){
        return ResponseEntity.ok(cartService.findAll(customerId));
    }
    @PostMapping(value = "/{customerId}/item",
            produces = {MediaType.APPLICATION_PROBLEM_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_PROBLEM_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CartEntityResponseDto> addItem(@PathVariable Long customerId,
                                                         @Valid @RequestBody CartRequestDto cartRequest){
        return ResponseEntity.ok(cartService.addItem(customerId, cartRequest));
    }

    @DeleteMapping(value = "/{customerId}/item",
            produces = {MediaType.APPLICATION_PROBLEM_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_PROBLEM_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CartEntityResponseDto> deleteItem(@PathVariable Long customerId,
                                                         @Valid @RequestBody CartRequestDeleteDto cartRequestDto){
        return ResponseEntity.ok(cartService.deleteItem(customerId,cartRequestDto));
    }


}
