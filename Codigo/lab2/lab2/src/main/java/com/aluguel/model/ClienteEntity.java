package com.aluguel.model;

import io.micronaut.data.annotation.Id;
import jakarta.persistence.Entity;

@Entity
public class ClienteEntity {
    @Id
    private String rg;
    private String cpf;
    private String profissao;
    private String nome;

    public ClienteEntity(String rg, String cpf, String profissao, String nome) {
        this.rg = rg;
        this.cpf = cpf;
        this.profissao = profissao;
        this.nome = nome;
    }

    public Object getId() {
        return rg;
    }

    public String getCpf() {
        return cpf;
    }

    public String getProfissao() {
        return profissao;
    }

    public String getNome() {
        return nome;
    }

    private void solicitarAluguel() {
        // TODO lógica para solicitar aluguel
    }
    
    private void modificarPedido() {
        // TODO lógica para modificar pedido
    }

    private void cancelarPedido() {
        // TODO lógica para cancelar pedido
    }

    private void assinarContrato() {
        // TODO lógica para assinar contrato
    }
}
