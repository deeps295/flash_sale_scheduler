package com.flashsale.service;

import com.flashsale.model.Product;
import com.flashsale.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public long count() { return productRepository.count(); }

    public Product create(Product p) { return productRepository.save(p); }

    public List<Product> findAll() { return productRepository.findAll(); }

    public Product findById(Long id) { return productRepository.findById(id).orElseThrow(); }

    public Product save(Product p) { return productRepository.save(p); }

    @Transactional
    public void adjustStock(Long productId, int delta) {
        var p = productRepository.findById(productId).orElseThrow();
        int newStock = p.getStock() + delta;
        if (newStock < 0) throw new IllegalStateException("Insufficient stock");
        p.setStock(newStock);
    }
}
