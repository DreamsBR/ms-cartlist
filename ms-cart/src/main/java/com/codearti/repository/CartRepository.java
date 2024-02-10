package com.codearti.repository;

import com.codearti.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<CartEntity, Long> {

    Optional<CartEntity> findByCustomerId(Long customerId);
}
