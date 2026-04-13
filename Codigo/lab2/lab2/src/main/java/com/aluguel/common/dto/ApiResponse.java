package com.aluguel.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response genérico para API
 */
public class ApiResponse<T> {
    @JsonProperty("sucesso")
    private boolean sucesso;

    @JsonProperty("mensagem")
    private String mensagem;

    @JsonProperty("dados")
    private T dados;

    @JsonProperty("erro")
    private String erro;

    // Construtor para sucesso
    public ApiResponse(T dados) {
        this.sucesso = true;
        this.dados = dados;
        this.mensagem = "Operação realizada com sucesso";
    }

    // Construtor para erro
    public ApiResponse(boolean sucesso, String erro) {
        this.sucesso = sucesso;
        this.erro = erro;
        this.dados = null;
    }

    // Construtor customizado
    public ApiResponse(boolean sucesso, String mensagem, T dados) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public T getDados() {
        return dados;
    }

    public void setDados(T dados) {
        this.dados = dados;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
