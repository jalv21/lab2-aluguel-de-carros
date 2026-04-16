/**
 * Status Enums for Frontend
 * Defines predefined status values for contracts, rental requests, and other entities
 */

const STATUS_ENUM = {
    // Contract Status
    CONTRATO: {
        PENDENTE: { value: 'PENDENTE', label: 'Pendente', icon: '⏳', color: '#FFC107', bgColor: '#FFF3CD' },
        ATIVO: { value: 'ATIVO', label: 'Ativo', icon: '✅', color: '#28A745', bgColor: '#D4EDDA' },
        ASSINADO: { value: 'ASSINADO', label: 'Assinado', icon: '✔️', color: '#007BFF', bgColor: '#D1ECF1' },
        VENCIDO: { value: 'VENCIDO', label: 'Vencido', icon: '❌', color: '#DC3545', bgColor: '#F8D7DA' },
        CANCELADO: { value: 'CANCELADO', label: 'Cancelado', icon: '🚫', color: '#6C757D', bgColor: '#E2E3E5' },
        EM_REVISAO: { value: 'EM_REVISAO', label: 'Em Revisão', icon: '📝', color: '#6F42C1', bgColor: '#E7DFF5' }
    },

    // Rental Request Status
    PEDIDO_ALUGUEL: {
        SOLICITADO: { value: 'SOLICITADO', label: 'Solicitado', icon: '📋', color: '#17A2B8', bgColor: '#D1ECF1' },
        AGUARDANDO_APROVACAO: { value: 'AGUARDANDO_APROVACAO', label: 'Aguardando Aprovação', icon: '⏳', color: '#FFC107', bgColor: '#FFF3CD' },
        APROVADO: { value: 'APROVADO', label: 'Aprovado', icon: '✅', color: '#28A745', bgColor: '#D4EDDA' },
        REJEITADO: { value: 'REJEITADO', label: 'Rejeitado', icon: '❌', color: '#DC3545', bgColor: '#F8D7DA' },
        CANCELADO: { value: 'CANCELADO', label: 'Cancelado', icon: '🚫', color: '#6C757D', bgColor: '#E2E3E5' },
        EM_PROCESSO: { value: 'EM_PROCESSO', label: 'Em Processo', icon: '⚙️', color: '#FF9800', bgColor: '#FFE0B2' },
        FINALIZADO: { value: 'FINALIZADO', label: 'Finalizado', icon: '✔️', color: '#4CAF50', bgColor: '#C8E6C9' }
    },

    // Contract Types
    TIPO_CONTRATO: {
        ALUGUEL: { value: 'ALUGUEL', label: 'Aluguel', icon: '🚗' },
        CREDITO: { value: 'CREDITO', label: 'Crédito', icon: '💳' },
        PROPRIEDADE: { value: 'PROPRIEDADE', label: 'Propriedade', icon: '🏠' }
    },

    // Vehicle Status
    VEICULO: {
        DISPONIVEL: { value: 'DISPONIVEL', label: 'Disponível', icon: '✅', color: '#28A745', bgColor: '#D4EDDA' },
        ALUGADO: { value: 'ALUGADO', label: 'Alugado', icon: '🚗', color: '#FFC107', bgColor: '#FFF3CD' },
        MANUTENCAO: { value: 'MANUTENCAO', label: 'Manutenção', icon: '🔧', color: '#FF9800', bgColor: '#FFE0B2' },
        INDISPONIVEL: { value: 'INDISPONIVEL', label: 'Indisponível', icon: '❌', color: '#DC3545', bgColor: '#F8D7DA' }
    }
};

/**
 * Get status configuration by category and value
 * @param {string} category - Status category (CONTRATO, PEDIDO_ALUGUEL, etc)
 * @param {string} value - Status value
 * @returns {Object} Status configuration with label, icon, color
 */
function getStatusConfig(category, value) {
    return STATUS_ENUM[category]?.[value] || { label: 'Desconhecido', icon: '❓', color: '#6C757D' };
}

/**
 * Render status badge with icon, label and color
 * @param {string} category - Status category
 * @param {string} value - Status value
 * @returns {string} HTML badge
 */
function renderStatusBadge(category, value) {
    const config = getStatusConfig(category, value);
    const bgColor = config.bgColor || '#F0F0F0';
    const color = config.color || '#333';
    
    return `<span style="
        display: inline-block;
        background-color: ${bgColor};
        color: ${color};
        padding: 4px 12px;
        border-radius: 20px;
        font-weight: bold;
        font-size: 12px;
        white-space: nowrap;
    ">
        ${config.icon} ${config.label}
    </span>`;
}

/**
 * Populate select element with status options
 * @param {HTMLSelectElement} element - Select element
 * @param {string} category - Status category
 */
function populateStatusSelect(element, category) {
    if (!element || !STATUS_ENUM[category]) return;

    const statuses = STATUS_ENUM[category];
    
    element.innerHTML = '<option value="">-- Selecione um status --</option>';
    
    Object.keys(statuses).forEach(key => {
        const config = statuses[key];
        const option = document.createElement('option');
        option.value = config.value;
        option.textContent = `${config.icon} ${config.label}`;
        element.appendChild(option);
    });
}

/**
 * Initialize all status selects on page
 */
function initializeStatusSelects() {
    document.querySelectorAll('[data-status-category]').forEach(element => {
        const category = element.getAttribute('data-status-category');
        if (category && element.tagName === 'SELECT') {
            populateStatusSelect(element, category);
        }
    });
}

// Initialize on page load
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initializeStatusSelects);
} else {
    initializeStatusSelects();
}
