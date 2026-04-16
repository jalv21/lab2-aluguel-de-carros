package com.aluguel.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import com.aluguel.service.AgenteService;
import com.aluguel.util.InputValidator;

@Controller
public class AuthController {
    @Inject private AgenteService agenteService;

    @Get("/login")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> showLogin() {
        return HttpResponse.ok(loginHtml(null));
    }

    @Post("/login")
    @Produces(MediaType.TEXT_HTML)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public HttpResponse<String> handleLogin(@Body LoginDTO request) {
        String login = request.getLogin();
        String senha = request.getSenha();
        
        if (login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
            return HttpResponse.ok(loginHtml("Campos obrigatorios"));
        }
        
        String clean_login = InputValidator.sanitizeString(login.trim());
        String clean_senha = InputValidator.sanitizeString(senha.trim());
        
        if (!InputValidator.isValidString(clean_login, 50) || !InputValidator.isValidString(clean_senha, 100)) {
            return HttpResponse.ok(loginHtml("Entrada invalida"));
        }
        
        if ("admin".equals(clean_login) && "admin123".equals(clean_senha)) {
            return HttpResponse.seeOther(java.net.URI.create("/"));
        }
        if ("teste".equals(clean_login) && "teste123".equals(clean_senha)) {
            return HttpResponse.seeOther(java.net.URI.create("/"));
        }
        
        return HttpResponse.ok(loginHtml("Credenciais invalidas"));
    }

    @Get("/logout")
    public HttpResponse<?> handleLogout() {
        return HttpResponse.seeOther(java.net.URI.create("/login"));
    }

    private String loginHtml(String error) {
        String errDiv = "";
        if (error != null && !error.isEmpty()) {
            errDiv = "<div style='color:red;padding:10px;margin:10px 0;background:#ffe0e0;border-radius:4px'>" + error + "</div>";
        }
        return "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Login</title><style>body{font-family:Arial,sans-serif;background:#f0f0f0;display:flex;align-items:center;justify-content:center;height:100vh;margin:0}.login-box{background:white;padding:40px;border-radius:8px;box-shadow:0 2px 10px rgba(0,0,0,0.1);width:350px}h1{text-align:center;color:#333;margin-bottom:30px}input{width:100%;padding:10px;margin:10px 0;border:1px solid #ddd;border-radius:4px;box-sizing:border-box}button{width:100%;padding:10px;margin-top:10px;background:#007bff;color:white;border:none;border-radius:4px;cursor:pointer;font-weight:bold}.demo{margin-top:20px;padding:10px;background:#f0f0f0;border-left:4px solid #007bff;font-size:12px}</style></head><body><div class='login-box'><h1>Sistema de Aluguel de Carros</h1>" + errDiv + "<form method='POST' action='/login'><input type='text' name='login' placeholder='Usuario' required><input type='password' name='senha' placeholder='Senha' required><button type='submit'>Entrar</button></form><div class='demo'><strong>Demo:</strong><br>admin / admin123<br>teste / teste123</div></div></body></html>";
    }

    @Introspected
    @Serdeable
    public static class LoginDTO {
        private String login;
        private String senha;

        public String getLogin() { return login; }
        public void setLogin(String login) { this.login = login; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }
}
