package com.flashsale.repository;

import com.flashsale.model.FlashSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface FlashSaleRepository extends JpaRepository<FlashSale, Long> {
    List<FlashSale> findByActiveTrueAndStartTimeBeforeAndEndTimeAfter(OffsetDateTime start, OffsetDateTime end);
}
