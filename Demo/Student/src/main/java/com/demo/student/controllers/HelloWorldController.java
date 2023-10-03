package com.demo.student.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HelloWorldController {

    @GetMapping("hello-world")
    public String getHelloWorld() {
        return "Hello World";
    }

    @GetMapping("update-hello-world")
    public String updateHelloWorld() {
        return "Update Hello World";
    }

}
