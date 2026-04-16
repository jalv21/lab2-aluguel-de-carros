/**
 * Main Application JavaScript
 * Handles event delegation, modals, toasts, and general initialization
 */

let currentModal = null;
let modalCallback = null;

/**
 * Initialize application on DOM ready
 */
document.addEventListener('DOMContentLoaded', function() {
    console.log('Application initialized');
    
    // Setup event delegation
    setupEventListeners();
    
    // Initialize tooltips and icons
    feather.replace();
    
    // Setup keyboard shortcuts
    setupKeyboardShortcuts();
});

/**
 * Setup event delegation for dynamic elements
 */
function setupEventListeners() {
    // Form submit handlers
    document.addEventListener('submit', function(e) {
        const form = e.target;
        
        // Check if form has data-validate attribute
        if (form.hasAttribute('data-validate')) {
            const validation = validateForm(form);
            if (!validation.valid) {
                e.preventDefault();
                displayFormErrors(form, validation.errors);
                showError('Verifique os erros no formulário');
                return false;
            }
        }
    });
    
    // Delete button handlers
    document.addEventListener('click', function(e) {
        if (e.target.hasAttribute('data-delete')) {
            e.preventDefault();
            const resourceId = e.target.getAttribute('data-delete');
            const resourceName = e.target.getAttribute('data-resource') || 'recurso';
            confirmDelete(resourceId, resourceName, () => {
                // This will be overridden by specific page implementations
            });
        }
    });
    
    // Search/filter handlers with debounce
    document.addEventListener('input', function(e) {
        if (e.target.hasAttribute('data-search')) {
            clearTimeout(e.target.searchTimeout);
            e.target.searchTimeout = setTimeout(() => {
                const searchEvent = new CustomEvent('search', { 
                    detail: { query: e.target.value } 
                });
                document.dispatchEvent(searchEvent);
            }, 300);
        }
    });
}

/**
 * Setup keyboard shortcuts
 */
function setupKeyboardShortcuts() {
    document.addEventListener('keydown', function(e) {
        // Ctrl/Cmd + K for search
        if ((e.ctrlKey || e.metaKey) && e.key === 'k') {
            e.preventDefault();
            const searchInput = document.querySelector('[data-search]');
            if (searchInput) searchInput.focus();
        }
        
        // Escape to close modal
        if (e.key === 'Escape') {
            closeModal();
        }
    });
}

/**
 * Open a modal dialog
 */
function openModal(title, content, confirmCallback = null) {
    const modal = document.getElementById('modalTemplate');
    const titleEl = document.getElementById('modalTitle');
    const bodyEl = document.getElementById('modalBody');
    const confirmBtn = document.getElementById('modalConfirm');
    
    titleEl.textContent = title;
    bodyEl.innerHTML = content;
    
    modalCallback = confirmCallback;
    
    modal.classList.remove('hidden');
    currentModal = modal;
    
    // Re-initialize feather icons in modal
    feather.replace();
}

/**
 * Close the modal
 */
function closeModal() {
    const modal = document.getElementById('modalTemplate');
    modal.classList.add('hidden');
    currentModal = null;
    modalCallback = null;
}

/**
 * Confirm modal action
 */
function confirmModal() {
    if (modalCallback && typeof modalCallback === 'function') {
        modalCallback();
    }
    closeModal();
}

/**
 * Confirm delete action with modal
 */
function confirmDelete(resourceId, resourceName, callback) {
    const content = `
        <p>Tem certeza que deseja deletar este ${resourceName}?</p>
        <p class="text-gray-500 text-sm mt-2">Esta ação não pode ser desfeita.</p>
    `;
    
    openModal(`Deletar ${resourceName}`, content, callback);
}

/**
 * Show confirmation modal for actions
 */
function confirmAction(title, message, confirmCallback) {
    const content = `
        <p>${message}</p>
    `;
    
    openModal(title, content, confirmCallback);
}

/**
 * Show a notification message with fade out
 */
function showNotification(message, type = 'info', duration = 4000) {
    const container = document.getElementById('messagesContainer');
    const bgColor = type === 'success' ? 'bg-green-100' : type === 'error' ? 'bg-red-100' : 'bg-blue-100';
    const textColor = type === 'success' ? 'text-green-800' : type === 'error' ? 'text-red-800' : 'text-blue-800';
    const borderColor = type === 'success' ? 'border-green-400' : type === 'error' ? 'border-red-400' : 'border-blue-400';
    
    const notification = document.createElement('div');
    notification.className = `${bgColor} ${textColor} ${borderColor} border-l-4 p-4 mb-4 rounded flex items-center gap-3`;
    notification.innerHTML = `
        ${type === 'success' ? '<i data-feather="check-circle" class="w-5 h-5 flex-shrink-0"></i>' : type === 'error' ? '<i data-feather="alert-circle" class="w-5 h-5 flex-shrink-0"></i>' : '<i data-feather="info" class="w-5 h-5 flex-shrink-0"></i>'}
        <span class="flex-1">${message}</span>
        <button onclick="this.parentNode.remove()" class="text-gray-400 hover:text-gray-600">
            <i data-feather="x" class="w-4 h-4"></i>
        </button>
    `;
    
    container.appendChild(notification);
    feather.replace();
    
    if (duration > 0) {
        setTimeout(() => notification.remove(), duration);
    }
}

/**
 * Format table column data for display
 */
function formatTableValue(value, type = 'text') {
    if (value === null || value === undefined) return '-';
    
    switch(type) {
        case 'currency':
            return formatCurrency(value);
        case 'date':
            return formatDate(value);
        case 'cpf':
            return formatCPF(value);
        case 'cnpj':
            return formatCNPJ(value);
        case 'boolean':
            return value ? '<span class="text-green-600">✓ Sim</span>' : '<span class="text-red-600">✗ Não</span>';
        case 'status':
            return formatStatus(value);
        default:
            return String(value);
    }
}

/**
 * Format status badge
 */
function formatStatus(status) {
    const statusMap = {
        'PENDENTE': { bg: 'bg-yellow-100', text: 'text-yellow-800', label: 'Pendente' },
        'APROVADO': { bg: 'bg-green-100', text: 'text-green-800', label: 'Aprovado' },
        'REJEITADO': { bg: 'bg-red-100', text: 'text-red-800', label: 'Rejeitado' },
        'CANCELADO': { bg: 'bg-gray-100', text: 'text-gray-800', label: 'Cancelado' },
    };
    
    const style = statusMap[status] || { bg: 'bg-gray-100', text: 'text-gray-800', label: status };
    return `<span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${style.bg} ${style.text}">${style.label}</span>`;
}

/**
 * Create a table row HTML for display
 */
function createTableRow(data, columns, actions = []) {
    let html = '<tr class="border-t hover:bg-gray-50">';
    
    for (const column of columns) {
        const value = data[column.key];
        const formatted = formatTableValue(value, column.type);
        html += `<td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">${formatted}</td>`;
    }
    
    // Add action buttons
    if (actions.length > 0) {
        html += '<td class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">';
        for (const action of actions) {
            html += action;
        }
        html += '</td>';
    }
    
    html += '</tr>';
    return html;
}

/**
 * Create edit button HTML
 */
function createEditButton(resourceId, resourceName) {
    return `
        <a href="/${resourceName}/${resourceId}/edit" class="text-blue-600 hover:text-blue-800 mr-3">
            <i data-feather="edit" class="w-4 h-4 inline"></i> Editar
        </a>
    `;
}

/**
 * Create delete button HTML
 */
function createDeleteButton(resourceId, resourceName, onDeleteCallback) {
    return `
        <button type="button" data-delete="${resourceId}" data-resource="${resourceName}" class="text-red-600 hover:text-red-800" onclick="if(confirm('Tem certeza?')) { deleteResource('${resourceId}', '${resourceName}', ${onDeleteCallback}); }">
            <i data-feather="trash-2" class="w-4 h-4 inline"></i> Deletar
        </button>
    `;
}

/**
 * Create action button HTML
 */
function createActionButton(label, resourceId, action, onclick) {
    return `
        <button type="button" class="text-green-600 hover:text-green-800 mr-3" onclick="${onclick}">
            <i data-feather="check" class="w-4 h-4 inline"></i> ${label}
        </button>
    `;
}

/**
 * Format a number as integer
 */
function formatInteger(num) {
    return Math.floor(num);
}

/**
 * Format a number with decimal places
 */
function formatDecimal(num, places = 2) {
    return parseFloat(num).toFixed(places);
}

/**
 * Highlight search terms in text
 */
function highlightSearchTerms(text, searchTerm) {
    if (!searchTerm) return text;
    
    const regex = new RegExp(`(${searchTerm})`, 'gi');
    return text.replace(regex, '<mark class="bg-yellow-200">$1</mark>');
}

/**
 * Show loading spinner
 */
function showLoading() {
    const loader = document.createElement('div');
    loader.id = 'loadingSpinner';
    loader.className = 'fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50';
    loader.innerHTML = `
        <div class="text-white text-center">
            <div class="inline-block">
                <div class="border-4 border-white border-t-blue-500 rounded-full animate-spin w-12 h-12"></div>
                <p class="mt-4">Carregando...</p>
            </div>
        </div>
    `;
    document.body.appendChild(loader);
}

/**
 * Hide loading spinner
 */
function hideLoading() {
    const loader = document.getElementById('loadingSpinner');
    if (loader) loader.remove();
}

/**
 * Execute with loading overlay
 */
async function executeWithLoading(promise) {
    showLoading();
    try {
        const result = await promise;
        hideLoading();
        return result;
    } catch (error) {
        hideLoading();
        throw error;
    }
}

/**
 * Export table to CSV
 */
function exportTableToCSV(tableId, filename = 'export.csv') {
    const table = document.getElementById(tableId);
    if (!table) return;
    
    let csv = [];
    const rows = table.querySelectorAll('tr');
    
    rows.forEach((row) => {
        const cols = row.querySelectorAll('td, th');
        let csvrow = [];
        cols.forEach((col) => {
            csvrow.push('"' + col.innerText.replace(/"/g, '""') + '"');
        });
        csv.push(csvrow.join(','));
    });
    
    downloadCSV(csv.join('\n'), filename);
}

/**
 * Download CSV file
 */
function downloadCSV(csv, filename) {
    const csvFile = new Blob([csv], { type: 'text/csv' });
    const downloadLink = document.createElement('a');
    downloadLink.href = URL.createObjectURL(csvFile);
    downloadLink.download = filename;
    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}

/**
 * Copy text to clipboard
 */
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        showNotification('Copiado para área de transferência!', 'success', 2000);
    }).catch(() => {
        showError('Erro ao copiar para área de transferência');
    });
}

/**
 * Scroll to element
 */
function scrollToElement(elementId) {
    const element = document.getElementById(elementId);
    if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
    }
}

/**
 * Check if an element is in viewport
 */
function isInViewport(element) {
    const rect = element.getBoundingClientRect();
    return (
        rect.top >= 0 &&
        rect.left >= 0 &&
        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
        rect.right <= (window.innerWidth || document.documentElement.clientWidth)
    );
}

/**
 * Get query parameter from URL
 */
function getQueryParam(paramName) {
    const searchParams = new URLSearchParams(window.location.search);
    return searchParams.get(paramName);
}

/**
 * Set query parameter in URL
 */
function setQueryParam(paramName, paramValue) {
    const searchParams = new URLSearchParams(window.location.search);
    searchParams.set(paramName, paramValue);
    window.history.replaceState({}, '', `${window.location.pathname}?${searchParams}`);
}

/**
 * Log debug info (only in development)
 */
function debug(message, data = null) {
    if (window.DEBUG_MODE) {
        console.log(`[DEBUG] ${message}`, data || '');
    }
}

/**
 * Handle user logout
 */
function logout() {
    if (confirm('Tem certeza que deseja sair?')) {
        // In production, this could invalidate the session on server
        window.location.href = '/logout';
    }
}

/**
 * Sanitize HTML to prevent XSS attacks
 */
function sanitizeHtml(html) {
    const div = document.createElement('div');
    div.textContent = html;
    return div.innerHTML;
}

/**
 * Validate email format
 */
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

/**
 * Generate CSRF token (if needed)
 */
function getCsrfToken() {
    return document.querySelector('meta[name="csrf-token"]')?.getAttribute('content') || '';
}
