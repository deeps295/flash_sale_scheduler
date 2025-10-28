package com.flashsale.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    @Builder.Default
    private boolean active = true;

    @OneToMany(mappedBy = "flashSale", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FlashSaleItem> items = new ArrayList<>();

    public boolean isLive() {
        var now = OffsetDateTime.now();
        return active && startTime != null && endTime != null && !now.isBefore(startTime) && !now.isAfter(endTime);
    }
}
