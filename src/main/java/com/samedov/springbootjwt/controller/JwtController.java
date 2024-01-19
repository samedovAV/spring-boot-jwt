package com.samedov.springbootjwt.controller;

import com.samedov.springbootjwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/token")
public class JwtController {

    private final JwtService jwtService;
    private final String username;

    @Autowired
    public JwtController(JwtService jwtService, @Value("${jwt.username}") String username) {
        this.jwtService = jwtService;
        this.username = username;
    }

    @GetMapping("/generate") // todo add swagger
    public String generateToken() {
        return jwtService.generateToken(username);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
        return jwtService.validateToken(token, username);
    }
}
