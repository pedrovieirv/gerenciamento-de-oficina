# Gerenciamento de Oficina 🚙🚗

Sistema de Gerenciamento de Oficina desenvolvido em Java, que conecta oficinas, veículos, clientes e funcionários em uma plataforma unificada, otimizando as operações essenciais e garantindo o controle eficiente de Ordens de Serviço.

## 📖 Introdução

O Sistema de Gerenciamento de Oficina é uma solução inovadora e eficiente, desenvolvida para atender às necessidades de oficinas mecânicas. Com o objetivo de automatizar e otimizar as operações essenciais, o sistema proporciona um controle completo sobre as Ordens de Serviço, veículos, clientes e funcionários, garantindo maior agilidade, precisão e organização no dia a dia da oficina.


## 🏗️ Arquitetura do Sistema

<img width="394" height="528" alt="image" src="https://github.com/user-attachments/assets/51a061f4-494a-4105-b7e0-b5d12bd0e1a1" />




### 🔧 Funcionalidades por Módulo

| **Módulo** | **Principais Funcionalidades** |
|-----------|--------------------------------|
| **Cliente** | • Cadastrar cliente<br>• Consultar por ID/CPF<br>• Editar dados<br>• Excluir cliente<br>• Listar clientes<br>• Ver histórico de OS |
| **Veículo** | • Cadastrar veículo vinculado a cliente<br>• Consultar veículo<br>• Editar informações<br>• Excluir veículo<br>• Listar veículos<br>• Listar veículos de um cliente |
| **Ordem de Serviço (OS)** | • Abrir nova OS<br>• Atualizar status (análise → execução → concluída → entregue)<br>• Registrar valores e materiais<br>• Associar cliente e veículo<br>• Listar e filtrar OS<br>• Consultar histórico |
| **Funcionário / Usuário** | • Cadastrar funcionário<br>• Login e autenticação<br>• Editar dados<br>• Controle de permissões<br>• Ativar/Desativar usuários |
| **Persistência (DAO)** | • Salvar e carregar dados em arquivos<br>• Atualizar arquivos após operações<br>• Garantir consistência entre entidades<br>• Tratamento básico de erros de leitura/escrita |
| **Interface (View/Main)** | • Menus organizados por módulo<br>• Entrada de dados via console<br>• Navegação entre operações<br>• Exibição de mensagens e relatórios |

## 🔧 Pré-requisitos

 Pré-requisitos

- **Java JDK** (versão 8 ou superior) instalado no seu computador
- Terminal/Prompt de comando

## Verificar se Java está instalado

Execute no terminal:

```bash
java -version
javac -version
```

Se aparecer uma mensagem com a versão do Java, está tudo certo!

---

## Opção 1: Usando o Script (Recomendado)

### Linux/Mac

1. Abra o terminal na pasta do projeto
2. Dê permissão de execução ao script:
   ```bash
   chmod +x executar.sh
   ```
3. Execute:
   ```bash
   ./executar.sh
   ```

### Windows

1. Abra o Command Prompt (cmd) na pasta do projeto
2. Execute:
   ```bash
   executar.bat
   ```

---

## Opção 2: Compilar Manualmente

### Passo 1: Criar o diretório de saída

```bash
mkdir -p bin
```

### Passo 2: Compilar os arquivos

Na pasta do projeto, execute (em uma linha):

```bash
javac -d bin src/usuario/Usuario.java src/usuario/UsuarioController.java src/usuario/UsuarioView.java src/cliente/Cliente.java src/cliente/ClienteController.java src/cliente/ClienteView.java src/veiculo/Veiculo.java src/veiculo/VeiculoController.java src/veiculo/VeiculoView.java src/funcionario/Funcionario.java src/funcionario/FuncionarioController.java src/funcionario/FuncionarioView.java src/ordemServico/StatusOrdemServico.java src/ordemServico/OrdemServico.java src/ordemServico/OrdemServicoController.java src/ordemServico/OrdemServicoView.java src/main/MainSystem.java
```


### Passo 3: Executar o programa

```bash
cd bin
java MainSystem
```

---

## Opção 3: Compilar Passo a Passo (Melhor para debug)

Se receber erros de compilação, compile cada módulo separadamente:

```bash
# Crie o diretório bin
mkdir -p bin

# Compile na ordem de dependência
javac -d bin src/usuario/Usuario.java
javac -d bin src/usuario/UsuarioController.java
javac -d bin src/usuario/UsuarioView.java
javac -d bin src/cliente/Cliente.java
javac -d bin src/cliente/ClienteController.java
javac -d bin src/cliente/ClienteView.java
javac -d bin src/veiculo/Veiculo.java
javac -d bin src/veiculo/VeiculoController.java
javac -d bin src/veiculo/VeiculoView.java
javac -d bin src/funcionario/Funcionario.java
javac -d bin src/funcionario/FuncionarioController.java
javac -d bin src/funcionario/FuncionarioView.java
javac -d bin src/ordemServico/StatusOrdemServico.java
javac -d bin src/ordemServico/OrdemServico.java
javac -d bin src/ordemServico/OrdemServicoController.java
javac -d bin src/ordemServico/OrdemServicoView.java
javac -d bin src/main/MainSystem.java

# Execute
cd bin
java MainSystem
```

---

## Usando o Programa

Ao iniciar o programa, você verá um menu de login.

### Credenciais Padrão (já cadastradas):

**Gerente:**
- Email: `carlos@oficina.com`
- Senha: `senha123`

**Funcionário 1:**
- Email: `joao@oficina.com`
- Senha: `senha456`

**Funcionário 2:**
- Email: `maria@oficina.com`
- Senha: `senha789`
  

### Funcionalidades Disponíveis:

- **Gerenciar Clientes**: Criar, listar, buscar, atualizar e deletar clientes
- **Gerenciar Veículos**: Criar, listar, buscar e atualizar veículos
- **Gerenciar Funcionários**: Criar, listar e atualizar funcionários (apenas gerente)
- **Gerenciar Ordens de Serviço**: Criar, listar, atualizar status e valores



## 👥 Colaboradores

| 💼 Integrante |
|---------------|
| 👨‍💻 **Gabriel de Menezes Sousa** |
| 👨‍💻 **Pedro Augusto Vieira da Silva** |
| 👨‍💻 **Brendo Duarte Bezerra** |
