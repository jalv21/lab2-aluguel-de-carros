/**
 * Enhanced Form Validation with Real-time Feedback
 * Provides field-level validation with visual feedback and integration with input masks
 */

/**
 * Show validation error below field
 */
function showFieldError(fieldElement, errorMessage) {
    // Remove existing error message
    const existingError = fieldElement.parentElement.querySelector('.field-error');
    if (existingError) existingError.remove();

    // Add error class to field
    fieldElement.classList.add('is-invalid');
    fieldElement.style.borderColor = '#DC3545';

    if (errorMessage) {
        const errorDiv = document.createElement('div');
        errorDiv.className = 'field-error';
        errorDiv.style.cssText = `
            color: #DC3545;
            font-size: 12px;
            margin-top: 4px;
            font-weight: 500;
            display: block;
        `;
        errorDiv.textContent = '❌ ' + errorMessage;
        fieldElement.parentElement.appendChild(errorDiv);
    }
}

/**
 * Clear validation error from field
 */
function clearFieldError(fieldElement) {
    fieldElement.classList.remove('is-invalid');
    fieldElement.style.borderColor = '';
    
    const errorDiv = fieldElement.parentElement.querySelector('.field-error');
    if (errorDiv) errorDiv.remove();

    // Show success indicator
    fieldElement.style.borderColor = '#28A745';
    fieldElement.style.backgroundColor = '#F0FFF4';
}

/**
 * Real-time field validation
 */
function attachFieldValidation(fieldElement, fieldName, rules = {}) {
    const mergedRules = { ...VALIDATION_RULES, ...rules };
    const rule = mergedRules[fieldName];

    if (!rule) return;

    // Validate on blur
    fieldElement.addEventListener('blur', () => {
        const value = fieldElement.value.trim();
        const error = validateField(fieldName, value, rule);
        
        if (error) {
            showFieldError(fieldElement, error);
        } else {
            clearFieldError(fieldElement);
        }
    });

    // Clear error on focus
    fieldElement.addEventListener('focus', () => {
        const existingError = fieldElement.parentElement.querySelector('.field-error');
        if (existingError) {
            existingError.style.display = 'none';
        }
    });

    // Validate on input for certain field types
    if (rule.isNumber || rule.min !== undefined || rule.max !== undefined) {
        fieldElement.addEventListener('input', () => {
            const value = fieldElement.value;
            if (value && rule.isNumber && isNaN(value)) {
                fieldElement.style.borderColor = '#FFC107';
            } else {
                fieldElement.style.borderColor = '';
            }
        });
    }
}

/**
 * Validate a single field
 */
function validateField(fieldName, value, rule) {
    // Check required
    if (rule.required && (!value || value === '')) {
        return 'Este campo é obrigatório';
    }

    if (!value) return null; // Skip empty optional fields

    // Check pattern
    if (rule.pattern && !rule.pattern.test(value)) {
        return rule.message || `Formato inválido`;
    }

    // Check custom validator
    if (rule.validator && !rule.validator(value)) {
        return rule.message || `Valor inválido`;
    }

    // Check minLength
    if (rule.minLength && value.length < rule.minLength) {
        return `Mínimo ${rule.minLength} caracteres (${value.length}/${rule.minLength})`;
    }

    // Check maxLength
    if (rule.maxLength && value.length > rule.maxLength) {
        return `Máximo ${rule.maxLength} caracteres (${value.length}/${rule.maxLength})`;
    }

    // Check isNumber
    if (rule.isNumber && isNaN(value)) {
        return `Deve ser um número`;
    }

    // Check min value
    if (rule.min !== undefined && parseFloat(value) < rule.min) {
        return `Mínimo permitido: ${rule.min}`;
    }

    // Check max value
    if (rule.max !== undefined && parseFloat(value) > rule.max) {
        return `Máximo permitido: ${rule.max}`;
    }

    // Check enum
    if (rule.enum && !rule.enum.includes(value)) {
        return `Valor inválido. Opções: ${rule.enum.join(', ')}`;
    }

    return null;
}

/**
 * Initialize real-time validation for form fields
 */
function initializeFieldValidation(formElement) {
    if (!formElement) return;

    const fields = formElement.querySelectorAll('input, select, textarea');
    
    fields.forEach(field => {
        const fieldName = field.name;
        if (fieldName) {
            attachFieldValidation(field, fieldName);
        }
    });
}

/**
 * Show validation summary at top of form
 */
function showValidationSummary(formElement, errors) {
    let summary = formElement.querySelector('.validation-summary');
    if (summary) summary.remove();

    if (Object.keys(errors).length === 0) return;

    summary = document.createElement('div');
    summary.className = 'validation-summary';
    summary.style.cssText = `
        background-color: #F8D7DA;
        border: 1px solid #F5C6CB;
        border-radius: 4px;
        padding: 12px;
        margin-bottom: 20px;
        color: #721C24;
    `;

    const header = document.createElement('strong');
    header.textContent = '⚠️ Erros encontrados:';
    summary.appendChild(header);

    const list = document.createElement('ul');
    list.style.cssText = 'margin: 8px 0 0 20px; padding: 0;';
    
    Object.entries(errors).forEach(([fieldName, errorMessage]) => {
        const item = document.createElement('li');
        item.textContent = errorMessage;
        item.style.cssText = 'margin: 4px 0; font-size: 14px;';
        list.appendChild(item);
    });

    summary.appendChild(list);
    formElement.insertBefore(summary, formElement.firstChild);
}

/**
 * Enhanced validateForm with visual feedback
 */
function validateFormEnhanced(formElement, rules = {}) {
    const result = validateForm(formElement, rules);
    
    // Remove previous error indicators
    formElement.querySelectorAll('.field-error').forEach(el => el.remove());
    formElement.querySelectorAll('.is-invalid').forEach(el => {
        el.classList.remove('is-invalid');
        el.style.borderColor = '';
    });

    // Show errors for invalid fields
    formElement.querySelectorAll('input, select, textarea').forEach(field => {
        const fieldName = field.name;
        if (result.errors[fieldName]) {
            showFieldError(field, result.errors[fieldName]);
        }
    });

    // Show summary
    showValidationSummary(formElement, result.errors);

    return result;
}

/**
 * Initialize validation for all forms on page
 */
function initializeAllValidation() {
    const forms = document.querySelectorAll('form[data-validate]');
    
    forms.forEach(form => {
        initializeFieldValidation(form);
        
        // Prevent submission if form is invalid
        form.addEventListener('submit', (e) => {
            const result = validateFormEnhanced(form);
            if (!result.valid) {
                e.preventDefault();
                // Focus on first invalid field
                const firstInvalid = form.querySelector('.is-invalid');
                if (firstInvalid) firstInvalid.focus();
            }
        });
    });
}

// Initialize on page load
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initializeAllValidation);
} else {
    initializeAllValidation();
}
