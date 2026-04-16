/**
 * Pagination Utilities
 * Handles pagination logic and UI rendering
 */

/**
 * Calculate total pages
 */
function calculateTotalPages(totalItems, pageSize) {
    return Math.ceil(totalItems / pageSize);
}

/**
 * Get pagination info object
 */
function getPaginationInfo(totalItems, currentPage = 1, pageSize = 15) {
    const totalPages = calculateTotalPages(totalItems, pageSize);
    const startItem = (currentPage - 1) * pageSize + 1;
    const endItem = Math.min(currentPage * pageSize, totalItems);
    
    return {
        currentPage,
        totalPages,
        totalItems,
        pageSize,
        startItem,
        endItem,
        hasPrevious: currentPage > 1,
        hasNext: currentPage < totalPages,
    };
}

/**
 * Create pagination HTML
 */
function createPaginationHTML(paginationInfo, onPageChange) {
    const { currentPage, totalPages, startItem, endItem, totalItems, hasPrevious, hasNext } = paginationInfo;
    
    if (totalPages <= 1) {
        return ''; // No pagination needed
    }
    
    let html = `
        <div class="flex items-center justify-between mt-8 px-6 py-4 bg-white border-t">
            <div class="text-sm text-gray-600">
                Mostrando <span class="font-semibold">${startItem}</span> a <span class="font-semibold">${endItem}</span> de <span class="font-semibold">${totalItems}</span> resultados
            </div>
            <div class="flex gap-2">
    `;
    
    // First page button
    html += hasPrevious 
        ? `<button onclick="${onPageChange}(1)" class="px-3 py-2 border rounded-lg hover:bg-gray-50">Primeira</button>`
        : `<button disabled class="px-3 py-2 border rounded-lg text-gray-400">Primeira</button>`;
    
    // Previous button
    html += hasPrevious 
        ? `<button onclick="${onPageChange}(${currentPage - 1})" class="px-3 py-2 border rounded-lg hover:bg-gray-50">‹ Anterior</button>`
        : `<button disabled class="px-3 py-2 border rounded-lg text-gray-400">‹ Anterior</button>`;
    
    // Page numbers
    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, currentPage + 2);
    
    if (startPage > 1) {
        html += `<button onclick="${onPageChange}(1)" class="px-3 py-2 border rounded-lg hover:bg-gray-50">1</button>`;
        if (startPage > 2) {
            html += `<span class="px-3 py-2">...</span>`;
        }
    }
    
    for (let i = startPage; i <= endPage; i++) {
        if (i === currentPage) {
            html += `<button class="px-3 py-2 border rounded-lg bg-blue-600 text-white">${i}</button>`;
        } else {
            html += `<button onclick="${onPageChange}(${i})" class="px-3 py-2 border rounded-lg hover:bg-gray-50">${i}</button>`;
        }
    }
    
    if (endPage < totalPages) {
        if (endPage < totalPages - 1) {
            html += `<span class="px-3 py-2">...</span>`;
        }
        html += `<button onclick="${onPageChange}(${totalPages})" class="px-3 py-2 border rounded-lg hover:bg-gray-50">${totalPages}</button>`;
    }
    
    // Next button
    html += hasNext 
        ? `<button onclick="${onPageChange}(${currentPage + 1})" class="px-3 py-2 border rounded-lg hover:bg-gray-50">Próximo ›</button>`
        : `<button disabled class="px-3 py-2 border rounded-lg text-gray-400">Próximo ›</button>`;
    
    // Last page button
    html += hasNext 
        ? `<button onclick="${onPageChange}(${totalPages})" class="px-3 py-2 border rounded-lg hover:bg-gray-50">Última</button>`
        : `<button disabled class="px-3 py-2 border rounded-lg text-gray-400">Última</button>`;
    
    html += `
            </div>
        </div>
    `;
    
    return html;
}

/**
 * Render pagination info text only
 */
function createPaginationText(paginationInfo) {
    const { startItem, endItem, totalItems } = paginationInfo;
    return `Mostrando ${startItem} a ${endItem} de ${totalItems} resultados`;
}

/**
 * Create simple pagination links
 */
function createSimplePaginationHTML(paginationInfo, onPageChange) {
    const { currentPage, totalPages, hasPrevious, hasNext } = paginationInfo;
    
    if (totalPages <= 1) {
        return '';
    }
    
    let html = '<div class="flex justify-center gap-2 mt-6">';
    
    if (hasPrevious) {
        html += `<button onclick="${onPageChange}(${currentPage - 1})" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">Anterior</button>`;
    }
    
    html += `<span class="px-4 py-2 text-gray-700">Página ${currentPage} de ${totalPages}</span>`;
    
    if (hasNext) {
        html += `<button onclick="${onPageChange}(${currentPage + 1})" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">Próximo</button>`;
    }
    
    html += '</div>';
    return html;
}

/**
 * Parse current page from URL
 */
function getCurrentPage() {
    const params = new URLSearchParams(window.location.search);
    return parseInt(params.get('page')) || 1;
}

/**
 * Parse page size from URL
 */
function getPageSize() {
    const params = new URLSearchParams(window.location.search);
    return parseInt(params.get('pageSize')) || 15;
}

/**
 * Navigate to page
 */
function goToPage(page, pageSize = 15) {
    const params = new URLSearchParams(window.location.search);
    params.set('page', page);
    params.set('pageSize', pageSize);
    window.location.search = params.toString();
}

/**
 * Update page without full reload (for AJAX)
 */
function updatePageURL(page, pageSize = 15) {
    const params = new URLSearchParams(window.location.search);
    params.set('page', page);
    params.set('pageSize', pageSize);
    window.history.replaceState({}, '', `${window.location.pathname}?${params.toString()}`);
}

/**
 * Setup pagination controls
 */
function setupPaginationControls(totalItems, pageSize, onPageChange) {
    const currentPage = getCurrentPage();
    const paginationInfo = getPaginationInfo(totalItems, currentPage, pageSize);
    
    // Create pagination HTML
    const paginationHTML = createPaginationHTML(paginationInfo, onPageChange);
    
    // Insert pagination at the end of the container
    const container = document.querySelector('[data-pagination-container]');
    if (container && paginationHTML) {
        container.innerHTML += paginationHTML;
    }
    
    return paginationInfo;
}

/**
 * Inline pagination for table footer
 */
function createInlinePagination(paginationInfo, containerId, onPageChange) {
    const container = document.getElementById(containerId);
    if (!container) return;
    
    const paginationHTML = createPaginationHTML(paginationInfo, `window.${onPageChange}`);
    container.innerHTML += paginationHTML;
}

/**
 * Create page size selector
 */
function createPageSizeSelector(currentPageSize, onPageSizeChange) {
    const sizes = [10, 15, 25, 50];
    let html = `
        <div class="flex items-center gap-2">
            <label for="pageSize" class="text-sm text-gray-700">Itens por página:</label>
            <select id="pageSize" onchange="handlePageSizeChange(this.value, '${onPageSizeChange}')" class="border border-gray-300 rounded-lg px-3 py-2">
    `;
    
    for (const size of sizes) {
        const selected = size === currentPageSize ? 'selected' : '';
        html += `<option value="${size}" ${selected}>${size}</option>`;
    }
    
    html += `
            </select>
        </div>
    `;
    
    return html;
}

/**
 * Handle page size change
 */
function handlePageSizeChange(newPageSize, onPageSizeChange) {
    const params = new URLSearchParams(window.location.search);
    params.set('pageSize', newPageSize);
    params.set('page', 1); // Reset to first page
    window.location.search = params.toString();
}

/**
 * Calculate items per row for grid layout
 */
function calculateItemsPerRow(containerWidth, itemWidth = 300, gap = 16) {
    return Math.floor((containerWidth + gap) / (itemWidth + gap));
}

/**
 * Calculate visible items on current viewport
 */
function getVisibleItemsCount() {
    const container = document.querySelector('[data-items-container]');
    if (!container) return 0;
    
    const items = container.querySelectorAll('[data-item]');
    return items.length;
}
