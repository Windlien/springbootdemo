package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Hello {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    @GetMapping("/welcomes")
    public Object welcome(){
        return restTemplate.getForObject("http://127.0.0.1:7000/provider/welcomes", String.class);
    }
    @GetMapping("/find")
    public Object find(){
        return restTemplate.getForObject("http://127.0.0.1:7000/find", String.class);
    }

}
