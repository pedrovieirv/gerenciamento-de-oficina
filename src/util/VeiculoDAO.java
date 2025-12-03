package util;

import veiculo.Veiculo;
import cliente.Cliente;
import java.util.*;

/**
 * DAO para persistência de Veiculo
 */
public class VeiculoDAO extends BaseDAO<Veiculo> {
    private static final String NOME_ARQUIVO = "veiculos";
    
    public VeiculoDAO() {
        super(NOME_ARQUIVO);
        carregarTodos();
    }
    
    /**
     * Salva um veículo com validação de placa única
     */
    @Override
    public boolean adicionar(Veiculo veiculo) {
        // Validação: placa não pode ser duplicada
        if (buscarPorPlaca(veiculo.getPlaca()) != null) {
            throw new IllegalArgumentException("Já existe veículo com a placa: " + veiculo.getPlaca());
        }
        return super.adicionar(veiculo);
    }
    
    /**
     * Atualiza um veículo existente
     */
    @Override
    public boolean atualizar(Veiculo veiculo) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdVeiculo() == veiculo.getIdVeiculo()) {
                dados.set(i, veiculo);
                return salvarTodos();
            }
        }
        return false;
    }
    
    /**
     * Remove um veículo pelo ID
     */
    @Override
    public boolean remover(int idVeiculo) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdVeiculo() == idVeiculo) {
                dados.remove(i);
                return salvarTodos();
            }
        }
        return false;
    }
    
    /**
     * Busca um veículo pelo ID
     */
    @Override
    public Veiculo buscarPorId(int idVeiculo) {
        for (Veiculo veiculo : dados) {
            if (veiculo.getIdVeiculo() == idVeiculo) {
                return veiculo;
            }
        }
        return null;
    }
    
    /**
     * Busca veículos pela placa
     */
    public Veiculo buscarPorPlaca(String placa) {
        for (Veiculo veiculo : dados) {
            if (veiculo.getPlaca().equals(placa)) {
                return veiculo;
            }
        }
        return null;
    }
    
    /**
     * Busca veículos pelo cliente
     */
    public List<Veiculo> buscarPorCliente(Cliente cliente) {
        List<Veiculo> resultado = new ArrayList<>();
        for (Veiculo veiculo : dados) {
            if (veiculo.getCliente() != null && veiculo.getCliente().equals(cliente)) {
                resultado.add(veiculo);
            }
        }
        return resultado;
    }
    
    /**
     * Próximo ID disponível para novo veículo
     */
    public int proximoId() {
        return idGenerator.proximoId("Veiculo");
    }
    
    /**
     * Atualiza os IDs máximos quando dados são carregados
     */
    @Override
    protected void atualizarIDsMaximos() {
        for (Veiculo veiculo : dados) {
            idGenerator.atualizarMaximo("Veiculo", veiculo.getIdVeiculo());
        }
    }
}
