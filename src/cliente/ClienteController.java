package cliente;

import java.util.ArrayList;
import java.util.List;
import util.ClienteDAO;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    // Criar cliente
    public boolean criarCliente(Cliente cliente) {
        try {
            cliente.setIdCliente(clienteDAO.proximoId());
            return clienteDAO.adicionar(cliente);
        } catch (Exception e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
            return false;
        }
    }

    // Listar todos os clientes
    public List<Cliente> listarClientes() {
        try {
            return clienteDAO.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Atualizar cliente
    public boolean atualizarCliente(Cliente cliente) {
        try {
            return clienteDAO.atualizar(cliente);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Remover cliente
    public boolean removerCliente(int idCliente) {
        try {
            return clienteDAO.remover(idCliente);
        } catch (Exception e) {
            System.err.println("Erro ao remover cliente: " + e.getMessage());
            return false;
        }
    }

    // Consultar cliente por nome
    public List<Cliente> consultarClientePorNome(String nome) {
        try {
            return clienteDAO.buscarPorNome(nome);
        } catch (Exception e) {
            System.err.println("Erro ao consultar cliente por nome: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Buscar cliente por ID
    public Cliente buscarClientePorId(int id) {
        try {
            return clienteDAO.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
            return null;
        }
    }

    // Buscar cliente por CPF
    public Cliente buscarClientePorCpf(String cpf) {
        try {
            return clienteDAO.buscarPorCPF(cpf);
        } catch (Exception e) {
            System.err.println("Erro ao buscar cliente por CPF: " + e.getMessage());
            return null;
        }
    }

    // Verificar se cliente existe
    public boolean clienteExiste(int idCliente) {
        return buscarClientePorId(idCliente) != null;
    }

    // Obter quantidade de clientes
    public int getTotalClientes() {
        try {
            return clienteDAO.tamanho();
        } catch (Exception e) {
            System.err.println("Erro ao obter total de clientes: " + e.getMessage());
            return 0;
        }
    }
}
