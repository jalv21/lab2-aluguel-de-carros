/**
 * REST API Client Wrapper
 * Centralizes all API calls with error handling and response parsing
 */

const API_BASE = '';
const API_ENDPOINTS = {
    // Cliente endpoints
    clientes: {
        list: '/clientes',
        create: '/clientes',
        getById: (id) => `/clientes/${id}`,
        update: (id) => `/clientes/${id}`,
        delete: (id) => `/clientes/${id}`,
        getByCpf: (cpf) => `/clientes/cpf/${cpf}`,
    },
    
    // Automovel endpoints
    automoveis: {
        list: '/automoveis',
        create: '/automoveis',
        getById: (id) => `/automoveis/${id}`,
        update: (id) => `/automoveis/${id}`,
        delete: (id) => `/automoveis/${id}`,
        getByPlaca: (placa) => `/automoveis/placa/${placa}`,
    },
    
    // PedidoAluguel endpoints
    pedidosAluguel: {
        list: '/pedidosaluguel',
        create: '/pedidosaluguel',
        getById: (id) => `/pedidosaluguel/${id}`,
        update: (id) => `/pedidosaluguel/${id}`,
        delete: (id) => `/pedidosaluguel/${id}`,
        getByClienteId: (clienteId) => `/pedidosaluguel/cliente/${clienteId}`,
        approve: (id) => `/pedidosaluguel/${id}/aprovar`,
        reject: (id) => `/pedidosaluguel/${id}/rejeitar`,
        cancel: (id) => `/pedidosaluguel/${id}/cancelar`,
    },
    
    // Contrato endpoints
    contratos: {
        list: '/contratos',
        create: '/contratos',
        getById: (id) => `/contratos/${id}`,
        update: (id) => `/contratos/${id}`,
        delete: (id) => `/contratos/${id}`,
        getByNumero: (numero) => `/contratos/numero/${numero}`,
        sign: (id) => `/contratos/${id}/assinar`,
    },
    
    // Agente endpoints
    agentes: {
        list: '/agentes',
        create: '/agentes',
        getById: (id) => `/agentes/${id}`,
        update: (id) => `/agentes/${id}`,
        delete: (id) => `/agentes/${id}`,
        getByCnpj: (cnpj) => `/agentes/cnpj/${cnpj}`,
    },
    
    // Rendimento endpoints
    rendimentos: {
        list: '/rendimentos',
        create: '/rendimentos',
        getById: (id) => `/rendimentos/${id}`,
        update: (id) => `/rendimentos/${id}`,
        delete: (id) => `/rendimentos/${id}`,
    },
};

/**
 * Base HTTP request handler
 * Handles common error scenarios and response parsing
 */
async function apiRequest(method, endpoint, data = null) {
    try {
        const options = {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
        };
        
        if (data && (method === 'POST' || method === 'PUT')) {
            options.body = JSON.stringify(data);
        }
        
        const response = await fetch(API_BASE + endpoint, options);
        
        // Handle different response types
        if (response.status === 204) {
            // No content
            return { success: true, data: null };
        }
        
        if (!response.ok) {
            // Error response
            const errorData = await response.json().catch(() => ({}));
            throw {
                status: response.status,
                message: errorData.message || getErrorMessage(response.status),
                data: errorData,
            };
        }
        
        const responseData = await response.json().catch(() => ({}));
        return { success: true, data: responseData };
        
    } catch (error) {
        console.error('API Error:', error);
        throw {
            status: error.status || 500,
            message: error.message || 'Erro ao comunicar com o servidor',
            data: error.data || {},
        };
    }
}

/**
 * Helper: Get user-friendly error messages
 */
function getErrorMessage(status) {
    const messages = {
        400: 'Dados inválidos. Verifique o formulário.',
        404: 'Recurso não encontrado.',
        409: 'Recurso já existe ou há conflito nos dados.',
        500: 'Erro interno do servidor. Tente novamente.',
    };
    return messages[status] || 'Erro desconhecido';
}

// ===========================
// CLIENTE API FUNCTIONS
// ===========================

async function fetchClientes(page = 1, pageSize = 15, search = '') {
    try {
        let url = API_ENDPOINTS.clientes.list;
        const params = new URLSearchParams({ page, pageSize });
        if (search) params.append('search', search);
        url += params.toString() ? '?' + params.toString() : '';
        
        const result = await apiRequest('GET', url);
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchClienteById(id) {
    try {
        const result = await apiRequest('GET', API_ENDPOINTS.clientes.getById(id));
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function createCliente(data) {
    try {
        const result = await apiRequest('POST', API_ENDPOINTS.clientes.create, data);
        showSuccess('Cliente criado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function updateCliente(id, data) {
    try {
        const result = await apiRequest('PUT', API_ENDPOINTS.clientes.update(id), data);
        showSuccess('Cliente atualizado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function deleteCliente(id) {
    try {
        const result = await apiRequest('DELETE', API_ENDPOINTS.clientes.delete(id));
        showSuccess('Cliente removido com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchClienteByCpf(cpf) {
    try {
        const result = await apiRequest('GET', API_ENDPOINTS.clientes.getByCpf(cpf));
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

// ===========================
// AUTOMOVEL API FUNCTIONS
// ===========================

async function fetchAutomoveis(page = 1, pageSize = 15, search = '') {
    try {
        let url = API_ENDPOINTS.automoveis.list;
        const params = new URLSearchParams({ page, pageSize });
        if (search) params.append('search', search);
        url += params.toString() ? '?' + params.toString() : '';
        
        const result = await apiRequest('GET', url);
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchAutomovelById(id) {
    try {
        const result = await apiRequest('GET', API_ENDPOINTS.automoveis.getById(id));
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function createAutomovel(data) {
    try {
        const result = await apiRequest('POST', API_ENDPOINTS.automoveis.create, data);
        showSuccess('Automóvel criado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function updateAutomovel(id, data) {
    try {
        const result = await apiRequest('PUT', API_ENDPOINTS.automoveis.update(id), data);
        showSuccess('Automóvel atualizado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function deleteAutomovel(id) {
    try {
        const result = await apiRequest('DELETE', API_ENDPOINTS.automoveis.delete(id));
        showSuccess('Automóvel removido com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchAutomovelByPlaca(placa) {
    try {
        const result = await apiRequest('GET', API_ENDPOINTS.automoveis.getByPlaca(placa));
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

// ===========================
// PEDIDO ALUGUEL API FUNCTIONS
// ===========================

async function fetchPedidosAluguel(page = 1, pageSize = 15, search = '', status = '') {
    try {
        let url = API_ENDPOINTS.pedidosAluguel.list;
        const params = new URLSearchParams({ page, pageSize });
        if (search) params.append('search', search);
        if (status) params.append('status', status);
        url += params.toString() ? '?' + params.toString() : '';
        
        const result = await apiRequest('GET', url);
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchPedidoAluguelById(id) {
    try {
        const result = await apiRequest('GET', API_ENDPOINTS.pedidosAluguel.getById(id));
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function createPedidoAluguel(data) {
    try {
        const result = await apiRequest('POST', API_ENDPOINTS.pedidosAluguel.create, data);
        showSuccess('Pedido de aluguel criado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function updatePedidoAluguel(id, data) {
    try {
        const result = await apiRequest('PUT', API_ENDPOINTS.pedidosAluguel.update(id), data);
        showSuccess('Pedido de aluguel atualizado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function deletePedidoAluguel(id) {
    try {
        const result = await apiRequest('DELETE', API_ENDPOINTS.pedidosAluguel.delete(id));
        showSuccess('Pedido de aluguel removido com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchPedidosAluguelByClienteId(clienteId, page = 1, pageSize = 15) {
    try {
        const url = API_ENDPOINTS.pedidosAluguel.getByClienteId(clienteId) + '?page=' + page + '&pageSize=' + pageSize;
        const result = await apiRequest('GET', url);
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function approvePedidoAluguel(id) {
    try {
        const result = await apiRequest('POST', API_ENDPOINTS.pedidosAluguel.approve(id));
        showSuccess('Pedido aprovado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function rejectPedidoAluguel(id) {
    try {
        const result = await apiRequest('POST', API_ENDPOINTS.pedidosAluguel.reject(id));
        showSuccess('Pedido rejeitado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function cancelPedidoAluguel(id) {
    try {
        const result = await apiRequest('POST', API_ENDPOINTS.pedidosAluguel.cancel(id));
        showSuccess('Pedido cancelado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

// ===========================
// CONTRATO API FUNCTIONS
// ===========================

async function fetchContratos(page = 1, pageSize = 15, search = '', tipo = '') {
    try {
        let url = API_ENDPOINTS.contratos.list;
        const params = new URLSearchParams({ page, pageSize });
        if (search) params.append('search', search);
        if (tipo) params.append('tipo', tipo);
        url += params.toString() ? '?' + params.toString() : '';
        
        const result = await apiRequest('GET', url);
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchContratoById(id) {
    try {
        const result = await apiRequest('GET', API_ENDPOINTS.contratos.getById(id));
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function createContrato(data) {
    try {
        const result = await apiRequest('POST', API_ENDPOINTS.contratos.create, data);
        showSuccess('Contrato criado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function updateContrato(id, data) {
    try {
        const result = await apiRequest('PUT', API_ENDPOINTS.contratos.update(id), data);
        showSuccess('Contrato atualizado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function deleteContrato(id) {
    try {
        const result = await apiRequest('DELETE', API_ENDPOINTS.contratos.delete(id));
        showSuccess('Contrato removido com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchContratoByNumero(numero) {
    try {
        const result = await apiRequest('GET', API_ENDPOINTS.contratos.getByNumero(numero));
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function signContrato(id) {
    try {
        const result = await apiRequest('POST', API_ENDPOINTS.contratos.sign(id));
        showSuccess('Contrato assinado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

// ===========================
// AGENTE API FUNCTIONS
// ===========================

async function fetchAgentes(page = 1, pageSize = 15, search = '') {
    try {
        let url = API_ENDPOINTS.agentes.list;
        const params = new URLSearchParams({ page, pageSize });
        if (search) params.append('search', search);
        url += params.toString() ? '?' + params.toString() : '';
        
        const result = await apiRequest('GET', url);
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchAgenteById(id) {
    try {
        const result = await apiRequest('GET', API_ENDPOINTS.agentes.getById(id));
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function createAgente(data) {
    try {
        const result = await apiRequest('POST', API_ENDPOINTS.agentes.create, data);
        showSuccess('Agente criado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function updateAgente(id, data) {
    try {
        const result = await apiRequest('PUT', API_ENDPOINTS.agentes.update(id), data);
        showSuccess('Agente atualizado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function deleteAgente(id) {
    try {
        const result = await apiRequest('DELETE', API_ENDPOINTS.agentes.delete(id));
        showSuccess('Agente removido com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchAgenteByCnpj(cnpj) {
    try {
        const result = await apiRequest('GET', API_ENDPOINTS.agentes.getByCnpj(cnpj));
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

// ===========================
// RENDIMENTO API FUNCTIONS
// ===========================

async function fetchRendimentos(page = 1, pageSize = 15, search = '') {
    try {
        let url = API_ENDPOINTS.rendimentos.list;
        const params = new URLSearchParams({ page, pageSize });
        if (search) params.append('search', search);
        url += params.toString() ? '?' + params.toString() : '';
        
        const result = await apiRequest('GET', url);
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function fetchRendimentoById(id) {
    try {
        const result = await apiRequest('GET', API_ENDPOINTS.rendimentos.getById(id));
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function createRendimento(data) {
    try {
        const result = await apiRequest('POST', API_ENDPOINTS.rendimentos.create, data);
        showSuccess('Rendimento criado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function updateRendimento(id, data) {
    try {
        const result = await apiRequest('PUT', API_ENDPOINTS.rendimentos.update(id), data);
        showSuccess('Rendimento atualizado com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

async function deleteRendimento(id) {
    try {
        const result = await apiRequest('DELETE', API_ENDPOINTS.rendimentos.delete(id));
        showSuccess('Rendimento removido com sucesso!');
        return result.data;
    } catch (error) {
        showError(error.message);
        throw error;
    }
}

// ===========================
// UTILITY FUNCTIONS
// ===========================

function showSuccess(message) {
    showToast(message, 'success');
}

function showError(message) {
    showToast(message, 'error');
}

function showToast(message, type = 'info') {
    const container = document.getElementById('toastContainer');
    const toast = document.createElement('div');
    
    const bgColor = type === 'success' ? 'bg-green-500' : type === 'error' ? 'bg-red-500' : 'bg-blue-500';
    
    toast.className = `${bgColor} text-white px-6 py-3 rounded-lg shadow-lg flex items-center gap-2 animate-slide-in`;
    toast.innerHTML = `
        ${type === 'success' ? '<i data-feather="check-circle" class="w-5 h-5"></i>' : type === 'error' ? '<i data-feather="alert-circle" class="w-5 h-5"></i>' : '<i data-feather="info" class="w-5 h-5"></i>'}
        <span>${message}</span>
    `;
    
    container.appendChild(toast);
    feather.replace();
    
    setTimeout(() => {
        toast.remove();
    }, 4000);
}

function logout() {
    if (confirm('Deseja realmente sair?')) {
        window.location.href = '/logout';
    }
}
