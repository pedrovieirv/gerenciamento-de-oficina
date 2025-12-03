package usuario;

import java.util.List;
import java.util.Scanner;

public class UsuarioView {
    private Scanner scanner;
    private UsuarioController controller;

    public UsuarioView(UsuarioController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void exibirMenuPrincipal() {
        int opcao = 0;
        do {
            System.out.println("\n========== GERENCIAR USUÁRIOS ==========");
            System.out.println("1. Listar Usuários");
            System.out.println("2. Buscar Usuário por ID");
            System.out.println("3. Buscar Usuário por Email");
            System.out.println("4. Atualizar Usuário");
            System.out.println("5. Deletar Usuário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch (opcao) {
                    case 1:
                        listarUsuarios();
                        break;
                    case 2:
                        buscarUsuarioPorId();
                        break;
                    case 3:
                        buscarUsuarioPorEmail();
                        break;
                    case 4:
                        atualizarUsuario();
                        break;
                    case 5:
                        deletarUsuario();
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

    private void listarUsuarios() {
        List<Usuario> usuarios = controller.listarUsuarios();
        
        if (usuarios.isEmpty()) {
            System.out.println("\n✗ Nenhum usuário cadastrado.");
            return;
        }

        System.out.println("\n========== LISTA DE USUÁRIOS ==========");
        System.out.printf("%-5s | %-20s | %-15s | %-25s\n", "ID", "Nome", "CPF", "Email");
        System.out.println("--------|------------------------|---------|---------");
        
        for (Usuario usuario : usuarios) {
            System.out.printf("%-5d | %-20s | %-15s | %-25s\n", 
                usuario.getIdUsuario(), 
                truncar(usuario.getNome(), 20),
                usuario.getCpf(),
                truncar(usuario.getEmail(), 25)
            );
        }
    }

    private void buscarUsuarioPorId() {
        try {
            System.out.print("\nDigite o ID do usuário: ");
            int id = Integer.parseInt(scanner.nextLine());

            Usuario usuario = controller.buscarPorId(id);
            
            if (usuario != null) {
                exibirDetalhesUsuario(usuario);
            } else {
                System.out.println("✗ Usuário não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido!");
        }
    }

    private void buscarUsuarioPorEmail() {
        System.out.print("\nDigite o email do usuário: ");
        String email = scanner.nextLine();

        Usuario usuario = controller.consultarPorEmail(email);
        
        if (usuario != null) {
            exibirDetalhesUsuario(usuario);
        } else {
            System.out.println("✗ Usuário não encontrado!");
        }
    }

    private void atualizarUsuario() {
        try {
            System.out.print("\nDigite o ID do usuário a atualizar: ");
            int id = Integer.parseInt(scanner.nextLine());

            Usuario usuario = controller.buscarPorId(id);
            
            if (usuario == null) {
                System.out.println("✗ Usuário não encontrado!");
                return;
            }

            System.out.println("\n========== ATUALIZAR USUÁRIO ==========");
            System.out.print("Novo nome (deixe em branco para não alterar): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) usuario.setNome(nome);

            System.out.print("Novo email: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) usuario.setEmail(email);

            if (controller.atualizarUsuario(usuario)) {
                System.out.println("✓ Usuário atualizado com sucesso!");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Erro ao processar dados!");
        }
    }

    private void deletarUsuario() {
        try {
            System.out.print("\nDigite o ID do usuário a deletar: ");
            int id = Integer.parseInt(scanner.nextLine());

            if (controller.buscarPorId(id) == null) {
                System.out.println("✗ Usuário não encontrado!");
                return;
            }

            System.out.print("Tem certeza? (s/n): ");
            String confirmacao = scanner.nextLine();
            
            if (confirmacao.equalsIgnoreCase("s")) {
                if (controller.removerUsuario(id)) {
                    System.out.println("✓ Usuário deletado com sucesso!");
                }
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido!");
        }
    }

    public void exibirDetalhesUsuario(Usuario usuario) {
        System.out.println("\n========== DETALHES DO USUÁRIO ==========");
        System.out.println("ID: " + usuario.getIdUsuario());
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("CPF: " + usuario.getCpf());
        System.out.println("Email: " + usuario.getEmail());
    }

    private String truncar(String str, int maxLen) {
        if (str.length() > maxLen) {
            return str.substring(0, maxLen - 3) + "...";
        }
        return str;
    }
}
