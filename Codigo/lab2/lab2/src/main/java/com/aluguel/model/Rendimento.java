package com.aluguel.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

/**
 * Rendimento (Income) entity - stores income information for Clientes
 */
@Serdeable
@Introspected
public class Rendimento {

    private Long id;
    private String entidadeEmpregadora;
    private double valor;

    public Rendimento() {
    }

    public Rendimento(String entidadeEmpregadora, double valor) {
        this.entidadeEmpregadora = entidadeEmpregadora;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntidadeEmpregadora() {
        return entidadeEmpregadora;
    }

    public void setEntidadeEmpregadora(String entidadeEmpregadora) {
        this.entidadeEmpregadora = entidadeEmpregadora;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Rendimento{" +
                "id=" + id +
                ", entidadeEmpregadora='" + entidadeEmpregadora + '\'' +
                ", valor=" + valor +
                '}';
    }
}
