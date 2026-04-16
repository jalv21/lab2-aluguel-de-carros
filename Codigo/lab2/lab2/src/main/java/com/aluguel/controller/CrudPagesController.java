package com.aluguel.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import com.aluguel.service.*;

@Controller
public class CrudPagesController {
    @Inject private ClienteService clienteService;
    @Inject private AutomovelService automovelService;
    @Inject private ContratoService contratoService;
    @Inject private PedidoAluguelService pedidoAluguelService;
    @Inject private AgenteService agenteService;
    @Inject private RendimentoService rendimentoService;

    private String getCrudPageTemplate(String title, String endpoint, String fields) {
        String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1'><title>" + title + " - Aluguel</title>"
            + "<style>"
            + "*{margin:0;padding:0;box-sizing:border-box}"
            + "html,body{height:100%}"
            + "body{font-family:'Segoe UI',sans-serif;background:#f5f5f5;padding:10px;font-size:16px}"
            + "nav{background:#2c3e50;color:white;padding:12px 15px;border-radius:4px;margin-bottom:15px;display:flex;justify-content:space-between;align-items:center;flex-wrap:wrap;gap:10px}"
            + "nav h2{font-size:clamp(18px,5vw,24px);margin:0}"
            + "nav a{color:white;text-decoration:none;background:rgba(255,255,255,0.15);padding:8px 12px;border-radius:4px;cursor:pointer;transition:all 0.3s;white-space:nowrap}"
            + "nav a:hover{background:rgba(255,255,255,0.25)}"
            + ".container{max-width:1200px;margin:0 auto;background:white;padding:15px;border-radius:8px;box-shadow:0 2px 10px rgba(0,0,0,0.1)}"
            + ".button-new{display:inline-block;padding:10px 15px;background:#27ae60;color:white;border:none;border-radius:4px;cursor:pointer;margin:0 0 15px 0;font-weight:bold;transition:background 0.3s;font-size:14px}"
            + ".button-new:hover{background:#229954}"
            + "table{width:100%;border-collapse:collapse;margin:15px 0;font-size:clamp(12px,2vw,14px)}"
            + "th{background:#2c3e50;color:white;padding:10px;text-align:left;font-weight:bold}"
            + "td{padding:10px;border-bottom:1px solid #ecf0f1}"
            + "tr:hover{background:#f9f9f9}"
            + "button{padding:6px 10px;border:none;border-radius:3px;cursor:pointer;margin:2px;font-size:12px;white-space:nowrap}"
            + "button:nth-child(1){background:#3498db;color:white}button:nth-child(1):hover{background:#2980b9}"
            + "button:nth-child(2){background:#e74c3c;color:white}button:nth-child(2):hover{background:#c0392b}"
            + ".modal{display:none;position:fixed;top:0;left:0;width:100%;height:100%;background:rgba(0,0,0,0.6);justify-content:center;align-items:center;z-index:1000;padding:10px}"
            + ".modal-content{background:white;padding:20px;border-radius:8px;width:100%;max-width:600px;max-height:90vh;overflow-y:auto}"
            + ".modal-content h2{color:#2c3e50;margin-bottom:15px;font-size:clamp(16px,4vw,20px)}"
            + "input,select,textarea{width:100%;padding:10px;margin:5px 0 15px 0;border:1px solid #bdc3c7;border-radius:4px;box-sizing:border-box;font-family:inherit;font-size:16px}"
            + "input:focus,select:focus,textarea:focus{outline:none;border-color:#3498db;box-shadow:0 0 5px rgba(52,152,219,0.3)}"
            + "label{display:block;margin-top:10px;margin-bottom:5px;font-weight:bold;color:#2c3e50;font-size:14px}"
            + "#dataTable{min-height:200px;overflow-x:auto}"
            + "#dataTable p{padding:20px;text-align:center;color:#7f8c8d;font-style:italic}"
            + "@media(max-width:768px){"
            + "body{padding:5px}"
            + "nav{padding:8px 10px}"
            + "nav h2{font-size:18px}"
            + ".container{padding:10px}"
            + ".button-new{padding:8px 12px;font-size:13px;width:100%}"
            + "table{font-size:12px}"
            + "th,td{padding:8px 5px}"
            + "button{padding:5px 8px;font-size:11px;margin:2px 1px}"
            + ".modal-content{padding:15px}"
            + "input,select,textarea{padding:8px;font-size:14px}"
            + "}"
            + "@media(max-width:480px){"
            + "body{padding:0}"
            + "nav{padding:8px;margin-bottom:10px}"
            + "nav h2{font-size:16px}"
            + "nav a{padding:6px 10px;font-size:12px}"
            + ".container{padding:8px;margin:0;border-radius:0}"
            + ".button-new{padding:6px 10px;font-size:12px;margin:0 0 10px 0}"
            + "table{font-size:11px;margin:10px 0}"
            + "th,td{padding:6px 4px}"
            + "button{padding:4px 6px;font-size:10px;margin:1px}"
            + ".modal-content{padding:12px;width:100vw;margin:-10px}"
            + "input,select,textarea{padding:8px;font-size:14px;margin:3px 0 10px 0}"
            + "label{font-size:13px;margin-bottom:3px}"
            + "}"
            + "</style></head><body>"
            + "<nav><div><h2>" + title + "</h2></div><div><a href='/' style='font-size:14px'>🏠 Menu Principal</a></div></nav>"
            + "<div class='container'><button class='button-new' id='btnNovo' onclick='adicionarNovo()'>➕ Novo</button>"
            + "<div id='dataTable'><p id='loadingMsg'>Carregando dados...</p></div></div>"
            + "<div id='modal' class='modal'><div class='modal-content'><h2 id='formTitle'>Novo Item</h2><div id='formContainer'></div></div></div>"
            + "<script>"
            + "class CRUDManager{constructor(e){this.endpoint=e.endpoint,this.title=e.title,this.fields=e.fields,this.idField=e.idField||'id',this.allData=[],this.currentItem=null,console.log('%c=== CRUD Manager Inicializado ===','color:green;font-weight:bold'),console.log('Endpoint:',this.endpoint),console.log('Título:',this.title),console.log('Campos:',this.fields),console.log('ID Field:',this.idField)}async loadData(){console.log('%c>>> INICIANDO loadData()','color:blue;font-weight:bold'),console.log('Endpoint:',this.endpoint);const e=document.getElementById('dataTable');if(!e)return void console.error('ERRO: Elemento #dataTable não encontrado!');try{console.log('Fazendo fetch para:',this.endpoint);const t=await fetch(this.endpoint);if(console.log('Response status:',t.status),console.log('Response ok:',t.ok),console.log('Response headers:',{'content-type':t.headers.get('content-type'),'content-length':t.headers.get('content-length')}),!t.ok)throw new Error('HTTP '+t.status+' - '+t.statusText);const a=await t.text();console.log('Response texto (primeiros 200 chars):',a.substring(0,200)),this.allData=JSON.parse(a),console.log('Dados parseados com sucesso!'),console.log('Total de registros:',this.allData.length),console.log('Primeiro registro:',this.allData[0]),this.renderTable(this.allData),console.log('%c<<< loadData() CONCLUÍDO COM SUCESSO','color:green;font-weight:bold')}catch(e){console.error('%c!!! ERRO em loadData():','color:red;font-weight:bold'),console.error('Tipo de erro:',e.constructor.name),console.error('Mensagem:',e.message),console.error('Stack:',e.stack);const t='Erro ao carregar dados: '+e.message;console.error(t),e.innerHTML='<p style=\"color:red;padding:20px;font-weight:bold;\">'+t+'</p>'}}renderTable(e){if(console.log('%c>>> renderTable(%d registros)','color:blue;font-weight:bold',e.length),!document.getElementById('dataTable'))return void console.error('ERRO: #dataTable não encontrado!');if(!e||0===e.length)return void(document.getElementById('dataTable').innerHTML='<p style=\"padding:20px;color:#7f8c8d;\">Nenhum registro encontrado</p>'),void console.warn('Nenhum dado para renderizar');let t='<table border=\"1\" style=\"width:100%; border-collapse: collapse;\"><thead><tr style=\"background:#2c3e50;\">';this.fields.forEach(e=>{t+=`<th style=\"padding:10px; color:white; text-align:left; font-weight:bold;\">${e.label}</th>`}),t+='<th style=\"padding:10px; color:white; font-weight:bold;\">Ações</th></tr></thead><tbody>',e.forEach((e,a)=>{console.log(`Renderizando linha ${a+1}:`,e),t+='<tr style=\"border-bottom:1px solid #ddd;\">';const n=e[this.idField];this.fields.forEach(a=>{const o=void 0!==e[a.name]?e[a.name]:'';t+=`<td style=\"padding:10px;\">${o}</td>`}),t+=`<td style=\"padding:10px;\"><button onclick=\"crudManager.openEditForm(${n})\" style=\"background:#3498db; color:white; border:none; padding:5px 10px; cursor:pointer; margin-right:5px; border-radius:3px;\">✏️ Editar</button><button onclick=\"crudManager.deleteItem(${n})\" style=\"background:#e74c3c; color:white; border:none; padding:5px 10px; cursor:pointer; border-radius:3px;\">🗑️ Deletar</button></td></tr>`}),t+='</tbody></table>',document.getElementById('dataTable').innerHTML=t,console.log('%c<<< renderTable() CONCLUÍDO','color:green;font-weight:bold')}openCreateForm(){console.log('%c>>> openCreateForm()','color:blue;font-weight:bold'),this.currentItem=null,this.showForm()}openEditForm(e){console.log('%c>>> openEditForm(%d)','color:blue;font-weight:bold',e);const t=this.allData.find(t=>t[this.idField]===e);console.log('Item encontrado:',t),t?(this.currentItem={...t},this.showForm()):(console.error('Item com ID',e,'não encontrado em allData'),alert('Item não encontrado'))}showForm(){console.log('%c>>> showForm()','color:blue;font-weight:bold'),console.log('currentItem:',this.currentItem);const e=document.getElementById('modal'),t=document.getElementById('formContainer'),a=document.getElementById('formTitle');if(!e||!t||!a)return void console.error('ERRO: Elementos do modal não encontrados!');a.textContent=this.currentItem?`Editar ${this.title}`:`Novo ${this.title}`;let n='';this.fields.forEach(e=>{const t=this.currentItem?this.currentItem[e.name]||'':'',a=e.required?'required':'';n+=`<div style=\"margin-bottom:15px;\"><label style=\"display:block; margin-bottom:5px; font-weight:bold; color:#2c3e50;\">${e.label}</label><input type=\"${e.type||'text'}\" data-field=\"${e.name}\" value=\"${t}\" style=\"width:100%; padding:8px; border:1px solid #ddd; border-radius:4px; box-sizing:border-box; font-size:14px;\" ${a}></div>`}),n+=`<div style=\"display:flex; gap:10px; margin-top:20px;\"><button onclick=\"crudManager.save()\" style=\"flex:1; background:#27ae60; color:white; padding:10px; border:none; border-radius:4px; cursor:pointer; font-weight:bold;\">💾 Salvar</button><button onclick=\"crudManager.closeForm()\" style=\"flex:1; background:#95a5a6; color:white; padding:10px; border:none; border-radius:4px; cursor:pointer; font-weight:bold;\">❌ Cancelar</button></div>`,t.innerHTML=n,e.style.display='flex',console.log('%c<<< showForm() CONCLUÍDO','color:green;font-weight:bold')}closeForm(){console.log('%c>>> closeForm()','color:blue;font-weight:bold');const e=document.getElementById('modal');e&&(e.style.display='none'),this.currentItem=null,console.log('%c<<< closeForm() CONCLUÍDO','color:green;font-weight:bold')}async save(){console.log('%c>>> save()','color:blue;font-weight:bold');const e={};if(this.fields.forEach(t=>{const a=document.querySelector(`input[data-field=\"${t.name}\"]`);a?(e[t.name]=a.value,console.log(`Campo '${t.name}':`,a.value)):console.warn(`Input data-field=\"${t.name}\" não encontrado!`)}),console.log('Dados:', e),this.fields.some(t=>t.required&&!e[t.name])){let t='';return this.fields.forEach(a=>{a.required&&!e[a.name]&&(t=`Campo \"${a.label}\" é obrigatório`)}),console.warn(t),void alert(t)}try{const t=this.currentItem?'PUT':'POST',a=this.currentItem?`${this.endpoint}/${this.currentItem[this.idField]}`:this.endpoint;console.log('URL:',a,'Método:',t);const n=await fetch(a,{method:t,headers:{'Content-Type':'application/json'},body:JSON.stringify(e)});if(console.log('Response status:',n.status),console.log('Response ok:',n.ok),!n.ok)throw new Error('Erro HTTP: '+n.status);this.closeForm(),await this.loadData(),alert(this.currentItem?'✅ Item atualizado com sucesso!':'✅ Item criado com sucesso!'),console.log('%c<<< save() CONCLUÍDO COM SUCESSO','color:green;font-weight:bold')}catch(e){console.error('%c!!! ERRO em save():','color:red;font-weight:bold'),console.error('Erro:',e.message),alert('❌ Erro ao salvar: '+e.message)}}async deleteItem(e){if(console.log('%c>>> deleteItem(%d)','color:blue;font-weight:bold',e),!confirm('Tem certeza que deseja deletar este item?'))return void console.log('Deletar cancelado pelo usuário');try{const t=`${this.endpoint}/${e}`;console.log('Deletando:',t);const a=await fetch(t,{method:'DELETE',headers:{'Content-Type':'application/json'}});if(console.log('Response status:',a.status),console.log('Response ok:',a.ok),!a.ok)throw new Error('Erro HTTP: '+a.status);await this.loadData(),alert('✅ Item deletado com sucesso!'),console.log('%c<<< deleteItem() CONCLUÍDO COM SUCESSO','color:green;font-weight:bold')}catch(e){console.error('%c!!! ERRO em deleteItem():','color:red;font-weight:bold'),console.error('Erro:',e.message),alert('❌ Erro ao deletar: '+e.message)}}addNew(){console.log('%c>>> addNew()','color:blue;font-weight:bold'),this.openCreateForm()}}"
            + "let crudManager = null;"
            + "function inicializarCRUD() {"
            + "  console.log('%c=== INICIALIZANDO CRUD ===', 'color:green;font-weight:bold;font-size:16px');"
            + "  crudManager = new CRUDManager({"
            + "    endpoint:'" + endpoint + "',"
            + "    title:'" + title + "',"
            + "    fields:" + fields
            + "  });"
            + "  console.log('CRUDManager criado:', crudManager);"
            + "  console.log('Chamando loadData()...');"
            + "  crudManager.loadData();"
            + "}"
            + "function adicionarNovo() {"
            + "  console.log('adicionarNovo() chamado');"
            + "  if (crudManager) {"
            + "    crudManager.addNew();"
            + "  } else {"
            + "    console.error('crudManager não inicializado!');"
            + "    alert('ERRO: Sistema não foi inicializado corretamente');"
            + "  }"
            + "}"
            + "if (document.readyState === 'loading') {"
            + "  document.addEventListener('DOMContentLoaded', inicializarCRUD);"
            + "} else {"
            + "  inicializarCRUD();"
            + "}"
            + "</script>"
            + "</body></html>";
        return html;
    }

    @Get("/clientes/page")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> clientesPage() {
        String fields = "[{name:'login',label:'Login',required:true},{name:'nome',label:'Nome',required:true},{name:'endereco',label:'Endereço'},{name:'rg',label:'RG'},{name:'cpf',label:'CPF'},{name:'profissao',label:'Profissão'}]";
        return HttpResponse.ok(getCrudPageTemplate("Clientes", "/clientes", fields));
    }

    @Get("/automoveis/page")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> automovelPage() {
        String fields = "[{name:'matricula',label:'Matrícula',required:true},{name:'placa',label:'Placa',required:true},{name:'marca',label:'Marca',required:true},{name:'modelo',label:'Modelo',required:true},{name:'ano',label:'Ano',type:'number',required:true}]";
        return HttpResponse.ok(getCrudPageTemplate("Automóveis", "/automoveis", fields));
    }

    @Get("/contratos/page")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> contratosPage() {
        String fields = "[{name:'numero',label:'Número',required:true},{name:'dataInicio',label:'Data Início'},{name:'dataFim',label:'Data Fim'},{name:'valor',label:'Valor',type:'number'}]";
        return HttpResponse.ok(getCrudPageTemplate("Contratos", "/contratos", fields));
    }

    @Get("/pedidos-aluguel/page")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> pedidosPage() {
        String fields = "[{name:'descricao',label:'Descrição',required:true},{name:'status',label:'Status',required:true},{name:'dataRequisicao',label:'Data Requisição'},{name:'valor',label:'Valor',type:'number'}]";
        return HttpResponse.ok(getCrudPageTemplate("Pedidos de Aluguel", "/pedidos-aluguel", fields));
    }

    @Get("/agentes/page")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> agentesPage() {
        String fields = "[{name:'nome',label:'Nome',required:true},{name:'cnpj',label:'CNPJ'},{name:'tipo',label:'Tipo'}]";
        return HttpResponse.ok(getCrudPageTemplate("Agentes", "/agentes", fields));
    }

    @Get("/rendimentos/page")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> rendimentosPage() {
        String fields = "[{name:'valor',label:'Valor',type:'number',required:true},{name:'dataMovimentacao',label:'Data'},{name:'descricao',label:'Descrição'}]";
        return HttpResponse.ok(getCrudPageTemplate("Rendimentos", "/rendimentos", fields));
    }
}
