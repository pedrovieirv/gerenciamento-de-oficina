package veiculo;

import java.util.List;
import java.util.Scanner;

public class VeiculoView {
    private Scanner scanner;
    private VeiculoController controller;

    public VeiculoView(VeiculoController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void exibirMenuPrincipal() {
        int opcao = 0;
        do {
            System.out.println("\n========== GERENCIAR VEÍCULOS ==========");
            System.out.println("1. Listar Veículos");
            System.out.println("2. Criar Novo Veículo");
            System.out.println("3. Buscar Veículo por ID");
            System.out.println("4. Buscar Veículo por Placa");
            System.out.println("5. Atualizar Veículo");
            System.out.println("6. Deletar Veículo");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        listarVeiculos();
                        break;
                    case 2:
                        criarVeiculo();
                        break;
                    case 3:
                        buscarVeiculoPorId();
                        break;
                    case 4:
                        buscarVeiculoPorPlaca();
                        break;
                    case 5:
                        atualizarVeiculo();
                        break;
                    case 6:
                        deletarVeiculo();
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

    private void listarVeiculos() {
        List<Veiculo> veiculos = controller.listarVeiculos();
        
        if (veiculos.isEmpty()) {
            System.out.println("\n✗ Nenhum veículo cadastrado.");
            return;
        }

        System.out.println("\n========== LISTA DE VEÍCULOS ==========");
        System.out.printf("%-5s | %-10s | %-15s | %-12s | %-6s\n", "ID", "Placa", "Modelo", "Marca", "Ano");
        System.out.println("--------|------------|-----------------|-----------|------");
        
        for (Veiculo veiculo : veiculos) {
            System.out.printf("%-5d | %-10s | %-15s | %-12s | %-6d\n", 
                veiculo.getIdVeiculo(), 
                veiculo.getPlaca(), 
                truncar(veiculo.getModelo(), 15),
                truncar(veiculo.getMarca(), 12),
                veiculo.getAno()
            );
        }
    }

    private void criarVeiculo() {
        System.out.println("\n========== CRIAR NOVO VEÍCULO ==========");
        
        try {
            System.out.print("ID do Veículo: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Placa: ");
            String placa = scanner.nextLine().trim();
            if (placa.isEmpty()) {
                System.out.println("✗ Placa não pode estar vazia!");
                return;
            }

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine().trim();
            if (modelo.isEmpty()) {
                System.out.println("✗ Modelo não pode estar vazio!");
                return;
            }

            System.out.print("Marca: ");
            String marca = scanner.nextLine().trim();
            if (marca.isEmpty()) {
                System.out.println("✗ Marca não pode estar vazia!");
                return;
            }

            System.out.print("Ano: ");
            int ano = Integer.parseInt(scanner.nextLine());
            if (ano < 1900 || ano > 2100) {
                System.out.println("✗ Ano inválido!");
                return;
            }

            System.out.print("Cor: ");
            String cor = scanner.nextLine().trim();
            if (cor.isEmpty()) {
                System.out.println("✗ Cor não pode estar vazia!");
                return;
            }

            System.out.print("CPF do cliente: ");
            String cpfCliente = scanner.nextLine().trim();
            if (cpfCliente.isEmpty()) {
                System.out.println("✗ CPF não pode estar vazio!");
                return;
            }

            try {
                if (controller.criarVeiculo(cpfCliente, placa, modelo, marca, ano, cor)) {
                    System.out.println("✓ Veículo criado com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("✗ Erro ao criar veículo: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Erro ao processar dados!");
        }
    }

    private void buscarVeiculoPorId() {
        try {
            System.out.print("\nDigite o ID do veículo: ");
            int id = Integer.parseInt(scanner.nextLine());

            Veiculo veiculo = controller.buscarVeiculoPorId(id);
            
            if (veiculo != null) {
                exibirDetalhesVeiculo(veiculo);
            } else {
                System.out.println("✗ Veículo não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido!");
        }
    }

    private void buscarVeiculoPorPlaca() {
        System.out.print("\nDigite a placa do veículo: ");
        String placa = scanner.nextLine();

        Veiculo veiculo = controller.buscarVeiculoPorPlaca(placa);
        
        if (veiculo != null) {
            exibirDetalhesVeiculo(veiculo);
        } else {
            System.out.println("✗ Veículo não encontrado!");
        }
    }

    private void atualizarVeiculo() {
        try {
            System.out.print("\nDigite o ID do veículo a atualizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            Veiculo veiculo = controller.buscarVeiculoPorId(id);
            
            if (veiculo == null) {
                System.out.println("✗ Veículo não encontrado!");
                return;
            }

            System.out.println("\n========== ATUALIZAR VEÍCULO ==========");
            System.out.print("Novo modelo (deixe em branco para não alterar): ");
            String modelo = scanner.nextLine();
            if (!modelo.isEmpty()) veiculo.setModelo(modelo);

            System.out.print("Novo ano: ");
            String anoStr = scanner.nextLine();
            if (!anoStr.isEmpty()) veiculo.setAno(Integer.parseInt(anoStr));

            System.out.print("Nova cor: ");
            String cor = scanner.nextLine();
            if (!cor.isEmpty()) veiculo.setCor(cor);

            if (controller.atualizarVeiculo(veiculo)) {
                System.out.println("✓ Veículo atualizado com sucesso!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Erro ao processar dados!");
        }
    }

    private void deletarVeiculo() {
        try {
            System.out.print("\nDigite o ID do veículo a deletar: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (controller.buscarVeiculoPorId(id) == null) {
                System.out.println("✗ Veículo não encontrado!");
                return;
            }

            System.out.print("Tem certeza? (s/n): ");
            String confirmacao = scanner.nextLine();
            
            if (confirmacao.equalsIgnoreCase("s")) {
                if (controller.removerVeiculo(id)) {
                    System.out.println("✓ Veículo deletado com sucesso!");
                }
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido!");
        }
    }

    public void exibirDetalhesVeiculo(Veiculo veiculo) {
        System.out.println("\n========== DETALHES DO VEÍCULO ==========");
        System.out.println("ID: " + veiculo.getIdVeiculo());
        System.out.println("Placa: " + veiculo.getPlaca());
        System.out.println("Modelo: " + veiculo.getModelo());
        System.out.println("Marca: " + veiculo.getMarca());
        System.out.println("Ano: " + veiculo.getAno());
        System.out.println("Cor: " + veiculo.getCor());
    }

    private String truncar(String str, int maxLen) {
        if (str.length() > maxLen) {
            return str.substring(0, maxLen - 3) + "...";
        }
        return str;
    }
}
