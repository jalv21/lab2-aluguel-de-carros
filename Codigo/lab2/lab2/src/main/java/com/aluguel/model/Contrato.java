package com.aluguel.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import com.aluguel.util.StatusContrato;

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
    private String status; // PENDENTE, ATIVO, ASSINADO, VENCIDO, CANCELADO, EM_REVISAO

    public Contrato() {
    }

    public Contrato(Long numero, String termos, String tipoContrato) {
        this.numero = numero;
        this.termos = termos;
        this.tipoContrato = tipoContrato;
        this.assinado = false;
        this.status = StatusContrato.PENDENTE.name();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = StatusContrato.fromString(status).name();
    }



    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", numero=" + numero +
                ", termos='" + termos + '\'' +
                ", tipoContrato='" + tipoContrato + '\'' +
                ", assinado=" + assinado +
                ", status='" + status + '\'' +
                '}';
    }
}
