package com.aluguel.common.exception;

/**
 * Exceção lançada quando há validação de dados inválida.
 */
public class DadosInvalidosException extends RuntimeException {
    private final String campo;

    public DadosInvalidosException(String mensagem) {
        super(mensagem);
        this.campo = null;
    }

    public DadosInvalidosException(String campo, String mensagem) {
        super(String.format("Campo '%s': %s", campo, mensagem));
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
