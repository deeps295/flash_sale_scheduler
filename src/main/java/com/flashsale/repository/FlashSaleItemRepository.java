package com.flashsale.repository;

import com.flashsale.model.FlashSaleItem;
import com.flashsale.model.FlashSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashSaleItemRepository extends JpaRepository<FlashSaleItem, Long> {
    List<FlashSaleItem> findByFlashSaleId(Long flashSaleId);
    List<FlashSaleItem> findByFlashSale(FlashSale sale);
}
