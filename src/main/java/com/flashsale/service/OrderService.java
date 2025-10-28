package com.flashsale.service;

import com.flashsale.model.*;
import com.flashsale.repository.FlashSaleItemRepository;
import com.flashsale.repository.OrderItemRepository;
import com.flashsale.repository.OrderRepository;
import com.flashsale.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final FlashSaleItemRepository flashSaleItemRepository;

    @Transactional
    public Order placeOrder(Long saleItemId, int quantity, String customerName, String customerEmail) {
        var saleItem = flashSaleItemRepository.findById(saleItemId).orElseThrow();
        var sale = saleItem.getFlashSale();
        if (!sale.isLive()) throw new IllegalStateException("Flash sale not active");

        var product = saleItem.getProduct();
        if (quantity <= 0) throw new IllegalArgumentException("Invalid quantity");
        if (saleItem.getQuantityLimit() != null && quantity > saleItem.getQuantityLimit())
            throw new IllegalStateException("Exceeds per-order limit");
        if (product.getStock() < quantity) throw new IllegalStateException("Insufficient stock");

        var order = Order.builder()
                .customerName(customerName)
                .customerEmail(customerEmail)
                .status(OrderStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .build();
        order = orderRepository.save(order);

        var item = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .unitPrice(saleItem.getDiscountPrice())
                .build();
        orderItemRepository.save(item);

        var total = saleItem.getDiscountPrice().multiply(BigDecimal.valueOf(quantity));
        order.setTotalAmount(total);

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        order.setStatus(OrderStatus.PAID);
        return order;
    }
}
