package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe para gerenciar a geração automática de IDs para todas as entidades
 */
public class IDGenerator {
    private static final String COUNTER_FILE = "data/idcounter.dat";
    private Map<String, Integer> counters;
    
    public IDGenerator() {
        this.counters = new HashMap<>();
        carregarContadores();
    }
    
    /**
     * Gera o próximo ID para uma entidade
     */
    public synchronized int proximoId(String entidade) {
        counters.putIfAbsent(entidade, 0);
        int novoId = counters.get(entidade) + 1;
        counters.put(entidade, novoId);
        salvarContadores();
        return novoId;
    }
    
    /**
     * Define um ID máximo para uma entidade (útil ao carregar dados)
     */
    public synchronized void atualizarMaximo(String entidade, int id) {
        int atual = counters.getOrDefault(entidade, 0);
        if (id > atual) {
            counters.put(entidade, id);
            salvarContadores();
        }
    }
    
    /**
     * Carrega os contadores do arquivo
     */
    @SuppressWarnings("unchecked")
    private void carregarContadores() {
        File file = new File(COUNTER_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                this.counters = (Map<String, Integer>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar contadores: " + e.getMessage());
                this.counters = new HashMap<>();
            }
        }
    }
    
    /**
     * Salva os contadores no arquivo
     */
    private void salvarContadores() {
        try {
            new File("data").mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COUNTER_FILE))) {
                oos.writeObject(counters);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar contadores: " + e.getMessage());
        }
    }
}
