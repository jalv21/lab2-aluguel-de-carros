package com.aluguel.util;

/**
 * Enum for contract status
 */
public enum StatusContrato {
    PENDENTE("Pendente", "⏳"),
    ATIVO("Ativo", "✅"),
    ASSINADO("Assinado", "✔️"),
    VENCIDO("Vencido", "❌"),
    CANCELADO("Cancelado", "🚫"),
    EM_REVISAO("Em Revisão", "📝");

    private final String displayName;
    private final String icon;

    StatusContrato(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }

    public static StatusContrato fromString(String value) {
        if (value == null) return PENDENTE;
        try {
            return StatusContrato.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return PENDENTE;
        }
    }
}
