import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Funcionario extends Usuario {
    private Date dataAdmissao;
    private boolean ehGerente;

    // Construtor padrão
    public Funcionario() {
        super();
    }

    // Construtor completo
    public Funcionario(int idUsuario, String nome, String cpf, String email, 
                       String senha, Date dataAdmissao, boolean ehGerente) {
        super(idUsuario, nome, cpf, email, senha);
        this.dataAdmissao = dataAdmissao;
        this.ehGerente = ehGerente;
    }

    // Construtor alternativo (sem ID)
    public Funcionario(String nome, String cpf, String email, String senha, 
                       Date dataAdmissao, boolean ehGerente) {
        super(nome, cpf, email, senha);
        this.dataAdmissao = dataAdmissao;
        this.ehGerente = ehGerente;
    }

    // Getters e Setters
    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public boolean isEhGerente() {
        return ehGerente;
    }

    public void setEhGerente(boolean ehGerente) {
        this.ehGerente = ehGerente;
    }

    // Métodos da classe
    public static Funcionario criarFuncionario(String nome, String cpf, String email, 
                                               String senha, Date dataAdmissao, 
                                               boolean ehGerente) {
        Funcionario funcionario = new Funcionario(nome, cpf, email, senha, dataAdmissao, ehGerente);
        System.out.println("Funcionário " + nome + " criado com sucesso!");
        return funcionario;
    }

    public void verFuncionario() {
        System.out.println("=== Dados do Funcionário ===");
        System.out.println("ID: " + getIdUsuario());
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Email: " + getEmail());
        System.out.println("Data de Admissão: " + dataAdmissao);
        System.out.println("É Gerente: " + (ehGerente ? "Sim" : "Não"));
    }

    public void editarFuncionario(String nome, String cpf, String email, String senha, 
                                  Date dataAdmissao, boolean ehGerente) {
        setNome(nome);
        setCpf(cpf);
        setEmail(email);
        setSenha(senha);
        this.dataAdmissao = dataAdmissao;
        this.ehGerente = ehGerente;
        System.out.println("Funcionário editado com sucesso!");
    }

    public static void desativarFuncionario(Funcionario funcionario) {
        // Lógica para desativar o funcionário
        System.out.println("Funcionário " + funcionario.getNome() + " desativado.");
    }

    public static List<Funcionario> listarFuncionarios() {
        // Lógica para listar todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>();
        // Aqui seria feita a consulta ao banco de dados ou lista em memória
        return funcionarios;
    }

    public void gerarRelatorioDeServicosRealizados() {
        System.out.println("=== Relatório de Serviços Realizados ===");
        System.out.println("Funcionário: " + nome);
        // Lógica para gerar o relatório
    }

    // Método auxiliar privado para gerar novo ID
    private static int gerarNovoId() {
        // Implementação simplificada
        return (int) (Math.random() * 10000);
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "idFuncionario=" + getIdUsuario() +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", dataAdmissao=" + dataAdmissao +
                ", ehGerente=" + ehGerente +
                '}';
    }
}