package com.fintech.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Created 24/01/2025 - 13:02 PM
 * @project SpringSecurityHTTP
 * @Author Sharon
 */

@Controller
@RequestMapping("")
public class HomeController {
	
    @GetMapping("index")
    public String index(){
        return "index";
    }
}
