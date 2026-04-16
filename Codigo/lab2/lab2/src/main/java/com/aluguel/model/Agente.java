package com.aluguel.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

/**
 * Agente (Agent) entity - base class for Banco and Empresa
 */
@Serdeable
@Introspected
public class Agente extends Usuario {

    private String cnpj;

    public Agente() {
        super();
    }

    public Agente(String login, String senha, String nome, String endereco, String cnpj) {
        super(login, senha, nome, endereco);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }



    @Override
    public String toString() {
        return "Agente{" +
                "cnpj='" + cnpj + '\'' +
                ", " + super.toString() +
                '}';
    }
}
