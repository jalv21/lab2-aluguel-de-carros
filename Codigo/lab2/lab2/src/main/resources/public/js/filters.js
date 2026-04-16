/**
 * Filtering and Search Utilities
 * Handles search, filtering, and advanced query building
 */

/**
 * Build filter query string from filter object
 */
function buildFilterQuery(filters) {
    const params = new URLSearchParams();
    
    for (const [key, value] of Object.entries(filters)) {
        if (value !== null && value !== undefined && value !== '') {
            params.append(key, value);
        }
    }
    
    return params.toString();
}

/**
 * Parse filter parameters from URL
 */
function parseFilterParams() {
    const params = new URLSearchParams(window.location.search);
    const filters = {};
    
    for (const [key, value] of params.entries()) {
        filters[key] = value;
    }
    
    return filters;
}

/**
 * Get search query from URL or parameter
 */
function getSearchQuery() {
    const params = new URLSearchParams(window.location.search);
    return params.get('search') || '';
}

/**
 * Get filter value from URL
 */
function getFilterValue(filterName) {
    const params = new URLSearchParams(window.location.search);
    return params.get(filterName) || '';
}

/**
 * Apply search filter and navigate
 */
function applySearch(searchQuery) {
    const params = new URLSearchParams(window.location.search);
    
    if (searchQuery) {
        params.set('search', searchQuery);
    } else {
        params.delete('search');
    }
    
    params.set('page', 1); // Reset to first page
    window.location.search = params.toString();
}

/**
 * Apply filters and navigate
 */
function applyFilters(filters) {
    const params = new URLSearchParams();
    
    for (const [key, value] of Object.entries(filters)) {
        if (value) {
            params.set(key, value);
        }
    }
    
    params.set('page', 1); // Reset to first page
    window.location.search = params.toString();
}

/**
 * Apply single filter
 */
function applySingleFilter(filterName, filterValue) {
    const params = new URLSearchParams(window.location.search);
    
    if (filterValue) {
        params.set(filterName, filterValue);
    } else {
        params.delete(filterName);
    }
    
    params.set('page', 1);
    window.location.search = params.toString();
}

/**
 * Clear all filters
 */
function clearFilters() {
    window.location.href = window.location.pathname;
}

/**
 * Clear specific filter
 */
function clearFilter(filterName) {
    const params = new URLSearchParams(window.location.search);
    params.delete(filterName);
    window.location.search = params.toString();
}

/**
 * Create filter dropdown HTML
 */
function createFilterDropdown(filterName, options, currentValue = '') {
    let html = `
        <div class="flex flex-col gap-2">
            <label for="${filterName}" class="text-sm font-medium text-gray-700">${filterName}</label>
            <select id="${filterName}" onchange="applySingleFilter('${filterName}', this.value)" class="border border-gray-300 rounded-lg px-3 py-2 text-sm">
                <option value="">-- Selecione --</option>
    `;
    
    for (const option of options) {
        const selected = option.value === currentValue ? 'selected' : '';
        html += `<option value="${option.value}" ${selected}>${option.label}</option>`;
    }
    
    html += `
            </select>
        </div>
    `;
    
    return html;
}

/**
 * Create status filter
 */
function createStatusFilter(currentStatus = '') {
    const statusOptions = [
        { value: 'PENDENTE', label: 'Pendente' },
        { value: 'APROVADO', label: 'Aprovado' },
        { value: 'REJEITADO', label: 'Rejeitado' },
        { value: 'CANCELADO', label: 'Cancelado' },
    ];
    
    return createFilterDropdown('status', statusOptions, currentStatus);
}

/**
 * Create contract type filter
 */
function createContractTypeFilter(currentType = '') {
    const typeOptions = [
        { value: 'ALUGUEL', label: 'Aluguel' },
        { value: 'CREDITO', label: 'Crédito' },
        { value: 'PROPRIEDADE', label: 'Propriedade' },
    ];
    
    return createFilterDropdown('tipoContrato', typeOptions, currentType);
}

/**
 * Create search box HTML
 */
function createSearchBox(placeholder = 'Buscar...', onSearch = 'applySearch') {
    const searchQuery = getSearchQuery();
    
    return `
        <div class="flex gap-2">
            <input 
                type="text" 
                id="searchInput"
                class="flex-1 border border-gray-300 rounded-lg px-4 py-2 text-sm" 
                placeholder="${placeholder}"
                value="${searchQuery}"
                data-search
                onkeyup="if(event.key === 'Enter') ${onSearch}(this.value)"
            >
            <button onclick="${onSearch}(document.getElementById('searchInput').value)" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 text-sm">
                <i data-feather="search" class="w-4 h-4 inline"></i>
            </button>
            ${searchQuery ? `<button onclick="clearFilters()" class="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 text-sm">
                <i data-feather="x" class="w-4 h-4 inline"></i>
            </button>` : ''}
        </div>
    `;
}

/**
 * Create advanced filter panel
 */
function createFilterPanel(filterConfigs = []) {
    let html = `
        <div class="bg-white p-4 rounded-lg border mb-4">
            <div class="flex justify-between items-center mb-4">
                <h3 class="font-semibold text-gray-800">Filtros Avançados</h3>
                <button onclick="clearFilters()" class="text-sm text-blue-600 hover:text-blue-700">
                    Limpar tudo
                </button>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
    `;
    
    for (const config of filterConfigs) {
        const currentValue = getFilterValue(config.name);
        
        if (config.type === 'select') {
            html += createFilterDropdown(config.name, config.options, currentValue);
        } else if (config.type === 'search') {
            html += `
                <div class="flex flex-col gap-2">
                    <label for="${config.name}" class="text-sm font-medium text-gray-700">${config.label}</label>
                    <input 
                        type="text" 
                        id="${config.name}"
                        placeholder="${config.placeholder || 'Digite aqui'}"
                        value="${currentValue}"
                        onkeyup="if(event.key === 'Enter') applySingleFilter('${config.name}', this.value)"
                        class="border border-gray-300 rounded-lg px-3 py-2 text-sm"
                    >
                </div>
            `;
        } else if (config.type === 'date') {
            html += `
                <div class="flex flex-col gap-2">
                    <label for="${config.name}" class="text-sm font-medium text-gray-700">${config.label}</label>
                    <input 
                        type="date" 
                        id="${config.name}"
                        value="${currentValue}"
                        onchange="applySingleFilter('${config.name}', this.value)"
                        class="border border-gray-300 rounded-lg px-3 py-2 text-sm"
                    >
                </div>
            `;
        }
    }
    
    html += `
            </div>
        </div>
    `;
    
    return html;
}

/**
 * Filter an array of items client-side
 */
function filterArray(items, searchTerm = '', filterKey = 'nome') {
    if (!searchTerm) return items;
    
    const term = searchTerm.toLowerCase();
    return items.filter(item => {
        const value = item[filterKey] ? item[filterKey].toString().toLowerCase() : '';
        return value.includes(term);
    });
}

/**
 * Sort array by key
 */
function sortArray(items, sortKey = 'id', sortOrder = 'asc') {
    return items.sort((a, b) => {
        const aVal = a[sortKey];
        const bVal = b[sortKey];
        
        if (typeof aVal === 'string') {
            return sortOrder === 'asc' 
                ? aVal.localeCompare(bVal)
                : bVal.localeCompare(aVal);
        } else {
            return sortOrder === 'asc' 
                ? aVal - bVal
                : bVal - aVal;
        }
    });
}

/**
 * Group array by key
 */
function groupArray(items, groupKey) {
    return items.reduce((groups, item) => {
        const key = item[groupKey];
        if (!groups[key]) {
            groups[key] = [];
        }
        groups[key].push(item);
        return groups;
    }, {});
}

/**
 * Create sortable table header
 */
function createSortableHeader(columnName, displayName = null, currentSort = null) {
    const displayText = displayName || columnName;
    const isActive = currentSort && currentSort.column === columnName;
    const arrow = isActive && currentSort.order === 'desc' ? ' ↓' : isActive && currentSort.order === 'asc' ? ' ↑' : '';
    
    return `
        <th class="px-6 py-3 bg-gray-50 text-left text-xs font-medium text-gray-700 uppercase tracking-wider cursor-pointer hover:bg-gray-100" 
            onclick="applySingleFilter('sort', '${columnName}:${currentSort && currentSort.column === columnName && currentSort.order === 'asc' ? 'desc' : 'asc'}')">
            ${displayText}${arrow}
        </th>
    `;
}

/**
 * Parse sort parameter from URL
 */
function parseSortParam() {
    const sortParam = getFilterValue('sort');
    if (!sortParam) return null;
    
    const [column, order] = sortParam.split(':');
    return { column, order: order || 'asc' };
}

/**
 * Create filter badge/chip
 */
function createFilterBadge(filterName, filterValue, displayLabel = null) {
    const label = displayLabel || `${filterName}: ${filterValue}`;
    
    return `
        <span class="inline-flex items-center gap-2 px-3 py-1 bg-blue-100 text-blue-800 rounded-full text-sm">
            ${label}
            <button onclick="clearFilter('${filterName}')" class="hover:text-blue-600">
                <i data-feather="x" class="w-4 h-4"></i>
            </button>
        </span>
    `;
}

/**
 * Create active filters display
 */
function createActiveFiltersDisplay() {
    const filters = parseFilterParams();
    if (Object.keys(filters).length === 0) return '';
    
    let html = '<div class="flex gap-2 mb-4 flex-wrap" id="activeFilters">';
    
    for (const [key, value] of Object.entries(filters)) {
        if (key !== 'page' && key !== 'pageSize') {
            html += createFilterBadge(key, value);
        }
    }
    
    html += '</div>';
    return html;
}

/**
 * Setup real-time search with debounce
 */
function setupRealtimeSearch(inputSelector, onSearch, debounceDelay = 300) {
    const input = document.querySelector(inputSelector);
    if (!input) return;
    
    let timeout;
    input.addEventListener('input', (e) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => {
            onSearch(e.target.value);
        }, debounceDelay);
    });
}

/**
 * Create filter toggle button
 */
function createFilterToggle(containerId = 'filterPanel') {
    return `
        <button onclick="document.getElementById('${containerId}').classList.toggle('hidden')" class="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 flex items-center gap-2">
            <i data-feather="filter" class="w-4 h-4"></i>
            Filtros
        </button>
    `;
}
