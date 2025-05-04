package com.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final Properties properties;

    public ConfigController() {
        this.properties = new Properties();
        ResourceBundle bundle = ResourceBundle.getBundle("custom", Locale.ENGLISH);
        String filePath = bundle.getString("filePath");
        System.out.println(filePath);
        try (FileInputStream fis = new FileInputStream(filePath)) {
            this.properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load properties file", e);
        }
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> getProperty(@PathVariable String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(value);
    }
}
