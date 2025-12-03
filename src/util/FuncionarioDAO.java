package util;

import funcionario.Funcionario;
import java.util.*;

/**
 * DAO para persistência de Funcionario
 */
public class FuncionarioDAO extends BaseDAO<Funcionario> {
    private static final String NOME_ARQUIVO = "funcionarios";
    
    public FuncionarioDAO() {
        super(NOME_ARQUIVO);
        carregarTodos();
    }
    
    /**
     * Atualiza um funcionário existente
     */
    @Override
    public boolean atualizar(Funcionario funcionario) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdUsuario() == funcionario.getIdUsuario()) {
                dados.set(i, funcionario);
                return salvarTodos();
            }
        }
        return false;
    }
    
    /**
     * Remove um funcionário pelo ID
     */
    @Override
    public boolean remover(int idFuncionario) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdUsuario() == idFuncionario) {
                dados.remove(i);
                return salvarTodos();
            }
        }
        return false;
    }
    
    /**
     * Busca um funcionário pelo ID
     */
    @Override
    public Funcionario buscarPorId(int idFuncionario) {
        for (Funcionario funcionario : dados) {
            if (funcionario.getIdUsuario() == idFuncionario) {
                return funcionario;
            }
        }
        return null;
    }
    
    /**
     * Busca um funcionário pelo login (usando email)
     */
    public Funcionario buscarPorLogin(String login) {
        for (Funcionario funcionario : dados) {
            if (funcionario.getEmail().equals(login)) {
                return funcionario;
            }
        }
        return null;
    }
    
    /**
     * Busca funcionários por tipo (Gerente ou Operacional)
     */
    public List<Funcionario> buscarPorCargo(String cargo) {
        List<Funcionario> resultado = new ArrayList<>();
        boolean procuraGerente = cargo.toLowerCase().contains("gerente");
        for (Funcionario funcionario : dados) {
            if (procuraGerente && funcionario.isEhGerente()) {
                resultado.add(funcionario);
            } else if (!procuraGerente && !funcionario.isEhGerente()) {
                resultado.add(funcionario);
            }
        }
        return resultado;
    }
    
    /**
     * Lista todos os gerentes
     */
    public List<Funcionario> listarGerentes() {
        List<Funcionario> gerentes = new ArrayList<>();
        for (Funcionario funcionario : dados) {
            if (funcionario.isEhGerente()) {
                gerentes.add(funcionario);
            }
        }
        return gerentes;
    }
    
    /**
     * Lista todos os operacionais
     */
    public List<Funcionario> listarOperacionais() {
        List<Funcionario> operacionais = new ArrayList<>();
        for (Funcionario funcionario : dados) {
            if (!funcionario.isEhGerente()) {
                operacionais.add(funcionario);
            }
        }
        return operacionais;
    }
    
    /**
     * Valida login e senha
     */
    public Funcionario autenticar(String login, String senha) {
        Funcionario funcionario = buscarPorLogin(login);
        if (funcionario != null && funcionario.login(login, senha)) {
            return funcionario;
        }
        return null;
    }
    
    /**
     * Próximo ID disponível para novo funcionário
     */
    public int proximoId() {
        return idGenerator.proximoId("Funcionario");
    }
    
    /**
     * Atualiza os IDs máximos quando dados são carregados
     */
    @Override
    protected void atualizarIDsMaximos() {
        for (Funcionario funcionario : dados) {
            idGenerator.atualizarMaximo("Funcionario", funcionario.getIdUsuario());
        }
    }
}
