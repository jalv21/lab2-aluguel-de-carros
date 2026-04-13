// API Base URL
const API_BASE_URL = 'http://localhost:8080';

// Login functionality
document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const login = document.getElementById('login').value;
    const senha = document.getElementById('senha').value;
    const tipoUsuario = document.getElementById('tipoUsuario').value;
    const messageDiv = document.getElementById('loginMessage');

    try {
        let endpoint = '';
        if (tipoUsuario === 'cliente') {
            endpoint = '/clientes';
        } else if (tipoUsuario === 'banco' || tipoUsuario === 'empresa') {
            endpoint = '/agentes';
        }

        // Get all users of the type
        const response = await fetch(`${API_BASE_URL}${endpoint}`);
        const usuarios = await response.json();

        // Find matching user
        const usuario = usuarios.find(u => u.login === login && u.senha === senha);

        if (usuario) {
            // Store user info in localStorage
            localStorage.setItem('currentUser', JSON.stringify(usuario));
            localStorage.setItem('userType', tipoUsuario);

            // Redirect to appropriate dashboard
            if (tipoUsuario === 'cliente') {
                window.location.href = 'dashboard-cliente.html';
            } else {
                window.location.href = 'dashboard-agente.html';
            }
        } else {
            showMessage(messageDiv, 'Login ou senha inválidos', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showMessage(messageDiv, 'Erro ao autenticar. Tente novamente.', 'error');
    }
});

function showMessage(element, message, type) {
    element.textContent = message;
    element.className = `message ${type}`;
    element.style.display = 'block';
    setTimeout(() => {
        element.style.display = 'none';
    }, 5000);
}
