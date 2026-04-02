package com.aluguelcarros.br.controller;

import com.aluguelcarros.br.records.NovoUsuarioRequest;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/usuarios")
public class UsuarioController {
    
    @Post
    public void cadastrar(@Body NovoUsuarioRequest request) {
        System.out.println(request);
    }
}
