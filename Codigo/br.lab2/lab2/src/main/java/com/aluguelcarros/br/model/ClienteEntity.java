package com.aluguelcarros.br.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClienteEntity extends UsuarioEntity {
    @Id
    private String rg;
    private String cpf;
    private String profissao;

    public String getRg() {
        return rg;
    }

    public String getCpf() {
        return cpf;
    }

    public String getProfissao() {
        return profissao;
    }

    public ClienteEntity(long id, String login, String senha, String nome, String endereco) {
        super(id, login, senha, nome, endereco);
    }

    @Override
    public boolean autenticar(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    public void solicitarAluguel() {
        // TODO
    }

    public void modificarPedido() {
        // TODO
    }

    public void cancelarPedido() {
        // TODO
    }

    public void assinarContrato() {
        // TODO
    }
}
