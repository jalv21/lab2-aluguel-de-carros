class CRUDManager {
    constructor(config) {
        this.endpoint = config.endpoint;
        this.title = config.title;
        this.fields = config.fields;
        this.idField = config.idField || 'id';
        this.allData = [];
        this.currentItem = null;
        
        console.log('%c=== CRUD Manager Inicializado ===', 'color:green;font-weight:bold');
        console.log('Endpoint:', this.endpoint);
        console.log('Título:', this.title);
        console.log('Campos:', this.fields);
        console.log('ID Field:', this.idField);
    }

    async loadData() {
        console.log('%c>>> INICIANDO loadData()', 'color:blue;font-weight:bold');
        console.log('Endpoint:', this.endpoint);
        
        const container = document.getElementById('dataTable');
        if (!container) {
            console.error('ERRO: Elemento #dataTable não encontrado!');
            return;
        }
        
        try {
            console.log('Fazendo fetch para:', this.endpoint);
            const response = await fetch(this.endpoint);
            
            console.log('Response status:', response.status);
            console.log('Response ok:', response.ok);
            console.log('Response headers:', {
                'content-type': response.headers.get('content-type'),
                'content-length': response.headers.get('content-length')
            });
            
            if (!response.ok) {
                throw new Error('HTTP ' + response.status + ' - ' + response.statusText);
            }
            
            const text = await response.text();
            console.log('Response texto (primeiros 200 chars):', text.substring(0, 200));
            
            this.allData = JSON.parse(text);
            console.log('Dados parseados com sucesso!');
            console.log('Total de registros:', this.allData.length);
            console.log('Primeiro registro:', this.allData[0]);
            
            this.renderTable(this.allData);
            console.log('%c<<< loadData() CONCLUÍDO COM SUCESSO', 'color:green;font-weight:bold');
            
        } catch (error) {
            console.error('%c!!! ERRO em loadData():', 'color:red;font-weight:bold');
            console.error('Tipo de erro:', error.constructor.name);
            console.error('Mensagem:', error.message);
            console.error('Stack:', error.stack);
            
            const errorMsg = 'Erro ao carregar dados: ' + error.message;
            console.error(errorMsg);
            container.innerHTML = '<p style="color:red;padding:20px;font-weight:bold;">' + errorMsg + '</p>';
        }
    }

    renderTable(data) {
        console.log('%c>>> renderTable(%d registros)', 'color:blue;font-weight:bold', data.length);
        const container = document.getElementById('dataTable');
        if (!container) {
            console.error('ERRO: #dataTable não encontrado!');
            return;
        }
        
        if (!data || data.length === 0) {
            console.warn('Nenhum dado para renderizar');
            container.innerHTML = '<p style="padding:20px;color:#7f8c8d;">Nenhum registro encontrado</p>';
            return;
        }

        let html = '<table border="1" style="width:100%; border-collapse: collapse;"><thead><tr style="background:#2c3e50;">';
        
        this.fields.forEach(field => {
            html += `<th style="padding:10px; color:white; text-align:left; font-weight:bold;">${field.label}</th>`;
        });
        html += '<th style="padding:10px; color:white; font-weight:bold;">Ações</th></tr></thead><tbody>';
        
        data.forEach((item, index) => {
            console.log(`Renderizando linha ${index + 1}:`, item);
            html += '<tr style="border-bottom:1px solid #ddd;">';
            this.fields.forEach(field => {
                const value = item[field.name] !== undefined ? item[field.name] : '';
                html += `<td style="padding:10px;">${value}</td>`;
            });
            const itemId = item[this.idField];
            html += `<td style="padding:10px;">
                <button onclick="crudManager.openEditForm(${itemId})" style="background:#3498db; color:white; border:none; padding:5px 10px; cursor:pointer; margin-right:5px; border-radius:3px;">✏️ Editar</button>
                <button onclick="crudManager.deleteItem(${itemId})" style="background:#e74c3c; color:white; border:none; padding:5px 10px; cursor:pointer; border-radius:3px;">🗑️ Deletar</button>
            </td></tr>`;
        });
        
        html += '</tbody></table>';
        container.innerHTML = html;
        console.log('%c<<< renderTable() CONCLUÍDO', 'color:green;font-weight:bold');
    }

    openCreateForm() {
        console.log('%c>>> openCreateForm()', 'color:blue;font-weight:bold');
        this.currentItem = null;
        this.showForm();
    }

    openEditForm(id) {
        console.log('%c>>> openEditForm(%d)', 'color:blue;font-weight:bold', id);
        const item = this.allData.find(i => i[this.idField] === id);
        console.log('Item encontrado:', item);
        if (item) {
            this.currentItem = { ...item };
            this.showForm();
        } else {
            console.error('Item com ID', id, 'não encontrado em allData');
        }
    }

    showForm() {
        console.log('%c>>> showForm()', 'color:blue;font-weight:bold');
        console.log('currentItem:', this.currentItem);
        
        const modal = document.getElementById('modal');
        const formDiv = document.getElementById('formContainer');
        const title = document.getElementById('formTitle');
        
        if (!modal || !formDiv || !title) {
            console.error('ERRO: Elementos do modal não encontrados!');
            console.log('modal:', modal);
            console.log('formDiv:', formDiv);
            console.log('title:', title);
            return;
        }
        
        title.textContent = this.currentItem ? `Editar ${this.title}` : `Novo ${this.title}`;
        
        let formHtml = '';
        this.fields.forEach(field => {
            const value = this.currentItem ? (this.currentItem[field.name] || '') : '';
            const required = field.required ? 'required' : '';
            formHtml += `
                <div style="margin-bottom:15px;">
                    <label style="display:block; margin-bottom:5px; font-weight:bold; color:#2c3e50;">${field.label}</label>
                    <input 
                        type="${field.type || 'text'}" 
                        data-field="${field.name}" 
                        value="${value}" 
                        style="width:100%; padding:8px; border:1px solid #ddd; border-radius:4px; box-sizing:border-box; font-size:14px;"
                        ${required}
                    >
                </div>
            `;
        });
        
        formHtml += `
            <div style="display:flex; gap:10px; margin-top:20px;">
                <button onclick="crudManager.save()" style="flex:1; background:#27ae60; color:white; padding:10px; border:none; border-radius:4px; cursor:pointer; font-weight:bold;">💾 Salvar</button>
                <button onclick="crudManager.closeForm()" style="flex:1; background:#95a5a6; color:white; padding:10px; border:none; border-radius:4px; cursor:pointer; font-weight:bold;">❌ Cancelar</button>
            </div>
        `;
        
        formDiv.innerHTML = formHtml;
        modal.style.display = 'flex';
        console.log('%c<<< showForm() CONCLUÍDO', 'color:green;font-weight:bold');
    }

    closeForm() {
        console.log('%c>>> closeForm()', 'color:blue;font-weight:bold');
        const modal = document.getElementById('modal');
        if (modal) {
            modal.style.display = 'none';
        }
        this.currentItem = null;
        console.log('%c<<< closeForm() CONCLUÍDO', 'color:green;font-weight:bold');
    }

    async save() {
        console.log('%c>>> save()', 'color:blue;font-weight:bold');
        const data = {};
        
        this.fields.forEach(field => {
            const input = document.querySelector(`input[data-field="${field.name}"]`);
            if (input) {
                data[field.name] = input.value;
                console.log(`Campo '${field.name}':`, input.value);
            } else {
                console.warn(`Input data-field="${field.name}" não encontrado!`);
            }
        });

        // Validação
        for (const field of this.fields) {
            if (field.required && !data[field.name]) {
                const msg = `Campo "${field.label}" é obrigatório`;
                console.warn(msg);
                alert(msg);
                return;
            }
        }

        try {
            console.log('Dados para salvar:', data);
            const method = this.currentItem ? 'PUT' : 'POST';
            const url = this.currentItem 
                ? `${this.endpoint}/${this.currentItem[this.idField]}` 
                : this.endpoint;
            
            console.log('URL:', url, 'Método:', method);
            
            const response = await fetch(url, {
                method: method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            console.log('Response status:', response.status);
            console.log('Response ok:', response.ok);

            if (!response.ok) {
                throw new Error('Erro HTTP: ' + response.status);
            }

            this.closeForm();
            await this.loadData();
            alert(this.currentItem ? '✅ Item atualizado com sucesso!' : '✅ Item criado com sucesso!');
            console.log('%c<<< save() CONCLUÍDO COM SUCESSO', 'color:green;font-weight:bold');
        } catch (error) {
            console.error('%c!!! ERRO em save():', 'color:red;font-weight:bold');
            console.error('Erro:', error.message);
            alert('❌ Erro ao salvar: ' + error.message);
        }
    }

    async deleteItem(id) {
        console.log('%c>>> deleteItem(%d)', 'color:blue;font-weight:bold', id);
        if (!confirm('Tem certeza que deseja deletar este item?')) {
            console.log('Deletar cancelado pelo usuário');
            return;
        }
        
        try {
            const url = `${this.endpoint}/${id}`;
            console.log('Deletando:', url);
            
            const response = await fetch(url, { 
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' }
            });

            console.log('Response status:', response.status);
            console.log('Response ok:', response.ok);

            if (!response.ok) {
                throw new Error('Erro HTTP: ' + response.status);
            }

            await this.loadData();
            alert('✅ Item deletado com sucesso!');
            console.log('%c<<< deleteItem() CONCLUÍDO COM SUCESSO', 'color:green;font-weight:bold');
        } catch (error) {
            console.error('%c!!! ERRO em deleteItem():', 'color:red;font-weight:bold');
            console.error('Erro:', error.message);
            alert('❌ Erro ao deletar: ' + error.message);
        }
    }

    addNew() {
        console.log('%c>>> addNew()', 'color:blue;font-weight:bold');
        this.openCreateForm();
    }
}
