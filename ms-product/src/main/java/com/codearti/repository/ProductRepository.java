package com.codearti.repository;

import com.codearti.model.entity.CategoryEntity;
import com.codearti.model.entity.DeleteProduct;
import com.codearti.model.entity.ProductEntity;
import com.codearti.model.entity.ProductStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    @Query("from ProductEntity where deleted = com.codearti.model.entity.DeleteProduct.CREATED and ((:status is null) or (status = :status))")
    List<ProductEntity> findAllByStatus(@Param("status") ProductStatus status);

    List<ProductEntity> findByCategoryAndDeleted(CategoryEntity category, DeleteProduct deleted);
}
