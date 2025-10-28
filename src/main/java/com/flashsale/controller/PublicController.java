package com.flashsale.controller;

import com.flashsale.service.FlashSaleService;
import com.flashsale.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PublicController {
    private final FlashSaleService flashSaleService;
    private final ProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sales", flashSaleService.getActiveSales());
        model.addAttribute("products", productService.findAll());
        return "index";
    }

    @GetMapping("/sale/{id}")
    public String sale(@PathVariable Long id, Model model) {
        model.addAttribute("sale", flashSaleService.get(id));
        model.addAttribute("items", flashSaleService.items(id));
        return "sale";
    }
}
