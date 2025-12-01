import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    private int idUsuario;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    
    private static int contadorId = 1;
    private static List<Usuario> usuarios = new ArrayList<>();
    
    // Construtor padrão
    public Usuario() {
        this.idUsuario = contadorId++;
    }
    
    // Construtor completo
    public Usuario(int idUsuario, String nome, String cpf, String email, String senha) {
        this.idUsuario = idUsuario > 0 ? idUsuario : contadorId++;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }
    
    // Construtor alternativo
    public Usuario(String nome, String cpf, String email, String senha) {
        this.idUsuario = contadorId++;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }
    
    // Getters e Setters
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    // Métodos do diagrama
    public boolean login(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }
    
    public static List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    public static Usuario criarUsuario(String nome, String cpf, String email, String senha) {
        // Este método deve ser implementado nas subclasses concretas
        // pois Usuario é abstrata
        throw new UnsupportedOperationException("Método deve ser implementado na subclasse");
    }
    
    public void verUsuario() {
        System.out.println("=== Dados do Usuário ===");
        System.out.println("ID: " + idUsuario);
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Email: " + email);
    }
    
    public void editarUsuario(String nome, String cpf, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }
    
    public static void desativarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        System.out.println("Usuário " + usuario.getNome() + " desativado com sucesso.");
    }
    
    // Método auxiliar para adicionar usuário à lista
    protected static void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}