// API Base URL
const API_BASE_URL = 'http://localhost:8080';

// Update fields based on user type
function updateFieldsBasedOnType() {
    const tipoUsuario = document.getElementById('tipoUsuario').value;
    const cpfGroup = document.getElementById('cpfGroup');
    const profissaoGroup = document.getElementById('profissaoGroup');
    const cnpjGroup = document.getElementById('cnpjGroup');

    if (tipoUsuario === 'cliente') {
        cpfGroup.style.display = 'block';
        profissaoGroup.style.display = 'block';
        cnpjGroup.style.display = 'none';
    } else {
        cpfGroup.style.display = 'none';
        profissaoGroup.style.display = 'none';
        cnpjGroup.style.display = 'block';
    }
}

// Handle registration form submission
document.getElementById('registerForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const nome = document.getElementById('nome').value;
    const login = document.getElementById('login').value;
    const senha = document.getElementById('senha').value;
    const endereco = document.getElementById('endereco').value;
    const tipoUsuario = document.getElementById('tipoUsuario').value;
    const messageDiv = document.getElementById('registerMessage');

    try {
        let userData = {
            nome: nome,
            login: login,
            senha: senha,
            endereco: endereco
        };

        let endpoint = '';

        if (tipoUsuario === 'cliente') {
            userData.cpf = document.getElementById('cpf').value;
            userData.profissao = document.getElementById('profissao').value;
            endpoint = '/clientes';
        } else if (tipoUsuario === 'banco') {
            userData.cnpj = document.getElementById('cnpj').value;
            endpoint = '/agentes';
        } else if (tipoUsuario === 'empresa') {
            userData.cnpj = document.getElementById('cnpj').value;
            endpoint = '/agentes';
        }

        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            showMessage(messageDiv, 'Cadastro realizado com sucesso! Redirecionando...', 'success');
            setTimeout(() => {
                window.location.href = 'index.html';
            }, 2000);
        } else {
            showMessage(messageDiv, 'Erro ao cadastrar. Verifique os dados.', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showMessage(messageDiv, 'Erro ao processar cadastro.', 'error');
    }
});

function showMessage(element, message, type) {
    element.textContent = message;
    element.className = `message ${type}`;
    element.style.display = 'block';
}
