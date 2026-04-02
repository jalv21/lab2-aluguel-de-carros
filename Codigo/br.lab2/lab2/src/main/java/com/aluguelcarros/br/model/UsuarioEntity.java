package com.aluguelcarros.br.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario")
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
