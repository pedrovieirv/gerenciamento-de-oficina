package veiculo;

import java.util.ArrayList;
import java.util.List;
import util.VeiculoDAO;
import util.OficinaService;
import cliente.Cliente;

public class VeiculoController {
    private VeiculoDAO veiculoDAO;
    private OficinaService oficinaService;

    public VeiculoController(OficinaService oficinaService) {
        this.veiculoDAO = new VeiculoDAO();
        this.oficinaService = oficinaService;
    }

    // Criar/Adicionar veículo para cliente (via OficinaService)
    public boolean criarVeiculo(String cpfCliente, String placa, String modelo, String marca, int ano, String cor) {
        try {
            return oficinaService.cadastrarVeiculoParaCliente(cpfCliente, placa, modelo, marca, ano, cor);
        } catch (IllegalArgumentException e) {
            throw e; // Re-lança para a view tratar
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar veículo: " + e.getMessage());
        }
    }

    // Criar veículo diretamente (para compatibilidade)
    public boolean criarVeiculo(Veiculo veiculo) {
        try {
            veiculo.setIdVeiculo(veiculoDAO.proximoId());
            return veiculoDAO.adicionar(veiculo);
        } catch (Exception e) {
            System.err.println("Erro ao criar veículo: " + e.getMessage());
            return false;
        }
    }

    // Listar todos os veículos
    public List<Veiculo> listarVeiculos() {
        try {
            return veiculoDAO.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar veículos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Atualizar veículo
    public boolean atualizarVeiculo(Veiculo veiculo) {
        try {
            return veiculoDAO.atualizar(veiculo);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar veículo: " + e.getMessage());
            return false;
        }
    }

    // Remover veículo
    public boolean removerVeiculo(int idVeiculo) {
        try {
            return veiculoDAO.remover(idVeiculo);
        } catch (Exception e) {
            System.err.println("Erro ao remover veículo: " + e.getMessage());
            return false;
        }
    }

    // Buscar veículo por ID
    public Veiculo buscarVeiculoPorId(int id) {
        try {
            return veiculoDAO.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar veículo por ID: " + e.getMessage());
            return null;
        }
    }

    // Buscar veículo por placa
    public Veiculo buscarVeiculoPorPlaca(String placa) {
        try {
            return veiculoDAO.buscarPorPlaca(placa);
        } catch (Exception e) {
            System.err.println("Erro ao buscar veículo por placa: " + e.getMessage());
            return null;
        }
    }

    // Buscar veículos por cliente
    public List<Veiculo> buscarVeiculosPorCliente(String cpfCliente) {
        try {
            return oficinaService.listarVeiculosDoCliente(cpfCliente);
        } catch (Exception e) {
            System.err.println("Erro ao buscar veículos por cliente: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Verificar se veículo existe
    public boolean veiculoExiste(int idVeiculo) {
        return buscarVeiculoPorId(idVeiculo) != null;
    }

    // Obter quantidade de veículos
    public int getTotalVeiculos() {
        try {
            return veiculoDAO.tamanho();
        } catch (Exception e) {
            System.err.println("Erro ao obter total de veículos: " + e.getMessage());
            return 0;
        }
    }
}
