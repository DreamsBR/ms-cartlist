package com.codearti.model.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.sql.Delete;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double stock;

    private BigDecimal price;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @CreationTimestamp
    private LocalDateTime updateDate;

    private ProductStatus status;

    private DeleteProduct deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @PrePersist
    void setPrePersist(){
        status = ProductStatus.NEW;
        deleted = DeleteProduct.CREATED;
    }

}
