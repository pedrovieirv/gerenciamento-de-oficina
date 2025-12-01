Descrição:

O sistema de Gerenciamento de Oficina é projetado para automatizar e
otimizar as operações essenciais de uma oficina mecânica, garantindo o controle
eficiente de Ordens de Serviço, veículos, clientes e funcionários.
● Funcionários e Cargos: Os funcionários são diferenciados no sistema por uma
classificação de "Gerente" ou "Não-Gerente".

● Permissões do Gerente: Apenas o Gerente possui permissão administrativa
para gerenciar (criar, editar, listar e desativar) o cadastro de outros
funcionários.

● Permissões Operacionais (Não-Gerentes): Funcionários de qualquer
categoria (incluindo os não-gerentes) são responsáveis pela operação da
oficina. Eles podem gerenciar integralmente os cadastros de Clientes e
Veículos, bem como criar e atualizar Ordens de Serviço.

● Clientes: O cliente deve estar cadastrado para solicitar serviços. Ele possui
permissão para visualizar o histórico de serviços realizados em seus veículos
e consultar o status atual de suas ordens.

● Ordem de Serviço (OS): Representa o trabalho realizado. O valor total da OS
é calculado somando-se o valor da mão de obra com o valor das peças
utilizadas. O método calcularValorTotal() atualiza o campo valorTotal
adicionando o valor da mão de obra ao valor das peças, garantindo que o
total reflita corretamente todos os custos envolvidos na execução do serviço.
