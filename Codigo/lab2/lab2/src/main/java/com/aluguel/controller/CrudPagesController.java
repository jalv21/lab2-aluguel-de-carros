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
            + "<script src='/js/masks.js'></script>"
            + "<script src='/js/status-enum.js'></script>"
            + "<script src='/js/validation.js'></script>"
            + "<script src='/js/validation-enhanced.js'></script>"
            + "<script src='/js/field-config.js'></script>"
            + "<script>"
            + "console.log('%c=== INICIANDO CARREGAMENTO DE SCRIPTS ===','color:blue;font-weight:bold');"
            + "let crudManager=null;"
            + "function inicializarCRUD(){"
            + "  console.log('%c>>> inicializarCRUD() chamado','color:blue');"
            + "  console.log('Verificando field-config:', typeof normalizeEntityType);"
            + "  console.log('Verificando renderField:', typeof renderField);"
            + "  const entityTypeRaw = '" + endpoint.substring(1) + "';"
            + "  console.log('Entity Type Raw:', entityTypeRaw);"
            + "  crudManager=new CRUDManager({endpoint:'" + endpoint + "',title:'" + title + "',entityType:entityTypeRaw,fields:" + fields + "});"
            + "  console.log('CRUDManager criado, chamando loadData');"
            + "  crudManager.loadData();"
            + "}"
            + "function adicionarNovo(){"
            + "  console.log('%c>>> adicionarNovo() chamado','color:orange');"
            + "  console.log('crudManager existe?', crudManager !== null);"
            + "  if(crudManager){"
            + "    console.log('Chamando crudManager.addNew()');"
            + "    crudManager.addNew();"
            + "  }else{"
            + "    console.error('crudManager é null!');"
            + "    alert('ERRO: Sistema não foi inicializado corretamente');"
            + "  }"
            + "}"
            + "class CRUDManager{"
            + "  constructor(e){"
            + "    this.endpoint=e.endpoint;"
            + "    this.title=e.title;"
            + "    this.fields=e.fields;"
            + "    this.entityType=e.entityType||'';"
            + "    this.idField=e.idField||'id';"
            + "    this.allData=[];"
            + "    this.currentItem=null;"
            + "    console.log('%c=== CRUD Manager Inicializado ===','color:green;font-weight:bold');"
            + "    console.log('Endpoint:',this.endpoint);"
            + "    console.log('Título:',this.title);"
            + "    console.log('Entity Type Bruto:',this.entityType);"
            + "  }"
            + "  addNew(){"
            + "    console.log('%c>>> addNew() chamado','color:purple');"
            + "    this.openCreateForm();"
            + "  }"
            + "  openCreateForm(){"
            + "    console.log('%c>>> openCreateForm()','color:purple');"
            + "    this.currentItem=null;"
            + "    this.showForm();"
            + "  }"
            + "  showForm(){"
            + "    console.log('%c>>> showForm() INICIANDO','color:purple;font-weight:bold');"
            + "    const modal=document.getElementById('modal');"
            + "    const formContainer=document.getElementById('formContainer');"
            + "    const formTitle=document.getElementById('formTitle');"
            + "    console.log('Modal existe?', modal !== null);"
            + "    console.log('FormContainer existe?', formContainer !== null);"
            + "    console.log('FormTitle existe?', formTitle !== null);"
            + "    if(!modal||!formContainer||!formTitle){"
            + "      console.error('ERRO: Elementos do modal não encontrados!');"
            + "      return;"
            + "    }"
            + "    formTitle.textContent=this.currentItem?`Editar ${this.title}`:`Novo ${this.title}`;"
            + "    let html='';"
            + "    this.fields.forEach(f=>{"
            + "      console.log('Processando campo:', f.name);"
            + "      const value=this.currentItem?this.currentItem[f.name]||'':\"\", def=getFieldDefinition(this.entityType,f.name);"
            + "      if(def){"
            + "        console.log('  - Definição encontrada');"
            + "      }else{"
            + "        console.warn('  - Definição NÃO encontrada para', this.entityType, f.name);"
            + "      }"
            + "      const campo=renderField(f.name,value,def);"
            + "      if(!campo){"
            + "        console.error('renderField retornou vazio!');"
            + "      }"
            + "      html+=campo;"
            + "    });"
            + "    html+=`<div style=\"display:flex; gap:10px; margin-top:20px;\"><button onclick=\"crudManager.save()\" style=\"flex:1; background:#27ae60; color:white; padding:10px; border:none; border-radius:4px; cursor:pointer; font-weight:bold;\">💾 Salvar</button><button onclick=\"crudManager.closeForm()\" style=\"flex:1; background:#95a5a6; color:white; padding:10px; border:none; border-radius:4px; cursor:pointer; font-weight:bold;\">❌ Cancelar</button></div>`;"
            + "    console.log('HTML gerado tamanho:', html.length);"
            + "    formContainer.innerHTML=html;"
            + "    modal.style.display='flex';"
            + "    console.log('%c<<< showForm() CONCLUÍDO','color:purple;font-weight:bold');"
            + "    setTimeout(()=>{"
            + "      console.log('Aplicando máscaras e validações');"
            + "      applyFieldMasks(formContainer);"
            + "      applyFieldValidations(formContainer);"
            + "    },100);"
            + "  }"
            + "  closeForm(){"
            + "    const modal=document.getElementById('modal');"
            + "    if(modal) modal.style.display='none';"
            + "    this.currentItem=null;"
            + "  }"
            + "  async loadData(){"
            + "    console.log('%c>>> loadData()','color:blue');"
            + "    const table=document.getElementById('dataTable');"
            + "    if(!table) return void console.error('ERRO: #dataTable não encontrado!');"
            + "    try{"
            + "      const resp=await fetch(this.endpoint);"
            + "      if(!resp.ok) throw new Error('HTTP '+resp.status);"
            + "      this.allData=await resp.json();"
            + "      this.renderTable(this.allData);"
            + "      console.log('<<< loadData() OK');"
            + "    }catch(err){"
            + "      console.error('ERRO loadData:', err.message);"
            + "      table.innerHTML='<p style=\"color:red;\">'+err.message+'</p>';"
            + "    }"
            + "  }"
            + "  renderTable(data){"
            + "    const table=document.getElementById('dataTable');"
            + "    if(!table) return void console.error('ERRO: #dataTable não encontrado!');"
            + "    if(!data||data.length===0){"
            + "      table.innerHTML='<p style=\"padding:20px;color:#7f8c8d;\">Nenhum registro encontrado</p>';"
            + "      return;"
            + "    }"
            + "    let html='<table border=\"1\" style=\"width:100%; border-collapse: collapse;\"><thead><tr style=\"background:#2c3e50;\">';"
            + "    this.fields.forEach(f=>{ html+=`<th style=\"padding:10px; color:white; text-align:left; font-weight:bold;\">${f.label}</th>`; });"
            + "    html+='<th style=\"padding:10px; color:white; font-weight:bold;\">Ações</th></tr></thead><tbody>';"
            + "    data.forEach(row=>{"
            + "      html+='<tr style=\"border-bottom:1px solid #ddd;\">';"
            + "      const id=row[this.idField];"
            + "      this.fields.forEach(f=>{"
            + "        const val=row[f.name]||'';"
            + "        html+=`<td style=\"padding:10px;\">${val}</td>`;"
            + "      });"
            + "      html+=`<td style=\"padding:10px;\"><button onclick=\"crudManager.openEditForm(${id})\" style=\"background:#3498db; color:white; border:none; padding:5px 10px; cursor:pointer; margin-right:5px; border-radius:3px;\">✏️ Editar</button><button onclick=\"crudManager.deleteItem(${id})\" style=\"background:#e74c3c; color:white; border:none; padding:5px 10px; cursor:pointer; border-radius:3px;\">🗑️ Deletar</button></td></tr>`;"
            + "    });"
            + "    html+='</tbody></table>';"
            + "    table.innerHTML=html;"
            + "  }"
            + "  openEditForm(id){"
            + "    const item=this.allData.find(x=>x[this.idField]===id);"
            + "    if(item){"
            + "      this.currentItem={...item};"
            + "      this.showForm();"
            + "    }else alert('Item não encontrado');"
            + "  }"
            + "  async save(){"
            + "    const data={};"
            + "    this.fields.forEach(f=>{"
            + "      const input=document.querySelector(`[data-field=\"${f.name}\"]`);"
            + "      if(input){"
            + "        const def=getFieldDefinition(this.entityType,f.name);"
            + "        if(!validateFieldElement(input)) return;"
            + "        const val=input.value.trim();"
            + "        if(def&&def.mask&&INPUT_MASKS[def.mask]){"
            + "          data[f.name]=INPUT_MASKS[def.mask].unformat(val);"
            + "        }else{"
            + "          data[f.name]=val;"
            + "        }"
            + "      }"
            + "    });"
            + "    if(this.fields.some(f=>f.required&&!data[f.name])){"
            + "      alert('❌ Preencha todos os campos obrigatórios!');"
            + "      return;"
            + "    }"
            + "    try{"
            + "      const method=this.currentItem?'PUT':'POST';"
            + "      const url=this.currentItem?`${this.endpoint}/${this.currentItem[this.idField]}`:this.endpoint;"
            + "      const resp=await fetch(url,{method,headers:{'Content-Type':'application/json'},body:JSON.stringify(data)});"
            + "      if(!resp.ok) throw new Error('Erro HTTP: '+resp.status);"
            + "      this.closeForm();"
            + "      await this.loadData();"
            + "      alert(this.currentItem?'✅ Item atualizado!':'✅ Item criado!');"
            + "    }catch(err){"
            + "      console.error('ERRO save:', err);"
            + "      alert('❌ '+err.message);"
            + "    }"
            + "  }"
            + "  async deleteItem(id){"
            + "    if(!confirm('Deletar este item?')) return;"
            + "    try{"
            + "      await fetch(`${this.endpoint}/${id}`,{method:'DELETE'});"
            + "      await this.loadData();"
            + "      alert('✅ Item deletado!');"
            + "    }catch(err){"
            + "      console.error('ERRO delete:', err);"
            + "      alert('❌ '+err.message);"
            + "    }"
            + "  }"
            + "}"
            + "console.log('%c=== WAITING FOR DOM ===','color:blue;font-weight:bold');"
            + "if(document.readyState==='loading'){"
            + "  document.addEventListener('DOMContentLoaded',inicializarCRUD);"
            + "}else{"
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
        String fields = "[{name:'numero',label:'Número',required:true},{name:'tipoContrato',label:'Tipo Contrato',required:true},{name:'termos',label:'Termos'},{name:'status',label:'Status',required:true}]";
        return HttpResponse.ok(getCrudPageTemplate("Contratos", "/contratos", fields));
    }

    @Get("/pedidos-aluguel/page")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> pedidosPage() {
        String fields = "[{name:'clienteId',label:'ID Cliente',type:'number',required:true},{name:'automovelId',label:'ID Automóvel',type:'number',required:true},{name:'dataLocal',label:'Data Local',type:'date',required:true},{name:'status',label:'Status',required:true}]";
        return HttpResponse.ok(getCrudPageTemplate("Pedidos de Aluguel", "/pedidos-aluguel", fields));
    }

    @Get("/agentes/page")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> agentesPage() {
        String fields = "[{name:'login',label:'Login',required:true},{name:'senha',label:'Senha',type:'password',required:true},{name:'nome',label:'Nome',required:true},{name:'endereco',label:'Endereço'},{name:'cnpj',label:'CNPJ',required:true}]";
        return HttpResponse.ok(getCrudPageTemplate("Agentes", "/agentes", fields));
    }

    @Get("/rendimentos/page")
    @Produces(MediaType.TEXT_HTML)
    public HttpResponse<String> rendimentosPage() {
        String fields = "[{name:'entidadeEmpregadora',label:'Entidade Empregadora',type:'text',required:true},{name:'valor',label:'Valor',type:'number',required:true}]";
        return HttpResponse.ok(getCrudPageTemplate("Rendimentos", "/rendimentos", fields));
    }
}
