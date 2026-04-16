package com.aluguel.util;

/**
 * Enum for rental request status
 */
public enum StatusPedidoAluguel {
    SOLICITADO("Solicitado", "📋"),
    AGUARDANDO_APROVACAO("Aguardando Aprovação", "⏳"),
    APROVADO("Aprovado", "✅"),
    REJEITADO("Rejeitado", "❌"),
    CANCELADO("Cancelado", "🚫"),
    EM_PROCESSO("Em Processo", "⚙️"),
    FINALIZADO("Finalizado", "✔️");

    private final String displayName;
    private final String icon;

    StatusPedidoAluguel(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }

    public static StatusPedidoAluguel fromString(String value) {
        if (value == null) return SOLICITADO;
        try {
            return StatusPedidoAluguel.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return SOLICITADO;
        }
    }
}
