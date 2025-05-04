package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/file-path")
    public String getFilePath() {
        ResourceBundle bundle = ResourceBundle.getBundle("custom", Locale.ENGLISH);
        return bundle.getString("filePath");
    }
}
