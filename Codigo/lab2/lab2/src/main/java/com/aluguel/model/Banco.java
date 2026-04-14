package com.aluguel.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

/**
 * Banco (Bank) entity - extends Agente
 */
@Serdeable
@Introspected
public class Banco extends Agente {

    public Banco() {
        super();
    }

    public Banco(String login, String senha, String nome, String endereco, String cnpj) {
        super(login, senha, nome, endereco, cnpj);
    }

    /**
     * Grant credit for a rental contract (Conceder Crédito)
     */
    public void concederCredito() {
        System.out.println(this.getNome() + " concedeu um crédito.");
    }

    @Override
    public String toString() {
        return "Banco{" + super.toString() + '}';
    }
}
