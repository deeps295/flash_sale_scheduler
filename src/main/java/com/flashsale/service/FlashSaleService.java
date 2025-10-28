package com.flashsale.service;

import com.flashsale.model.*;
import com.flashsale.repository.FlashSaleItemRepository;
import com.flashsale.repository.FlashSaleRepository;
import com.flashsale.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlashSaleService {
    private final FlashSaleRepository flashSaleRepository;
    private final FlashSaleItemRepository flashSaleItemRepository;
    private final ProductRepository productRepository;

    public FlashSale createSale(String name, OffsetDateTime start, OffsetDateTime end, boolean active) {
        var sale = FlashSale.builder().name(name).startTime(start).endTime(end).active(active).build();
        return flashSaleRepository.save(sale);
    }

    public FlashSale get(Long id) { return flashSaleRepository.findById(id).orElseThrow(); }

    public List<FlashSale> getActiveSales() {
        var now = OffsetDateTime.now();
        return flashSaleRepository.findByActiveTrueAndStartTimeBeforeAndEndTimeAfter(now, now);
    }

    public FlashSaleItem addItem(Long saleId, Long productId, BigDecimal discountPrice, Integer limit) {
        var sale = flashSaleRepository.findById(saleId).orElseThrow();
        var product = productRepository.findById(productId).orElseThrow();
        var item = FlashSaleItem.builder()
                .flashSale(sale)
                .product(product)
                .discountPrice(discountPrice)
                .quantityLimit(limit == null ? 5 : limit)
                .build();
        return flashSaleItemRepository.save(item);
    }

    public List<FlashSaleItem> items(Long saleId) { return flashSaleItemRepository.findByFlashSaleId(saleId); }
}
