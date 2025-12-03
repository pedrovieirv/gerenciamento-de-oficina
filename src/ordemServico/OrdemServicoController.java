package ordemServico;

import java.util.ArrayList;
import java.util.List;
import util.OrdemServicoDAO;
import util.OficinaService;
import cliente.Cliente;
import veiculo.Veiculo;
import funcionario.Funcionario;

public class OrdemServicoController {
    private OrdemServicoDAO ordemServicoDAO;
    private OficinaService oficinaService;

    public OrdemServicoController(OficinaService oficinaService) {
        this.ordemServicoDAO = new OrdemServicoDAO();
        this.oficinaService = oficinaService;
    }

    // Criar Ordem de Serviço via OficinaService
    public boolean criarOrdemServico(String cpfCliente, String placaVeiculo, int idFuncionario, String descricao, double valorMaoObra) {
        try {
            return oficinaService.abrirOrdemServico(cpfCliente, placaVeiculo, idFuncionario, descricao, valorMaoObra);
        } catch (Exception e) {
            System.err.println("Erro ao criar ordem de serviço: " + e.getMessage());
            return false;
        }
    }

    // Criar Ordem de Serviço diretamente (para compatibilidade)
    public boolean criarOrdemServico(OrdemServico ordemServico) {
        try {
            int novoId = ordemServicoDAO.proximoId();
            ordemServico.setIdOrdem(novoId);
            return ordemServicoDAO.adicionar(ordemServico);
        } catch (Exception e) {
            System.err.println("Erro ao criar ordem de serviço: " + e.getMessage());
            return false;
        }
    }

    // Editar Ordem de Serviço via OficinaService
    public boolean editarOrdemServico(int idOrdem, String descricao, double valorMaoObra) {
        try {
            return oficinaService.editarOrdemServico(idOrdem, descricao, valorMaoObra);
        } catch (Exception e) {
            System.err.println("Erro ao editar ordem de serviço: " + e.getMessage());
            return false;
        }
    }

    // Listar todas as Ordens de Serviço
    public List<OrdemServico> listarOrdensServico() {
        try {
            return ordemServicoDAO.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar ordens de serviço: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Atualizar Ordem de Serviço
    public boolean atualizarOrdemServico(OrdemServico ordemServico) {
        try {
            return ordemServicoDAO.atualizar(ordemServico);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar ordem de serviço: " + e.getMessage());
            return false;
        }
    }

    // Remover Ordem de Serviço
    public boolean removerOrdemServico(int id) {
        try {
            return ordemServicoDAO.remover(id);
        } catch (Exception e) {
            System.err.println("Erro ao remover ordem de serviço: " + e.getMessage());
            return false;
        }
    }

    // Buscar Ordem de Serviço por ID
    public OrdemServico buscarOrdemPorId(int id) {
        try {
            return ordemServicoDAO.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar ordem por ID: " + e.getMessage());
            return null;
        }
    }

    // Listar ordens por cliente
    public List<OrdemServico> listarOrdensDoCliente(Cliente cliente) {
        try {
            return ordemServicoDAO.buscarPorCliente(cliente);
        } catch (Exception e) {
            System.err.println("Erro ao listar ordens do cliente: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Listar ordens por funcionário
    public List<OrdemServico> listarOrdensPorFuncionario(Funcionario funcionario) {
        try {
            return ordemServicoDAO.buscarPorFuncionario(funcionario);
        } catch (Exception e) {
            System.err.println("Erro ao listar ordens por funcionário: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Listar ordens por veículo
    public List<OrdemServico> listarOrdensPorVeiculo(Veiculo veiculo) {
        try {
            return ordemServicoDAO.buscarPorVeiculo(veiculo);
        } catch (Exception e) {
            System.err.println("Erro ao listar ordens por veículo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Listar ordens por status
    public List<OrdemServico> listarOrdensPorStatus(String status) {
        try {
            return ordemServicoDAO.buscarPorStatus(status);
        } catch (Exception e) {
            System.err.println("Erro ao listar ordens por status: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ========== TRANSIÇÕES DE STATUS ==========

    // Aprovar ordem (EM_ANALISE -> EM_EXECUCAO)
    public boolean aprovarOrdemServico(int idOrdem) {
        try {
            return oficinaService.aprovarOrdemServico(idOrdem);
        } catch (Exception e) {
            System.err.println("✗ ERRO ao aprovar ordem: " + e.getMessage());
            return false;
        }
    }

    // Finalizar ordem (EM_EXECUCAO -> CONCLUIDO)
    public boolean finalizarOrdemServico(int idOrdem) {
        try {
            return oficinaService.finalizarOrdemServico(idOrdem);
        } catch (Exception e) {
            System.err.println("✗ ERRO ao finalizar ordem: " + e.getMessage());
            return false;
        }
    }

    // Entregar ordem (CONCLUIDO -> ENTREGUE)
    public boolean entregarOrdemServico(int idOrdem) {
        try {
            return oficinaService.entregarOrdemServico(idOrdem);
        } catch (Exception e) {
            System.err.println("✗ ERRO ao entregar ordem: " + e.getMessage());
            return false;
        }
    }

    // ========== FIM TRANSIÇÕES DE STATUS ==========
    public List<OrdemServico> listarAbertas() {
        try {
            return ordemServicoDAO.listarAbertas();
        } catch (Exception e) {
            System.err.println("Erro ao listar ordens abertas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Listar ordens em andamento
    public List<OrdemServico> listarEmAndamento() {
        try {
            return ordemServicoDAO.listarEmAndamento();
        } catch (Exception e) {
            System.err.println("Erro ao listar ordens em andamento: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Listar ordens concluídas
    public List<OrdemServico> listarConcluidas() {
        try {
            return ordemServicoDAO.listarConcluidas();
        } catch (Exception e) {
            System.err.println("Erro ao listar ordens concluídas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Verificar se ordem existe
    public boolean ordemExiste(int idOrdem) {
        return buscarOrdemPorId(idOrdem) != null;
    }

    // Obter quantidade de ordens
    public int getTotalOrdens() {
        try {
            return ordemServicoDAO.tamanho();
        } catch (Exception e) {
            System.err.println("Erro ao obter total de ordens: " + e.getMessage());
            return 0;
        }
    }
}