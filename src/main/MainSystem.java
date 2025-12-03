package main;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import cliente.ClienteController;
import cliente.Cliente;
import veiculo.VeiculoController;
import veiculo.Veiculo;
import usuario.UsuarioController;
import usuario.Usuario;
import funcionario.FuncionarioController;
import funcionario.Funcionario;
import ordemServico.OrdemServicoController;
import ordemServico.OrdemServico;
import util.OficinaService;
import util.ClienteDAO;
import util.VeiculoDAO;
import util.FuncionarioDAO;
import util.OrdemServicoDAO;

public class MainSystem {
    private static FuncionarioController funcController = new FuncionarioController();
    private static ClienteController clienteController = new ClienteController();
    
    // Instancia OficinaService com todos os DAOs necessários
    private static OficinaService oficinaService;
    private static VeiculoController veiculoController;
    private static OrdemServicoController ordemController;
    
    private static UsuarioController usuarioController = new UsuarioController();
    private static Funcionario usuarioLogado = null;
    private static Scanner scanner = new Scanner(System.in);

    static {
        // Inicializa OficinaService com todos os DAOs
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            VeiculoDAO veiculoDAO = new VeiculoDAO();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
            
            oficinaService = new OficinaService(clienteDAO, veiculoDAO, funcionarioDAO, ordemServicoDAO);
            veiculoController = new VeiculoController(oficinaService);
            ordemController = new OrdemServicoController(oficinaService);
        } catch (Exception e) {
            System.err.println("Erro ao inicializar OficinaService: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Carrega dados dos arquivos na inicialização
        carregarDados();
        
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  Sistema de Gerenciamento de Oficina      ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        boolean rodando = true;
        
        while (rodando) {
            if (usuarioLogado == null) {
                exibirMenuLogin();
                if (!fazerLogin()) {
                    rodando = false;
                }
            } else {
                exibirMenuPrincipal();
                int opcao = lerInteiro();
                rodando = processarMenuPrincipal(opcao);
            }
        }
        
        // Salva dados ao sair do sistema
        salvarDados();
        System.out.println("\n✓ Sistema encerrado. Dados salvos com sucesso!");
        scanner.close();
    }

    // ========== CARREGAMENTO E SALVAMENTO DE DADOS ==========
    /**
     * Carrega dados persistidos dos arquivos
     */
    private static void carregarDados() {
        try {
            // Carrega dados de todas as entidades
            List<Funcionario> funcionarios = funcController.listarFuncionarios();
            List<Cliente> clientes = clienteController.listarClientes();
            List<Veiculo> veiculos = veiculoController.listarVeiculos();
            List<OrdemServico> ordens = ordemController.listarOrdensServico();
            
            int totalCarregado = funcionarios.size() + clientes.size() + 
                                veiculos.size() + ordens.size();
            
            System.out.println("✓ Dados carregados do sistema de persistência:");
            System.out.println("  - Funcionários: " + funcionarios.size());
            System.out.println("  - Clientes: " + clientes.size());
            System.out.println("  - Veículos: " + veiculos.size());
            System.out.println("  - Ordens de Serviço: " + ordens.size());
            System.out.println("  Total: " + totalCarregado + " registros\n");
            
            // Se não houver dados, cria usuário de login
            if (totalCarregado == 0) {
                System.out.println("! Criando usuário padrão para login...\n");
                inicializarDadosPadroes();
            }
        } catch (Exception e) {
            System.err.println("⚠ Erro ao carregar dados: " + e.getMessage());
            System.out.println("Criando usuário padrão para login...\n");
            inicializarDadosPadroes();
        }
    }
    
    /**
     * Salva todos os dados persistidos nos arquivos
     */
    private static void salvarDados() {
        try {
            // Os dados são salvos automaticamente em cada operação
            // Este método pode ser usado para garantir salvamento explícito
            System.out.println("\n✓ Sincronizando dados com persistência...");
        } catch (Exception e) {
            System.err.println("⚠ Erro ao salvar dados: " + e.getMessage());
        }
    }

    // ========== INICIALIZAÇÃO ==========
    /**
     * Inicializa o sistema com dados mínimos (apenas usuário para login)
     */
    private static void inicializarDadosPadroes() {
        try {
            // Criar apenas um gerente para login
            Funcionario gerente = new Funcionario("Carlos Silva", "12345678901", 
                "carlos@oficina.com", "senha123", new Date(), true);
            funcController.criarFuncionario(gerente);
            
            System.out.println("✓ Usuário de login criado com sucesso!");
            System.out.println("  Email: carlos@oficina.com");
            System.out.println("  Senha: senha123\n");
        } catch (Exception e) {
            System.err.println("✗ Erro ao inicializar usuário: " + e.getMessage());
        }
        /*
        // DADOS ADICIONAIS DESATIVADOS - Descomente para carregar mais dados
        try {
            // Criar funcionários operacionais
            Funcionario op1 = new Funcionario("João Santos", "98765432101", 
                "joao@oficina.com", "senha456", new Date(), false);
            Funcionario op2 = new Funcionario("Maria Oliveira", "55544433322", 
                "maria@oficina.com", "senha789", new Date(), false);
            
            funcController.criarFuncionario(op1);
            funcController.criarFuncionario(op2);

            // Criar clientes
            Cliente cliente1 = new Cliente("Pedro Oliveira", "11122233344", 
                "11-99999-8888", "Rua A, 123", "pedro@email.com");
            Cliente cliente2 = new Cliente("Ana Costa", "22233344455", 
                "11-88888-7777", "Rua B, 456", "ana@email.com");
            
            clienteController.criarCliente(cliente1);
            clienteController.criarCliente(cliente2);

            // Buscar clientes criados com IDs corretos
            cliente1 = clienteController.buscarClientePorId(cliente1.getIdCliente());
            cliente2 = clienteController.buscarClientePorId(cliente2.getIdCliente());

            // Criar veículos via OficinaService
            try {
                veiculoController.criarVeiculo("11122233344", "ABC-1234", "Fusca", "Volkswagen", 2015, "Azul");
                veiculoController.criarVeiculo("11122233344", "DEF-5678", "Gol", "Volkswagen", 2018, "Branco");
                veiculoController.criarVeiculo("22233344455", "GHI-9012", "Civic", "Honda", 2020, "Preto");
            } catch (Exception e) {
                System.err.println("Erro ao criar veículos: " + e.getMessage());
            }

            // Buscar veículos criados
            Veiculo veiculo1 = veiculoController.buscarVeiculoPorPlaca("ABC-1234");
            Veiculo veiculo2 = veiculoController.buscarVeiculoPorPlaca("DEF-5678");

            // Criar ordens de serviço via OficinaService
            try {
                ordemController.criarOrdemServico("11122233344", "ABC-1234", gerente.getIdUsuario(), "Trocar óleo e filtro", 100.0);
                ordemController.criarOrdemServico("22233344455", "GHI-9012", op1.getIdUsuario(), "Revisar freios", 200.0);
            } catch (Exception e) {
                System.err.println("Erro ao criar ordens de serviço: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("✗ Erro ao inicializar dados adicionais: " + e.getMessage());
        }
        */
    }

    // ========== MENUS ==========
    private static void exibirMenuLogin() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║           MENU DE LOGIN                    ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        System.out.println("1. Fazer Login");
        System.out.println("2. Sair");
        System.out.print("\nEscolha uma opção: ");
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      MENU PRINCIPAL - Bem-vindo!           ║");
        System.out.println("║  Usuário: " + String.format("%-31s", usuarioLogado.getNome()) + "║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Veículos");
        System.out.println("3. Gerenciar Funcionários");
        System.out.println("4. Gerenciar Ordens de Serviço");
        System.out.println("5. Fazer Logout");
        System.out.print("\nEscolha uma opção: ");
    }

    private static void exibirMenuClientes() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║       GERENCIAR CLIENTES                   ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        System.out.println("1. Adicionar Cliente");
        System.out.println("2. Listar Clientes");
        System.out.println("3. Buscar Cliente por CPF");
        System.out.println("4. Atualizar Cliente");
        System.out.println("5. Remover Cliente");
        System.out.println("6. Voltar ao Menu Principal");
        System.out.print("\nEscolha uma opção: ");
    }

    private static void exibirMenuVeiculos() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║       GERENCIAR VEÍCULOS                   ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        System.out.println("1. Adicionar Veículo");
        System.out.println("2. Listar Veículos");
        System.out.println("3. Buscar Veículo por Placa");
        System.out.println("4. Atualizar Veículo");
        System.out.println("5. Remover Veículo");
        System.out.println("6. Voltar ao Menu Principal");
        System.out.print("\nEscolha uma opção: ");
    }

    private static void exibirMenuFuncionarios() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      GERENCIAR FUNCIONÁRIOS                ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        System.out.println("1. Adicionar Funcionário");
        System.out.println("2. Listar Funcionários");
        System.out.println("3. Atualizar Funcionário");
        System.out.println("4. Remover Funcionário");
        System.out.println("5. Voltar ao Menu Principal");
        System.out.print("\nEscolha uma opção: ");
    }

    private static void exibirMenuOrdens() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║     GERENCIAR ORDENS DE SERVIÇO            ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        System.out.println("1. Criar Ordem de Serviço");
        System.out.println("2. Listar Ordens de Serviço");
        System.out.println("3. Atualizar Ordem de Serviço");
        System.out.println("4. Remover Ordem de Serviço");
        System.out.println("5. Voltar ao Menu Principal");
        System.out.print("\nEscolha uma opção: ");
    }

    // ========== PROCESSAMENTO DE MENUS ==========
    private static boolean fazerLogin() {
        int opcao = lerInteiro();
        
        if (opcao == 1) {
            System.out.print("\nDigite seu email: ");
            String email = scanner.nextLine();
            System.out.print("Digite sua senha: ");
            String senha = scanner.nextLine();
            
            Funcionario func = funcController.validarLogin(email, senha);
            if (func != null) {
                usuarioLogado = func;
                System.out.println("\n✓ Login realizado com sucesso! Bem-vindo, " + func.getNome());
                return true;
            }
            System.out.println("\n✗ Email ou senha incorretos!");
            return true;
        } else if (opcao == 2) {
            return false;
        }
        return true;
    }

    private static boolean processarMenuPrincipal(int opcao) {
        switch (opcao) {
            case 1: gerenciarClientes(); break;
            case 2: gerenciarVeiculos(); break;
            case 3: gerenciarFuncionarios(); break;
            case 4: gerenciarOrdens(); break;
            case 5: 
                usuarioLogado = null;
                System.out.println("\n✓ Logout realizado com sucesso!");
                return true;
            default: System.out.println("\n✗ Opção inválida!");
        }
        return true;
    }

    // ========== GERENCIAMENTO DE CLIENTES ==========
    private static void gerenciarClientes() {
        boolean voltar = false;
        
        while (!voltar) {
            exibirMenuClientes();
            int opcao = lerInteiro();
            
            switch (opcao) {
                case 1: adicionarCliente(); break;
                case 2: listarClientes(); break;
                case 3: buscarClientePorCpf(); break;
                case 4: atualizarCliente(); break;
                case 5: removerCliente(); break;
                case 6: voltar = true; break;
                default: System.out.println("\n✗ Opção inválida!");
            }
        }
    }

    private static void adicionarCliente() {
        System.out.println("\n--- Adicionar Cliente ---");
        System.out.print("ID: ");
        int id = lerInteiro();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        Cliente cliente = new Cliente(id, nome, cpf, telefone, endereco, email);
        clienteController.criarCliente(cliente);
        System.out.println("✓ Cliente adicionado com sucesso!");
    }

    private static void listarClientes() {
        System.out.println("\n--- Clientes Cadastrados ---");
        List<Cliente> clientes = clienteController.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : clientes) {
                System.out.println("  • " + c.getNome() + " (CPF: " + c.getCpf() + ")");
            }
        }
    }

    private static void buscarClientePorCpf() {
        System.out.print("\nDigite o CPF: ");
        String cpf = scanner.nextLine();
        Cliente cliente = clienteController.buscarClientePorCpf(cpf);
        if (cliente != null) {
            System.out.println("✓ Cliente encontrado:");
            System.out.println("  Nome: " + cliente.getNome());
            System.out.println("  CPF: " + cliente.getCpf());
            System.out.println("  Telefone: " + cliente.getTelefone());
            System.out.println("  Email: " + cliente.getEmail());
        } else {
            System.out.println("✗ Cliente não encontrado!");
        }
    }

    private static void atualizarCliente() {
        System.out.print("\nDigite o CPF do cliente: ");
        String cpf = scanner.nextLine();
        Cliente cliente = clienteController.buscarClientePorCpf(cpf);
        
        if (cliente != null) {
            System.out.print("Novo nome (atual: " + cliente.getNome() + "): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) cliente.setNome(nome);
            
            System.out.print("Novo telefone (atual: " + cliente.getTelefone() + "): ");
            String telefone = scanner.nextLine();
            if (!telefone.isEmpty()) cliente.setTelefone(telefone);
            
            System.out.print("Novo endereço (atual: " + cliente.getEndereco() + "): ");
            String endereco = scanner.nextLine();
            if (!endereco.isEmpty()) cliente.setEndereco(endereco);
            
            System.out.print("Novo email (atual: " + cliente.getEmail() + "): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) cliente.setEmail(email);
            
            System.out.println("✓ Cliente atualizado com sucesso!");
        } else {
            System.out.println("✗ Cliente não encontrado!");
        }
    }

    private static void removerCliente() {
        System.out.print("\nDigite o ID do cliente: ");
        int id = lerInteiro();
        clienteController.removerCliente(id);
        System.out.println("✓ Cliente removido com sucesso!");
    }

    // ========== GERENCIAMENTO DE VEÍCULOS ==========
    private static void gerenciarVeiculos() {
        boolean voltar = false;
        
        while (!voltar) {
            exibirMenuVeiculos();
            int opcao = lerInteiro();
            
            switch (opcao) {
                case 1: adicionarVeiculo(); break;
                case 2: listarVeiculos(); break;
                case 3: buscarVeiculoPorPlaca(); break;
                case 4: atualizarVeiculo(); break;
                case 5: removerVeiculo(); break;
                case 6: voltar = true; break;
                default: System.out.println("\n✗ Opção inválida!");
            }
        }
    }

    private static void adicionarVeiculo() {
        System.out.println("\n--- Adicionar Veículo ---");
        System.out.print("ID: ");
        int id = lerInteiro();
        
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
        int ano = lerInteiro();
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
            if (veiculoController.criarVeiculo(cpfCliente, placa, modelo, marca, ano, cor)) {
                System.out.println("✓ Veículo adicionado com sucesso!");
            } else {
                System.out.println("✗ Falha ao adicionar veículo!");
            }
        } catch (Exception e) {
            System.out.println("✗ ERRO: " + e.getMessage());
        }
    }

    private static void listarVeiculos() {
        System.out.println("\n--- Veículos Cadastrados ---");
        List<Veiculo> veiculos = veiculoController.listarVeiculos();
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            for (Veiculo v : veiculos) {
                System.out.println("  • " + v.getMarca() + " " + v.getModelo() + " (Placa: " + v.getPlaca() + ")");
            }
        }
    }

    private static void buscarVeiculoPorPlaca() {
        System.out.print("\nDigite a placa: ");
        String placa = scanner.nextLine();
        Veiculo veiculo = veiculoController.buscarVeiculoPorPlaca(placa);
        if (veiculo != null) {
            System.out.println("✓ Veículo encontrado:");
            System.out.println("  Marca: " + veiculo.getMarca());
            System.out.println("  Modelo: " + veiculo.getModelo());
            System.out.println("  Placa: " + veiculo.getPlaca());
            System.out.println("  Ano: " + veiculo.getAno());
            System.out.println("  Cor: " + veiculo.getCor());
        } else {
            System.out.println("✗ Veículo não encontrado!");
        }
    }

    private static void atualizarVeiculo() {
        System.out.print("\nDigite a placa do veículo: ");
        String placa = scanner.nextLine();
        Veiculo veiculo = veiculoController.buscarVeiculoPorPlaca(placa);
        
        if (veiculo != null) {
            System.out.print("Novo modelo (atual: " + veiculo.getModelo() + "): ");
            String modelo = scanner.nextLine();
            if (!modelo.isEmpty()) veiculo.setModelo(modelo);
            
            System.out.print("Novo ano (atual: " + veiculo.getAno() + "): ");
            String anoStr = scanner.nextLine();
            if (!anoStr.isEmpty()) veiculo.setAno(Integer.parseInt(anoStr));
            
            System.out.print("Nova cor (atual: " + veiculo.getCor() + "): ");
            String cor = scanner.nextLine();
            if (!cor.isEmpty()) veiculo.setCor(cor);
            
            System.out.println("✓ Veículo atualizado com sucesso!");
        } else {
            System.out.println("✗ Veículo não encontrado!");
        }
    }

    private static void removerVeiculo() {
        System.out.print("\nDigite o ID do veículo: ");
        int id = lerInteiro();
        veiculoController.removerVeiculo(id);
        System.out.println("✓ Veículo removido com sucesso!");
    }

    // ========== GERENCIAMENTO DE FUNCIONÁRIOS ==========
    private static void gerenciarFuncionarios() {
        if (!usuarioLogado.isEhGerente()) {
            System.out.println("\n✗ Acesso negado! Apenas gerentes podem gerenciar funcionários.");
            return;
        }
        
        boolean voltar = false;
        
        while (!voltar) {
            exibirMenuFuncionarios();
            int opcao = lerInteiro();
            
            switch (opcao) {
                case 1: adicionarFuncionario(); break;
                case 2: listarFuncionarios(); break;
                case 3: atualizarFuncionario(); break;
                case 4: removerFuncionario(); break;
                case 5: voltar = true; break;
                default: System.out.println("\n✗ Opção inválida!");
            }
        }
    }

    private static void adicionarFuncionario() {
        System.out.println("\n--- Adicionar Funcionário ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("É gerente? (S/N): ");
        String isGerente = scanner.nextLine();
        boolean ehGerente = isGerente.equalsIgnoreCase("S");
        
        Funcionario func = new Funcionario(nome, cpf, email, senha, new Date(), ehGerente);
        funcController.criarFuncionario(func);
        System.out.println("✓ Funcionário adicionado com sucesso!");
    }

    private static void listarFuncionarios() {
        System.out.println("\n--- Funcionários Cadastrados ---");
        List<Funcionario> funcionarios = funcController.listarFuncionarios();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
        } else {
            for (Funcionario f : funcionarios) {
                System.out.println("  • " + f.getNome() + " - Gerente: " + 
                    (f.isEhGerente() ? "Sim" : "Não"));
            }
        }
    }

    private static void atualizarFuncionario() {
        System.out.print("\nDigite o CPF do funcionário: ");
        String cpf = scanner.nextLine();
        List<Funcionario> funcionarios = funcController.listarFuncionarios();
        Funcionario func = null;
        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) {
                func = f;
                break;
            }
        }
        
        if (func != null) {
            System.out.print("Novo nome (atual: " + func.getNome() + "): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) func.setNome(nome);
            System.out.println("✓ Funcionário atualizado com sucesso!");
        } else {
            System.out.println("✗ Funcionário não encontrado!");
        }
    }

    private static void removerFuncionario() {
        System.out.print("\nDigite o ID do funcionário: ");
        int id = lerInteiro();
        funcController.removerFuncionario(id);
        System.out.println("✓ Funcionário removido com sucesso!");
    }

    // ========== GERENCIAMENTO DE ORDENS ==========
    private static void gerenciarOrdens() {
        boolean voltar = false;
        
        while (!voltar) {
            exibirMenuOrdens();
            int opcao = lerInteiro();
            
            switch (opcao) {
                case 1: criarOrdemServico(); break;
                case 2: listarOrdensServico(); break;
                case 3: atualizarOrdemServico(); break;
                case 4: removerOrdemServico(); break;
                case 5: voltar = true; break;
                default: System.out.println("\n✗ Opção inválida!");
            }
        }
    }

    private static void criarOrdemServico() {
        System.out.println("\n--- Criar Ordem de Serviço ---");
        System.out.print("ID da Ordem: ");
        int id = lerInteiro();
        
        System.out.print("CPF do Cliente: ");
        String cpfCliente = scanner.nextLine().trim();
        if (cpfCliente.isEmpty()) {
            System.out.println("✗ CPF não pode estar vazio!");
            return;
        }
        Cliente cliente = clienteController.buscarClientePorCpf(cpfCliente);
        if (cliente == null) {
            System.out.println("✗ Cliente não encontrado!");
            return;
        }
        
        System.out.print("Placa do Veículo: ");
        String placa = scanner.nextLine().trim();
        if (placa.isEmpty()) {
            System.out.println("✗ Placa não pode estar vazia!");
            return;
        }
        Veiculo veiculo = veiculoController.buscarVeiculoPorPlaca(placa);
        if (veiculo == null) {
            System.out.println("✗ Veículo não encontrado!");
            return;
        }
        
        System.out.print("Descrição do Problema: ");
        String descricao = scanner.nextLine().trim();
        if (descricao.isEmpty()) {
            System.out.println("✗ Descrição não pode estar vazia!");
            return;
        }
        
        System.out.print("Valor da Mão de Obra: R$ ");
        double valorMaoObra = lerDouble();
        if (valorMaoObra < 0) {
            System.out.println("✗ Valor não pode ser negativo!");
            return;
        }
        
        System.out.print("Valor das Peças: R$ ");
        double valorPecas = lerDouble();
        if (valorPecas < 0) {
            System.out.println("✗ Valor não pode ser negativo!");
            return;
        }
        
        try {
            OrdemServico ordem = new OrdemServico(id, cliente, veiculo, usuarioLogado, descricao, valorMaoObra);
            ordem.setValorPecas(valorPecas);
            ordem.calcularValorTotal();
            
            ordemController.criarOrdemServico(ordem);
            System.out.println("✓ Ordem de Serviço criada com sucesso!");
            System.out.printf("  Valor Total: R$ %.2f\n", ordem.getValorTotal());
        } catch (Exception e) {
            System.out.println("✗ Erro ao criar ordem: " + e.getMessage());
        }
    }

    private static void listarOrdensServico() {
        System.out.println("\n--- Ordens de Serviço ---");
        List<OrdemServico> ordens = ordemController.listarOrdensServico();
        if (ordens.isEmpty()) {
            System.out.println("Nenhuma ordem cadastrada.");
        } else {
            for (OrdemServico o : ordens) {
                System.out.printf("  • Ordem #%d - Cliente: %s - Status: %s - Total: R$ %.2f\n",
                    o.getIdOrdem(), o.getCliente().getNome(), o.getStatus(), o.getValorTotal());
            }
        }
    }

    private static void atualizarOrdemServico() {
        System.out.print("\nDigite o ID da ordem: ");
        int id = lerInteiro();
        OrdemServico ordem = ordemController.buscarOrdemPorId(id);
        
        if (ordem != null) {
            System.out.println("\nAtualizações disponíveis:");
            System.out.println("1. Atualizar valores");
            System.out.print("Escolha: ");
            int opcao = lerInteiro();
            
            if (opcao == 1) {
                System.out.print("Novo valor mão de obra: R$ ");
                double valorMao = lerDouble();
                System.out.print("Novo valor peças: R$ ");
                double valorPecas = lerDouble();
                ordem.setValorMaoObra(valorMao);
                ordem.setValorPecas(valorPecas);
                ordem.calcularValorTotal();
                System.out.printf("✓ Valores atualizados! Novo total: R$ %.2f\n", ordem.getValorTotal());
            }
        } else {
            System.out.println("✗ Ordem não encontrada!");
        }
    }

    private static void removerOrdemServico() {
        System.out.print("\nDigite o ID da ordem: ");
        int id = lerInteiro();
        ordemController.removerOrdemServico(id);
        System.out.println("✓ Ordem removida com sucesso!");
    }

    // ========== UTILITÁRIOS ==========
    private static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
