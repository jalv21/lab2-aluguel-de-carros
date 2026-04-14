package com.aluguel.common.exception;

/**
 * Exceção lançada quando um recurso não é encontrado no repositório.
 */
public class RecursoNaoEncontradoException extends RuntimeException {
    private final String recurso;
    private final Long id;

    public RecursoNaoEncontradoException(String recurso, Long id) {
        super(String.format("%s com ID %d não encontrado", recurso, id));
        this.recurso = recurso;
        this.id = id;
    }

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
        this.recurso = null;
        this.id = null;
    }

    public String getRecurso() {
        return recurso;
    }

    public Long getId() {
        return id;
    }
}
