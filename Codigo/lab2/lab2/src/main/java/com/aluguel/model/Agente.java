package com.aluguel.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

/**
 * Agente (Agent) entity - abstract base class for Banco and Empresa
 */
@Serdeable
@Introspected
public abstract class Agente extends Usuario {

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

    /**
     * Evaluate a rental request (Avaliar Pedido)
     */
    public void avaliarPedido() {
        System.out.println(this.getNome() + " avaliou um pedido.");
    }

    /**
     * Modify a rental request (Modificar Pedido)
     */
    public void modificarPedido() {
        System.out.println(this.getNome() + " modificou um pedido.");
    }

    @Override
    public String toString() {
        return "Agente{" +
                "cnpj='" + cnpj + '\'' +
                ", " + super.toString() +
                '}';
    }
}
