package com.aluguel.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import java.net.URI;

@Controller
public class AuthController {

    @Post("/login")
    public HttpResponse<?> login(@FormValue String login, 
                                  @FormValue String senha,
                                  @FormValue String tipoUsuario) {
        // Simular autenticação
        if ("cliente".equalsIgnoreCase(login) && "123".equals(senha) && "CLIENTE".equals(tipoUsuario)) {
            return HttpResponse.seeOther(URI.create("/dashboard-cliente.html?id=1"));
        } else if ("banco".equalsIgnoreCase(login) && "123".equals(senha) && "BANCO".equals(tipoUsuario)) {
            return HttpResponse.seeOther(URI.create("/dashboard-agente.html?id=2"));
        } else if ("empresa".equalsIgnoreCase(login) && "123".equals(senha) && "EMPRESA".equals(tipoUsuario)) {
            return HttpResponse.seeOther(URI.create("/dashboard-agente.html?id=3"));
        } else {
            return HttpResponse.seeOther(URI.create("/login.html?error=Credenciais%20inválidas"));
        }
    }

    @Post("/register")
    public HttpResponse<?> register(@FormValue String login,
                                     @FormValue String senha,
                                     @FormValue String nome) {
        // Simular registro
        return HttpResponse.seeOther(URI.create("/login.html?success=Usuário%20registrado"));
    }
}

import com.aluguel.model.Cliente;
import com.aluguel.model.Banco;
import com.aluguel.model.Empresa;
import com.aluguel.service.ClienteService;
import com.aluguel.service.AgenteService;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalDateTime;

@Controller
public class AuthController {

    @Inject
    private ResourceLoader resourceLoader;

    @Inject
    private ClienteService clienteService;

    @Inject
    private AgenteService agenteService;

    // GET login page
    @Get("/login")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> loginPage() throws IOException {
        return getTemplateFile("public/login.html");
    }

    // POST login
    @Post("/login")
    public HttpResponse<?> login(@FormValue String login, 
                                  @FormValue String senha,
                                  @FormValue String tipoUsuario) {
        try {
            if (login == null || senha == null || tipoUsuario == null) {
                return redirect("/login.html?error=Todos os campos são obrigatórios");
            }

            // Simular autenticação - em produção seria com banco de dados
            if ("cliente".equalsIgnoreCase(login) && "123".equals(senha) && "CLIENTE".equals(tipoUsuario)) {
                return HttpResponse.seeOther(URI.create("/dashboard-cliente.html?id=1")).header("Set-Cookie", "userId=1");
            } else if ("banco".equalsIgnoreCase(login) && "123".equals(senha) && "BANCO".equals(tipoUsuario)) {
                return HttpResponse.seeOther(URI.create("/dashboard-agente.html?id=2")).header("Set-Cookie", "userId=2");
            } else if ("empresa".equalsIgnoreCase(login) && "123".equals(senha) && "EMPRESA".equals(tipoUsuario)) {
                return HttpResponse.seeOther(URI.create("/dashboard-agente.html?id=3")).header("Set-Cookie", "userId=3");
            } else {
                return redirect("/login.html?error=Credenciais inválidas");
            }
        } catch (Exception e) {
            return redirect("/login.html?error=" + e.getMessage());
        }
    }

    // GET register page
    @Get("/register")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> registerPage() throws IOException {
        return getTemplateFile("public/register.html");
    }

    // POST register
    @Post("/register")
    public HttpResponse<?> register(@FormValue String login,
                                     @FormValue String senha,
                                     @FormValue String nome,
                                     @FormValue String endereco,
                                     @FormValue String tipoUsuario,
                                     @FormValue(required = false) String cpf,
                                     @FormValue(required = false) String rg,
                                     @FormValue(required = false) String profissao,
                                     @FormValue(required = false) String cnpj) {
        try {
            if (login == null || senha == null || nome == null) {
                return redirect("/register.html?error=Campos obrigatórios vazios");
            }

            // Criar novo cliente
            Cliente cliente = new Cliente();
            cliente.setLogin(login);
            cliente.setSenha(senha);
            cliente.setNome(nome);
            cliente.setEndereco(endereco);
            if (cpf != null) cliente.setCpf(cpf);
            if (rg != null) cliente.setRg(rg);
            if (profissao != null) cliente.setProfissao(profissao);

            clienteService.criar(cliente);

            return redirect("/login.html?success=Usuário registrado com sucesso");
        } catch (Exception e) {
            return redirect("/register.html?error=" + e.getMessage());
        }
    }

    private HttpResponse<?> redirect(String path) {
        return HttpResponse.seeOther(URI.create(path));
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
package com.aluguel.controller;

import com.aluguel.model.*;
import com.aluguel.service.*;
import com.aluguel.util.ThymeleafTemplateEngine;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.cookie.Cookie;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.*;

@Controller
public class AuthController {

    @Inject
    private ClienteService clienteService;

    @Inject
    private AgenteService agenteService;

    @Inject
    private ThymeleafTemplateEngine templateEngine;

    @Get("/")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> index() {
        String html = templateEngine.render("login", new HashMap<>());
        return HttpResponse.ok(html);
    }

    @Get("/login")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> login(@QueryValue(required = false) String error,
                                       @QueryValue(required = false) String success) {
        Map<String, Object> model = new HashMap<>();
        if (error != null) model.put("error", error);
        if (success != null) model.put("success", success);
        String html = templateEngine.render("login", model);
        return HttpResponse.ok(html);
    }

    @Post("/login")
    public HttpResponse<?> authenticate(@FormValue String login, 
                                         @FormValue String senha,
                                         @FormValue String tipoUsuario) {
        try {
            if ("CLIENTE".equals(tipoUsuario)) {
                for (Cliente c : clienteService.listar()) {
                    if (c.getLogin().equals(login) && c.autenticar(login, senha)) {
                        return HttpResponse.seeOther(URI.create("/dashboard-cliente"))
                                .cookie(Cookie.of("currentUserId", c.getId().toString()))
                                .cookie(Cookie.of("userType", "CLIENTE"));
                    }
                }
            } else if ("BANCO".equals(tipoUsuario) || "EMPRESA".equals(tipoUsuario)) {
                for (Agente a : agenteService.listar()) {
                    if (a.getLogin().equals(login) && a.autenticar(login, senha)) {
                        return HttpResponse.seeOther(URI.create("/dashboard-agente"))
                                .cookie(Cookie.of("currentUserId", a.getId().toString()))
                                .cookie(Cookie.of("userType", tipoUsuario));
                    }
                }
            }
            return HttpResponse.redirect(URI.create("/login?error=Usuário ou senha inválidos"));
        } catch (Exception e) {
            return HttpResponse.redirect(URI.create("/login?error=" + e.getMessage()));
        }
    }

    @Get("/register")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> register(@QueryValue(required = false) String error) {
        Map<String, Object> model = new HashMap<>();
        if (error != null) model.put("error", error);
        String html = templateEngine.render("register", model);
        return HttpResponse.ok(html);
    }

    @Post("/register")
    public HttpResponse<?> criar(@FormValue String tipoUsuario,
                                  @FormValue String nome,
                                  @FormValue String login,
                                  @FormValue String senha,
                                  @FormValue String endereco,
                                  @FormValue(required = false) String rg,
                                  @FormValue(required = false) String cpf,
                                  @FormValue(required = false) String profissao,
                                  @FormValue(required = false) String cnpj) {
        try {
            if ("CLIENTE".equals(tipoUsuario)) {
                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setLogin(login);
                cliente.setSenha(senha);
                cliente.setEndereco(endereco);
                cliente.setRg(rg);
                cliente.setCpf(cpf);
                cliente.setProfissao(profissao);
                clienteService.criar(cliente);
                return HttpResponse.redirect(URI.create("/login?success=Cliente cadastrado com sucesso!"));
            } else if ("BANCO".equals(tipoUsuario)) {
                Banco agente = new Banco();
                agente.setNome(nome);
                agente.setLogin(login);
                agente.setSenha(senha);
                agente.setEndereco(endereco);
                agente.setCnpj(cnpj);
                agenteService.criar(agente);
                return HttpResponse.redirect(URI.create("/login?success=Banco cadastrado com sucesso!"));
            } else if ("EMPRESA".equals(tipoUsuario)) {
                Empresa agente = new Empresa();
                agente.setNome(nome);
                agente.setLogin(login);
                agente.setSenha(senha);
                agente.setEndereco(endereco);
                agente.setCnpj(cnpj);
                agenteService.criar(agente);
                return HttpResponse.redirect(URI.create("/login?success=Empresa cadastrada com sucesso!"));
            }
            return HttpResponse.redirect(URI.create("/register?error=Tipo de usuário inválido"));
        } catch (IllegalArgumentException e) {
            return HttpResponse.redirect(URI.create("/register?error=" + e.getMessage()));
        } catch (Exception e) {
            return HttpResponse.redirect(URI.create("/register?error=Erro ao cadastrar"));
        }
    }

    @Post("/logout")
    public HttpResponse<?> logout() {
        return HttpResponse.seeOther(URI.create("/login"))
                .cookie(Cookie.of("currentUserId", "").maxAge(0))
                .cookie(Cookie.of("userType", "").maxAge(0));
    }
}
package com.aluguel.controller;

import com.aluguel.model.Cliente;
import com.aluguel.model.Agente;
import com.aluguel.model.Banco;
import com.aluguel.model.Empresa;
import com.aluguel.service.ClienteService;
import com.aluguel.service.AgenteService;
import com.aluguel.util.ThymeleafTemplateEngine;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.cookie.Cookie;
import jakarta.inject.Inject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Inject
    private ClienteService clienteService;

    @Inject
    private AgenteService agenteService;

    @Inject
    private ThymeleafTemplateEngine templateEngine;

    @Get("/")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> index() {
        Map<String, Object> model = new HashMap<>();
        String html = templateEngine.render("login", model);
        return HttpResponse.ok(html);
    }

    @Get("/login")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> login(@QueryValue(required = false) String error,
                                       @QueryValue(required = false) String success) {
        Map<String, Object> model = new HashMap<>();
        if (error != null) model.put("error", error);
        if (success != null) model.put("success", success);
        String html = templateEngine.render("login", model);
        return HttpResponse.ok(html);
    }

    @Post("/login")
    public HttpResponse<?> authenticate(@FormValue String login, 
                                         @FormValue String senha,
                                         @FormValue String tipoUsuario) {
        try {
            if ("CLIENTE".equals(tipoUsuario)) {
                Cliente cliente = clienteService.obter(1L); // Buscar por CPF/login
                for (Cliente c : clienteService.listar()) {
                    if (c.getLogin().equals(login) && c.autenticar(login, senha)) {
                        return HttpResponse.seeOther(URI.create("/dashboard-cliente"))
                                .cookie(Cookie.of("currentUserId", c.getId().toString()))
                                .cookie(Cookie.of("userType", "CLIENTE"));
                    }
                }
            } else if ("BANCO".equals(tipoUsuario) || "EMPRESA".equals(tipoUsuario)) {
                for (Agente a : agenteService.listar()) {
                    if (a.getLogin().equals(login) && a.autenticar(login, senha)) {
                        return HttpResponse.seeOther(URI.create("/dashboard-agente"))
                                .cookie(Cookie.of("currentUserId", a.getId().toString()))
                                .cookie(Cookie.of("userType", tipoUsuario));
                    }
                }
            }
            
            return HttpResponse.redirect(URI.create("/login?error=Usuário ou senha inválidos"));
        } catch (Exception e) {
            return HttpResponse.redirect(URI.create("/login?error=" + e.getMessage()));
        }
    }

    @Get("/register")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> register(@QueryValue(required = false) String error) {
        Map<String, Object> model = new HashMap<>();
        if (error != null) model.put("error", error);
        String html = templateEngine.render("register", model);
        return HttpResponse.ok(html);
    }

    @Post("/register")
    public HttpResponse<?> criar(@FormValue String tipoUsuario,
                                  @FormValue String nome,
                                  @FormValue String login,
                                  @FormValue String senha,
                                  @FormValue String endereco,
                                  @FormValue(required = false) String rg,
                                  @FormValue(required = false) String cpf,
                                  @FormValue(required = false) String profissao,
                                  @FormValue(required = false) String cnpj) {
        try {
            if ("CLIENTE".equals(tipoUsuario)) {
                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setLogin(login);
                cliente.setSenha(senha);
                cliente.setEndereco(endereco);
                cliente.setRg(rg);
                cliente.setCpf(cpf);
                cliente.setProfissao(profissao);
                
                clienteService.criar(cliente);
                return HttpResponse.redirect(URI.create("/login?success=Cliente cadastrado com sucesso! Faça login para continuar."));
            } else if ("BANCO".equals(tipoUsuario)) {
                Banco agente = new Banco();
                agente.setNome(nome);
                agente.setLogin(login);
                agente.setSenha(senha);
                agente.setEndereco(endereco);
                agente.setCnpj(cnpj);
                
                agenteService.criar(agente);
                return HttpResponse.redirect(URI.create("/login?success=Banco cadastrado com sucesso! Faça login para continuar."));
            } else if ("EMPRESA".equals(tipoUsuario)) {
                Empresa agente = new Empresa();
                agente.setNome(nome);
                agente.setLogin(login);
                agente.setSenha(senha);
                agente.setEndereco(endereco);
                agente.setCnpj(cnpj);
                
                agenteService.criar(agente);
                return HttpResponse.redirect(URI.create("/login?success=Empresa cadastrada com sucesso! Faça login para continuar."));
            }
            
            return HttpResponse.redirect(URI.create("/register?error=Tipo de usuário inválido"));
        } catch (IllegalArgumentException e) {
            return HttpResponse.redirect(URI.create("/register?error=" + e.getMessage()));
        } catch (Exception e) {
            return HttpResponse.redirect(URI.create("/register?error=Erro ao cadastrar: " + e.getMessage()));
        }
    }

    @Post("/logout")
    public HttpResponse<?> logout() {
        return HttpResponse.seeOther(URI.create("/login"))
                .cookie(Cookie.of("currentUserId", "").maxAge(0))
                .cookie(Cookie.of("userType", "").maxAge(0));
    }
}
package com.aluguel.controller;

import com.aluguel.model.Cliente;
import com.aluguel.model.Agente;
import com.aluguel.service.ClienteService;
import com.aluguel.service.AgenteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.views.View;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Inject
    private ClienteService clienteService;

    @Inject
    private AgenteService agenteService;

    @Get("/")
    @View("login")
    public HttpResponse<?> index() {
        return HttpResponse.ok();
    }

    @Get("/login")
    @View("login")
    public HttpResponse<?> login() {
        return HttpResponse.ok();
    }

    @Post("/login")
    public HttpResponse<?> authenticate(@QueryValue String login, 
                                         @QueryValue String senha,
                                         @QueryValue String tipoUsuario) {
        try {
            Map<String, Object> model = new HashMap<>();
            
            if ("CLIENTE".equals(tipoUsuario)) {
                // Buscar cliente
                Cliente cliente = clienteService.obterPorCpf(login);
                if (cliente != null && cliente.autenticar(login, senha)) {
                    // Armazenar na sessão
                    return HttpResponse.seeOther(java.net.URI.create("/dashboard-cliente"))
                            .cookie(io.micronaut.http.cookie.Cookie.of("currentUser", cliente.getId().toString()))
                            .cookie(io.micronaut.http.cookie.Cookie.of("userType", "CLIENTE"));
                }
            } else if ("BANCO".equals(tipoUsuario) || "EMPRESA".equals(tipoUsuario)) {
                // Buscar agente
                Agente agente = agenteService.obterPorCnpj(login);
                if (agente != null && agente.autenticar(login, senha)) {
                    return HttpResponse.seeOther(java.net.URI.create("/dashboard-agente"))
                            .cookie(io.micronaut.http.cookie.Cookie.of("currentUser", agente.getId().toString()))
                            .cookie(io.micronaut.http.cookie.Cookie.of("userType", tipoUsuario));
                }
            }
            
            model.put("error", "Usuário ou senha inválidos");
            return HttpResponse.ok(model);
        } catch (Exception e) {
            Map<String, Object> model = new HashMap<>();
            model.put("error", "Erro ao autenticar: " + e.getMessage());
            return HttpResponse.ok(model);
        }
    }

    @Get("/register")
    @View("register")
    public HttpResponse<?> register() {
        return HttpResponse.ok();
    }

    @Post("/register")
    public HttpResponse<?> criar(@QueryValue String tipoUsuario,
                                  @QueryValue String nome,
                                  @QueryValue String login,
                                  @QueryValue String senha,
                                  @QueryValue String endereco,
                                  @QueryValue(required = false) String rg,
                                  @QueryValue(required = false) String cpf,
                                  @QueryValue(required = false) String profissao,
                                  @QueryValue(required = false) String cnpj) {
        try {
            Map<String, Object> model = new HashMap<>();
            
            if ("CLIENTE".equals(tipoUsuario)) {
                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setLogin(login);
                cliente.setSenha(senha);
                cliente.setEndereco(endereco);
                cliente.setRg(rg);
                cliente.setCpf(cpf);
                cliente.setProfissao(profissao);
                
                clienteService.criar(cliente);
                model.put("success", "Cliente cadastrado com sucesso! Faça login para continuar.");
                model.put("tipoUsuario", "");
                return HttpResponse.seeOther(java.net.URI.create("/login"));
            } else if ("BANCO".equals(tipoUsuario) || "EMPRESA".equals(tipoUsuario)) {
                Agente agente = "BANCO".equals(tipoUsuario) ? 
                    new com.aluguel.model.Banco() : 
                    new com.aluguel.model.Empresa();
                agente.setNome(nome);
                agente.setLogin(login);
                agente.setSenha(senha);
                agente.setEndereco(endereco);
                agente.setCnpj(cnpj);
                
                agenteService.criar(agente);
                model.put("success", "Agente cadastrado com sucesso! Faça login para continuar.");
                return HttpResponse.seeOther(java.net.URI.create("/login"));
            }
            
            model.put("error", "Tipo de usuário inválido");
            return HttpResponse.ok(model);
        } catch (IllegalArgumentException e) {
            Map<String, Object> model = new HashMap<>();
            model.put("error", e.getMessage());
            return HttpResponse.ok(model);
        } catch (Exception e) {
            Map<String, Object> model = new HashMap<>();
            model.put("error", "Erro ao cadastrar: " + e.getMessage());
            return HttpResponse.ok(model);
        }
    }

    @Post("/logout")
    public HttpResponse<?> logout() {
        return HttpResponse.seeOther(java.net.URI.create("/login"))
                .cookie(io.micronaut.http.cookie.Cookie.of("currentUser", "").maxAge(0))
                .cookie(io.micronaut.http.cookie.Cookie.of("userType", "").maxAge(0));
    }
}
