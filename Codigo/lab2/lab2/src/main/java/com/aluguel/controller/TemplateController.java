package com.aluguel.controller;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Controller
public class TemplateController {

    @Inject
    private ResourceLoader resourceLoader;

    @Get("/login.html")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> loginHtml() throws IOException {
        return getTemplateFile("public/login.html");
    }

    @Get("/register.html")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> registerHtml() throws IOException {
        return getTemplateFile("public/register.html");
    }

    @Get("/dashboard-cliente.html")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> dashboardCliente() throws IOException {
        return getTemplateFile("public/dashboard-cliente.html");
    }

    @Get("/dashboard-agente.html")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> dashboardAgente() throws IOException {
        return getTemplateFile("public/dashboard-agente.html");
    }

    @Get("/index.html")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> index() throws IOException {
        return getTemplateFile("public/index.html");
    }

    @Get("/layout.html")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> layout() throws IOException {
        return getTemplateFile("public/layout.html");
    }

    @Post("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> login() {
        // Redirecionar para dashboard cliente por padrão
        return HttpResponse.seeOther(URI.create("/dashboard-cliente.html"));
    }

    @Post("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public HttpResponse<?> register() {
        // Redirecionar para login após registro
        return HttpResponse.seeOther(URI.create("/login.html?success=Usuário%20registrado"));
    }

    private HttpResponse<String> getTemplateFile(String path) throws IOException {
        var resource = resourceLoader.getResource("classpath:" + path);
        if (resource.isPresent()) {
            try (InputStream is = resource.get().openStream()) {
                String content = new String(is.readAllBytes(), "UTF-8");
                return HttpResponse.ok(content);
            }
        }
        return HttpResponse.notFound();
    }
}
