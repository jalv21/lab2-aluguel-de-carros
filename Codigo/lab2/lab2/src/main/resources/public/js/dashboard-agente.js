// API Base URL
const API_BASE_URL = 'http://localhost:8080';

// Initialize dashboard
document.addEventListener('DOMContentLoaded', () => {
    checkAuthentication();
    loadPedidosPendentes();
    loadTodosPedidos();
    loadClientes();
    loadAutomoveis();
    loadContratos();
    loadAgentes();
});

// Check if user is authenticated
function checkAuthentication() {
    const currentUser = localStorage.getItem('currentUser');
    const userType = localStorage.getItem('userType');

    if (!currentUser || (userType !== 'banco' && userType !== 'empresa')) {
        window.location.href = 'index.html';
        return;
    }

    const usuario = JSON.parse(currentUser);
    document.getElementById('userName').textContent = usuario.nome;
}

// Logout
function logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userType');
    window.location.href = 'index.html';
}

// Load pending rental requests
async function loadPedidosPendentes() {
    try {
        const response = await fetch(`${API_BASE_URL}/pedidos`);
        const pedidos = await response.json();
        const pendentes = pedidos.filter(p => p.status === 'PENDENTE');

        const tableBody = document.getElementById('pedidosPendentesBody');
        tableBody.innerHTML = '';

        if (pendentes.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="6">Nenhum pedido pendente</td></tr>';
            return;
        }

        for (const pedido of pendentes) {
            let clienteNome = 'N/A';
            let autoNome = 'N/A';

            try {
                const clienteResp = await fetch(`${API_BASE_URL}/clientes/${pedido.clienteId}`);
                const cliente = await clienteResp.json();
                clienteNome = cliente.nome;
            } catch (e) {}

            try {
                const autoResp = await fetch(`${API_BASE_URL}/automoveis/${pedido.automovelId}`);
                const auto = await autoResp.json();
                autoNome = `${auto.marca} ${auto.modelo}`;
            } catch (e) {}

            const row = `
                <tr>
                    <td>${pedido.id}</td>
                    <td>${clienteNome}</td>
                    <td>${autoNome}</td>
                    <td>${pedido.dataPedido}</td>
                    <td><span class="badge badge-pending">${pedido.status}</span></td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn btn-success" onclick="aprovarPedido(${pedido.id})">Aprovar</button>
                            <button class="btn btn-danger" onclick="rejeitarPedido(${pedido.id})">Rejeitar</button>
                        </div>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        }
    } catch (error) {
        console.error('Error loading pendentes:', error);
    }
}

// Load all rental requests
async function loadTodosPedidos() {
    try {
        const response = await fetch(`${API_BASE_URL}/pedidos`);
        const pedidos = await response.json();

        const tableBody = document.getElementById('todosPedidosBody');
        tableBody.innerHTML = '';

        if (pedidos.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="7">Nenhum pedido encontrado</td></tr>';
            return;
        }

        for (const pedido of pedidos) {
            let clienteNome = 'N/A';
            let autoNome = 'N/A';

            try {
                const clienteResp = await fetch(`${API_BASE_URL}/clientes/${pedido.clienteId}`);
                const cliente = await clienteResp.json();
                clienteNome = cliente.nome;
            } catch (e) {}

            try {
                const autoResp = await fetch(`${API_BASE_URL}/automoveis/${pedido.automovelId}`);
                const auto = await autoResp.json();
                autoNome = `${auto.marca} ${auto.modelo}`;
            } catch (e) {}

            const row = `
                <tr>
                    <td>${pedido.id}</td>
                    <td>${clienteNome}</td>
                    <td>${autoNome}</td>
                    <td>${pedido.dataPedido}</td>
                    <td>${pedido.dataLocal}</td>
                    <td><span class="badge badge-${getStatusClass(pedido.status)}">${pedido.status}</span></td>
                    <td>
                        <button class="btn btn-secondary" onclick="viewPedido(${pedido.id})">Ver Detalhes</button>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        }
    } catch (error) {
        console.error('Error loading todos pedidos:', error);
    }
}

// Load clients
async function loadClientes() {
    try {
        const response = await fetch(`${API_BASE_URL}/clientes`);
        const clientes = await response.json();

        const tableBody = document.getElementById('clientesTableBody');
        tableBody.innerHTML = '';

        clientes.forEach(cliente => {
            const row = `
                <tr>
                    <td>${cliente.id}</td>
                    <td>${cliente.nome}</td>
                    <td>${cliente.cpf}</td>
                    <td>${cliente.login}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn btn-secondary" onclick="editCliente(${cliente.id})">Editar</button>
                            <button class="btn btn-danger" onclick="deleteCliente(${cliente.id})">Deletar</button>
                        </div>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        console.error('Error loading clientes:', error);
    }
}

// Load vehicles
async function loadAutomoveis() {
    try {
        const response = await fetch(`${API_BASE_URL}/automoveis`);
        const automoveis = await response.json();

        const tableBody = document.getElementById('automovelTableBody');
        tableBody.innerHTML = '';

        automoveis.forEach(auto => {
            const row = `
                <tr>
                    <td>${auto.id}</td>
                    <td>${auto.matricula}</td>
                    <td>${auto.marca}</td>
                    <td>${auto.modelo}</td>
                    <td>${auto.placa}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn btn-secondary" onclick="editAutomovel(${auto.id})">Editar</button>
                            <button class="btn btn-danger" onclick="deleteAutomovel(${auto.id})">Deletar</button>
                        </div>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        console.error('Error loading automoveis:', error);
    }
}

// Load contracts
async function loadContratos() {
    try {
        const response = await fetch(`${API_BASE_URL}/contratos`);
        const contratos = await response.json();

        const tableBody = document.getElementById('contratosTableBody');
        tableBody.innerHTML = '';

        contratos.forEach(contrato => {
            const row = `
                <tr>
                    <td>${contrato.id}</td>
                    <td>${contrato.numero}</td>
                    <td>${contrato.tipoContrato}</td>
                    <td>${contrato.assinado ? 'Sim' : 'Não'}</td>
                    <td>
                        <div class="action-buttons">
                            ${!contrato.assinado ? `<button class="btn btn-success" onclick="assinarContrato(${contrato.id})">Assinar</button>` : ''}
                            <button class="btn btn-danger" onclick="deleteContrato(${contrato.id})">Deletar</button>
                        </div>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        console.error('Error loading contratos:', error);
    }
}

// Load agents
async function loadAgentes() {
    try {
        const response = await fetch(`${API_BASE_URL}/agentes`);
        const agentes = await response.json();

        const tableBody = document.getElementById('agentesTableBody');
        tableBody.innerHTML = '';

        agentes.forEach(agente => {
            const tipo = agente.constructor.name || 'N/A';
            const row = `
                <tr>
                    <td>${agente.id}</td>
                    <td>${agente.nome}</td>
                    <td>${agente.cnpj}</td>
                    <td>${agente.tipo}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn btn-danger" onclick="deleteAgente(${agente.id})">Deletar</button>
                        </div>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        console.error('Error loading agentes:', error);
    }
}

// Approve rental request
async function aprovarPedido(pedidoId) {
    try {
        const response = await fetch(`${API_BASE_URL}/pedidos/${pedidoId}/aprovar`, {
            method: 'PUT'
        });

        if (response.ok) {
            alert('Pedido aprovado com sucesso');
            loadPedidosPendentes();
            loadTodosPedidos();
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

// Reject rental request
async function rejeitarPedido(pedidoId) {
    try {
        const response = await fetch(`${API_BASE_URL}/pedidos/${pedidoId}/rejeitar`, {
            method: 'PUT'
        });

        if (response.ok) {
            alert('Pedido rejeitado com sucesso');
            loadPedidosPendentes();
            loadTodosPedidos();
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

// Sign contract
async function assinarContrato(contratoId) {
    try {
        const response = await fetch(`${API_BASE_URL}/contratos/${contratoId}/assinar`, {
            method: 'PUT'
        });

        if (response.ok) {
            alert('Contrato assinado com sucesso');
            loadContratos();
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

// CRUD operations for clients
function showFormCliente() {
    document.getElementById('clienteForm').style.display = 'block';
}

function hideFormCliente() {
    document.getElementById('clienteForm').style.display = 'none';
    document.getElementById('clienteId').value = '';
}

// Similar CRUD functions for vehicles and other entities...
function showFormAutomovel() {
    document.getElementById('automovelForm').style.display = 'block';
}

function hideFormAutomovel() {
    document.getElementById('automovelForm').style.display = 'none';
    document.getElementById('automovelId').value = '';
}

// Show section
function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(s => s.classList.remove('active'));

    const section = document.getElementById(sectionId);
    if (section) {
        section.classList.add('active');

        // Reload data when showing specific sections
        if (sectionId === 'pedidos-pendentes') loadPedidosPendentes();
        if (sectionId === 'todos-pedidos') loadTodosPedidos();
        if (sectionId === 'clientes') loadClientes();
        if (sectionId === 'automoveis') loadAutomoveis();
        if (sectionId === 'contratos') loadContratos();
        if (sectionId === 'agentes') loadAgentes();
    }

    // Update active menu item
    document.querySelectorAll('.sidebar-menu a').forEach(a => a.classList.remove('active'));
    event.target.classList.add('active');
}

// Delete operations
async function deleteCliente(id) {
    if (!confirm('Tem certeza que deseja deletar este cliente?')) return;
    try {
        const response = await fetch(`${API_BASE_URL}/clientes/${id}`, {
            method: 'DELETE'
        });
        if (response.ok) {
            loadClientes();
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function deleteAutomovel(id) {
    if (!confirm('Tem certeza que deseja deletar este automóvel?')) return;
    try {
        const response = await fetch(`${API_BASE_URL}/automoveis/${id}`, {
            method: 'DELETE'
        });
        if (response.ok) {
            loadAutomoveis();
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function deleteContrato(id) {
    if (!confirm('Tem certeza que deseja deletar este contrato?')) return;
    try {
        const response = await fetch(`${API_BASE_URL}/contratos/${id}`, {
            method: 'DELETE'
        });
        if (response.ok) {
            loadContratos();
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function deleteAgente(id) {
    if (!confirm('Tem certeza que deseja deletar este agente?')) return;
    try {
        const response = await fetch(`${API_BASE_URL}/agentes/${id}`, {
            method: 'DELETE'
        });
        if (response.ok) {
            loadAgentes();
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

// Helper functions
function getStatusClass(status) {
    switch (status) {
        case 'PENDENTE': return 'pending';
        case 'APROVADO': return 'approved';
        case 'REJEITADO': return 'rejected';
        case 'CANCELADO': return 'cancelled';
        default: return 'pending';
    }
}

function viewPedido(id) {
    alert(`Visualizando detalhes do pedido ${id}`);
}

function editCliente(id) {
    alert(`Editando cliente ${id}`);
}

function editAutomovel(id) {
    alert(`Editando automóvel ${id}`);
}
