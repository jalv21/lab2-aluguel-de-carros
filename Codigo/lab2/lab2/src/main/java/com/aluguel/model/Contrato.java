package com.aluguel.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

/**
 * Contrato (Contract) entity
 */
@Serdeable
@Introspected
public class Contrato {

    private Long id;
    private Long numero;
    private String termos;
    private String tipoContrato; // ALUGUEL, CREDITO, PROPRIEDADE
    private boolean assinado;

    public Contrato() {
    }

    public Contrato(Long numero, String termos, String tipoContrato) {
        this.numero = numero;
        this.termos = termos;
        this.tipoContrato = tipoContrato;
        this.assinado = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getTermos() {
        return termos;
    }

    public void setTermos(String termos) {
        this.termos = termos;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public boolean isAssinado() {
        return assinado;
    }

    public void setAssinado(boolean assinado) {
        this.assinado = assinado;
    }

    /**
     * Generate PDF of contract
     */
    public void gerarPDF() {
        System.out.println("PDF do contrato " + this.numero + " foi gerado.");
    }

    /**
     * Register signature
     */
    public void registrarAssinatura() {
        System.out.println("Assinatura do contrato " + this.numero + " foi registrada.");
        this.assinado = true;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", numero=" + numero +
                ", termos='" + termos + '\'' +
                ", tipoContrato='" + tipoContrato + '\'' +
                ", assinado=" + assinado +
                '}';
    }
}
