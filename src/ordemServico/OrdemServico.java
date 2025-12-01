import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import ordemServico.StatusOrdemServico;
import cliente.Cliente;
import veiculo.Veiculo;
import funcionario.Funcionario;

public class OrdemServico {
    private int idOrdem;
    private Date dataAbertura;
    private Date dataConclusao;
    private StatusOrdemServico status;
    private String descricaoProblema;
    private double valorTotal;
    private double valorMaoObra;
    private double valorPecas;
    
    // Relacionamentos
    private Cliente cliente;
    private Veiculo veiculo;
    private Funcionario funcionario;
    
    // Construtor
    public OrdemServico(int idOrdem, Cliente cliente, Veiculo veiculo, 
                        Funcionario funcionario, String descricaoProblema, 
                        double valorMaoObra) {
        this.idOrdem = idOrdem;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.funcionario = funcionario;
        this.descricaoProblema = descricaoProblema;
        this.valorMaoObra = valorMaoObra;
        this.dataAbertura = new Date();
        this.status = StatusOrdemServico.EM_ANALISE;
        this.valorPecas = 0.0;
        this.valorTotal = calcularValorTotal();
    }
    
    // Métodos de negócio
    public static OrdemServico criarOrdemServico(Cliente cliente, Veiculo veiculo, 
                                                  Funcionario funcionario, 
                                                  String descricaoProblema, 
                                                  double valorMaoObra) {
        int novoId = gerarProximoId();
        return new OrdemServico(novoId, cliente, veiculo, funcionario, 
                               descricaoProblema, valorMaoObra);
    }
    
    public double calcularValorTotal() {
        this.valorTotal = this.valorMaoObra + this.valorPecas;
        return this.valorTotal;
    }
    
    public void verOrdemServico() {
        System.out.println("=== Ordem de Serviço #" + idOrdem + " ===");
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Veículo: " + veiculo.getModelo() + " - " + veiculo.getPlaca());
        System.out.println("Funcionário: " + funcionario.getNome());
        System.out.println("Status: " + status);
        System.out.println("Data Abertura: " + dataAbertura);
        System.out.println("Data Conclusão: " + dataConclusao);
        System.out.println("Problema: " + descricaoProblema);
        System.out.println("Valor Mão de Obra: R$ " + valorMaoObra);
        System.out.println("Valor Peças: R$ " + valorPecas);
        System.out.println("Valor Total: R$ " + valorTotal);
    }
    
    public void editarOrdemServico(StatusOrdemServico status, Date dataConclusao, 
                                   String descricaoProblema, double valorMaoObra) {
        this.status = status;
        this.dataConclusao = dataConclusao;
        this.descricaoProblema = descricaoProblema;
        this.valorMaoObra = valorMaoObra;
        calcularValorTotal();
    }
    
    public static void excluirOrdemServico(OrdemServico ordemServico) {
        // Lógica para excluir a ordem de serviço do banco de dados
        System.out.println("Ordem de serviço #" + ordemServico.getIdOrdem() + " excluída.");
    }
    
    public static List<OrdemServico> listarOrdensDeServicoPorVeiculo(Veiculo veiculo) {
        // Lógica para buscar ordens de serviço do veículo no banco de dados
        List<OrdemServico> ordens = new ArrayList<>();
        // Implementar busca no banco
        return ordens;
    }
    
    public static OrdemServico buscarServico(int idOrdem) {
        // Lógica para buscar ordem de serviço por ID no banco de dados
        // Implementar busca no banco
        return null;
    }
    
    public void realizarChecklist(Veiculo veiculo) {
        System.out.println("Realizando checklist do veículo: " + veiculo.getPlaca());
        // Implementar lógica de checklist
        System.out.println("Checklist concluído.");
    }
    
    public void finalizarServico(OrdemServico ordem) {
        ordem.setStatus(StatusOrdemServico.CONCLUIDO);
        ordem.setDataConclusao(new Date());
        ordem.calcularValorTotal();
        System.out.println("Serviço finalizado. Valor total: R$ " + ordem.getValorTotal());
    }
    
    public static List<OrdemServico> listarOrdensPorStatus(StatusOrdemServico status) {
        // Lógica para buscar ordens por status no banco de dados
        List<OrdemServico> ordens = new ArrayList<>();
        // Implementar busca no banco
        return ordens;
    }
    
    public static List<OrdemServico> listarOrdensPorCliente(String cpf) {
        // Lógica para buscar ordens por CPF do cliente no banco de dados
        List<OrdemServico> ordens = new ArrayList<>();
        // Implementar busca no banco
        return ordens;
    }
    
    // Método auxiliar para gerar ID
    private static int gerarProximoId() {
        // Lógica para gerar o próximo ID (pode ser do banco de dados)
        return (int) (Math.random() * 10000);
    }
    
    // Getters e Setters
    public int getIdOrdem() {
        return idOrdem;
    }
    
    public void setIdOrdem(int idOrdem) {
        this.idOrdem = idOrdem;
    }
    
    public Date getDataAbertura() {
        return dataAbertura;
    }
    
    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
    
    public Date getDataConclusao() {
        return dataConclusao;
    }
    
    public void setDataConclusao(Date dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
    
    public StatusOrdemServico getStatus() {
        return status;
    }
    
    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }
    
    public String getDescricaoProblema() {
        return descricaoProblema;
    }
    
    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }
    
    public double getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public double getValorMaoObra() {
        return valorMaoObra;
    }
    
    public void setValorMaoObra(double valorMaoObra) {
        this.valorMaoObra = valorMaoObra;
    }
    
    public double getValorPecas() {
        return valorPecas;
    }
    
    public void setValorPecas(double valorPecas) {
        this.valorPecas = valorPecas;
        calcularValorTotal();
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Veiculo getVeiculo() {
        return veiculo;
    }
    
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }
    
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}