package com.flashsale.service;

import com.flashsale.model.Order;
import com.flashsale.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderRepository orderRepository;

    public long totalOrders() { return orderRepository.count(); }

    public BigDecimal totalRevenue() {
        return orderRepository.findAll().stream()
                .map(Order::getTotalAmount)
                .filter(a -> a != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
