// API Base URL
const API_BASE_URL = 'http://localhost:8080';

// Initialize dashboard
document.addEventListener('DOMContentLoaded', () => {
    checkAuthentication();
    loadUserProfile();
    loadMeusPedidos();
    loadAutomoveis();
    populateAutomovelSelect();
});

// Check if user is authenticated
function checkAuthentication() {
    const currentUser = localStorage.getItem('currentUser');
    const userType = localStorage.getItem('userType');

    if (!currentUser || userType !== 'cliente') {
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

// Load user profile
async function loadUserProfile() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));

    document.getElementById('perfilNome').value = currentUser.nome || '';
    document.getElementById('perfilLogin').value = currentUser.login || '';
    document.getElementById('perfilEndereco').value = currentUser.endereco || '';
    document.getElementById('perfilCpf').value = currentUser.cpf || '';
    document.getElementById('perfilProfissao').value = currentUser.profissao || '';
}

// Load user's rental requests
async function loadMeusPedidos() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));

    try {
        const response = await fetch(`${API_BASE_URL}/pedidos/cliente/${currentUser.id}`);
        const pedidos = await response.json();

        const tableBody = document.getElementById('pedidosTableBody');
        tableBody.innerHTML = '';

        if (pedidos.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="6">Nenhum pedido encontrado</td></tr>';
            return;
        }

        for (const pedido of pedidos) {
            let automovelInfo = 'N/A';
            try {
                const autoResponse = await fetch(`${API_BASE_URL}/automoveis/${pedido.automovelId}`);
                const auto = await autoResponse.json();
                automovelInfo = `${auto.marca} ${auto.modelo}`;
            } catch (e) {
                automovelInfo = `ID: ${pedido.automovelId}`;
            }

            const row = `
                <tr>
                    <td>${pedido.id}</td>
                    <td>${automovelInfo}</td>
                    <td>${pedido.dataPedido}</td>
                    <td>${pedido.dataLocal}</td>
                    <td><span class="badge badge-${getStatusClass(pedido.status)}">${pedido.status}</span></td>
                    <td>
                        <div class="action-buttons">
                            ${pedido.status === 'PENDENTE' ? `<button class="btn btn-secondary" onclick="cancelarPedido(${pedido.id})">Cancelar</button>` : ''}
                        </div>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        }
    } catch (error) {
        console.error('Error loading pedidos:', error);
    }
}

// Load available vehicles
async function loadAutomoveis() {
    try {
        const response = await fetch(`${API_BASE_URL}/automoveis`);
        const automoveis = await response.json();

        const container = document.getElementById('automovelsList');
        container.innerHTML = '';

        automoveis.forEach(auto => {
            const card = `
                <div class="card">
                    <h3>${auto.marca} ${auto.modelo}</h3>
                    <p><strong>Ano:</strong> ${auto.ano}</p>
                    <p><strong>Placa:</strong> ${auto.placa}</p>
                    <p><strong>Matrícula:</strong> ${auto.matricula}</p>
                    <button class="btn btn-primary" onclick="selectVehicle(${auto.id})">Alugar</button>
                </div>
            `;
            container.innerHTML += card;
        });
    } catch (error) {
        console.error('Error loading automoveis:', error);
    }
}

// Populate vehicle select dropdown
async function populateAutomovelSelect() {
    try {
        const response = await fetch(`${API_BASE_URL}/automoveis`);
        const automoveis = await response.json();

        const select = document.getElementById('automovelSelect');
        automoveis.forEach(auto => {
            const option = document.createElement('option');
            option.value = auto.id;
            option.textContent = `${auto.marca} ${auto.modelo} (${auto.placa})`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading automoveis:', error);
    }
}

// Create new rental request
async function criarNovoPedido(event) {
    event.preventDefault();

    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const automovelId = document.getElementById('automovelSelect').value;
    const dataAluguel = document.getElementById('dataAluguel').value;
    const messageDiv = document.getElementById('novoPedidoMessage');

    if (!automovelId) {
        showMessage(messageDiv, 'Selecione um automóvel', 'error');
        return;
    }

    try {
        const pedidoData = {
            clienteId: currentUser.id,
            automovelId: parseInt(automovelId),
            dataLocal: dataAluguel,
            status: 'PENDENTE'
        };

        const response = await fetch(`${API_BASE_URL}/pedidos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(pedidoData)
        });

        if (response.ok) {
            showMessage(messageDiv, 'Pedido criado com sucesso!', 'success');
            document.getElementById('novoPedidoForm').reset();
            setTimeout(() => {
                loadMeusPedidos();
                showSection('meus-pedidos');
            }, 1500);
        } else {
            showMessage(messageDiv, 'Erro ao criar pedido', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showMessage(messageDiv, 'Erro ao processar solicitação', 'error');
    }
}

// Cancel rental request
async function cancelarPedido(pedidoId) {
    if (!confirm('Tem certeza que deseja cancelar este pedido?')) return;

    try {
        const response = await fetch(`${API_BASE_URL}/pedidos/${pedidoId}/cancelar`, {
            method: 'PUT'
        });

        if (response.ok) {
            alert('Pedido cancelado com sucesso');
            loadMeusPedidos();
        } else {
            alert('Erro ao cancelar pedido');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

// Select vehicle for rental (from vehicle cards)
function selectVehicle(automovelId) {
    document.getElementById('automovelSelect').value = automovelId;
    showSection('novo-pedido');
    document.getElementById('dataAluguel').focus();
}

// Load rendimentos
async function loadRendimentos() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));

    try {
        const response = await fetch(`${API_BASE_URL}/rendimentos`);
        const rendimentos = await response.json();

        // Filter rendimentos (in a real app, you'd have userId associated)
        const tableBody = document.getElementById('rendimentosTableBody');
        tableBody.innerHTML = '';

        rendimentos.forEach(r => {
            const row = `
                <tr>
                    <td>${r.entidadeEmpregadora}</td>
                    <td>R$ ${parseFloat(r.valor).toFixed(2)}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn btn-secondary" onclick="deleteRendimento(${r.id})">Deletar</button>
                        </div>
                    </td>
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        console.error('Error loading rendimentos:', error);
    }
}

// Add rendimento form visibility
function showAddRendimento() {
    document.getElementById('novoRendimentoForm').style.display = 'block';
}

function hideAddRendimento() {
    document.getElementById('novoRendimentoForm').style.display = 'none';
}

// Add rendimento submission
document.getElementById('novoRendimentoForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const entidade = document.getElementById('entidade').value;
    const valor = document.getElementById('valor').value;

    try {
        const response = await fetch(`${API_BASE_URL}/rendimentos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                entidadeEmpregadora: entidade,
                valor: parseFloat(valor)
            })
        });

        if (response.ok) {
            hideAddRendimento();
            loadRendimentos();
            document.getElementById('novoRendimentoForm').reset();
        }
    } catch (error) {
        console.error('Error:', error);
    }
});

// Delete rendimento
async function deleteRendimento(id) {
    if (!confirm('Tem certeza?')) return;

    try {
        const response = await fetch(`${API_BASE_URL}/rendimentos/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadRendimentos();
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

// Show section
function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(s => s.classList.remove('active'));

    const section = document.getElementById(sectionId);
    if (section) {
        section.classList.add('active');

        // Load data when showing specific sections
        if (sectionId === 'meus-pedidos') loadMeusPedidos();
        if (sectionId === 'automoveis') loadAutomoveis();
        if (sectionId === 'rendimentos') loadRendimentos();
    }

    // Update active menu item
    document.querySelectorAll('.sidebar-menu a').forEach(a => a.classList.remove('active'));
    event.target.classList.add('active');
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

function showMessage(element, message, type) {
    element.textContent = message;
    element.className = `message ${type}`;
    element.style.display = 'block';
    setTimeout(() => {
        element.style.display = 'none';
    }, 5000);
}
