package com.bside.app.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/webview")
public class PrivacyWebController {

    @GetMapping("/privacy")
    public String getPrivacy(){
        return "privacy";
    }
}
