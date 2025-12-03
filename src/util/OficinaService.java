package util;

import cliente.Cliente;
import veiculo.Veiculo;
import funcionario.Funcionario;
import ordemServico.OrdemServico;
import ordemServico.StatusOrdemServico;

/**
 * Serviço de orquestração de operações da oficina.
 * Centraliza regras de negócio e validações.
 */
public class OficinaService {

    private ClienteDAO clienteDAO;
    private VeiculoDAO veiculoDAO;
    private FuncionarioDAO funcionarioDAO;
    private OrdemServicoDAO ordemServicoDAO;

    public OficinaService(ClienteDAO clienteDAO,
                          VeiculoDAO veiculoDAO,
                          FuncionarioDAO funcionarioDAO,
                          OrdemServicoDAO ordemServicoDAO) {
        this.clienteDAO = clienteDAO;
        this.veiculoDAO = veiculoDAO;
        this.funcionarioDAO = funcionarioDAO;
        this.ordemServicoDAO = ordemServicoDAO;
    }

    /**
     * Normaliza CPF removendo formatação (pontos e hífens)
     */
    private String normalizarCPF(String cpf) {
        if (cpf == null) return null;
        return cpf.replaceAll("[^0-9]", "");
    }

    /**
     * Cadastra um veículo **já vinculado ao cliente**.
     * 
     * REGRA: Não existe veículo sem cliente.
     * 
     * @param cpfCliente CPF do cliente proprietário
     * @param placa Placa do veículo (deve ser única)
     * @param modelo Modelo do veículo
     * @param marca Marca do veículo
     * @param ano Ano de fabricação
     * @param cor Cor do veículo
     * @throws IllegalArgumentException se cliente não existe ou placa duplicada
     */
    public boolean cadastrarVeiculoParaCliente(String cpfCliente,
                                            String placa,
                                            String modelo,
                                            String marca,
                                            int ano,
                                            String cor) {

        // Normaliza e busca o cliente pelo CPF
        String cpfNormalizado = normalizarCPF(cpfCliente);
        Cliente cliente = clienteDAO.buscarPorCPF(cpfNormalizado);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o CPF: " + cpfCliente);
        }

        // Valida placa duplicada (VeiculoDAO já faz isso no adicionar)
        if (veiculoDAO.buscarPorPlaca(placa) != null) {
            throw new IllegalArgumentException("Já existe veículo com a placa: " + placa);
        }

        // Cria e salva o veículo com referência ao cliente
        Veiculo veiculo = new Veiculo(cliente, placa, modelo, marca, ano, cor);
        return veiculoDAO.adicionar(veiculo);
    }

    /**
     * Abre uma ordem de serviço com **validações fortes**.
     * 
     * REGRAS:
     * 1. Cliente deve existir
     * 2. Veículo deve existir
     * 3. Veículo DEVE pertencer ao cliente informado
     * 4. Funcionário deve existir
     * 
     * @param cpfCliente CPF do cliente
     * @param placaVeiculo Placa do veículo
     * @param idFuncionario ID do funcionário responsável
     * @param descricaoProblema Descrição do problema
     * @param valorMaoObra Valor da mão de obra
     * @throws IllegalArgumentException se qualquer validação falhar
     */
    public boolean abrirOrdemServico(String cpfCliente,
                                  String placaVeiculo,
                                  int idFuncionario,
                                  String descricaoProblema,
                                  double valorMaoObra) {

        // 1. Busca o cliente (normaliza CPF)
        String cpfNormalizado = normalizarCPF(cpfCliente);
        Cliente cliente = clienteDAO.buscarPorCPF(cpfNormalizado);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o CPF: " + cpfCliente);
        }

        // 2. Busca o veículo
        Veiculo veiculo = veiculoDAO.buscarPorPlaca(placaVeiculo);
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não encontrado com a placa: " + placaVeiculo);
        }

        // 3. VALIDAÇÃO CRÍTICA: veículo pertence ao cliente?
        if (!veiculo.getCliente().equals(cliente)) {
            throw new IllegalArgumentException(
                "ERRO: Este veículo (placa: " + placaVeiculo + ") não está cadastrado para o cliente " +
                cliente.getNome() + ". Veículo pertence a: " + veiculo.getCliente().getNome()
            );
        }

        // 4. Busca o funcionário
        Funcionario funcionario = funcionarioDAO.buscarPorId(idFuncionario);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não encontrado com ID: " + idFuncionario);
        }

        // Se tudo passou, cria a ordem de serviço
        OrdemServico ordem = new OrdemServico(cliente, veiculo, funcionario,
                                              descricaoProblema, valorMaoObra);

        return ordemServicoDAO.adicionar(ordem);
    }

    /**
     * Edita uma ordem de serviço existente.
     * 
     * @param idOrdem ID da ordem a editar
     * @param novaDescricao Nova descrição (ou null para manter)
     * @param novoValorMaoObra Novo valor de mão de obra (ou -1 para manter)
     */
    public boolean editarOrdemServico(int idOrdem,
                                   String novaDescricao,
                                   double novoValorMaoObra) {

        OrdemServico ordem = ordemServicoDAO.buscarPorId(idOrdem);
        if (ordem == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + idOrdem);
        }

        if (novaDescricao != null && !novaDescricao.trim().isEmpty()) {
            ordem.setDescricaoProblema(novaDescricao);
        }

        if (novoValorMaoObra >= 0) {
            ordem.setValorMaoObra(novoValorMaoObra);
        }

        ordem.calcularValorTotal();
        return ordemServicoDAO.atualizar(ordem);
    }

    /**
     * Transiciona a ordem de EM_ANALISE para EM_EXECUCAO.
     * Validações:
     * - Ordem deve estar em EM_ANALISE
     * - Valores já devem estar preenchidos
     */
    public boolean aprovarOrdemServico(int idOrdem) {
        OrdemServico ordem = ordemServicoDAO.buscarPorId(idOrdem);
        if (ordem == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + idOrdem);
        }

        if (ordem.getStatus() != StatusOrdemServico.EM_ANALISE) {
            throw new IllegalArgumentException(
                "Ordem #" + idOrdem + " está em status " + ordem.getStatus() + 
                ". Apenas ordens em EM_ANALISE podem ser aprovadas."
            );
        }

        ordem.setStatus(StatusOrdemServico.EM_EXECUCAO);
        return ordemServicoDAO.atualizar(ordem);
    }

    /**
     * Finaliza a ordem de serviço, passando para CONCLUIDO.
     * Validações:
     * - Ordem deve estar em EM_EXECUCAO
     * - Define dataConclusao com a data/hora atual
     * - Calcula o valor total
     */
    public boolean finalizarOrdemServico(int idOrdem) {
        OrdemServico ordem = ordemServicoDAO.buscarPorId(idOrdem);
        if (ordem == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + idOrdem);
        }

        if (ordem.getStatus() != StatusOrdemServico.EM_EXECUCAO) {
            throw new IllegalArgumentException(
                "Ordem #" + idOrdem + " está em status " + ordem.getStatus() + 
                ". Apenas ordens em EM_EXECUCAO podem ser finalizadas."
            );
        }

        ordem.setStatus(StatusOrdemServico.CONCLUIDO);
        ordem.setDataConclusao(new java.util.Date());
        ordem.calcularValorTotal();
        return ordemServicoDAO.atualizar(ordem);
    }

    /**
     * Entrega a ordem de serviço ao cliente.
     * Validações:
     * - Ordem deve estar em CONCLUIDO
     * - Após isso, a ordem não pode mais ser editada
     */
    public boolean entregarOrdemServico(int idOrdem) {
        OrdemServico ordem = ordemServicoDAO.buscarPorId(idOrdem);
        if (ordem == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + idOrdem);
        }

        if (ordem.getStatus() != StatusOrdemServico.CONCLUIDO) {
            throw new IllegalArgumentException(
                "Ordem #" + idOrdem + " está em status " + ordem.getStatus() + 
                ". Apenas ordens em CONCLUIDO podem ser entregues."
            );
        }

        ordem.setStatus(StatusOrdemServico.ENTREGUE);
        return ordemServicoDAO.atualizar(ordem);
    }

    /**
     * Retorna todos os veículos de um cliente específico.
     * 
     * @param cpfCliente CPF do cliente
     * @return Lista de veículos do cliente
     */
    public java.util.List<Veiculo> listarVeiculosDoCliente(String cpfCliente) {
        String cpfNormalizado = normalizarCPF(cpfCliente);
        Cliente cliente = clienteDAO.buscarPorCPF(cpfNormalizado);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o CPF: " + cpfCliente);
        }
        return veiculoDAO.buscarPorCliente(cliente);
    }

    // Getters dos DAOs para acesso direto quando necessário
    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public VeiculoDAO getVeiculoDAO() {
        return veiculoDAO;
    }

    public FuncionarioDAO getFuncionarioDAO() {
        return funcionarioDAO;
    }

    public OrdemServicoDAO getOrdemServicoDAO() {
        return ordemServicoDAO;
    }
}
