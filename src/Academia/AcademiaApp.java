package src.Academia;

import java.util.ArrayList; // Importa a classe ArrayList para uso posterior na declaração de listas.
import java.util.Iterator; // Importa a classe Iterator para percorrer as listas.
import java.util.List; // Importa a classe List para criar listas de objetos.
import java.util.Scanner; // Importa a classe Scanner para entrada de dados do usuário.
import java.util.concurrent.atomic.AtomicInteger; // Importa a classe AtomicInteger para geração de IDs únicos.
import java.sql.Connection; // Importa a classe Connection para manipulação de conexões com banco de dados.
import java.sql.PreparedStatement; // Importa a classe PreparedStatement para preparar consultas SQL.
import java.sql.ResultSet; // Importa a classe ResultSet para armazenar resultados de consultas SQL.
import java.sql.SQLException; // Importa a classe SQLException para lidar com exceções relacionadas ao banco de dados.

import conexao.Conexao; // Importa a classe Conexao, presumivelmente para estabelecer uma conexão com um banco de dados.

public class AcademiaApp { // Declaração da classe AcademiaApp.
    private static final Scanner scanner = new Scanner(System.in); // Cria um objeto Scanner para leitura de entrada do teclado.
    private static final Connection conn = Conexao.getConexao(); // Obtém uma conexão com o banco de dados usando a classe Conexao.

    // Declaração de várias listas para armazenar diferentes tipos de objetos (Aluno, Instrutor, AlunoMembro, Treino).
    private static List<Aluno> alunos = new ArrayList<>();
    private static List<Instrutor> instrutores = new ArrayList<>();
    private static List<AlunoMembro> alunosMembros = new ArrayList<>();
    private static Treino treino = new Treino();

    // Cria objetos AtomicInteger para gerar IDs únicos para Aluno e Instrutor.
    private static AtomicInteger alunoIdGenerator = new AtomicInteger(1);
    private static AtomicInteger instrutorIdGenerator = new AtomicInteger(1);


    // MÉTODO PARA CADASTRAR ALUNO

    public static void cadastrarAluno(Connection connection) {
        while (true) {
            System.out.println("\nCadastrar Aluno:");
            String nome;
    
            // Loop para garantir que o nome do aluno seja válido
            while (true) {
                System.out.print("Nome do aluno: ");
                nome = scanner.nextLine();
    
                // Verifica se o nome contém apenas letras e espaços em branco
                if (!nome.matches("^[a-zA-Z\\s]+$")) {
                    System.out.println("O nome do aluno não pode conter números ou caracteres especiais. Tente novamente.");
                } else {
                    // Formata o nome com a primeira letra em maiúscula e o restante em minúscula
                    nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
                    break;
                }
            }
    
            int idade;
            boolean entradaValida = false;
    
            // Loop para garantir que a idade do aluno seja um número inteiro válido
            do {
                System.out.print("Idade do aluno: ");
                idade = 0;
    
                try {
                    idade = scanner.nextInt();
                    scanner.nextLine();
                    entradaValida = true;
    
                    // Verifica se a idade é maior ou igual a zero
                    if (idade < 0) {
                        System.out.println("Idade não pode ser negativa. Tente novamente.");
                        entradaValida = false;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Idade inválida. Certifique-se de inserir um número inteiro.");
                    scanner.nextLine();
                }
            } while (!entradaValida);
    
            boolean temProblemaDeSaude;
    
            // Loop para garantir que a resposta sobre problemas de saúde seja válida
            do {
                System.out.print("Problema de saúde (Sim/Não): ");
                String respostaProblemaSaude = scanner.nextLine();
    
                if (respostaProblemaSaude.equalsIgnoreCase("Sim")) {
                    temProblemaDeSaude = true;
                } else if (respostaProblemaSaude.equalsIgnoreCase("Não")) {
                    temProblemaDeSaude = false;
                } else {
                    System.out.println("Resposta inválida. Digite 'Sim' ou 'Não'.");
                    temProblemaDeSaude = false;
                }
            } while (!temProblemaDeSaude && !temProblemaDeSaude);
    
            String respostaSexo;
    
            // Loop para garantir que a resposta sobre o sexo seja válida
            do {
                System.out.print("Sexo (M/F): ");
                respostaSexo = scanner.nextLine();
    
                if (respostaSexo.equalsIgnoreCase("M")) {
                    respostaSexo = "Masculino";
                } else if (respostaSexo.equalsIgnoreCase("F")) {
                    respostaSexo = "Feminino";
                } else {
                    System.out.println("Resposta inválida. Digite 'M' para homem ou 'F' para mulher.");
                }
            } while (!respostaSexo.equalsIgnoreCase("Masculino") && !respostaSexo.equalsIgnoreCase("Feminino"));
    
            String tipoPagamento;
    
            // Loop para garantir que o tipo de pagamento seja válido
            do {
                System.out.print("Tipo de pagamento (Mensal/Anual): ");
                tipoPagamento = scanner.nextLine();
    
                if (!tipoPagamento.equalsIgnoreCase("Mensal") && !tipoPagamento.equalsIgnoreCase("Anual")) {
                    System.out.println("Tipo de pagamento inválido. Digite 'Mensal' ou 'Anual'.");
                }
            } while (!tipoPagamento.equalsIgnoreCase("Mensal") && !tipoPagamento.equalsIgnoreCase("Anual"));
    
            // Converte o tipo de pagamento para o formato desejado
            if (tipoPagamento.equalsIgnoreCase("Mensal")) {
                tipoPagamento = "Pagamento Mensal";
            } else if (tipoPagamento.equalsIgnoreCase("Anual")) {
                tipoPagamento = "Pagamento Anual";
            }
    
            // Cria um objeto Aluno com as informações coletadas
            Aluno aluno = new Aluno(alunoIdGenerator.getAndIncrement(), nome, idade, temProblemaDeSaude, respostaSexo, tipoPagamento);
    
            // Define se o aluno é membro com base no tipo de pagamento
            if (tipoPagamento.equalsIgnoreCase("Pagamento Anual")) {
                aluno.setMembro(true);
            } else {
                aluno.setMembro(false);
            }
    
            try {
                // Prepara a consulta SQL para inserir o aluno no banco de dados
                String sql = "INSERT INTO Aluno (nome, idade, sexo, problema_saude, tipo_pagamento, membro) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                // Define os parâmetros da consulta com os valores do objeto Aluno
                preparedStatement.setString(1, aluno.getNome());
                preparedStatement.setInt(2, aluno.getIdade());
                preparedStatement.setString(3, aluno.getSexo());
                preparedStatement.setBoolean(4, aluno.isProblemaSaude());
                preparedStatement.setString(5, aluno.getTipoPagamento());
                preparedStatement.setBoolean(6, aluno.isMembro());
    
                // Executa a consulta e verifica o número de linhas afetadas
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("Aluno cadastrado com sucesso!");
                } else {
                    System.out.println("Falha ao cadastrar o aluno.");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao inserir aluno no banco de dados: " + e.getMessage());
            }
    
            // Pergunta se deseja cadastrar outro aluno
            System.out.print("Deseja cadastrar outro aluno? (S/N): ");
            String resposta = scanner.nextLine();
            if (!resposta.equalsIgnoreCase("S")) {
                break;
            }
        }
    }    
    
    //MÉTODO PARA EXIBIR A LISTA DE ALUNOS

    public static void listarAlunos(Connection connection) {
        // Exibe um cabeçalho indicando que a lista de alunos será exibida.
        System.out.println("\nLista de Alunos:");
    
        try {
            // Prepara uma consulta SQL para selecionar todos os registros da tabela "Aluno".
            String sql = "SELECT * FROM Aluno";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            // Executa a consulta e armazena o resultado em um objeto ResultSet.
            ResultSet resultSet = preparedStatement.executeQuery();
    
            // Verifica se o resultado da consulta está vazio.
            if (!resultSet.isBeforeFirst()) {
                System.out.println("A lista de alunos está vazia.");
            } else {
                // Itera através dos registros no ResultSet e exibe as informações de cada aluno.
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");
                    int idade = resultSet.getInt("idade");
                    String sexo = resultSet.getString("sexo");
                    boolean problemaSaude = resultSet.getBoolean("problema_saude");
                    String tipoPagamento = resultSet.getString("tipo_pagamento");
                    boolean membro = resultSet.getBoolean("membro");
    
                    // Exibe as informações do aluno.
                    System.out.println("ID: " + id);
                    System.out.println("Nome: " + nome);
                    System.out.println("Idade: " + idade);
                    System.out.println("Sexo: " + sexo);
                    System.out.println("Problema de Saúde: " + (problemaSaude ? "Sim" : "Não"));
                    System.out.println("Tipo de Pagamento: " + tipoPagamento);
                    System.out.println("Membro: " + (membro ? "Sim" : "Não"));
                    System.out.println(); // Linha em branco para separar os registros.
                }
            }
        } catch (SQLException e) {
            // Trata exceção em caso de erro na execução da consulta SQL.
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        }
    
        // Chama a função para aguardar a pressão da tecla Enter antes de continuar.
        pressioneEnterParaContinuar();
    }
    
    //MÉTODO PARA EDITAR UM ALUNO

    public static void editarAluno(Connection connection) {
    while (true) {
        // Exibe um cabeçalho indicando que a edição de aluno está em andamento.
        System.out.println("\nEditar Aluno:");

        int idAlunoEditar;
        boolean encontrado = false;

        while (true) {
            // Solicita ao usuário que insira o ID do aluno a ser editado ou 0 para sair.
            System.out.print("Digite o ID do aluno que deseja editar (ou 0 para sair): ");
            idAlunoEditar = scanner.nextInt();
            scanner.nextLine(); // Limpa o caractere de nova linha

            if (idAlunoEditar == 0) {
                return; // Sai da edição se o usuário inserir 0.
            }

            try {
                // Prepara uma consulta SQL para buscar o aluno com o ID fornecido.
                String sql = "SELECT * FROM Aluno WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idAlunoEditar);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Aluno encontrado no banco de dados
                    encontrado = true;
                    String nomeAtual = resultSet.getString("nome");
                    int idadeAtual = resultSet.getInt("idade");
                    boolean problemaSaudeAtual = resultSet.getBoolean("problema_saude");
                    String tipoPagamentoAtual = resultSet.getString("tipo_pagamento");

                    // Solicita ao usuário que insira novos valores ou mantenha os valores atuais.
                    System.out.print("Novo nome (" + nomeAtual + "): ");
                    String novoNome = scanner.nextLine();

                    // Valida se o novo nome contém apenas letras e espaços em branco.
                    if (!novoNome.matches("^[a-zA-Z\\s]+$")) {
                        System.out.println("O novo nome do aluno não pode conter números ou caracteres especiais.");
                        return;
                    }

                    // Formata o novo nome com a primeira letra em maiúscula e o restante em minúscula.
                    novoNome = novoNome.substring(0, 1).toUpperCase() + novoNome.substring(1).toLowerCase();

                    System.out.print("Nova idade (" + idadeAtual + "): ");
                    int novaIdade = 0;

                    try {
                        // Lê a nova idade, garantindo que seja um número inteiro válido.
                        novaIdade = scanner.nextInt();
                        scanner.nextLine(); // Limpa o caractere de nova linha
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Idade inválida. Certifique-se de inserir um número inteiro.");
                        return;
                    }

                    System.out.print("Problema de saúde (Sim/Não) [" + (problemaSaudeAtual ? "Sim" : "Não") + "]: ");
                    String novoProblemaSaude = scanner.nextLine();

                    // Valida se a resposta do problema de saúde é "Sim" ou "Não".
                    if (!novoProblemaSaude.equalsIgnoreCase("Sim") && !novoProblemaSaude.equalsIgnoreCase("Não")) {
                        System.out.println("Resposta inválida. Digite 'Sim' ou 'Não'.");
                        return;
                    }

                    System.out.print("Tipo de pagamento (Mensal/Anual) [" + tipoPagamentoAtual + "]: ");
                    String tipoPagamento = scanner.nextLine();

                    // Valida se o tipo de pagamento é "Mensal" ou "Anual".
                    if (!tipoPagamento.equalsIgnoreCase("Mensal") && !tipoPagamento.equalsIgnoreCase("Anual")) {
                        System.out.println("Tipo de pagamento inválido. Digite 'Mensal' ou 'Anual'.");
                        return;
                    }

                    // Verifica se os novos dados são diferentes dos dados atuais.
                    if (!novoNome.equalsIgnoreCase(nomeAtual) || novaIdade != idadeAtual
                            || !novoProblemaSaude.equalsIgnoreCase(problemaSaudeAtual ? "Sim" : "Não")
                            || !tipoPagamento.equalsIgnoreCase(tipoPagamentoAtual)) {

                        try {
                            // Prepara uma consulta SQL para atualizar os dados do aluno no banco de dados.
                            sql = "UPDATE Aluno SET nome = ?, idade = ?, problema_saude = ?, tipo_pagamento = ? WHERE id = ?";
                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setString(1, novoNome);
                            preparedStatement.setInt(2, novaIdade);
                            preparedStatement.setBoolean(3, novoProblemaSaude.equalsIgnoreCase("Sim"));
                            preparedStatement.setString(4, tipoPagamento);
                            preparedStatement.setInt(5, idAlunoEditar);

                            int rowsAffected = preparedStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Aluno editado com sucesso.");
                            } else {
                                System.out.println("Falha ao editar o aluno.");
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao editar aluno no banco de dados: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Nenhum dado novo fornecido. O aluno permanece inalterado.");
                    }

                    break; // Sai do loop se o aluno for encontrado e editado com sucesso
                } else {
                    System.out.println("Aluno não encontrado. Tente novamente.");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao buscar aluno no banco de dados: " + e.getMessage());
            }
        }

        // Pergunta se deseja editar outro aluno.
        System.out.print("Deseja editar outro aluno? (S/N): ");
        String resposta = scanner.nextLine();
        if (!resposta.equalsIgnoreCase("S")) {
            break; // Sai do loop de edição se a resposta não for "S".
        }
    }
}

    //MÉTODO PARA REMOVER(EXCLUIR) UM ALUNO

    public static void removerAluno(Connection connection) {
        System.out.println("\nRemover Aluno:");

        // Variáveis para armazenar o ID do aluno a ser excluído e se o aluno foi encontrado.
        int idAlunoExcluir;
        boolean encontrado = false;

        while (true) {
            // Solicita ao usuário que insira o ID do aluno a ser excluído ou 0 para sair.
            System.out.print("Digite o ID do aluno que deseja excluir (ou 0 para sair): ");
            idAlunoExcluir = scanner.nextInt();
            scanner.nextLine(); // Limpa o caractere de nova linha

            if (idAlunoExcluir == 0) {
                // Sai da remoção do aluno se o usuário inserir 0.
                System.out.println("Saindo da exclusão de aluno...");
                return;
            }

            try {
                // Prepara uma consulta SQL para excluir o aluno com o ID fornecido.
                String sql = "DELETE FROM Aluno WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idAlunoExcluir);

                // Executa a consulta SQL para excluir o aluno.
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Se a consulta afetou uma ou mais linhas, o aluno foi removido com sucesso.
                    System.out.println("Aluno removido com sucesso.");
                } else {
                    // Se nenhuma linha foi afetada, o aluno não foi encontrado ou houve uma falha na remoção.
                    System.out.println("Aluno não encontrado ou falha ao remover o aluno.");
                }
            } catch (SQLException e) {
                // Trata exceção em caso de erro na exclusão do aluno no banco de dados.
                System.out.println("Erro ao remover aluno do banco de dados: " + e.getMessage());
            }

            // Pergunta ao usuário se deseja remover outro aluno.
            System.out.print("Deseja remover outro aluno? (S para sim, N para não): ");
            String resposta = scanner.nextLine().trim().toUpperCase();

            if (!resposta.equals("S")) {
                // Sai do loop de remoção se o usuário não quiser remover outro aluno.
                System.out.println("Saindo da exclusão de aluno...");
                return;
            }
        }
    }

    //MÉTODO PARA ENCONTRAR UM ALUNO PELO ID

    public static void buscarAlunoPorId(Connection connection) {
        while (true) {
            // Exibe um cabeçalho indicando que a busca pelo aluno por ID está em andamento.
            System.out.println("\nBuscar Aluno por ID:");
            System.out.print("Digite o ID do aluno que deseja buscar (ou 0 para sair): ");
    
            // Solicita ao usuário que insira o ID do aluno a ser buscado.
            int idAluno = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
    
            if (idAluno == 0) {
                System.out.println("Saindo da busca por aluno...");
                break; // Sai da busca se o usuário escolher sair.
            }
    
            boolean encontrado = false;
    
            try {
                // Prepara uma consulta SQL para buscar o aluno pelo ID.
                String sql = "SELECT * FROM Aluno WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idAluno);
    
                // Executa a consulta SQL e armazena o resultado em um objeto ResultSet.
                ResultSet resultSet = preparedStatement.executeQuery();
    
                if (resultSet.next()) {
                    // Se um aluno com o ID fornecido for encontrado no banco de dados.
                    int id = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");
                    int idade = resultSet.getInt("idade");
                    String sexo = resultSet.getString("sexo");
                    boolean problemaSaude = resultSet.getBoolean("problema_saude");
                    String tipoPagamento = resultSet.getString("tipo_pagamento");
                    boolean membro = resultSet.getBoolean("membro");
    
                    // Cria um objeto Aluno com os dados do resultado da consulta.
                    Aluno aluno = new Aluno(id, nome, idade, problemaSaude, sexo, tipoPagamento);
                    aluno.setMembro(membro);
    
                    // Exibe as informações do aluno encontrado.
                    System.out.println("Aluno encontrado: " + aluno);
    
                    encontrado = true;
                }
            } catch (SQLException e) {
                System.out.println("Erro ao buscar aluno no banco de dados: " + e.getMessage());
            }
    
            if (!encontrado) {
                System.out.println("Nenhum aluno encontrado com o ID fornecido.");
            }
    
            // Pergunta ao usuário se deseja buscar outro aluno por ID.
            System.out.print("Deseja buscar outro aluno (S/N)? ");
            String resposta = scanner.nextLine().toUpperCase();
            if (!resposta.equals("S")) {
                System.out.println("Saindo da busca por aluno...");
                break; // Sai do loop de busca se o usuário não quiser buscar outro aluno.
            }
        }
    }

    //MÉTODO PARA CADASTRAR UM INSTRUTOR

    public static void cadastrarInstrutor(Connection connection) {
        while (true) {
            // Exibe um cabeçalho indicando que o cadastro de instrutor está em andamento.
            System.out.println("\nCadastrar Instrutor:");
            
            // Solicita ao usuário que insira o nome do instrutor.
            System.out.print("Nome do instrutor: ");
            String nome = scanner.nextLine();
    
            // Valida se o nome contém apenas letras e espaços em branco.
            if (!nome.matches("^[a-zA-Z\\s]+$")) {
                System.out.println("O nome do instrutor não pode conter números ou caracteres especiais.");
                continue; // Continua solicitando o nome até que seja válido.
            }
    
            // Formata o nome com a primeira letra em maiúscula e o restante em minúscula.
            nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
    
            String turnoTrabalho;
            do {
                // Solicita ao usuário que insira o turno de trabalho do instrutor (Manhã, Tarde ou Noite).
                System.out.print("Turno de trabalho do instrutor (Manhã, Tarde ou Noite): ");
                turnoTrabalho = scanner.nextLine().toLowerCase();
    
                // Valida se o turno de trabalho é válido (manhã, tarde ou noite).
                if (!turnoTrabalho.equals("manhã") && !turnoTrabalho.equals("tarde") && !turnoTrabalho.equals("noite")) {
                    System.out.println("Turno de trabalho inválido. Use 'manhã', 'tarde' ou 'noite'.");
                }
            } while (!turnoTrabalho.equals("manhã") && !turnoTrabalho.equals("tarde") && !turnoTrabalho.equals("noite"));
    
            try {
                // Prepara uma consulta SQL para inserir o instrutor no banco de dados.
                String sql = "INSERT INTO Instrutor (nome, turno_trabalho) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, turnoTrabalho);
    
                // Executa a consulta SQL para inserir o instrutor.
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("Instrutor cadastrado com sucesso!");
                } else {
                    System.out.println("Falha ao cadastrar o instrutor.");
                }
            } catch (SQLException e) {
                // Trata exceção em caso de erro ao inserir o instrutor no banco de dados.
                System.out.println("Erro ao inserir instrutor no banco de dados: " + e.getMessage());
            }
    
            // Pergunta ao usuário se deseja cadastrar outro instrutor.
            System.out.print("Deseja cadastrar outro instrutor? (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (!resposta.equals("S")) {
                break; // Sai do loop de cadastro se o usuário não quiser cadastrar outro instrutor.
            }
        }
    }
    
    //MÉTODO PARA EXIBIR A LISTA DE INSTRUTORES

    public static void listarInstrutores(Connection connection) {
        System.out.println("\nLista de Instrutores:");
    
        try {
            // Prepara uma consulta SQL para selecionar todos os instrutores no banco de dados.
            String sql = "SELECT * FROM Instrutor";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            // Verifica se o conjunto de resultados está vazio.
            if (!resultSet.isBeforeFirst()) {
                System.out.println("A lista de instrutores está vazia.");
            } else {
                // Se houver instrutores, itera pelos resultados e exibe suas informações.
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");
                    String turnoTrabalho = resultSet.getString("turno_trabalho");
    
                    // Exibe as informações de cada instrutor na lista.
                    System.out.println("ID: " + id);
                    System.out.println("Nome: " + nome);
                    System.out.println("Turno de Trabalho: " + turnoTrabalho);
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            // Trata exceção em caso de erro ao listar instrutores no banco de dados.
            System.out.println("Erro ao listar instrutores: " + e.getMessage());
        }
    
        // Aguarda que o usuário pressione Enter para continuar.
        pressioneEnterParaContinuar();
    }

    //MÉTODO PARA EDITAR UM INSTRUTOR

    public static void editarInstrutor(Connection connection) {
        while (true) {
            // Exibe um cabeçalho indicando que a edição do instrutor está em andamento.
            System.out.println("\nEditar Instrutor:");
            System.out.print("Digite o ID do instrutor que deseja editar (ou 0 para sair): ");
            
            // Solicita ao usuário que insira o ID do instrutor a ser editado.
            int idInstrutorEditar = scanner.nextInt();
            scanner.nextLine();
    
            if (idInstrutorEditar == 0) {
                return; // Sai da edição se o usuário escolher sair.
            }
    
            boolean encontrado = false;
    
            try {
                // Consulta o banco de dados para verificar se o instrutor com o ID fornecido existe.
                String checkSql = "SELECT id FROM Instrutor WHERE id = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkSql);
                checkStatement.setInt(1, idInstrutorEditar);
                ResultSet checkResult = checkStatement.executeQuery();
    
                if (checkResult.next()) {
                    // O instrutor com o ID fornecido existe no banco de dados.
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
    
                    // Valida se o novo nome contém apenas letras e espaços em branco.
                    if (!novoNome.matches("^[a-zA-Z\\s]+$")) {
                        System.out.println("O novo nome do instrutor não pode conter números ou caracteres especiais.");
                        continue; // Continua pedindo o novo nome.
                    }
    
                    // Formata o novo nome com a primeira letra em maiúscula e o restante em minúscula.
                    novoNome = novoNome.substring(0, 1).toUpperCase() + novoNome.substring(1).toLowerCase();
    
                    System.out.print("Novo turno de trabalho: ");
                    String novoTurnoTrabalho = scanner.nextLine().toLowerCase();
    
                    // Valida se o novo turno de trabalho é válido (manhã, tarde ou noite).
                    if (!novoTurnoTrabalho.equals("manhã") && !novoTurnoTrabalho.equals("tarde") && !novoTurnoTrabalho.equals("noite")) {
                        System.out.println("Turno de trabalho inválido. Use 'manhã', 'tarde' ou 'noite'.");
                        continue; // Continua pedindo o novo turno de trabalho.
                    }
    
                    // Atualiza as informações do instrutor no banco de dados.
                    String updateSql = "UPDATE Instrutor SET nome = ?, turno_trabalho = ? WHERE id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                    updateStatement.setString(1, novoNome);
                    updateStatement.setString(2, novoTurnoTrabalho);
                    updateStatement.setInt(3, idInstrutorEditar);
    
                    int rowsAffected = updateStatement.executeUpdate();

                        // Verifica quantas linhas foram afetadas pela operação de atualização no banco de dados.
                        if (rowsAffected > 0) {
                            // Se pelo menos uma linha foi afetada, a operação foi bem-sucedida.
                            System.out.println("Instrutor editado com sucesso.");
                        } else {
                            // Caso contrário, se nenhuma linha foi afetada, a operação falhou.
                            System.out.println("Falha ao editar o instrutor.");
                        }
    
                    encontrado = true;
                } else {
                    System.out.println("Instrutor não encontrado.");
                }
            } catch (SQLException e) {
                // Trata exceção em caso de erro ao atualizar o instrutor no banco de dados.
                System.out.println("Erro ao atualizar instrutor no banco de dados: " + e.getMessage());
            }
    
            if (!encontrado) {
                // Se a variável "encontrado" for falsa, isso significa que o instrutor não foi encontrado no banco de dados.
                System.out.println("Instrutor não encontrado.");
            }
        }
    }
    
    //MÉTODO PARA REMOVER(EXCLUIR) UM INSTRUTOR

    public static void excluirInstrutor(Connection connection) {
        while (true) {
            // Exibe um cabeçalho indicando que a exclusão do instrutor está em andamento.
            System.out.println("\nExcluir Instrutor:");
            System.out.print("Digite o ID do instrutor que deseja excluir (ou 0 para sair): ");
            
            // Solicita ao usuário que insira o ID do instrutor a ser excluído.
            int idInstrutorExcluir = scanner.nextInt();
            scanner.nextLine();
    
            if (idInstrutorExcluir == 0) {
                System.out.println("Saindo da exclusão de instrutor...");
                return; // Sai da exclusão se o usuário escolher sair.
            }
    
            boolean encontrado = false;
    
            try {
                // Prepara uma consulta SQL para excluir o instrutor com o ID fornecido.
                String sql = "DELETE FROM Instrutor WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idInstrutorExcluir);
    
                // Executa a operação de exclusão no banco de dados.
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    // Se pelo menos uma linha foi afetada, a exclusão foi bem-sucedida.
                    System.out.println("Instrutor removido com sucesso.");
                } else {
                    // Caso contrário, se nenhuma linha foi afetada, a operação falhou.
                    System.out.println("Falha ao remover o instrutor. Certifique-se de que o ID seja válido.");
                }
            } catch (SQLException e) {
                // Trata exceção em caso de erro ao excluir o instrutor no banco de dados.
                System.out.println("Erro ao excluir instrutor no banco de dados: " + e.getMessage());
            }
    
            // Pergunta ao usuário se deseja excluir outro instrutor.
            System.out.print("Deseja excluir outro instrutor? (S para sim, N para não): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (!resposta.equals("S")) {
                System.out.println("Saindo da exclusão de instrutor...");
                return; // Sai do loop de exclusão se o usuário não quiser excluir outro instrutor.
            }
    
        }
    }

    //MÉTODO PARA ENCONTRAR UM INSTRUTOR PELO ID
    
    public static void buscarInstrutorPorId(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Exibe um cabeçalho indicando que a busca pelo instrutor por ID está em andamento.
            System.out.println("\nBuscar Instrutor por ID:");
            System.out.print("Digite o ID do instrutor que deseja buscar (ou 0 para sair): ");
    
            // Solicita ao usuário que insira o ID do instrutor a ser buscado.
            int idInstrutor = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
    
            if (idInstrutor == 0) {
                System.out.println("Saindo da busca por instrutor...");
                break; // Sai da busca se o usuário escolher sair.
            }
    
            boolean encontrado = false;
    
            try {
                // Prepara uma consulta SQL para buscar um instrutor com o ID fornecido.
                String sql = "SELECT * FROM Instrutor WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idInstrutor);
                ResultSet resultSet = preparedStatement.executeQuery();
    
                if (resultSet.next()) {
                    // Se um resultado for encontrado no banco de dados.
                    int id = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");
                    String turnoTrabalho = resultSet.getString("turno_trabalho");
    
                    // Cria um objeto Instrutor com os dados encontrados.
                    Instrutor instrutorEncontrado = new Instrutor(id, nome, turnoTrabalho);
                    System.out.println("Instrutor encontrado:\n" + instrutorEncontrado);
                    encontrado = true;
                } else {
                    System.out.println("Nenhum instrutor encontrado com o ID fornecido.");
                }
            } catch (SQLException e) {
                // Trata exceção em caso de erro ao buscar o instrutor no banco de dados.
                System.out.println("Erro ao buscar instrutor no banco de dados: " + e.getMessage());
            }
    
            // Pergunta ao usuário se deseja buscar outro instrutor por ID.
            System.out.print("Deseja buscar outro instrutor (S/N)? ");
            String resposta = scanner.nextLine().toUpperCase();
            if (!resposta.equals("S")) {
                System.out.println("Saindo da busca por instrutor...");
                break; // Sai do loop de busca se o usuário não quiser buscar outro instrutor.
            }
        }
    }
    
    //MÉTODO PARA TORNAR UM ALUNO EM MEMBRO

    public static void tornarAlunoMembro(Connection connection) {
        while (true) {
            // Exibe um cabeçalho indicando que o processo de tornar um aluno membro está em andamento.
            System.out.println("\nTornar Aluno Membro:");
            System.out.print("Digite o ID do aluno que deseja tornar membro (ou 0 para sair): ");
            int idAluno = scanner.nextInt();
            scanner.nextLine();
    
            if (idAluno == 0) {
                return; // Sai do método se o usuário escolher sair.
            }
    
            boolean encontrado = false;
    
            try {
                // Verifica se o ID do aluno existe no banco de dados.
                String checkSql = "SELECT id FROM Aluno WHERE id = ?";
                PreparedStatement checkStatement = connection.prepareStatement(checkSql);
                checkStatement.setInt(1, idAluno);
                ResultSet checkResult = checkStatement.executeQuery();
    
                if (checkResult.next()) {
                    // O ID do aluno existe no banco de dados, proceda para torná-lo membro.
                    String updateSql = "UPDATE Aluno SET membro = true WHERE id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                    updateStatement.setInt(1, idAluno);
    
                    int rowsAffected = updateStatement.executeUpdate();
    
                    if (rowsAffected > 0) {
                        System.out.println("Aluno tornou-se membro com sucesso.");
                    } else {
                        System.out.println("Falha ao tornar o aluno membro.");
                    }
    
                    encontrado = true;
                } else {
                    System.out.println("ID de aluno não encontrado no banco de dados.");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao tornar aluno membro no banco de dados: " + e.getMessage());
            }
    
            if (!encontrado) {
                System.out.println("Aluno não encontrado.");
            }
    
            // Pergunta ao usuário se deseja tornar outro aluno membro.
            System.out.print("Deseja tornar outro aluno membro? (S para sim, N para não): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (!resposta.equals("S")) {
                return; // Sai do método se o usuário não quiser tornar outro aluno membro.
            }
        }
    }
    
    //MÉTODO PARA LISTAR APENAS OS ALUNOS QUE SÃO MEMBROS
        
    public static void listarAlunosMembros(Connection connection) {
        // Exibe um cabeçalho indicando que a lista de alunos membros está sendo gerada.
        System.out.println("\nLista de Alunos Membros:");
    
        try {
            // Consulta o banco de dados para selecionar todos os alunos que são membros (membro = true).
            String sql = "SELECT * FROM Aluno WHERE membro = true";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            boolean encontrouAlunoMembro = false;
    
            while (resultSet.next()) {
                // Recupera os dados de cada aluno membro do resultado da consulta.
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                int idade = resultSet.getInt("idade");
                String sexo = resultSet.getString("sexo");
                boolean problemaSaude = resultSet.getBoolean("problema_saude");
                String tipoPagamento = resultSet.getString("tipo_pagamento");
    
                // Exibe os detalhes do aluno membro.
                System.out.println("ID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Idade: " + idade);
                System.out.println("Sexo: " + sexo);
                System.out.println("Problema de Saúde: " + (problemaSaude ? "Sim" : "Não"));
                System.out.println("Tipo de Pagamento: " + tipoPagamento);
                System.out.println();
    
                encontrouAlunoMembro = true;
            }
    
            // Se nenhum aluno membro foi encontrado, exibe uma mensagem indicando que a lista está vazia.
            if (!encontrouAlunoMembro) {
                System.out.println("A lista de alunos membros está vazia.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos membros: " + e.getMessage());
        }
    
        // Aguarda o usuário pressionar Enter antes de retornar ao menu principal.
        pressioneEnterParaContinuar();
    }
    
    //MÉTODO PARA REMOVER(EXCLUIR) UM ALUNO DE MEMBRO

    public static void removerAlunoMembro(Connection connection) {
        while (true) {
            // Exibe uma mensagem e solicita o ID do aluno membro a ser removido
            System.out.println("\nRemover Aluno Membro:");
            System.out.print("Digite o ID do aluno membro que deseja remover (ou 0 para sair): ");
            int idAlunoMembro = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
    
            if (idAlunoMembro == 0) {
                return; // Sai da função se o usuário escolher sair
            }
    
            boolean encontrado = false; // Variável para rastrear se o aluno foi encontrado no banco de dados
    
            try {
                // Verifica se o ID do aluno existe no banco de dados e se ele é um membro
                String checkSql = "SELECT id FROM Aluno WHERE id = ? AND membro = true";
                PreparedStatement checkStatement = connection.prepareStatement(checkSql);
                checkStatement.setInt(1, idAlunoMembro);
                ResultSet checkResult = checkStatement.executeQuery();
    
                if (checkResult.next()) {
                    // O ID do aluno existe no banco de dados e ele é um membro; proceda para remover a associação
                    String updateSql = "UPDATE Aluno SET membro = false WHERE id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                    updateStatement.setInt(1, idAlunoMembro);
    
                    int rowsAffected = updateStatement.executeUpdate();
    
                    if (rowsAffected > 0) {
                        // A associação do aluno como membro foi removida com sucesso
                        System.out.println("Membro removido com sucesso.");
                    } else {
                        // Falha ao remover a associação do aluno como membro
                        System.out.println("Falha ao remover o membro.");
                    }
    
                    encontrado = true;
                } else {
                    // ID de aluno membro não encontrado no banco de dados ou não é membro
                    System.out.println("ID de aluno membro não encontrado no banco de dados ou não é membro.");
                }
            } catch (SQLException e) {
                // Tratamento de erro em caso de falha na execução da consulta SQL ou atualização
                System.out.println("Erro ao remover membro no banco de dados: " + e.getMessage());
            }
    
            if (!encontrado) {
                // Exibido se o aluno não foi encontrado no banco de dados ou não é membro
                System.out.println("Aluno membro não encontrado ou não é membro.");
            }
    
            // Pergunta ao usuário se deseja remover outro aluno membro
            System.out.print("Deseja remover outro aluno membro? (S para sim, N para não): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (!resposta.equals("S")) {
                return; // Sai da função se o usuário não quiser remover a associação de outro aluno
            }
        }
    }
    
    //MÉTODO PARA OBTER UM TREINO

    public static void obterTreino() {
    while (true) {
        // Exibe uma mensagem e solicita o dia da semana para obter o treino (ou 'voltar' para retornar ao menu)
        System.out.println("\nSolicitar Treino:");
        System.out.print("Digite o dia da semana para obter o treino (ou 'voltar' para retornar ao menu): ");
        String entrada = scanner.nextLine();

        if (entrada.equalsIgnoreCase("voltar")) {
            return; // Retorna ao menu anterior se 'voltar' for inserido
        }

        // Obtém o treino para o dia especificado
        String treinoDia = treino.getTreino(entrada);

        if (treinoDia != null) {
            // Exibe o treino para o dia especificado, se encontrado
            System.out.println("\nTreino para " + entrada + ": " + treinoDia);
        } else {
            // Exibe uma mensagem se o treino não for encontrado para o dia especificado
            System.out.println("Treino não encontrado para o dia " + entrada);
        }

        // Pergunta ao usuário se deseja obter outro treino
        System.out.print("Deseja obter outro treino? (Sim/Não): ");
        String resposta = scanner.nextLine();

        if (!resposta.equalsIgnoreCase("Sim")) {
            return; // Sai do loop se a resposta não for "Sim"
        }
    }
}

    //MÉTODO PARA VOLTAR AO MENU 
    
    private static void pressioneEnterParaContinuar() {
        // Exibe uma mensagem instruindo o usuário a pressionar Enter para continuar
        System.out.println("\nPressione Enter para continuar...");

        // Aguarda até que o usuário pressione a tecla Enter
        scanner.nextLine();
    }
}