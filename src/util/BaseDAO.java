package util;

import java.io.*;
import java.util.*;

/**
 * Classe DAO genérica para operações de persistência
 */
public abstract class BaseDAO<T> {
    protected String diretorioDados = "data";
    protected String nomeArquivo;
    protected List<T> dados;
    protected IDGenerator idGenerator;
    
    public BaseDAO(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.dados = new ArrayList<>();
        this.idGenerator = new IDGenerator();
        criarDiretorioDados();
    }
    
    /**
     * Cria o diretório de dados se não existir
     */
    protected void criarDiretorioDados() {
        File dir = new File(diretorioDados);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Carrega todos os dados do arquivo
     */
    public List<T> carregarTodos() {
        File file = new File(diretorioDados + "/" + nomeArquivo + ".dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                @SuppressWarnings("unchecked")
                List<T> carregados = (List<T>) ois.readObject();
                this.dados = carregados != null ? carregados : new ArrayList<>();
                atualizarIDsMaximos();
                return new ArrayList<>(dados);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar " + nomeArquivo + ": " + e.getMessage());
                this.dados = new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }
    
    /**
     * Salva todos os dados no arquivo
     */
    public boolean salvarTodos() {
        try {
            criarDiretorioDados();
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(diretorioDados + "/" + nomeArquivo + ".dat"))) {
                oos.writeObject(new ArrayList<>(dados));
                return true;
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar " + nomeArquivo + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Adiciona um novo registro
     */
    public boolean adicionar(T objeto) {
        if (dados.add(objeto)) {
            return salvarTodos();
        }
        return false;
    }
    
    /**
     * Atualiza um registro existente
     */
    public abstract boolean atualizar(T objeto);
    
    /**
     * Remove um registro
     */
    public abstract boolean remover(int id);
    
    /**
     * Busca um registro por ID
     */
    public abstract T buscarPorId(int id);
    
    /**
     * Retorna todos os registros
     */
    public List<T> listarTodos() {
        return new ArrayList<>(dados);
    }
    
    /**
     * Atualiza os IDs máximos após carregar dados
     */
    protected abstract void atualizarIDsMaximos();
    
    /**
     * Retorna o tamanho da lista
     */
    public int tamanho() {
        return dados.size();
    }
    
    /**
     * Limpa todos os dados
     */
    public void limpar() {
        dados.clear();
        salvarTodos();
    }
}
