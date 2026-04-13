package com.aluguel.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

/**
 * Empresa (Company) entity - extends Agente
 */
@Serdeable
@Introspected
public class Empresa extends Agente {

    public Empresa() {
        super();
    }

    public Empresa(String login, String senha, String nome, String endereco, String cnpj) {
        super(login, senha, nome, endereco, cnpj);
    }

    @Override
    public String toString() {
        return "Empresa{" + super.toString() + '}';
    }
}
