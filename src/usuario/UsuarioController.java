package usuario;

import java.util.ArrayList;
import java.util.List;
import util.UsuarioDAO;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Adicionar usuário
    public boolean adicionarUsuario(Usuario usuario) {
        try {
            usuario.setIdUsuario(usuarioDAO.proximoId());
            return usuarioDAO.adicionar(usuario);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar usuário: " + e.getMessage());
            return false;
        }
    }

    // Listar todos os usuários
    public List<Usuario> listarUsuarios() {
        try {
            return usuarioDAO.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Consultar usuário por nome
    public List<Usuario> consultarPorNome(String nome) {
        try {
            return usuarioDAO.buscarPorNome(nome);
        } catch (Exception e) {
            System.err.println("Erro ao consultar usuário por nome: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Consultar usuário por email (alias para buscarPorLogin)
    public Usuario consultarPorEmail(String email) {
        try {
            return usuarioDAO.buscarPorLogin(email);
        } catch (Exception e) {
            System.err.println("Erro ao consultar usuário por email: " + e.getMessage());
            return null;
        }
    }

    // Atualizar usuário
    public boolean atualizarUsuario(Usuario usuario) {
        try {
            return usuarioDAO.atualizar(usuario);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    // Remover usuário
    public boolean removerUsuario(int idUsuario) {
        try {
            return usuarioDAO.remover(idUsuario);
        } catch (Exception e) {
            System.err.println("Erro ao remover usuário: " + e.getMessage());
            return false;
        }
    }

    // Buscar usuário por ID
    public Usuario buscarPorId(int id) {
        try {
            return usuarioDAO.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
            return null;
        }
    }

    // Buscar usuário por login
    public Usuario buscarPorLogin(String login) {
        try {
            return usuarioDAO.buscarPorLogin(login);
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário por login: " + e.getMessage());
            return null;
        }
    }

    // Validar login
    public Usuario validarLogin(String login, String senha) {
        try {
            return usuarioDAO.autenticar(login, senha);
        } catch (Exception e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
            return null;
        }
    }

    // Verificar se usuário existe
    public boolean usuarioExiste(int idUsuario) {
        return buscarPorId(idUsuario) != null;
    }

    // Obter quantidade de usuários
    public int getTotalUsuarios() {
        try {
            return usuarioDAO.tamanho();
        } catch (Exception e) {
            System.err.println("Erro ao obter total de usuários: " + e.getMessage());
            return 0;
        }
    }
}