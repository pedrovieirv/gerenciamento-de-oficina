package util;

import usuario.Usuario;
import java.util.*;

/**
 * DAO para persistência de Usuario
 */
public class UsuarioDAO extends BaseDAO<Usuario> {
    private static final String NOME_ARQUIVO = "usuarios";
    
    public UsuarioDAO() {
        super(NOME_ARQUIVO);
        carregarTodos();
    }
    
    /**
     * Atualiza um usuário existente
     */
    @Override
    public boolean atualizar(Usuario usuario) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdUsuario() == usuario.getIdUsuario()) {
                dados.set(i, usuario);
                return salvarTodos();
            }
        }
        return false;
    }
    
    /**
     * Remove um usuário pelo ID
     */
    @Override
    public boolean remover(int idUsuario) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdUsuario() == idUsuario) {
                dados.remove(i);
                return salvarTodos();
            }
        }
        return false;
    }
    
    /**
     * Busca um usuário pelo ID
     */
    @Override
    public Usuario buscarPorId(int idUsuario) {
        for (Usuario usuario : dados) {
            if (usuario.getIdUsuario() == idUsuario) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Busca um usuário pelo login (usando email)
     */
    public Usuario buscarPorLogin(String login) {
        for (Usuario usuario : dados) {
            if (usuario.getEmail().equals(login)) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Busca usuários pelo nome
     */
    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : dados) {
            if (usuario.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }
    
    /**
     * Valida login e senha
     */
    public Usuario autenticar(String login, String senha) {
        Usuario usuario = buscarPorLogin(login);
        if (usuario != null && usuario.login(login, senha)) {
            return usuario;
        }
        return null;
    }
    
    /**
     * Próximo ID disponível para novo usuário
     */
    public int proximoId() {
        return idGenerator.proximoId("Usuario");
    }
    
    /**
     * Atualiza os IDs máximos quando dados são carregados
     */
    @Override
    protected void atualizarIDsMaximos() {
        for (Usuario usuario : dados) {
            idGenerator.atualizarMaximo("Usuario", usuario.getIdUsuario());
        }
    }
}
