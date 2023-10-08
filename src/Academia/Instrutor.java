package src.Academia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import src.AcademiaDAO.InstrutorDAO;

public class Instrutor {
    private String nome;
    private String sexo;
    private int idade;
    private String turnoDeTrabalho;

    public Instrutor(String nome, int idade, String sexo, String turnoDeTrabalho) {
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.turnoDeTrabalho = turnoDeTrabalho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getTurnoDeTrabalho() {
        return turnoDeTrabalho;
    }

    public void setTurnoDeTrabalho(String turnoDeTrabalho) {
        this.turnoDeTrabalho = turnoDeTrabalho;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\nIdade: " + idade + "\nSexo: " + sexo + "\nTurno de Trabalho: " + turnoDeTrabalho;
    }

    private Scanner scanner = new Scanner(System.in);

    // MÉTODO PARA CADASTRAR UM NOVO INSTRUTOR

    public void cadastrarInstrutor(Connection connection) {
        System.out.println("Cadastro de Instrutor\n");

        // Apresentação inicial
        System.out.println("Digite os dados do Instrutor (ou digite 0 para sair):\n");

        while (true) {
            String nome;
            do {
                // Solicita o nome do instrutor e verifica se é válido
                System.out.print("Nome: ");
                nome = scanner.nextLine();
                if (!nome.equals("0") && !nome.matches("[A-Za-z ]+")) {
                    System.out.println("Nome inválido. Use apenas letras e espaços.");
                }
            } while (!nome.equals("0") && !nome.matches("[A-Za-z ]+"));

            if (nome.equals("0")) {
                break; // Sai do loop se o usuário digitar "0"
            }

            // Solicita e lê a idade do instrutor
            System.out.print("Idade: ");
            int idade = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            String respostaSexo;
            do {
                // Solicita o sexo do instrutor e valida a entrada
                System.out.print("Sexo (M/F): ");
                respostaSexo = scanner.nextLine();

                // Converte "M" para "Masculino" e "F" para "Feminino"
                if (respostaSexo.equalsIgnoreCase("M")) {
                    respostaSexo = "Masculino";
                } else if (respostaSexo.equalsIgnoreCase("F")) {
                    respostaSexo = "Feminino";
                } else {
                    System.out.println("Resposta inválida. Digite 'M' para homem ou 'F' para mulher.");
                }
            } while (!respostaSexo.equalsIgnoreCase("Masculino") && !respostaSexo.equalsIgnoreCase("Feminino"));

            String turno;
            do {
                // Solicita o turno de trabalho e valida a entrada
                System.out.print("Turno de Trabalho (Manhã/Tarde/Noite): ");
                turno = scanner.nextLine();

                if (!turno.equalsIgnoreCase("Manhã") && !turno.equalsIgnoreCase("Tarde")
                        && !turno.equalsIgnoreCase("Noite")) {
                    System.out.println("Turno inválido. Digite 'Manhã', 'Tarde' ou 'Noite'.");
                }
            } while (!turno.equalsIgnoreCase("Manhã") && !turno.equalsIgnoreCase("Tarde")
                    && !turno.equalsIgnoreCase("Noite"));

            // Cria um objeto Instrutor com os dados informados
            Instrutor instrutor = new Instrutor(nome, idade, respostaSexo, turno);

            // Chama o método de cadastro do InstrutorDAO para inserir o instrutor no banco
            // de dados
            InstrutorDAO instrutorDAO = new InstrutorDAO(connection);
            instrutorDAO.cadastrarInstrutor(instrutor);

            // Pergunta se deseja cadastrar outro instrutor
            System.out.print("Deseja cadastrar outro instrutor? (S/N): ");
            char respostaContinuar = scanner.nextLine().toUpperCase().charAt(0);
            if (respostaContinuar != 'S') {
                break; // Sai do loop se não desejar cadastrar outro instrutor
            }
        }
    }

    // MÉTODO PARA LISTAR TODOS OS INSTRUTORES CADASTRADOS

    public void listarInstrutores(Connection connection) {
        InstrutorDAO instrutorDAO = new InstrutorDAO(connection);
        instrutorDAO.listarInstrutores();
        pressioneEnterParaContinuar();
    }

    // MÉTODO PARA EDITAR AS INFORMAÇÕES DO INSTRUTOR

    public void editarInstrutor(Connection connection) {
        InstrutorDAO instrutorDAO = new InstrutorDAO(connection);

        try {
            while (true) {
                // Solicita o nome do instrutor a ser editado ou '0' para sair
                System.out.print("Digite o nome do instrutor que deseja editar (ou '0' para sair): ");
                System.out.print("\nNome -> ");
                String nome = scanner.nextLine();

                if (nome.equals("0")) {
                    System.out.println("Encerrando a edição de instrutores.");
                    break; // Sai do método se o usuário inserir "0"
                }

                Instrutor instrutorAtual = instrutorDAO.buscarInstrutorPorNome(nome);

                if (instrutorAtual != null) {
                    // Exibe as informações atuais do instrutor
                    System.out.println("Informações atuais do instrutor:");
                    System.out.println(instrutorAtual);

                    System.out.println("Digite as novas informações do instrutor:");

                    // Solicita e lê o novo nome do instrutor
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();

                    // Solicita e lê a nova idade do instrutor
                    System.out.print("Nova idade: ");
                    int novaIdade = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer

                    String novoTurno;
                    do {
                        // Solicita e lê o novo turno e valida a entrada
                        System.out.print("Novo turno (Manhã/Tarde/Noite): ");
                        novoTurno = scanner.nextLine();
                    } while (!novoTurno.equalsIgnoreCase("Manhã") &&
                            !novoTurno.equalsIgnoreCase("Tarde") &&
                            !novoTurno.equalsIgnoreCase("Noite"));

                    instrutorDAO.editarInstrutor(nome, novoNome, novaIdade, novoTurno);

                    System.out.println("\nInstrutor editado com sucesso!");

                    char continuar;
                    do {
                        // Pergunta se deseja editar outro instrutor
                        System.out.print("\nDeseja editar outro instrutor? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');

                    if (continuar == 'N') {
                        System.out.println("Encerrando a edição de instrutores.");
                        break; // Sai do método se não desejar editar outro instrutor
                    }
                } else {
                    System.out.println("\nInstrutor não encontrado.");

                    char continuar;
                    do {
                        // Pergunta se deseja tentar editar outro instrutor
                        System.out.print("\nDeseja tentar editar outro instrutor? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');

                    if (continuar == 'N') {
                        System.out.println("Encerrando a edição de instrutores.");
                        break; // Sai do método se não desejar editar outro instrutor
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
        }
    }

    // MÉTODO PARA EXCLUIR UM INSTRUTOR

    public void excluirInstrutor(Connection connection) {
        InstrutorDAO instrutorDAO = new InstrutorDAO(connection);

        try {
            while (true) {
                // Solicita o nome do instrutor que deseja excluir
                System.out.print("Digite o nome do instrutor que deseja excluir (ou '0' para sair): ");
                System.out.print("\nNome -> ");
                String nome = scanner.nextLine();

                if (nome.equals("0")) {
                    // Encerra a exclusão de instrutores se "0" for inserido
                    System.out.println("Encerrando a exclusão de instrutores.");
                    return;
                }

                // Chama o método buscarInstrutorPorNome do objeto InstrutorDAO para obter
                // informações do instrutor
                Instrutor instrutor = instrutorDAO.buscarInstrutorPorNome(nome);

                if (instrutor != null) {
                    // Exibe informações do instrutor a ser excluído
                    System.out.println("\nInformações do instrutor a ser excluído:");
                    System.out.println(instrutor);

                    // Solicita confirmação da exclusão
                    char confirmacao;
                    do {
                        System.out.print("Tem certeza que deseja excluir este instrutor? (S/N): ");
                        confirmacao = scanner.nextLine().toUpperCase().charAt(0);
                    } while (confirmacao != 'S' && confirmacao != 'N');

                    if (confirmacao == 'S') {
                        // Chama o método excluirInstrutor do objeto InstrutorDAO para excluir o
                        // instrutor do banco de dados
                        instrutorDAO.excluirInstrutor(instrutor);

                        // Exibe uma mensagem de sucesso
                        System.out.println("\nInstrutor excluído com sucesso!");
                    } else {
                        System.out.println("\nExclusão cancelada.");
                    }

                    // Pergunta se deseja excluir outro instrutor
                    char continuar;
                    do {
                        System.out.print("\nDeseja excluir outro instrutor? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');

                    if (continuar == 'N') {
                        // Encerra a exclusão de instrutores se não desejar excluir outro instrutor
                        System.out.println("Encerrando a exclusão de instrutores.");
                        return;
                    }
                } else {
                    // Exibe uma mensagem se o instrutor não for encontrado no banco de dados
                    System.out.println("Instrutor não encontrado.");

                    // Pergunta se deseja tentar excluir outro instrutor
                    char continuar;
                    do {
                        System.out.print("\nDeseja tentar excluir outro instrutor? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');

                    if (continuar == 'N') {
                        // Encerra a exclusão de instrutores se não desejar excluir outro instrutor
                        System.out.println("Encerrando a exclusão de instrutores.");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Trata exceções imprimindo informações de erro
        }
    }

    // MÉTODO PARA BUSCAR UM INSTRUTOR POR NOME

    public void buscarInstrutorPorNome(Connection connection) {
        while (true) {
            // Solicita o nome do instrutor a ser buscado ou '0' para sair
            System.out.print("Digite o nome do instrutor que deseja buscar (ou '0' para sair): ");
            System.out.print("\nNome -> ");
            String nome = scanner.nextLine();

            if (nome.equals("0")) {
                System.out.println("Encerrando a busca de instrutores.");
                break;
            }

            InstrutorDAO instrutorDAO = new InstrutorDAO(connection);
            Instrutor instrutor = instrutorDAO.buscarInstrutorPorNome(nome);

            if (instrutor != null) {
                // Exibe informações do instrutor encontrado
                System.out.println("\nInstrutor encontrado:");
                System.out.println("Dados do instrutor:");
                System.out.println(instrutor);
            } else {
                // Exibe uma mensagem se o instrutor não for encontrado no banco de dados
                System.out.println("Instrutor não encontrado.");
            }

            // Pergunta se deseja buscar outro instrutor ou sair
            System.out.print("Deseja buscar outro instrutor? (S/N): ");
            String resposta = scanner.nextLine().toUpperCase();
            if (!resposta.equals("S")) {
                System.out.println("Encerrando a busca de instrutores.");
                break;
            }
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