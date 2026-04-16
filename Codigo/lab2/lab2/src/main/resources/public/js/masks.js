/**
 * Input Masking and Formatting Utilities
 * Provides masks and formatters for Brazilian documents and fields
 */

const INPUT_MASKS = {
    // CPF: 000.000.000-00
    cpf: {
        mask: (value) => {
            if (!value) return '';
            value = value.replace(/\D/g, '').slice(0, 11);
            if (value.length <= 3) return value;
            if (value.length <= 6) return value.slice(0, 3) + '.' + value.slice(3);
            if (value.length <= 9) return value.slice(0, 3) + '.' + value.slice(3, 6) + '.' + value.slice(6);
            return value.slice(0, 3) + '.' + value.slice(3, 6) + '.' + value.slice(6, 9) + '-' + value.slice(9);
        },
        unformat: (value) => value.replace(/\D/g, ''),
        placeholder: '000.000.000-00'
    },

    // CNPJ: 00.000.000/0000-00
    cnpj: {
        mask: (value) => {
            if (!value) return '';
            value = value.replace(/\D/g, '').slice(0, 14);
            if (value.length <= 2) return value;
            if (value.length <= 5) return value.slice(0, 2) + '.' + value.slice(2);
            if (value.length <= 8) return value.slice(0, 2) + '.' + value.slice(2, 5) + '.' + value.slice(5);
            if (value.length <= 12) return value.slice(0, 2) + '.' + value.slice(2, 5) + '.' + value.slice(5, 8) + '/' + value.slice(8);
            return value.slice(0, 2) + '.' + value.slice(2, 5) + '.' + value.slice(5, 8) + '/' + value.slice(8, 12) + '-' + value.slice(12);
        },
        unformat: (value) => value.replace(/\D/g, ''),
        placeholder: '00.000.000/0000-00'
    },

    // Phone: (00) 00000-0000 or (00) 0000-0000
    phone: {
        mask: (value) => {
            if (!value) return '';
            value = value.replace(/\D/g, '').slice(0, 11);
            if (value.length <= 2) return '(' + value;
            if (value.length <= 7) return '(' + value.slice(0, 2) + ') ' + value.slice(2);
            return '(' + value.slice(0, 2) + ') ' + value.slice(2, 7) + '-' + value.slice(7);
        },
        unformat: (value) => value.replace(/\D/g, ''),
        placeholder: '(00) 00000-0000'
    },

    // License Plate: ABC-1234
    placa: {
        mask: (value) => {
            if (!value) return '';
            value = value.toUpperCase().replace(/[^A-Z0-9]/g, '').slice(0, 7);
            if (value.length <= 3) return value;
            return value.slice(0, 3) + '-' + value.slice(3);
        },
        unformat: (value) => value.replace(/\D/g, '').toUpperCase(),
        placeholder: 'ABC-1234'
    },

    // Currency: 0,00
    currency: {
        mask: (value) => {
            if (!value) return '';
            let cleanValue = value.replace(/\D/g, '');
            let num = cleanValue / 100;
            return 'R$ ' + num.toFixed(2).replace('.', ',');
        },
        unformat: (value) => {
            return value.replace(/\D/g, '') / 100;
        },
        placeholder: 'R$ 0,00'
    },

    // Percentage: 00,00%
    percentage: {
        mask: (value) => {
            if (!value) return '';
            let num = parseFloat(value);
            return num.toFixed(2).replace('.', ',') + '%';
        },
        unformat: (value) => parseFloat(value.replace(/\D/g, '')) / 100,
        placeholder: '0,00%'
    },

    // Date: DD/MM/YYYY
    date: {
        mask: (value) => {
            if (!value) return '';
            value = value.replace(/\D/g, '').slice(0, 8);
            if (value.length <= 2) return value;
            if (value.length <= 4) return value.slice(0, 2) + '/' + value.slice(2);
            return value.slice(0, 2) + '/' + value.slice(2, 4) + '/' + value.slice(4);
        },
        unformat: (value) => value.replace(/\D/g, ''),
        placeholder: 'DD/MM/YYYY'
    }
};

/**
 * Apply mask to an input element
 * @param {HTMLInputElement} element - The input element
 * @param {string} maskType - Type of mask (cpf, cnpj, phone, placa, currency, percentage, date)
 */
function applyInputMask(element, maskType) {
    if (!element || !INPUT_MASKS[maskType]) return;

    const maskConfig = INPUT_MASKS[maskType];
    element.placeholder = maskConfig.placeholder;

    element.addEventListener('input', (e) => {
        e.target.value = maskConfig.mask(e.target.value);
    });

    element.addEventListener('blur', (e) => {
        e.target.value = maskConfig.mask(e.target.value);
    });
}

/**
 * Auto-detect and apply appropriate mask based on field name
 */
function autoApplyMask(element) {
    if (!element) return;

    const fieldName = element.name || element.id || '';
    const lowerName = fieldName.toLowerCase();

    if (lowerName.includes('cpf')) applyInputMask(element, 'cpf');
    else if (lowerName.includes('cnpj')) applyInputMask(element, 'cnpj');
    else if (lowerName.includes('phone') || lowerName.includes('telefone')) applyInputMask(element, 'phone');
    else if (lowerName.includes('placa')) applyInputMask(element, 'placa');
    else if (lowerName.includes('valor') || lowerName.includes('preco')) applyInputMask(element, 'currency');
    else if (lowerName.includes('percentual') || lowerName.includes('porcentagem')) applyInputMask(element, 'percentage');
    else if (lowerName.includes('data') || lowerName.includes('date')) applyInputMask(element, 'date');
}

/**
 * Initialize masks for all inputs with data-mask attribute
 */
function initializeMasks() {
    document.querySelectorAll('[data-mask]').forEach(element => {
        const maskType = element.getAttribute('data-mask');
        if (maskType) {
            applyInputMask(element, maskType);
        }
    });

    // Auto-detect masks by field name
    document.querySelectorAll('input[type="text"]').forEach(autoApplyMask);
}

// Initialize on page load
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initializeMasks);
} else {
    initializeMasks();
}
