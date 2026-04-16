/**
 * Configuração de Campos CRUD com Validações, Máscaras e Dropdowns
 */

// Mapeamento de plurais para singulares
const ENTITY_TYPE_MAP = {
    clientes: 'cliente',
    agentes: 'agente',
    automoveis: 'automovel',
    contratos: 'contrato',
    'pedidos-aluguel': 'pedidoAluguel',
    rendimentos: 'rendimento'
};

function normalizeEntityType(entityType) {
    return ENTITY_TYPE_MAP[entityType] || entityType;
}

const FIELD_DEFINITIONS = {
    // ========== CLIENTES ==========
    cliente: {
        login: {
            label: 'Login',
            type: 'text',
            required: true,
            validate: ['required', 'minLength:3', 'maxLength:50'],
            help: 'Mínimo 3 caracteres'
        },
        nome: {
            label: 'Nome',
            type: 'text',
            required: true,
            validate: ['required', 'minLength:5', 'maxLength:100'],
            help: 'Nome completo'
        },
        endereco: {
            label: 'Endereço',
            type: 'text',
            required: false,
            validate: ['maxLength:150'],
            help: 'Endereço completo'
        },
        rg: {
            label: 'RG',
            type: 'text',
            required: false,
            mask: 'rg',
            validate: ['maxLength:15'],
            help: 'Ex: 12.345.678-90'
        },
        cpf: {
            label: 'CPF',
            type: 'text',
            required: true,
            mask: 'cpf',
            validate: ['required', 'cpf'],
            help: 'Ex: 123.456.789-00'
        },
        profissao: {
            label: 'Profissão',
            type: 'text',
            required: false,
            validate: ['maxLength:50'],
            help: 'Sua profissão'
        }
    },

    // ========== AGENTES (Banco/Empresa) ==========
    agente: {
        login: {
            label: 'Login',
            type: 'text',
            required: true,
            validate: ['required', 'minLength:3', 'maxLength:50'],
            help: 'Mínimo 3 caracteres'
        },
        senha: {
            label: 'Senha',
            type: 'password',
            required: true,
            validate: ['required', 'minLength:6', 'maxLength:50'],
            help: 'Mínimo 6 caracteres'
        },
        nome: {
            label: 'Nome',
            type: 'text',
            required: true,
            validate: ['required', 'minLength:5', 'maxLength:100'],
            help: 'Nome da empresa ou banco'
        },
        endereco: {
            label: 'Endereço',
            type: 'text',
            required: false,
            validate: ['maxLength:150'],
            help: 'Endereço completo'
        },
        cnpj: {
            label: 'CNPJ',
            type: 'text',
            required: true,
            mask: 'cnpj',
            validate: ['required', 'cnpj'],
            help: 'Ex: 12.345.678/0001-90'
        }
    },

    // ========== AUTOMÓVEIS ==========
    automovel: {
        matricula: {
            label: 'Matrícula',
            type: 'text',
            required: true,
            validate: ['required', 'minLength:5', 'maxLength:50'],
            help: 'Número de série do veículo'
        },
        placa: {
            label: 'Placa',
            type: 'text',
            required: true,
            mask: 'placa',
            validate: ['required'],
            help: 'Ex: ABC-1234'
        },
        marca: {
            label: 'Marca',
            type: 'text',
            required: true,
            validate: ['required', 'minLength:2', 'maxLength:50'],
            help: 'Ex: Toyota, Honda, Ford'
        },
        modelo: {
            label: 'Modelo',
            type: 'text',
            required: true,
            validate: ['required', 'minLength:2', 'maxLength:50'],
            help: 'Ex: Corolla, Civic, Focus'
        },
        ano: {
            label: 'Ano',
            type: 'number',
            required: true,
            validate: ['required', 'number', 'minValue:1900', 'maxValue:2099'],
            help: 'Ano de fabricação'
        }
    },

    // ========== CONTRATOS ==========
    contrato: {
        numero: {
            label: 'Número do Contrato',
            type: 'number',
            required: true,
            validate: ['required', 'number', 'minValue:1'],
            help: 'Identificador único'
        },
        tipoContrato: {
            label: 'Tipo de Contrato',
            type: 'select',
            required: true,
            validate: ['required'],
            options: [
                { value: 'ALUGUEL', label: '🚗 Aluguel de Veículo' },
                { value: 'CREDITO', label: '💳 Financiamento' },
                { value: 'PROPRIEDADE', label: '📋 Propriedade' }
            ],
            help: 'Escolha o tipo de contrato'
        },
        termos: {
            label: 'Termos e Condições',
            type: 'textarea',
            required: false,
            validate: ['maxLength:1000'],
            help: 'Descrição dos termos do contrato'
        },
        status: {
            label: 'Status do Contrato',
            type: 'select',
            required: true,
            validate: ['required'],
            options: [
                { value: 'PENDENTE', label: '⏳ Pendente', color: '#f39c12' },
                { value: 'ATIVO', label: '✅ Ativo', color: '#27ae60' },
                { value: 'ASSINADO', label: '✔️ Assinado', color: '#2980b9' },
                { value: 'VENCIDO', label: '❌ Vencido', color: '#e74c3c' },
                { value: 'CANCELADO', label: '🚫 Cancelado', color: '#95a5a6' },
                { value: 'EM_REVISAO', label: '📝 Em Revisão', color: '#9b59b6' }
            ],
            help: 'Estado atual do contrato'
        }
    },

    // ========== PEDIDOS DE ALUGUEL ==========
    pedidoAluguel: {
        clienteId: {
            label: 'ID do Cliente',
            type: 'number',
            required: true,
            validate: ['required', 'number', 'minValue:1'],
            help: 'Identificador do cliente'
        },
        automovelId: {
            label: 'ID do Automóvel',
            type: 'number',
            required: true,
            validate: ['required', 'number', 'minValue:1'],
            help: 'Identificador do veículo'
        },
        dataLocal: {
            label: 'Data de Locação',
            type: 'date',
            required: true,
            validate: ['required', 'futureDate'],
            help: 'Data de início do aluguel'
        },
        status: {
            label: 'Status do Pedido',
            type: 'select',
            required: true,
            validate: ['required'],
            options: [
                { value: 'SOLICITADO', label: '📋 Solicitado', color: '#3498db' },
                { value: 'AGUARDANDO_APROVACAO', label: '⏳ Aguardando Aprovação', color: '#f39c12' },
                { value: 'APROVADO', label: '✅ Aprovado', color: '#27ae60' },
                { value: 'REJEITADO', label: '❌ Rejeitado', color: '#e74c3c' },
                { value: 'CANCELADO', label: '🚫 Cancelado', color: '#95a5a6' },
                { value: 'EM_PROCESSO', label: '⚙️ Em Processo', color: '#9b59b6' },
                { value: 'FINALIZADO', label: '✔️ Finalizado', color: '#2ecc71' }
            ],
            help: 'Estado do pedido de aluguel'
        }
    },

    // ========== RENDIMENTOS ==========
    rendimento: {
        entidadeEmpregadora: {
            label: 'Entidade Empregadora',
            type: 'text',
            required: true,
            validate: ['required', 'minLength:5', 'maxLength:100'],
            help: 'Nome da empresa ou instituição'
        },
        valor: {
            label: 'Valor do Rendimento',
            type: 'number',
            required: true,
            validate: ['required', 'number', 'minValue:0'],
            help: 'Ex: 1234.56'
        }
    }
};

/**
 * Obtém definição de campo
 */
function getFieldDefinition(entityType, fieldName) {
    const normalized = normalizeEntityType(entityType);
    const entity = FIELD_DEFINITIONS[normalized];
    if (!entity) {
        console.warn(`Entity type '${entityType}' (normalized: '${normalized}') not found`);
        return null;
    }
    const field = entity[fieldName];
    if (!field) {
        console.warn(`Field '${fieldName}' not found in entity '${normalized}'`);
        return null;
    }
    return field;
}

/**
 * Obtém todas as definições de um tipo de entidade
 */
function getEntityDefinitions(entityType) {
    const normalized = normalizeEntityType(entityType);
    return FIELD_DEFINITIONS[normalized] || {};
}

/**
 * Renderiza um campo com base em sua definição
 */
function renderField(fieldName, value = '', definition = null) {
    if (!definition) {
        return `<input type="text" data-field="${fieldName}" value="${value}" style="width:100%; padding:10px; border:1px solid #ddd; border-radius:4px; box-sizing:border-box; font-size:14px;">`;
    }

    const { label, type, required, validate, mask, options, help } = definition;
    const requiredAttr = required ? 'required' : '';
    const helpText = help ? `<small style="color:#7f8c8d; display:block; margin-top:3px;">${help}</small>` : '';

    let input = '';

    if (type === 'select' && options) {
        input = `<select data-field="${fieldName}" ${requiredAttr} style="width:100%; padding:10px; border:1px solid #ddd; border-radius:4px; box-sizing:border-box; font-size:14px;">
            <option value="">-- Selecione --</option>
            ${options.map(opt => `<option value="${opt.value}" ${value === opt.value ? 'selected' : ''}>${opt.label}</option>`).join('')}
        </select>`;
    } else if (type === 'textarea') {
        input = `<textarea data-field="${fieldName}" ${requiredAttr} style="width:100%; padding:10px; border:1px solid #ddd; border-radius:4px; box-sizing:border-box; font-size:14px; min-height:80px; font-family:inherit; resize:vertical;">${value}</textarea>`;
    } else {
        const inputType = type === 'number' ? 'number' : (type === 'date' ? 'date' : (type === 'password' ? 'password' : 'text'));
        input = `<input type="${inputType}" data-field="${fieldName}" value="${value}" data-mask="${mask || ''}" data-validate="${(validate || []).join(',')}${requiredAttr ? ' ' + requiredAttr : ''}" style="width:100%; padding:10px; border:1px solid #ddd; border-radius:4px; box-sizing:border-box; font-size:14px;">`;
    }

    return `<div style="margin-bottom:15px;">
        <label style="display:block; margin-bottom:5px; font-weight:bold; color:#2c3e50; font-size:14px;">${label}${required ? ' <span style="color:red;">*</span>' : ''}</label>
        ${input}
        ${helpText}
    </div>`;
}

/**
 * Aplica máscaras aos inputs
 */
function applyFieldMasks(container) {
    const inputs = container.querySelectorAll('input[data-mask]');
    inputs.forEach(input => {
        const maskType = input.dataset.mask;
        if (maskType && INPUT_MASKS[maskType]) {
            input.addEventListener('input', (e) => {
                e.target.value = INPUT_MASKS[maskType].mask(e.target.value);
            });
        }
    });
}

/**
 * Aplica validações aos inputs
 */
function applyFieldValidations(container) {
    const inputs = container.querySelectorAll('input[data-validate], select[required], textarea[required]');
    inputs.forEach(input => {
        input.addEventListener('blur', (e) => {
            validateFieldElement(e.target);
        });
    });
}

/**
 * Valida um elemento individual
 */
function validateFieldElement(element) {
    const validations = element.dataset.validate ? element.dataset.validate.split(',').filter(v => v) : [];
    const value = element.value.trim();
    const isRequired = element.hasAttribute('required');

    // Validação obrigatória
    if (isRequired && !value) {
        setFieldError(element, 'Campo obrigatório');
        return false;
    }

    // Validações específicas
    for (const validation of validations) {
        let isValid = true;
        let errorMsg = '';

        if (validation.startsWith('minLength:')) {
            const min = parseInt(validation.split(':')[1]);
            isValid = value.length >= min;
            errorMsg = `Mínimo ${min} caracteres`;
        } else if (validation.startsWith('maxLength:')) {
            const max = parseInt(validation.split(':')[1]);
            isValid = value.length <= max;
            errorMsg = `Máximo ${max} caracteres`;
        } else if (validation.startsWith('minValue:')) {
            const min = parseFloat(validation.split(':')[1]);
            isValid = parseFloat(value) >= min;
            errorMsg = `Valor mínimo: ${min}`;
        } else if (validation.startsWith('maxValue:')) {
            const max = parseFloat(validation.split(':')[1]);
            isValid = parseFloat(value) <= max;
            errorMsg = `Valor máximo: ${max}`;
        } else if (validation === 'cpf') {
            isValid = validateCPF(value);
            errorMsg = 'CPF inválido';
        } else if (validation === 'cnpj') {
            isValid = validateCNPJ(value);
            errorMsg = 'CNPJ inválido';
        } else if (validation === 'email') {
            isValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
            errorMsg = 'Email inválido';
        } else if (validation === 'number') {
            isValid = !isNaN(value) && value !== '';
            errorMsg = 'Deve ser um número';
        } else if (validation === 'futureDate') {
            isValid = new Date(value) > new Date();
            errorMsg = 'Data deve estar no futuro';
        }

        if (!isValid && value) {
            setFieldError(element, errorMsg);
            return false;
        }
    }

    clearFieldError(element);
    return true;
}

/**
 * Define erro em um campo
 */
function setFieldError(element, message) {
    element.style.borderColor = '#e74c3c';
    element.style.backgroundColor = '#fadbd8';
    
    let errorDiv = element.parentElement.querySelector('.field-error');
    if (!errorDiv) {
        errorDiv = document.createElement('div');
        errorDiv.className = 'field-error';
        errorDiv.style.cssText = 'color:#e74c3c; font-size:12px; margin-top:3px; font-weight:bold;';
        element.parentElement.appendChild(errorDiv);
    }
    errorDiv.textContent = '❌ ' + message;
}

/**
 * Remove erro de um campo
 */
function clearFieldError(element) {
    element.style.borderColor = '#27ae60';
    element.style.backgroundColor = '#eafaf1';
    
    const errorDiv = element.parentElement.querySelector('.field-error');
    if (errorDiv) {
        errorDiv.remove();
    }
}

/**
 * Validações do CPF (Luhn)
 */
function validateCPF(cpf) {
    cpf = cpf.replace(/\D/g, '');
    if (cpf.length !== 11) return false;
    if (/(\d)\1{10}/.test(cpf)) return false;

    let sum = 0;
    let remainder;

    for (let i = 1; i <= 9; i++) {
        sum += parseInt(cpf.substring(i - 1, i)) * (11 - i);
    }

    remainder = (sum * 10) % 11;
    if (remainder === 10 || remainder === 11) remainder = 0;
    if (remainder !== parseInt(cpf.substring(9, 10))) return false;

    sum = 0;
    for (let i = 1; i <= 10; i++) {
        sum += parseInt(cpf.substring(i - 1, i)) * (12 - i);
    }

    remainder = (sum * 10) % 11;
    if (remainder === 10 || remainder === 11) remainder = 0;
    if (remainder !== parseInt(cpf.substring(10, 11))) return false;

    return true;
}

/**
 * Validações do CNPJ
 */
function validateCNPJ(cnpj) {
    cnpj = cnpj.replace(/\D/g, '');
    if (cnpj.length !== 14) return false;
    if (/(\d)\1{13}/.test(cnpj)) return false;

    let sum = 0;
    let remainder;

    for (let i = 0; i < 12; i++) {
        sum += parseInt(cnpj[i]) * ((i < 4) ? (5 - i) : (13 - i));
    }

    remainder = sum % 11;
    remainder = remainder < 2 ? 0 : 11 - remainder;
    if (remainder !== parseInt(cnpj[12])) return false;

    sum = 0;
    for (let i = 0; i < 13; i++) {
        sum += parseInt(cnpj[i]) * ((i < 5) ? (6 - i) : (14 - i));
    }

    remainder = sum % 11;
    remainder = remainder < 2 ? 0 : 11 - remainder;
    if (remainder !== parseInt(cnpj[13])) return false;

    return true;
}
