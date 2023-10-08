package src.Academia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.Scanner;

import src.AcademiaDAO.TreinoDAO;

public class Treino {
    private String nomeTreino;
    private String diaTreino;
    private int exercicios;
    private String tipoTreino;
  
    public Treino(String nomeTreino, String diaTreino, int exercicios, String tipoTreino) {
        this.nomeTreino = nomeTreino;
        this.diaTreino = diaTreino;
        this.exercicios = exercicios;
        this.tipoTreino = tipoTreino;
    }

    public String getNomeTreino() {
        return nomeTreino;
    }


    public void setNomeTreino(String nomeTreino) {
        this.nomeTreino = nomeTreino;
    }


    public String getDiaTreino() {
        return diaTreino;
    }


    public void setDiaTreino(String diaTreino) {
        this.diaTreino = diaTreino;
    }


    public int getExercicios() {
        return exercicios;
    }


    public void setExercicios(int exercicios) {
        this.exercicios = exercicios;
    }


    public String getTipoTreino() {
        return tipoTreino;
    }


    public void setTipoTreino(String tipoTreino) {
        this.tipoTreino = tipoTreino;
    }

    @Override
    public String toString() {
        return "Nome do Treino: " + nomeTreino + "\nDia do Treino: " + diaTreino + "\nExercicios: " + exercicios
                + "\nTipo de Treino: " + tipoTreino;
    }

    public void cadastrarTreino(Connection connection) {
        try {
            Scanner scanner = new Scanner(System.in);
            // Loop infinito para permitir o cadastro de múltiplos treinos
            while (true) {
                System.out.println("Digite os dados do Treino (ou insira '0' para cancelar):\n");
                
                // Solicita o nome do treino ao usuário
                System.out.print("Nome do Treino: ");
                String nomeTreino = scanner.nextLine();
    
                // Verifica se o usuário deseja cancelar o cadastro (inserindo '0')
                if (nomeTreino.equals("0")) {
                    System.out.println("Cadastro de treino cancelado.");
                    break; // Sai do loop caso '0' seja inserido
                }
    
                // Array com os nomes dos dias da semana válidos
                String[] diasDaSemana = {"segunda", "terça", "quarta", "quinta", "sexta", "sábado", "domingo"};
                boolean diaValido = false;
    
                // Loop para garantir que o dia do treino inserido seja válido
                String diaTreino = "";
                do {
                    // Solicita o dia do treino ao usuário (ex: segunda, terça, etc.)
                    System.out.print("Dia do Treino (somente dias da semana, ex: segunda): ");
                    diaTreino = scanner.nextLine().toLowerCase();
    
                    // Verifica se o dia inserido está na lista de dias válidos
                    if (Arrays.asList(diasDaSemana).contains(diaTreino)) {
                        diaValido = true;
                    } else {
                        System.out.println("Dia inválido. Insira um dia da semana válido.");
                    }
                } while (!diaValido);
    
                // Solicita a quantidade de exercícios do treino
                int exercicios = 0;
                boolean inputValido = false;
    
                while (!inputValido) {
                    System.out.print("Quantidade de Exercícios: ");
                    // Verifica se o numero é inteiro
                    if (scanner.hasNextInt()) {
                        exercicios = scanner.nextInt();
                        inputValido = true;
                    } else {
                        System.out.println("Por favor, insira um número inteiro válido.");
                        scanner.nextLine(); // Limpa o buffer
                    }
                }
                scanner.nextLine(); // Limpa o buffer
    
                // Solicita o tipo de treino (ex: força, hipertrofia, cárdio)
                System.out.print("Tipo de Treino (ex: força, hipertrofia, cárdio): ");
                String tipoTreino = scanner.nextLine();
    
                // Cria uma instância de TreinoDAO e insere o treino no banco de dados
                TreinoDAO treinoDAO = new TreinoDAO();
                treinoDAO.cadastrarTreino(connection, nomeTreino, diaTreino, exercicios, tipoTreino);
    
                // Exibe uma mensagem de sucesso informando que o treino foi cadastrado
                System.out.println("\nTreino cadastrado com sucesso!");
    
                // Pergunta ao usuário se deseja cadastrar outro treino
                System.out.print("\nDeseja cadastrar outro treino? (S/N): ");
                char respostaContinuar = scanner.nextLine().toUpperCase().charAt(0);
                if (respostaContinuar != 'S') {
                    break; // Sai do loop se não desejar cadastrar outro treino
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Trata exceções imprimindo informações de erro
        }
    }

    public void listarTreinos(Connection connection) {
        try {
            // Prepara a instrução SQL para selecionar todos os dados dos treinos
            String sql = "SELECT nome_treino, dia_treino, exercicios, tipo_treino FROM treinos";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            // Executa a consulta SQL e obtém o resultado em um ResultSet
            ResultSet resultSet = statement.executeQuery();
    
            // Verifica se o ResultSet não está vazio (sem registros)
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Nenhum treino cadastrado.");
            } else {
                System.out.println("Lista de Treinos:\n");
                
                // Itera pelos resultados no ResultSet e exibe os detalhes de cada treino
                while (resultSet.next()) {
                    System.out.println("Nome do Treino: " + resultSet.getString("nome_treino"));
                    System.out.println("Dia do Treino: " + resultSet.getString("dia_treino"));
                    System.out.println("Quantidade de Exercícios: " + resultSet.getInt("exercicios"));
                    System.out.println("Tipo de Treino: " + resultSet.getString("tipo_treino"));
                    System.out.println("------------------------------");
                }
            }
    
            // Fecha o ResultSet e a declaração preparada para liberar recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
        }
    
        pressioneEnterParaContinuar(); // Aguarda uma entrada do usuário antes de retornar
    }

    //MÉTODO PARA EDITAR UM TREINO
    public void editarTreino(Connection connection) {
        Scanner scanner = new Scanner(System.in);
    
        try {
            while (true) {
                // Solicita o nome do treino a ser editado
                System.out.print("Digite o nome do treino que deseja editar (ou insira '0' para cancelar): ");
                System.out.print("\nNome -> ");
                String nomeTreinoEditar = scanner.nextLine();
    
                // Verifica se o usuário deseja cancelar a edição
                if (nomeTreinoEditar.equals("0")) {
                    System.out.println("Edição de treino cancelada.");
                    break;
                }
    
                // Consulta o banco de dados para verificar se o treino existe
                Treino treinoExistente = TreinoDAO.buscarTreinoPorNome(connection, nomeTreinoEditar);
    
                if (treinoExistente != null) {
                    // Exibe as informações atuais do treino
                    System.out.println("\nInformações atuais do treino:");
                    System.out.println(treinoExistente);
    
                    // Use a lógica do método cadastrarTreino para validar e atualizar os dados do treino
                    // Solicita os novos dados do treino
                    System.out.println("Digite as novas informações do treino (ou insira '0' para cancelar):\n");
                    System.out.print("Novo nome do Treino: ");
                    String novoNomeTreino = scanner.nextLine();
    
                    // Verifica se o usuário deseja cancelar a edição
                    if (novoNomeTreino.equals("0")) {
                        System.out.println("Edição de treino cancelada.");
                        break;
                    }
    
                    // Solicita o novo dia do treino
                    String[] diasDaSemana = {"segunda", "terça", "quarta", "quinta", "sexta", "sábado", "domingo"};
                    boolean diaValido = false;
                    String novoDiaTreino = "";
    
                    do {
                        System.out.print("Novo dia do Treino (somente dias da semana, ex: segunda): ");
                        novoDiaTreino = scanner.nextLine().toLowerCase();
    
                        if (Arrays.asList(diasDaSemana).contains(novoDiaTreino)) {
                            diaValido = true;
                        } else {
                            System.out.println("Dia inválido. Insira um dia da semana válido.");
                        }
                    } while (!diaValido);
    
                    // Solicita a nova quantidade de exercícios
                    int novaQuantidadeExercicios = 0;
                    boolean inputValido = false;
    
                    while (!inputValido) {
                        System.out.print("Nova quantidade de Exercícios: ");
    
                        if (scanner.hasNextInt()) {
                            novaQuantidadeExercicios = scanner.nextInt();
                            inputValido = true;
                        } else {
                            System.out.println("Por favor, insira um número inteiro válido.");
                            scanner.nextLine(); // Limpa o buffer
                        }
                    }
                    scanner.nextLine(); // Limpa o buffer
    
                    // Solicita o novo tipo de treino
                    System.out.print("Novo tipo de Treino (ex: força, hipertrofia, cárdio): ");
                    String novoTipoTreino = scanner.nextLine();
    
                    // Atualiza os dados do treino no banco de dados usando o método da TreinoDAO
                    Treino novoTreino = new Treino(novoNomeTreino, novoDiaTreino, novaQuantidadeExercicios, novoTipoTreino);
                    TreinoDAO.editarTreino(connection, novoTreino, nomeTreinoEditar);
    
                    System.out.println("\nTreino editado com sucesso!");
    
                    // Pergunta se deseja editar outro treino
                    char continuar;
                    do {
                        System.out.print("\nDeseja editar outro treino? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');
    
                    if (continuar == 'N') {
                        System.out.println("Encerrando a edição de treinos.");
                        break; // Sai do método se não desejar editar outro treino
                    }
                } else {
                    System.out.println("Treino não encontrado.");
    
                    // Pergunta se deseja tentar editar outro treino
                    char continuar;
                    do {
                        System.out.print("\nDeseja tentar editar outro treino? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');
    
                    if (continuar == 'N') {
                        System.out.println("Encerrando a edição de treinos.");
                        break; // Sai do método se não desejar editar outro treino
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
        } finally {
        }
    }

    //MÉTODO PARA EXCLUIR UM TREINO

    public void excluirTreino(Connection connection) {
        Scanner scanner = new Scanner(System.in);
    
        try {
            while (true) {
                // Solicita o nome do treino a ser excluído
                System.out.print("Digite o nome do treino que deseja excluir (ou insira '0' para cancelar): ");
                System.out.print("\nNome -> ");
                String nomeTreinoExcluir = scanner.nextLine();
    
                // Verifica se o usuário deseja cancelar a exclusão
                if (nomeTreinoExcluir.equals("0")) {
                    System.out.println("Exclusão de treino cancelada.");
                    break;
                }
    
                // Consulta o banco de dados para verificar se o treino existe
                Treino treinoExistente = TreinoDAO.buscarTreinoPorNome(connection, nomeTreinoExcluir);
    
                if (treinoExistente != null) {
                    // Exibe as informações do treino a ser excluído
                    System.out.println("\nInformações do treino a ser excluído:");
                    System.out.println(treinoExistente);
    
                    // Pergunta ao usuário se tem certeza de que deseja excluir o treino
                    char confirmacao;
                    do {
                        System.out.print("Tem certeza que deseja excluir este treino? (S/N): ");
                        confirmacao = scanner.nextLine().toUpperCase().charAt(0);
                    } while (confirmacao != 'S' && confirmacao != 'N');
    
                    if (confirmacao == 'S') {
                        // Exclui o treino usando o método da TreinoDAO
                        TreinoDAO.excluirTreino(connection, nomeTreinoExcluir);
                        System.out.println("\nTreino excluído com sucesso!");
                    } else {
                        System.out.println("Exclusão cancelada.");
                    }
    
                    // Pergunta se deseja excluir outro treino
                    char continuar;
                    do {
                        System.out.print("Deseja excluir outro treino? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');
    
                    if (continuar == 'N') {
                        System.out.println("Encerrando a exclusão de treinos.");
                        break; // Sai do método se não desejar excluir outro treino
                    }
                } else {
                    System.out.println("Treino não encontrado.");
    
                    // Pergunta se deseja tentar excluir outro treino
                    char continuar;
                    do {
                        System.out.print("\nDeseja tentar excluir outro treino? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');
    
                    if (continuar == 'N') {
                        System.out.println("Encerrando a exclusão de treinos.");
                        break; // Sai do método se não desejar excluir outro treino
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
        }
    }
    
    //MÉTODO PARA BUSCAR UM TREINO PELO NOME

    public void buscarTreinoPorNome(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                // Solicita o nome do treino a ser buscado
                System.out.print("Digite o nome do treino que deseja buscar (ou insira '0' para cancelar): ");
                System.out.print("\nNome -> ");
                String nomeTreinoBuscar = scanner.nextLine();

                // Verifica se o usuário deseja cancelar a busca
                if (nomeTreinoBuscar.equals("0")) {
                    System.out.println("Busca de treino cancelada.");
                    break;
                }

                // Use o método buscarTreinoPorNome da TreinoDAO para buscar o treino
                Treino treinoEncontrado = TreinoDAO.buscarTreinoPorNome(connection, nomeTreinoBuscar);

                if (treinoEncontrado != null) {
                    // Exibe as informações do treino encontrado
                    System.out.println("\nTreino encontrado:");
                    System.out.println(treinoEncontrado);
                } else {
                    System.out.println("Treino não encontrado.");
                }

                // Pergunta se deseja buscar outro treino ou sair
                System.out.print("\nDeseja buscar outro treino? (S/N): ");
                String resposta = scanner.nextLine().toUpperCase();
                if (!resposta.equals("S")) {
                    System.out.println("Encerrando a busca de treinos.");
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
        } finally {
        }
    }



    private static void pressioneEnterParaContinuar() {
        Scanner scanner = new Scanner(System.in);
        // Exibe uma mensagem instruindo o usuário a pressionar Enter para continuar
        System.out.println("\nPressione Enter para continuar...");

        // Aguarda até que o usuário pressione a tecla Enter
        scanner.nextLine();
    }
} 