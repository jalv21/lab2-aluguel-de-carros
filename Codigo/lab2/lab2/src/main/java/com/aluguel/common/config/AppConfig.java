package com.aluguel.common.config;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

/**
 * Configurações globais da aplicação
 */
@Factory
public class AppConfig {

    /**
     * Constantes da aplicação
     */
    public static class Constants {
        public static final String API_VERSION = "1.0";
        public static final String API_BASE_PATH = "/";
        public static final int MAX_PAGE_SIZE = 100;
        public static final int DEFAULT_PAGE_SIZE = 20;
    }

    /**
     * Configurações de validação
     */
    public static class Validation {
        public static final int MIN_LOGIN_LENGTH = 3;
        public static final int MAX_LOGIN_LENGTH = 50;
        public static final int MIN_PASSWORD_LENGTH = 3;
        public static final int MAX_PASSWORD_LENGTH = 100;
        public static final int MIN_NAME_LENGTH = 2;
        public static final int MAX_NAME_LENGTH = 200;
    }

    /**
     * Configurações de mensagens
     */
    public static class Messages {
        public static final String RECURSO_NAO_ENCONTRADO = "Recurso não encontrado";
        public static final String DADOS_INVALIDOS = "Dados inválidos fornecidos";
        public static final String ERRO_INTERNO = "Erro interno do servidor";
        public static final String OPERACAO_SUCESSO = "Operação realizada com sucesso";
        public static final String RECURSO_CRIADO = "Recurso criado com sucesso";
        public static final String RECURSO_ATUALIZADO = "Recurso atualizado com sucesso";
        public static final String RECURSO_DELETADO = "Recurso deletado com sucesso";
    }

    @Singleton
    public AppProperties appProperties() {
        return new AppProperties();
    }

    /**
     * Classe para propriedades da aplicação
     */
    public static class AppProperties {
        private final String version = Constants.API_VERSION;
        private final String appName = "Sistema de Aluguel de Carros";
        private final String description = "API REST para gerenciamento de aluguel de carros";

        public String getVersion() {
            return version;
        }

        public String getAppName() {
            return appName;
        }

        public String getDescription() {
            return description;
        }
    }
}
