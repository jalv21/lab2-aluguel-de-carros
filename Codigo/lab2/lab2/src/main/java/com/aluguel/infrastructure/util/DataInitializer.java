package com.aluguel.infrastructure.util;

import com.aluguel.model.*;
import com.aluguel.repository.*;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class DataInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    @Inject
    private ClienteRepository clienteRepository;

    @Inject
    private AutomovelRepository automovelRepository;

    @Inject
    private AgenteRepository agenteRepository;

    @Inject
    private PedidoAluguelRepository pedidoAluguelRepository;

    @Inject
    private ContratoRepository contratoRepository;

    @Inject
    private RendimentoRepository rendimentoRepository;

    @EventListener
    public void onStartup(StartupEvent event) {
        try {
            LOG.info("========== INICIANDO POPULAÇÃO DE DADOS ==========");
            initializaClientes();
            LOG.info("✓ Clientes inicializados");
            initializeAutomoveis();
            LOG.info("✓ Automóveis inicializados");
            initializeAgentes();
            LOG.info("✓ Agentes inicializados");
            initializePedidosAluguel();
            LOG.info("✓ Pedidos de aluguel inicializados");
            initializeContratos();
            LOG.info("✓ Contratos inicializados");
            initializeRendimentos();
            LOG.info("✓ Rendimentos inicializados");
            LOG.info("========== POPULAÇÃO DE DADOS CONCLUÍDA ==========");
        } catch (Exception e) {
            LOG.error("ERRO ao popular dados: ", e);
        }
    }

    private void initializaClientes() {
        try {
            LOG.info("Iniciando população de CLIENTES...");
            
            // Cliente de teste
            Cliente cliente1 = new Cliente();
            cliente1.setLogin("cliente");
            cliente1.setSenha("123");
            cliente1.setNome("Cliente Teste");
            cliente1.setEndereco("Rua A, 123");
            cliente1.setRg("123456789");
            cliente1.setCpf("123.456.789-00");
            cliente1.setProfissao("Engenheiro");
            Cliente saved1 = clienteRepository.save(cliente1);
            LOG.info("Cliente 1 salvo: ID=" + saved1.getId() + ", Login=" + saved1.getLogin());

            Cliente cliente2 = new Cliente();
            cliente2.setLogin("joao");
            cliente2.setSenha("123");
            cliente2.setNome("João Silva");
            cliente2.setEndereco("Rua B, 456");
            cliente2.setRg("987654321");
            cliente2.setCpf("987.654.321-00");
            cliente2.setProfissao("Desenvolvedor");
            Cliente saved2 = clienteRepository.save(cliente2);
            LOG.info("Cliente 2 salvo: ID=" + saved2.getId() + ", Login=" + saved2.getLogin());

            Cliente cliente3 = new Cliente();
            cliente3.setLogin("maria");
            cliente3.setSenha("123");
            cliente3.setNome("Maria Santos");
            cliente3.setEndereco("Rua C, 789");
            cliente3.setRg("456789123");
            cliente3.setCpf("456.789.123-00");
            cliente3.setProfissao("Médica");
            Cliente saved3 = clienteRepository.save(cliente3);
            LOG.info("Cliente 3 salvo: ID=" + saved3.getId() + ", Login=" + saved3.getLogin());
            
            LOG.info("Total de clientes no repositório: " + clienteRepository.findAll().size());
        } catch (Exception e) {
            LOG.error("Erro ao inicializar clientes: ", e);
            e.printStackTrace();
        }
    }

    private void initializeAutomoveis() {
        try {
            LOG.info("Iniciando população de AUTOMÓVEIS...");
            
            // Automóvel de teste
            Automovel auto1 = new Automovel();
            auto1.setMatricula("ABC1234");
            auto1.setAno(2024);
            auto1.setMarca("Toyota");
            auto1.setModelo("Corolla");
            auto1.setPlaca("ABC-1234");
            Automovel saved1 = automovelRepository.save(auto1);
            LOG.info("Automóvel 1 salvo: ID=" + saved1.getId() + ", Placa=" + saved1.getPlaca());

            Automovel auto2 = new Automovel();
            auto2.setMatricula("XYZ5678");
            auto2.setAno(2023);
            auto2.setMarca("Honda");
            auto2.setModelo("Civic");
            auto2.setPlaca("XYZ-5678");
            Automovel saved2 = automovelRepository.save(auto2);
            LOG.info("Automóvel 2 salvo: ID=" + saved2.getId() + ", Placa=" + saved2.getPlaca());

            Automovel auto3 = new Automovel();
            auto3.setMatricula("DEF9012");
            auto3.setAno(2024);
            auto3.setMarca("Hyundai");
            auto3.setModelo("HB20");
            auto3.setPlaca("DEF-9012");
            Automovel saved3 = automovelRepository.save(auto3);
            LOG.info("Automóvel 3 salvo: ID=" + saved3.getId() + ", Placa=" + saved3.getPlaca());
            
            LOG.info("Total de automóveis no repositório: " + automovelRepository.findAll().size());
        } catch (Exception e) {
            LOG.error("Erro ao inicializar automóveis: ", e);
            e.printStackTrace();
        }
    }

    private void initializeAgentes() {
        try {
            LOG.info("Iniciando população de AGENTES...");
            
            // Banco de teste
            Banco banco1 = new Banco();
            banco1.setLogin("banco");
            banco1.setSenha("123");
            banco1.setNome("Banco do Brasil");
            banco1.setEndereco("Avenida Paulista, 1000");
            banco1.setCnpj("00.360.305/0001-60");
            Agente savedBanco = agenteRepository.save(banco1);
            LOG.info("Banco salvo: ID=" + savedBanco.getId() + ", Nome=" + savedBanco.getNome());

            // Empresa de teste
            Empresa empresa1 = new Empresa();
            empresa1.setLogin("empresa");
            empresa1.setSenha("123");
            empresa1.setNome("Empresa Aluguel XYZ");
            empresa1.setEndereco("Rua Comercial, 500");
            empresa1.setCnpj("12.345.678/0001-90");
            Agente savedEmpresa = agenteRepository.save(empresa1);
            LOG.info("Empresa salva: ID=" + savedEmpresa.getId() + ", Nome=" + savedEmpresa.getNome());
            
            LOG.info("Total de agentes no repositório: " + agenteRepository.findAll().size());
        } catch (Exception e) {
            LOG.error("Erro ao inicializar agentes: ", e);
            e.printStackTrace();
        }
    }

    private void initializePedidosAluguel() {
        try {
            LOG.info("Iniciando população de PEDIDOS DE ALUGUEL...");
            
            // Pedido de aluguel para cliente 1 (ID 1)
            PedidoAluguel pedido1 = new PedidoAluguel();
            pedido1.setClienteId(1L);
            pedido1.setAutomovelId(1L);
            pedido1.setDataLocal(LocalDate.of(2026, 4, 20));
            pedido1.setDataPedido(LocalDate.of(2026, 4, 13));
            pedido1.setStatus("PENDENTE");
            pedido1.setAssinado(false);
            PedidoAluguel saved1 = pedidoAluguelRepository.save(pedido1);
            LOG.info("Pedido 1 salvo: ID=" + saved1.getId() + ", Status=" + saved1.getStatus());

            // Pedido de aluguel para cliente 2 (ID 2)
            PedidoAluguel pedido2 = new PedidoAluguel();
            pedido2.setClienteId(2L);
            pedido2.setAutomovelId(2L);
            pedido2.setDataLocal(LocalDate.of(2026, 4, 25));
            pedido2.setDataPedido(LocalDate.of(2026, 4, 13));
            pedido2.setStatus("APROVADO");
            pedido2.setAssinado(true);
            PedidoAluguel saved2 = pedidoAluguelRepository.save(pedido2);
            LOG.info("Pedido 2 salvo: ID=" + saved2.getId() + ", Status=" + saved2.getStatus());

            // Pedido de aluguel para cliente 3 (ID 3)
            PedidoAluguel pedido3 = new PedidoAluguel();
            pedido3.setClienteId(3L);
            pedido3.setAutomovelId(3L);
            pedido3.setDataLocal(LocalDate.of(2026, 5, 1));
            pedido3.setDataPedido(LocalDate.of(2026, 4, 13));
            pedido3.setStatus("PENDENTE");
            pedido3.setAssinado(false);
            PedidoAluguel saved3 = pedidoAluguelRepository.save(pedido3);
            LOG.info("Pedido 3 salvo: ID=" + saved3.getId() + ", Status=" + saved3.getStatus());
            
            LOG.info("Total de pedidos no repositório: " + pedidoAluguelRepository.findAll().size());
        } catch (Exception e) {
            LOG.error("Erro ao inicializar pedidos de aluguel: ", e);
            e.printStackTrace();
        }
    }

    private void initializeContratos() {
        try {
            LOG.info("Iniciando população de CONTRATOS...");
            
            // Contrato de aluguel 1
            Contrato contrato1 = new Contrato();
            contrato1.setNumero(1001L);
            contrato1.setTermos("Contrato de aluguel de veículo com seguro incluído");
            contrato1.setTipoContrato("ALUGUEL");
            contrato1.setAssinado(false);
            Contrato saved1 = contratoRepository.save(contrato1);
            LOG.info("Contrato 1 salvo: ID=" + saved1.getId() + ", Número=" + saved1.getNumero());

            // Contrato de aluguel 2
            Contrato contrato2 = new Contrato();
            contrato2.setNumero(1002L);
            contrato2.setTermos("Contrato de aluguel com seguro adicional");
            contrato2.setTipoContrato("ALUGUEL");
            contrato2.setAssinado(true);
            Contrato saved2 = contratoRepository.save(contrato2);
            LOG.info("Contrato 2 salvo: ID=" + saved2.getId() + ", Número=" + saved2.getNumero());

            // Contrato de crédito 1
            Contrato contrato3 = new Contrato();
            contrato3.setNumero(2001L);
            contrato3.setTermos("Contrato de crédito para financiamento de aluguel");
            contrato3.setTipoContrato("CREDITO");
            contrato3.setAssinado(false);
            Contrato saved3 = contratoRepository.save(contrato3);
            LOG.info("Contrato 3 salvo: ID=" + saved3.getId() + ", Número=" + saved3.getNumero());
            
            LOG.info("Total de contratos no repositório: " + contratoRepository.findAll().size());
        } catch (Exception e) {
            LOG.error("Erro ao inicializar contratos: ", e);
            e.printStackTrace();
        }
    }

    private void initializeRendimentos() {
        try {
            LOG.info("Iniciando população de RENDIMENTOS...");
            
            // Rendimento cliente 1
            Rendimento rendimento1 = new Rendimento();
            rendimento1.setEntidadeEmpregadora("Empresa ABC Ltda");
            rendimento1.setValor(5000.00);
            Rendimento saved1 = rendimentoRepository.save(rendimento1);
            LOG.info("Rendimento 1 salvo: ID=" + saved1.getId() + ", Valor=" + saved1.getValor());

            // Rendimento cliente 2
            Rendimento rendimento2 = new Rendimento();
            rendimento2.setEntidadeEmpregadora("Empresa XYZ Ltda");
            rendimento2.setValor(6500.00);
            Rendimento saved2 = rendimentoRepository.save(rendimento2);
            LOG.info("Rendimento 2 salvo: ID=" + saved2.getId() + ", Valor=" + saved2.getValor());

            // Rendimento cliente 3
            Rendimento rendimento3 = new Rendimento();
            rendimento3.setEntidadeEmpregadora("Hospital Central");
            rendimento3.setValor(7200.00);
            Rendimento saved3 = rendimentoRepository.save(rendimento3);
            LOG.info("Rendimento 3 salvo: ID=" + saved3.getId() + ", Valor=" + saved3.getValor());
            
            LOG.info("Total de rendimentos no repositório: " + rendimentoRepository.findAll().size());
        } catch (Exception e) {
            LOG.error("Erro ao inicializar rendimentos: ", e);
            e.printStackTrace();
        }
    }
}

