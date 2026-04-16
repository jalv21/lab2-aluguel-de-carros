/**
 * Client-Side Form Validation
 * Validates CPF, CNPJ, email, dates, and other common fields
 */

const VALIDATION_RULES = {
    // Login field
    login: {
        required: true,
        minLength: 3,
        maxLength: 50,
        pattern: /^[a-zA-Z0-9_-]+$/,
        message: 'Login deve ter 3-50 caracteres e conter apenas letras, números, _ ou -',
    },
    
    // Password field
    senha: {
        required: true,
        minLength: 3,
        maxLength: 100,
        message: 'Senha deve ter 3-100 caracteres',
    },
    
    // Name field
    nome: {
        required: true,
        minLength: 2,
        maxLength: 200,
        message: 'Nome deve ter 2-200 caracteres',
    },
    
    // Address field
    endereco: {
        required: true,
        minLength: 5,
        maxLength: 300,
        message: 'Endereço deve ter 5-300 caracteres',
    },
    
    // CPF field (Brazilian taxpayer ID)
    cpf: {
        required: true,
        pattern: /^\d{3}\.\d{3}\.\d{3}-\d{2}$/,
        validator: validateCPF,
        message: 'CPF inválido (formato: 000.000.000-00)',
    },
    
    // RG field
    rg: {
        required: true,
        minLength: 5,
        maxLength: 20,
        message: 'RG deve ter 5-20 caracteres',
    },
    
    // Profession field
    profissao: {
        required: true,
        minLength: 2,
        maxLength: 100,
        message: 'Profissão deve ter 2-100 caracteres',
    },
    
    // CNPJ field (Brazilian company ID)
    cnpj: {
        required: true,
        pattern: /^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/,
        validator: validateCNPJ,
        message: 'CNPJ inválido (formato: 00.000.000/0000-00)',
    },
    
    // License plate
    placa: {
        required: true,
        pattern: /^[A-Z]{3}-\d{4}$/,
        message: 'Placa inválida (formato: ABC-1234)',
    },
    
    // Year of vehicle
    ano: {
        required: true,
        min: 1900,
        max: 2100,
        isNumber: true,
        message: 'Ano deve estar entre 1900 e 2100',
    },
    
    // Brand, model, registration
    marca: { required: true, minLength: 2, maxLength: 100 },
    modelo: { required: true, minLength: 2, maxLength: 100 },
    matricula: { required: true, minLength: 2, maxLength: 100 },
    
    // Rental date
    dataLocal: {
        required: true,
        validator: validateFutureDate,
        message: 'Data deve ser no futuro',
    },
    
    // Contract number
    numero: {
        required: true,
        isNumber: true,
        min: 1,
        message: 'Número de contrato inválido',
    },
    
    // Contract type
    tipoContrato: {
        required: true,
        enum: ['ALUGUEL', 'CREDITO', 'PROPRIEDADE'],
        message: 'Tipo de contrato inválido',
    },
    
    // Income value
    valor: {
        required: true,
        isNumber: true,
        min: 0.01,
        message: 'Valor deve ser maior que 0',
    },
    
    // Income entity
    entidadeEmpregadora: {
        required: true,
        minLength: 2,
        maxLength: 200,
        message: 'Entidade empregadora deve ter 2-200 caracteres',
    },
    
    // Contract terms
    termos: {
        required: true,
        minLength: 10,
        maxLength: 5000,
        message: 'Termos do contrato deve ter 10-5000 caracteres',
    },
};

/**
 * Validate CPF using Luhn algorithm
 * Format: 000.000.000-00
 */
function validateCPF(cpf) {
    if (!cpf) return false;
    
    // Remove formatting
    const cleaned = cpf.replace(/\D/g, '');
    
    if (cleaned.length !== 11) return false;
    if (/^(\d)\1{10}$/.test(cleaned)) return false; // All same digits
    
    // Luhn algorithm
    let sum = 0;
    let remainder;
    
    for (let i = 1; i <= 9; i++) {
        sum += parseInt(cleaned.substring(i - 1, i)) * (11 - i);
    }
    
    remainder = (sum * 10) % 11;
    if (remainder === 10 || remainder === 11) remainder = 0;
    if (remainder !== parseInt(cleaned.substring(9, 10))) return false;
    
    sum = 0;
    for (let i = 1; i <= 10; i++) {
        sum += parseInt(cleaned.substring(i - 1, i)) * (12 - i);
    }
    
    remainder = (sum * 10) % 11;
    if (remainder === 10 || remainder === 11) remainder = 0;
    if (remainder !== parseInt(cleaned.substring(10, 11))) return false;
    
    return true;
}

/**
 * Validate CNPJ
 * Format: 00.000.000/0000-00
 */
function validateCNPJ(cnpj) {
    if (!cnpj) return false;
    
    const cleaned = cnpj.replace(/\D/g, '');
    
    if (cleaned.length !== 14) return false;
    if (/^(\d)\1{13}$/.test(cleaned)) return false; // All same digits
    
    // CNPJ validation
    let size = cleaned.length - 2;
    let numbers = cleaned.substring(0, size);
    let digits = cleaned.substring(size);
    let sum = 0;
    let pos = size - 7;
    
    for (let i = size; i >= 1; i--) {
        sum += numbers.charAt(size - i) * pos--;
        if (pos < 2) pos = 9;
    }
    
    let result = sum % 11 < 2 ? 0 : 11 - sum % 11;
    if (result !== parseInt(digits.charAt(0))) return false;
    
    size = size + 1;
    numbers = cleaned.substring(0, size);
    sum = 0;
    pos = size - 7;
    
    for (let i = size; i >= 1; i--) {
        sum += numbers.charAt(size - i) * pos--;
        if (pos < 2) pos = 9;
    }
    
    result = sum % 11 < 2 ? 0 : 11 - sum % 11;
    if (result !== parseInt(digits.charAt(1))) return false;
    
    return true;
}

/**
 * Validate that date is in the future
 */
function validateFutureDate(dateStr) {
    if (!dateStr) return false;
    
    const date = new Date(dateStr);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    
    return date > today;
}

/**
 * Validate email format
 */
function validateEmail(email) {
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return pattern.test(email);
}

/**
 * Validate required field
 */
function validateRequired(value, fieldName = '') {
    if (value === null || value === undefined || value === '') {
        return `${fieldName} é obrigatório`;
    }
    return null;
}

/**
 * Validate minimum length
 */
function validateMinLength(value, min, fieldName = '') {
    if (value && value.length < min) {
        return `${fieldName} deve ter pelo menos ${min} caracteres`;
    }
    return null;
}

/**
 * Validate maximum length
 */
function validateMaxLength(value, max, fieldName = '') {
    if (value && value.length > max) {
        return `${fieldName} pode ter no máximo ${max} caracteres`;
    }
    return null;
}

/**
 * Validate number
 */
function validateNumber(value, fieldName = '') {
    if (value && isNaN(value)) {
        return `${fieldName} deve ser um número`;
    }
    return null;
}

/**
 * Validate minimum value
 */
function validateMinValue(value, min, fieldName = '') {
    if (value !== null && value !== undefined && value !== '' && parseFloat(value) < min) {
        return `${fieldName} deve ser maior que ${min}`;
    }
    return null;
}

/**
 * Validate maximum value
 */
function validateMaxValue(value, max, fieldName = '') {
    if (value !== null && value !== undefined && value !== '' && parseFloat(value) > max) {
        return `${fieldName} deve ser menor que ${max}`;
    }
    return null;
}

/**
 * Main form validation function
 * @param {HTMLFormElement} formElement - The form to validate
 * @param {Object} rules - Custom validation rules override
 * @returns {Object} { valid: boolean, errors: {fieldName: errorMessage, ...} }
 */
function validateForm(formElement, rules = {}) {
    const errors = {};
    const formData = new FormData(formElement);
    
    // Merge with default rules
    const mergedRules = { ...VALIDATION_RULES, ...rules };
    
    // Validate each field in the form
    for (const [fieldName, value] of formData.entries()) {
        const rule = mergedRules[fieldName];
        if (!rule) continue;
        
        // Check required
        if (rule.required) {
            const requiredError = validateRequired(value, fieldName);
            if (requiredError) {
                errors[fieldName] = requiredError;
                continue;
            }
        }
        
        if (!value) continue; // Skip empty optional fields
        
        // Check pattern
        if (rule.pattern && !rule.pattern.test(value)) {
            errors[fieldName] = rule.message || `${fieldName} inválido`;
            continue;
        }
        
        // Check custom validator
        if (rule.validator && !rule.validator(value)) {
            errors[fieldName] = rule.message || `${fieldName} inválido`;
            continue;
        }
        
        // Check minLength
        if (rule.minLength) {
            const minError = validateMinLength(value, rule.minLength, fieldName);
            if (minError) {
                errors[fieldName] = minError;
                continue;
            }
        }
        
        // Check maxLength
        if (rule.maxLength) {
            const maxError = validateMaxLength(value, rule.maxLength, fieldName);
            if (maxError) {
                errors[fieldName] = maxError;
                continue;
            }
        }
        
        // Check isNumber
        if (rule.isNumber) {
            const numError = validateNumber(value, fieldName);
            if (numError) {
                errors[fieldName] = numError;
                continue;
            }
        }
        
        // Check min value
        if (rule.min !== undefined) {
            const minError = validateMinValue(value, rule.min, fieldName);
            if (minError) {
                errors[fieldName] = minError;
                continue;
            }
        }
        
        // Check max value
        if (rule.max !== undefined) {
            const maxError = validateMaxValue(value, rule.max, fieldName);
            if (maxError) {
                errors[fieldName] = maxError;
                continue;
            }
        }
        
        // Check enum
        if (rule.enum && !rule.enum.includes(value)) {
            errors[fieldName] = rule.message || `${fieldName} inválido`;
            continue;
        }
    }
    
    return {
        valid: Object.keys(errors).length === 0,
        errors: errors,
    };
}

/**
 * Format CPF: 000.000.000-00
 */
function formatCPF(cpf) {
    if (!cpf) return '';
    const cleaned = cpf.replace(/\D/g, '');
    return cleaned.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
}

/**
 * Format CNPJ: 00.000.000/0000-00
 */
function formatCNPJ(cnpj) {
    if (!cnpj) return '';
    const cleaned = cnpj.replace(/\D/g, '');
    return cleaned.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, '$1.$2.$3/$4-$5');
}

/**
 * Format currency: R$ 1.234,56
 */
function formatCurrency(value) {
    if (value === null || value === undefined) return 'R$ 0,00';
    const num = parseFloat(value);
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
    }).format(num);
}

/**
 * Format date: dd/mm/yyyy
 */
function formatDate(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr + 'T00:00:00Z'); // Add time to avoid timezone issues
    return new Intl.DateTimeFormat('pt-BR').format(date);
}

/**
 * Get query parameter from URL
 */
function getQueryParam(name) {
    const params = new URLSearchParams(window.location.search);
    return params.get(name);
}

/**
 * Apply single filter and reload page
 */
function applySingleFilter(filterName, value) {
    const params = new URLSearchParams(window.location.search);
    if (value) {
        params.set(filterName, value);
    } else {
        params.delete(filterName);
    }
    params.set('page', 1);
    window.location.search = params.toString();
}

/**
 * Create search box HTML
 */
function createSearchBox(placeholder, onSearchCallback) {
    const searchQuery = getQueryParam('search') || '';
    return `
        <div class="relative">
            <input type="text" id="searchInput" placeholder="${placeholder}" value="${searchQuery}" 
                   class="form-input w-full pl-10"
                   onkeyup="debounceSearch('${onSearchCallback}', this.value, 300)">
            <i data-feather="search" class="w-5 h-5 absolute left-3 top-3 text-gray-400"></i>
        </div>
    `;
}

/**
 * Debounce search
 */
let searchTimeout;
function debounceSearch(callback, value, delay = 300) {
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(() => {
        if (typeof window[callback] === 'function') {
            window[callback](value);
        }
    }, delay);
}

/**
 * Create form label with required indicator
 */
function createFormLabel(labelText, required = false) {
    return `<label class="form-label">${labelText}${required ? ' <span class="text-red-500">*</span>' : ''}</label>`;
}

/**
 * Display form errors
 */
function displayFormErrors(formElement, errors) {
    // Clear all previous error messages
    formElement.querySelectorAll('.form-error-message').forEach(el => el.remove());
    formElement.querySelectorAll('.form-input, .form-select, .form-textarea').forEach(el => {
        el.classList.remove('border-red-500', 'ring-red-500');
    });
    
    // Display new errors
    for (const [fieldName, errorMessage] of Object.entries(errors)) {
        const field = formElement.querySelector(`[name="${fieldName}"]`);
        if (field) {
            field.classList.add('border-red-500', 'ring-1', 'ring-red-500');
            const errorDiv = document.createElement('div');
            errorDiv.className = 'form-error-message text-red-600 text-sm mt-1';
            errorDiv.textContent = errorMessage;
            field.parentNode.insertBefore(errorDiv, field.nextSibling);
        }
    }
}

/**
 * Display validation errors in the form
 */
function displayFormErrors(formElement, errors) {
    // Clear previous errors
    formElement.querySelectorAll('.error-message').forEach(el => el.remove());
    formElement.querySelectorAll('.border-red-500').forEach(el => {
        el.classList.remove('border-red-500');
        el.classList.add('border-gray-300');
    });
    
    // Display errors
    for (const [fieldName, errorMessage] of Object.entries(errors)) {
        const field = formElement.querySelector(`[name="${fieldName}"]`);
        if (field) {
            field.classList.remove('border-gray-300');
            field.classList.add('border-red-500');
            
            const errorEl = document.createElement('p');
            errorEl.className = 'error-message text-red-500 text-sm mt-1';
            errorEl.textContent = errorMessage;
            field.parentNode.appendChild(errorEl);
        }
    }
}

/**
 * Clear form errors
 */
function clearFormErrors(formElement) {
    formElement.querySelectorAll('.error-message').forEach(el => el.remove());
    formElement.querySelectorAll('.border-red-500').forEach(el => {
        el.classList.remove('border-red-500');
        el.classList.add('border-gray-300');
    });
}

/**
 * Format CPF for display: 000.000.000-00
 */
function formatCPF(cpf) {
    const cleaned = cpf.replace(/\D/g, '');
    if (cleaned.length !== 11) return cpf;
    return `${cleaned.substring(0, 3)}.${cleaned.substring(3, 6)}.${cleaned.substring(6, 9)}-${cleaned.substring(9)}`;
}

/**
 * Format CNPJ for display: 00.000.000/0000-00
 */
function formatCNPJ(cnpj) {
    const cleaned = cnpj.replace(/\D/g, '');
    if (cleaned.length !== 14) return cnpj;
    return `${cleaned.substring(0, 2)}.${cleaned.substring(2, 5)}.${cleaned.substring(5, 8)}/${cleaned.substring(8, 12)}-${cleaned.substring(12)}`;
}

/**
 * Format currency for display: R$ 1,234.56
 */
function formatCurrency(value) {
    const num = parseFloat(value);
    if (isNaN(num)) return 'R$ 0,00';
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL',
    }).format(num);
}

/**
 * Format date for display: DD/MM/YYYY
 */
function formatDate(dateStr) {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
}

/**
 * Convert date from DD/MM/YYYY to ISO format (YYYY-MM-DD)
 */
function dateToISO(dateStr) {
    if (!dateStr) return '';
    const [day, month, year] = dateStr.split('/');
    return `${year}-${month}-${day}`;
}

/**
 * Convert date from ISO format (YYYY-MM-DD) to DD/MM/YYYY
 */
function dateFromISO(dateStr) {
    return formatDate(dateStr);
}

/**
 * Parse CSV-like input with line breaks (for contract terms, etc)
 */
function parseMultilineText(text) {
    return text ? text.trim().split('\n').map(line => line.trim()).filter(line => line) : [];
}
