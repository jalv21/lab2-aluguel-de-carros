package com.aluguel.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import java.time.LocalDate;

/**
 * PedidoAluguel (Rental Request) entity
 */
@Serdeable
@Introspected
public class PedidoAluguel {

    private Long id;
    private Long clienteId;
    private Long automovelId;
    private LocalDate dataLocal;
    private String status; // PENDENTE, APROVADO, REJEITADO, CANCELADO
    private LocalDate dataPedido;
    private boolean assinado;

    public PedidoAluguel() {
    }

    public PedidoAluguel(Long clienteId, Long automovelId, LocalDate dataLocal) {
        this.clienteId = clienteId;
        this.automovelId = automovelId;
        this.dataLocal = dataLocal;
        this.status = "PENDENTE";
        this.dataPedido = LocalDate.now();
        this.assinado = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getAutomovelId() {
        return automovelId;
    }

    public void setAutomovelId(Long automovelId) {
        this.automovelId = automovelId;
    }

    public LocalDate getDataLocal() {
        return dataLocal;
    }

    public void setDataLocal(LocalDate dataLocal) {
        this.dataLocal = dataLocal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public boolean isAssinado() {
        return assinado;
    }

    public void setAssinado(boolean assinado) {
        this.assinado = assinado;
    }

    /**
     * Update rental request status
     */
    public void atualizarStatus() {
        System.out.println("Status do pedido ID " + this.id + " atualizado para: " + this.status);
    }

    @Override
    public String toString() {
        return "PedidoAluguel{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", automovelId=" + automovelId +
                ", dataLocal=" + dataLocal +
                ", status='" + status + '\'' +
                ", dataPedido=" + dataPedido +
                ", assinado=" + assinado +
                '}';
    }
}
