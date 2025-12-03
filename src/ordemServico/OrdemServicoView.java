package ordemServico;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import cliente.Cliente;
import veiculo.Veiculo;
import funcionario.Funcionario;

public class OrdemServicoView {
    private Scanner scanner;
    private OrdemServicoController controller;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public OrdemServicoView(OrdemServicoController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void exibirMenuPrincipal() {
        int opcao = 0;
        do {
            System.out.println("\n========== GERENCIAR ORDENS DE SERVIÇO ==========");
            System.out.println("1. Listar Ordens de Serviço");
            System.out.println("2. Buscar Ordem por ID");
            System.out.println("3. Listar Ordens por Status");
            System.out.println("4. Atualizar Valores da Ordem");
            System.out.println("5. Gerenciar Status (Fluxo de Vida)");
            System.out.println("6. Deletar Ordem de Serviço");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        listarOrdens();
                        break;
                    case 2:
                        buscarOrdenPorId();
                        break;
                    case 3:
                        listarPorStatus();
                        break;
                    case 4:
                        atualizarValoresOrdem();
                        break;
                    case 5:
                        gerenciarStatusOrdem();
                        break;
                    case 6:
                        deletarOrdem();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("✗ Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("✗ Digite um número válido!");
            }
        } while (opcao != 0);
    }

    private void listarOrdens() {
        List<OrdemServico> ordens = controller.listarOrdensServico();
        
        if (ordens.isEmpty()) {
            System.out.println("\n✗ Nenhuma ordem de serviço cadastrada.");
            return;
        }

        System.out.println("\n========== LISTA DE ORDENS DE SERVIÇO ==========");
        System.out.printf("%-5s | %-15s | %-20s | %-12s\n", "ID", "Status", "Cliente", "Valor Total");
        System.out.println("--------|-----------------|------------------------|---------");
        
        for (OrdemServico ordem : ordens) {
            System.out.printf("%-5d | %-15s | %-20s | R$%.2f\n", 
                ordem.getIdOrdem(), 
                ordem.getStatus().toString(),
                truncar(ordem.getCliente().getNome(), 20),
                ordem.getValorTotal()
            );
        }
    }

    private void buscarOrdenPorId() {
        try {
            System.out.print("\nDigite o ID da ordem: ");
            int id = Integer.parseInt(scanner.nextLine());

            OrdemServico ordem = controller.buscarOrdemPorId(id);
            
            if (ordem != null) {
                exibirDetalhesOrdem(ordem);
            } else {
                System.out.println("✗ Ordem de serviço não encontrada!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido!");
        }
    }

    private void listarPorStatus() {
        System.out.println("\nEscolha um status:");
        for (StatusOrdemServico status : StatusOrdemServico.values()) {
            System.out.println("- " + status.toString());
        }
        System.out.print("Status: ");
        String statusStr = scanner.nextLine().toUpperCase();

        try {
            List<OrdemServico> ordens = controller.listarOrdensPorStatus(statusStr);
            
            if (ordens.isEmpty()) {
                System.out.println("✗ Nenhuma ordem com este status.");
                return;
            }

            System.out.println("\n========== ORDENS COM STATUS: " + statusStr + " ==========");
            for (OrdemServico ordem : ordens) {
                System.out.printf("ID: %-5d | Cliente: %-20s | Valor: R$%.2f\n", 
                    ordem.getIdOrdem(), 
                    truncar(ordem.getCliente().getNome(), 20),
                    ordem.getValorTotal()
                );
            }
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Status inválido!");
        }
    }

    private void atualizarValoresOrdem() {
        try {
            System.out.print("\nDigite o ID da ordem a atualizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            OrdemServico ordem = controller.buscarOrdemPorId(id);
            
            if (ordem == null) {
                System.out.println("✗ Ordem de serviço não encontrada!");
                return;
            }

            // Verifica se ordem pode ser editada (não em ENTREGUE)
            if (ordem.getStatus() == StatusOrdemServico.ENTREGUE) {
                System.out.println("✗ ERRO: Ordem #" + id + " já foi entregue e não pode ser editada!");
                return;
            }

            System.out.println("\n========== ATUALIZAR VALORES ==========");
            System.out.println("Status atual: " + ordem.getStatus());
            
            System.out.print("Novo valor mão de obra (deixe em branco para não alterar): ");
            String valorMOStr = scanner.nextLine();
            if (!valorMOStr.isEmpty()) {
                try {
                    double valorMO = Double.parseDouble(valorMOStr);
                    if (valorMO < 0) {
                        System.out.println("✗ Valor não pode ser negativo!");
                        return;
                    }
                    ordem.setValorMaoObra(valorMO);
                } catch (NumberFormatException e) {
                    System.out.println("✗ Valor inválido!");
                    return;
                }
            }

            System.out.print("Novo valor de peças (deixe em branco para não alterar): ");
            String valorPecasStr = scanner.nextLine();
            if (!valorPecasStr.isEmpty()) {
                try {
                    double valorPecas = Double.parseDouble(valorPecasStr);
                    if (valorPecas < 0) {
                        System.out.println("✗ Valor não pode ser negativo!");
                        return;
                    }
                    ordem.setValorPecas(valorPecas);
                } catch (NumberFormatException e) {
                    System.out.println("✗ Valor inválido!");
                    return;
                }
            }

            System.out.print("Nova descrição (deixe em branco para não alterar): ");
            String novaDesc = scanner.nextLine();
            if (!novaDesc.isEmpty()) {
                ordem.setDescricaoProblema(novaDesc);
            }

            ordem.calcularValorTotal();
            
            if (controller.atualizarOrdemServico(ordem)) {
                System.out.println("✓ Ordem atualizada com sucesso!");
                System.out.println("Novo valor total: R$" + String.format("%.2f", ordem.getValorTotal()));
            } else {
                System.out.println("✗ Erro ao atualizar ordem!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Erro ao processar dados!");
        }
    }

    private void gerenciarStatusOrdem() {
        try {
            System.out.print("\nDigite o ID da ordem: ");
            int id = Integer.parseInt(scanner.nextLine());

            OrdemServico ordem = controller.buscarOrdemPorId(id);
            
            if (ordem == null) {
                System.out.println("✗ Ordem de serviço não encontrada!");
                return;
            }

            exibirDetalhesOrdem(ordem);

            System.out.println("\n========== FLUXO DE VIDA DA ORDEM ==========");
            System.out.println("Status Atual: " + ordem.getStatus());
            System.out.println();

            // Menu de transições disponíveis baseado no status atual
            if (ordem.getStatus() == StatusOrdemServico.EM_ANALISE) {
                System.out.println("Opções disponíveis:");
                System.out.println("1. Aprovar Serviço (EM_ANALISE → EM_EXECUCAO)");
                System.out.println("0. Cancelar");
                System.out.print("Escolha: ");
                String escolha = scanner.nextLine();

                if (escolha.equals("1")) {
                    if (controller.aprovarOrdemServico(id)) {
                        System.out.println("✓ Ordem aprovada! Status: EM_EXECUCAO");
                    } else {
                        System.out.println("✗ Erro ao aprovar ordem!");
                    }
                }
            } 
            else if (ordem.getStatus() == StatusOrdemServico.EM_EXECUCAO) {
                System.out.println("Opções disponíveis:");
                System.out.println("1. Finalizar Serviço (EM_EXECUCAO → CONCLUIDO)");
                System.out.println("0. Cancelar");
                System.out.print("Escolha: ");
                String escolha = scanner.nextLine();

                if (escolha.equals("1")) {
                    if (controller.finalizarOrdemServico(id)) {
                        System.out.println("✓ Serviço finalizado! Status: CONCLUIDO");
                        System.out.println("Data de conclusão definida automaticamente.");
                    } else {
                        System.out.println("✗ Erro ao finalizar ordem!");
                    }
                }
            } 
            else if (ordem.getStatus() == StatusOrdemServico.CONCLUIDO) {
                System.out.println("Opções disponíveis:");
                System.out.println("1. Entregar ao Cliente (CONCLUIDO → ENTREGUE)");
                System.out.println("0. Cancelar");
                System.out.print("Escolha: ");
                String escolha = scanner.nextLine();

                if (escolha.equals("1")) {
                    if (controller.entregarOrdemServico(id)) {
                        System.out.println("✓ Ordem entregue ao cliente! Status: ENTREGUE");
                        System.out.println("Ordem não poderá mais ser editada.");
                    } else {
                        System.out.println("✗ Erro ao entregar ordem!");
                    }
                }
            } 
            else if (ordem.getStatus() == StatusOrdemServico.ENTREGUE) {
                System.out.println("❌ Ordem já foi entregue ao cliente.");
                System.out.println("Status: ENTREGUE (não pode ser alterado)");
                System.out.println("Consultar apenas para histórico.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido!");
        }
    }

    private void deletarOrdem() {
        try {
            System.out.print("\nDigite o ID da ordem a deletar: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (controller.buscarOrdemPorId(id) == null) {
                System.out.println("✗ Ordem de serviço não encontrada!");
                return;
            }

            System.out.print("Tem certeza? (s/n): ");
            String confirmacao = scanner.nextLine();
            
            if (confirmacao.equalsIgnoreCase("s")) {
                if (controller.removerOrdemServico(id)) {
                    System.out.println("✓ Ordem deletada com sucesso!");
                }
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido!");
        }
    }

    private void exibirDetalhesOrdem(OrdemServico ordem) {
        System.out.println("\n========== DETALHES DA ORDEM DE SERVIÇO ==========");
        System.out.println("ID: " + ordem.getIdOrdem());
        System.out.println("Status: " + ordem.getStatus());
        System.out.println("Data de Abertura: " + dateFormat.format(ordem.getDataAbertura()));
        
        if (ordem.getDataConclusao() != null) {
            System.out.println("Data de Conclusão: " + dateFormat.format(ordem.getDataConclusao()));
        }
        
        System.out.println("\nCliente: " + ordem.getCliente().getNome());
        System.out.println("Veículo: " + ordem.getVeiculo().getPlaca() + " - " + ordem.getVeiculo().getModelo());
        System.out.println("Funcionário: " + ordem.getFuncionario().getNome());
        System.out.println("\nProblema: " + ordem.getDescricaoProblema());
        System.out.println("\nValores:");
        System.out.println("  Mão de Obra: R$" + ordem.getValorMaoObra());
        System.out.println("  Peças: R$" + ordem.getValorPecas());
        System.out.println("  Total: R$" + ordem.getValorTotal());
    }

    private String truncar(String str, int maxLen) {
        if (str.length() > maxLen) {
            return str.substring(0, maxLen - 3) + "...";
        }
        return str;
    }
}
