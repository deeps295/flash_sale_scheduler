package com.flashsale.controller;

import com.flashsale.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public String placeOrder(@RequestParam Long saleItemId,
                             @RequestParam int quantity,
                             @RequestParam String customerName,
                             @RequestParam String customerEmail) {
        var order = orderService.placeOrder(saleItemId, quantity, customerName, customerEmail);
        return "redirect:/order/success/" + order.getId();
    }

    @GetMapping("/order/success/{id}")
    public String success(@PathVariable Long id, Model model) {
        model.addAttribute("orderId", id);
        return "order_success";
    }
}
