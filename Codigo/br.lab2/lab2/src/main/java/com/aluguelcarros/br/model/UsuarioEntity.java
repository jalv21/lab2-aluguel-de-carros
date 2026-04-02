package com.aluguelcarros.br.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public abstract class UsuarioEntity {
    @Id
    protected long id;
    protected String login;
    protected String senha;
    protected String nome;
    protected String endereco;

    public UsuarioEntity(long id, String login, String senha, String nome, String endereco) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.endereco = endereco;
    }

    public abstract boolean autenticar(String login, String senha);
}
