package com.flashsale.controller;

import com.flashsale.repository.OrderRepository;
import com.flashsale.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final OrderRepository orderRepository;

    @GetMapping
    public String reports(Model model) {
        model.addAttribute("totalOrders", reportService.totalOrders());
        model.addAttribute("totalRevenue", reportService.totalRevenue());
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/reports";
    }
}
