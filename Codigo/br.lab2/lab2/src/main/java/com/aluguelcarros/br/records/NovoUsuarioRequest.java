package com.aluguelcarros.br.records;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Introspected
public record NovoUsuarioRequest(String nome, String login, String senha) {
}