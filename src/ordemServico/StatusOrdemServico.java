package ordemServico;

public enum StatusOrdemServico {
    EM_ANALISE("Em Análise"),
    EM_EXECUCAO("Em Execução"),
    CONCLUIDO("Concluído"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private String descricao;

    StatusOrdemServico(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
