package com.flashsale.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private FlashSale flashSale;

    @ManyToOne(optional = false)
    private Product product;

    @Column(precision = 12, scale = 2)
    private BigDecimal discountPrice;

    private Integer quantityLimit; // per order limit
}
