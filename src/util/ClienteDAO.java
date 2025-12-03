package util;

import cliente.Cliente;
import java.util.*;

/**
 * DAO para persistência de Cliente
 */
public class ClienteDAO extends BaseDAO<Cliente> {
    private static final String NOME_ARQUIVO = "clientes";
    
    public ClienteDAO() {
        super(NOME_ARQUIVO);
        carregarTodos();
    }
    
    /**
     * Atualiza um cliente existente
     */
    @Override
    public boolean atualizar(Cliente cliente) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdCliente() == cliente.getIdCliente()) {
                dados.set(i, cliente);
                return salvarTodos();
            }
        }
        return false;
    }
    
    /**
     * Remove um cliente pelo ID
     */
    @Override
    public boolean remover(int idCliente) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdCliente() == idCliente) {
                dados.remove(i);
                return salvarTodos();
            }
        }
        return false;
    }
    
    /**
     * Busca um cliente pelo ID
     */
    @Override
    public Cliente buscarPorId(int idCliente) {
        for (Cliente cliente : dados) {
            if (cliente.getIdCliente() == idCliente) {
                return cliente;
            }
        }
        return null;
    }
    
    /**
     * Busca clientes pelo nome
     */
    public List<Cliente> buscarPorNome(String nome) {
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente cliente : dados) {
            if (cliente.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }
    
    /**
     * Busca clientes pelo CPF
     */
    public Cliente buscarPorCPF(String cpf) {
        String cpfNormalizado = normalizarCPF(cpf);
        for (Cliente cliente : dados) {
            if (normalizarCPF(cliente.getCpf()).equals(cpfNormalizado)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Normaliza CPF removendo formatação (pontos e hífens)
     */
    private String normalizarCPF(String cpf) {
        if (cpf == null) return "";
        return cpf.replaceAll("[^0-9]", "");
    }
    
    /**
     * Próximo ID disponível para novo cliente
     */
    public int proximoId() {
        return idGenerator.proximoId("Cliente");
    }
    
    /**
     * Atualiza os IDs máximos quando dados são carregados
     */
    @Override
    protected void atualizarIDsMaximos() {
        for (Cliente cliente : dados) {
            idGenerator.atualizarMaximo("Cliente", cliente.getIdCliente());
        }
    }
}
