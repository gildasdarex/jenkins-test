package com.pej.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	@GetMapping("/pej/dashboard")
    String index(Model model) {
    	System.out.println("Starting Index Ok");
        return "dashboard";
    }
}
