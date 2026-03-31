package com.aluguel.controller;

import com.aluguel.service.HelloService;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/")
public class HelloController {
    HelloService service = new HelloService();
    
    @Get
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return service.exampleMessage();
    }
}
