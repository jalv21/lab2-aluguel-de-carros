package com.aluguel.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import com.aluguel.service.ClienteService;
import com.aluguel.service.AutomovelService;
import com.aluguel.service.ContratoService;

@Controller
public class HomeController {
    @Inject private ClienteService clienteService;
    @Inject private AutomovelService automovelService;
    @Inject private ContratoService contratoService;

    @Get("/")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> home() {
        int clientCount = clienteService.listar().size();
        int carCount = automovelService.listar().size();
        int contractCount = contratoService.listar().size();
        
        String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Dashboard - Aluguel de Carros</title><style>*{margin:0;padding:0;box-sizing:border-box}body{font-family:Arial,sans-serif;background:#f5f5f5;padding:20px}nav{background:#2c3e50;color:white;padding:15px 20px;border-radius:4px;margin-bottom:30px;display:flex;justify-content:space-between;align-items:center}nav h1{font-size:24px}nav a{color:white;text-decoration:none;margin-left:20px;padding:8px 12px;background:rgba(255,255,255,0.1);border-radius:4px;cursor:pointer;transition:background 0.3s}.container{max-width:1200px;margin:0 auto}.cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(250px,1fr));gap:20px;margin-bottom:30px}.card{background:white;padding:30px;border-radius:8px;box-shadow:0 2px 8px rgba(0,0,0,0.1);text-align:center}.card h2{color:#2c3e50;margin-bottom:10px;font-size:32px}.card p{color:#7f8c8d;font-size:14px}.menu{display:grid;grid-template-columns:repeat(auto-fit,minmax(200px,1fr));gap:15px}.menu a{display:block;padding:20px;background:white;border:2px solid #3498db;color:#3498db;text-decoration:none;border-radius:4px;text-align:center;font-weight:bold;cursor:pointer;transition:all 0.3s}.menu a:hover{background:#3498db;color:white;transform:translateY(-2px)}</style></head><body><nav><h1>🚗 Sistema de Aluguel</h1><a href='/logout' onclick='return confirm(\"Sair do sistema?\")'>Sair</a></nav><div class='container'><h2>Bem-vindo ao Dashboard!</h2><p style='color:#7f8c8d;margin:20px 0'>Você fez login com sucesso. Abaixo estão as estatísticas e opções do sistema:</p><div class='cards'><div class='card'><h2>" + clientCount + "</h2><p>Clientes</p></div><div class='card'><h2>" + carCount + "</h2><p>Automóveis</p></div><div class='card'><h2>" + contractCount + "</h2><p>Contratos</p></div></div><h3 style='color:#2c3e50;margin:20px 0'>Menu de Opções:</h3><div class='menu'><a href='/clientes/page'>👥 Clientes</a><a href='/automoveis/page'>🚗 Automóveis</a><a href='/contratos/page'>📄 Contratos</a><a href='/pedidos-aluguel/page'>📋 Pedidos</a><a href='/agentes/page'>🏢 Agentes</a><a href='/rendimentos/page'>💰 Rendimentos</a></div></div></body></html>";
        return HttpResponse.ok(html);
    }
}
