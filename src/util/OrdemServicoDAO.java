package util;

import ordemServico.OrdemServico;
import cliente.Cliente;
import veiculo.Veiculo;
import funcionario.Funcionario;
import java.util.*;

/**
 * DAO para persistência de OrdemServico
 */
public class OrdemServicoDAO extends BaseDAO<OrdemServico> {
    private static final String NOME_ARQUIVO = "ordens";
    
    public OrdemServicoDAO() {
        super(NOME_ARQUIVO);
        carregarTodos();
    }
    
    /**
     * Atualiza uma ordem de serviço existente
     */
    @Override
    public boolean atualizar(OrdemServico ordem) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdOrdem() == ordem.getIdOrdem()) {
                dados.set(i, ordem);
                return salvarTodos();
            }
        }
        return false;
    }
    
    /**
     * Remove uma ordem de serviço pelo ID
     */
    @Override
    public boolean remover(int idOrdem) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdOrdem() == idOrdem) {
                dados.remove(i);
                return salvarTodos();
            }
        }
        return false;
    }
    
    /**
     * Busca uma ordem de serviço pelo ID
     */
    @Override
    public OrdemServico buscarPorId(int idOrdem) {
        for (OrdemServico ordem : dados) {
            if (ordem.getIdOrdem() == idOrdem) {
                return ordem;
            }
        }
        return null;
    }
    
    /**
     * Busca ordens de serviço por cliente
     */
    public List<OrdemServico> buscarPorCliente(Cliente cliente) {
        List<OrdemServico> resultado = new ArrayList<>();
        for (OrdemServico ordem : dados) {
            if (ordem.getCliente() != null && ordem.getCliente().equals(cliente)) {
                resultado.add(ordem);
            }
        }
        return resultado;
    }
    
    /**
     * Busca ordens de serviço por veículo
     */
    public List<OrdemServico> buscarPorVeiculo(Veiculo veiculo) {
        List<OrdemServico> resultado = new ArrayList<>();
        for (OrdemServico ordem : dados) {
            if (ordem.getVeiculo() != null && ordem.getVeiculo().equals(veiculo)) {
                resultado.add(ordem);
            }
        }
        return resultado;
    }
    
    /**
     * Busca ordens de serviço por funcionário
     */
    public List<OrdemServico> buscarPorFuncionario(Funcionario funcionario) {
        List<OrdemServico> resultado = new ArrayList<>();
        for (OrdemServico ordem : dados) {
            if (ordem.getFuncionario() != null && ordem.getFuncionario().equals(funcionario)) {
                resultado.add(ordem);
            }
        }
        return resultado;
    }
    
    /**
     * Busca ordens de serviço por status
     */
    public List<OrdemServico> buscarPorStatus(String status) {
        List<OrdemServico> resultado = new ArrayList<>();
        for (OrdemServico ordem : dados) {
            if (ordem.getStatus() != null && ordem.getStatus().toString().equals(status)) {
                resultado.add(ordem);
            }
        }
        return resultado;
    }
    
    /**
     * Lista todas as ordens abertas
     */
    public List<OrdemServico> listarAbertas() {
        return buscarPorStatus("EM_ANALISE");
    }
    
    /**
     * Lista todas as ordens em andamento
     */
    public List<OrdemServico> listarEmAndamento() {
        return buscarPorStatus("EM_EXECUCAO");
    }
    
    /**
     * Lista todas as ordens concluídas
     */
    public List<OrdemServico> listarConcluidas() {
        return buscarPorStatus("CONCLUIDO");
    }
    
    /**
     * Próximo ID disponível para nova ordem
     */
    public int proximoId() {
        return idGenerator.proximoId("OrdemServico");
    }
    
    /**
     * Atualiza os IDs máximos quando dados são carregados
     */
    @Override
    protected void atualizarIDsMaximos() {
        for (OrdemServico ordem : dados) {
            idGenerator.atualizarMaximo("OrdemServico", ordem.getIdOrdem());
        }
    }
}
