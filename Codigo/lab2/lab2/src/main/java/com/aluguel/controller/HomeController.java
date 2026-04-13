package com.aluguel.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/")
public class HomeController {
    @Get
    public HttpResponse<?> index() {
        return HttpResponse.redirect(java.net.URI.create("/login.html"));
    }
}
