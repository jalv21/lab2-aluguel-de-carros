package com.aluguelcarros.br.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENTE")
public class ClienteEntity extends UsuarioEntity {
    @Column(unique = true)
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
