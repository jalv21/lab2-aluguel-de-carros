package com.aluguel.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.core.io.ResourceResolver;
import jakarta.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
public class StaticController {
    @Inject
    private ResourceResolver resourceResolver;

    @Get("/js/{filename}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<String> serveJs(@PathVariable String filename) {
        try {
            Optional<URL> resource = resourceResolver.getResource("classpath:public/js/" + filename);
            if (resource.isPresent()) {
                String content = new String(resource.get().openStream().readAllBytes(), StandardCharsets.UTF_8);
                String contentType = filename.endsWith(".js") ? "application/javascript" : "text/plain";
                return HttpResponse.ok(content).contentType(contentType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HttpResponse.notFound();
    }

    @Get("/css/{filename}")
    @Produces(MediaType.TEXT_CSS)
    public HttpResponse<String> serveCss(@PathVariable String filename) {
        try {
            Optional<URL> resource = resourceResolver.getResource("classpath:public/css/" + filename);
            if (resource.isPresent()) {
                String content = new String(resource.get().openStream().readAllBytes(), StandardCharsets.UTF_8);
                return HttpResponse.ok(content).contentType(MediaType.TEXT_CSS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HttpResponse.notFound();
    }
}
