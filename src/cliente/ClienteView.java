package cliente;

import java.util.List;
import java.util.Scanner;

public class ClienteView {
    private Scanner scanner;
    private ClienteController controller;

    public ClienteView(ClienteController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    // Menu principal de clientes
    public void exibirMenuPrincipal() {
        int opcao = 0;
        do {
            System.out.println("\n========== GERENCIAR CLIENTES ==========");
            System.out.println("1. Listar Clientes");
            System.out.println("2. Criar Novo Cliente");
            System.out.println("3. Buscar Cliente por ID");
            System.out.println("4. Buscar Cliente por CPF");
            System.out.println("5. Atualizar Cliente");
            System.out.println("6. Deletar Cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        listarClientes();
                        break;
                    case 2:
                        criarCliente();
                        break;
                    case 3:
                        buscarClientePorId();
                        break;
                    case 4:
                        buscarClientePorCpf();
                        break;
                    case 5:
                        atualizarCliente();
                        break;
                    case 6:
                        deletarCliente();
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

    private void listarClientes() {
        List<Cliente> clientes = controller.listarClientes();
        
        if (clientes.isEmpty()) {
            System.out.println("\n✗ Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n========== LISTA DE CLIENTES ==========");
        System.out.printf("%-5s | %-20s | %-15s | %-15s\n", "ID", "Nome", "CPF", "Telefone");
        System.out.println("--------|------------------------|---------|---------");
        
        for (Cliente cliente : clientes) {
            System.out.printf("%-5d | %-20s | %-15s | %-15s\n", 
                cliente.getIdCliente(), 
                truncar(cliente.getNome(), 20),
                cliente.getCpf(), 
                cliente.getTelefone()
            );
        }
    }

    private void criarCliente() {
        System.out.println("\n========== CRIAR NOVO CLIENTE ==========");
        
        try {
            System.out.print("ID do Cliente: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("✗ Nome não pode estar vazio!");
                return;
            }

            System.out.print("CPF: ");
            String cpf = normalizarCPF(scanner.nextLine());
            if (cpf == null || cpf.isEmpty()) {
                System.out.println("✗ CPF não pode estar vazio!");
                return;
            }
            if (cpf.length() != 11) {
                System.out.println("✗ CPF deve ter 11 dígitos!");
                return;
            }

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine().trim();
            if (telefone.isEmpty()) {
                System.out.println("✗ Telefone não pode estar vazio!");
                return;
            }

            System.out.print("Endereço: ");
            String endereco = scanner.nextLine().trim();
            if (endereco.isEmpty()) {
                System.out.println("✗ Endereço não pode estar vazio!");
                return;
            }

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("✗ Email não pode estar vazio!");
                return;
            }
            if (!email.contains("@")) {
                System.out.println("✗ Email inválido!");
                return;
            }

            Cliente cliente = new Cliente(id, nome, cpf, telefone, endereco, email);
            if (controller.criarCliente(cliente)) {
                System.out.println("✓ Cliente criado com sucesso!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID deve ser um número válido!");
        }
    }

    /**
     * Normaliza CPF removendo formatação (pontos e hífens)
     */
    private String normalizarCPF(String cpf) {
        if (cpf == null) return null;
        return cpf.replaceAll("[^0-9]", "");
    }

    private void buscarClientePorId() {
        try {
            System.out.print("\nDigite o ID do cliente: ");
            int id = Integer.parseInt(scanner.nextLine());

            Cliente cliente = controller.buscarClientePorId(id);
            
            if (cliente != null) {
                exibirDetalhesCliente(cliente);
            } else {
                System.out.println("✗ Cliente não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido!");
        }
    }

    private void buscarClientePorCpf() {
        System.out.print("\nDigite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        Cliente cliente = controller.buscarClientePorCpf(cpf);
        
        if (cliente != null) {
            exibirDetalhesCliente(cliente);
        } else {
            System.out.println("✗ Cliente não encontrado!");
        }
    }

    private void atualizarCliente() {
        try {
            System.out.print("\nDigite o ID do cliente a atualizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            Cliente cliente = controller.buscarClientePorId(id);
            
            if (cliente == null) {
                System.out.println("✗ Cliente não encontrado!");
                return;
            }

            System.out.println("\n========== ATUALIZAR CLIENTE ==========");
            System.out.print("Novo nome (deixe em branco para não alterar): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) cliente.setNome(nome);

            System.out.print("Novo telefone: ");
            String telefone = scanner.nextLine();
            if (!telefone.isEmpty()) cliente.setTelefone(telefone);

            System.out.print("Novo endereço: ");
            String endereco = scanner.nextLine();
            if (!endereco.isEmpty()) cliente.setEndereco(endereco);

            System.out.print("Novo email: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) cliente.setEmail(email);

            if (controller.atualizarCliente(cliente)) {
                System.out.println("✓ Cliente atualizado com sucesso!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Erro ao processar dados!");
        }
    }

    private void deletarCliente() {
        try {
            System.out.print("\nDigite o ID do cliente a deletar: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (controller.buscarClientePorId(id) == null) {
                System.out.println("✗ Cliente não encontrado!");
                return;
            }

            System.out.print("Tem certeza? (s/n): ");
            String confirmacao = scanner.nextLine();
            
            if (confirmacao.equalsIgnoreCase("s")) {
                if (controller.removerCliente(id)) {
                    System.out.println("✓ Cliente deletado com sucesso!");
                }
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido!");
        }
    }

    public void exibirDetalhesCliente(Cliente cliente) {
        System.out.println("\n========== DETALHES DO CLIENTE ==========");
        System.out.println("ID: " + cliente.getIdCliente());
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Telefone: " + cliente.getTelefone());
        System.out.println("Endereço: " + cliente.getEndereco());
        System.out.println("Email: " + cliente.getEmail());
    }

    private String truncar(String str, int maxLen) {
        if (str.length() > maxLen) {
            return str.substring(0, maxLen - 3) + "...";
        }
        return str;
    }
}
