package com.aluguel.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

/**
 * Cliente (Customer) entity - extends Usuario with customer-specific properties
 */
@Serdeable
@Introspected
public class Cliente extends Usuario {

    private String rg;
    private String cpf;
    private String profissao;

    public Cliente() {
        super();
    }

    public Cliente(String login, String senha, String nome, String endereco, 
                   String rg, String cpf, String profissao) {
        super(login, senha, nome, endereco);
        this.rg = rg;
        this.cpf = cpf;
        this.profissao = profissao;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }



    @Override
    public String toString() {
        return "Cliente{" +
                "rg='" + rg + '\'' +
                ", cpf='" + cpf + '\'' +
                ", profissao='" + profissao + '\'' +
                ", " + super.toString() +
                '}';
    }
}
