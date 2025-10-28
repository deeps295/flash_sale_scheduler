package com.flashsale.controller;

import com.flashsale.service.FlashSaleService;
import com.flashsale.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ProductService productService;
    private final FlashSaleService flashSaleService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("sales", flashSaleService.getActiveSales());
        return "admin/index";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/products";
    }

    @PostMapping("/products")
    public String createProduct(@RequestParam String name,
                                @RequestParam String description,
                                @RequestParam BigDecimal price,
                                @RequestParam Integer stock) {
        productService.create(com.flashsale.model.Product.builder()
                .name(name).description(description).price(price).stock(stock).active(true).build());
        return "redirect:/admin/products";
    }

    @GetMapping("/sales")
    public String sales(Model model) {
        model.addAttribute("sales", flashSaleService.getActiveSales());
        model.addAttribute("products", productService.findAll());
        return "admin/sales";
    }

    @PostMapping("/sales")
    public String createSale(@RequestParam String name,
                             @RequestParam("start") String startStr,
                             @RequestParam("end") String endStr,
                             @RequestParam(defaultValue = "true") boolean active) {
        LocalDateTime start = LocalDateTime.parse(startStr);
        LocalDateTime end = LocalDateTime.parse(endStr);
        OffsetDateTime s = start.atZone(ZoneId.systemDefault()).toOffsetDateTime();
        OffsetDateTime e = end.atZone(ZoneId.systemDefault()).toOffsetDateTime();
        flashSaleService.createSale(name, s, e, active);
        return "redirect:/admin/sales";
    }

    @PostMapping("/sales/{saleId}/items")
    public String addItem(@PathVariable Long saleId,
                          @RequestParam Long productId,
                          @RequestParam BigDecimal discountPrice,
                          @RequestParam(required = false) Integer quantityLimit) {
        flashSaleService.addItem(saleId, productId, discountPrice, quantityLimit);
        return "redirect:/sale/" + saleId;
    }
}
