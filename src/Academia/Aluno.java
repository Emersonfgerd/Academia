package src.Academia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import src.AcademiaDAO.AlunoDAO;

public class Aluno {

    private String nome;
    private int idade;
    private String sexo;
    private boolean problemaDeSaude;

    public Aluno(String nome, int idade, String sexo, boolean problemaDeSaude) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.problemaDeSaude = problemaDeSaude;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getSexo() {
        return sexo;
    }

    public boolean temProblemaDeSaude() {
        return problemaDeSaude;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setProblemaDeSaude(boolean problemaDeSaude) {
        this.problemaDeSaude = problemaDeSaude;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\nIdade: " + idade + "\nSexo: " + sexo + "\nProblema de Saúde: "
                + (problemaDeSaude ? "Sim" : "Não");
    }

    private Scanner scanner = new Scanner(System.in);

    // MÉTODO PARA CADASTRAR UM NOVO ALUNO

    public void cadastrarAluno(Connection connection) {
        System.out.println("Cadastro de Alunos\n");

        AlunoDAO alunoDAO = new AlunoDAO(connection);

        while (true) {
            String nome;
            do {
                // Solicita o nome do aluno, verificando se contém apenas letras e espaços
                System.out.print("Nome: ");
                nome = scanner.nextLine();
                if (!nome.equals("0") && !nome.matches("[A-Za-z ]+")) {
                    System.out.println("Nome inválido. Use apenas letras e espaços.");
                }
            } while (!nome.equals("0") && !nome.matches("[A-Za-z ]+"));

            if (nome.equals("0")) {
                break; // Sai do loop se o usuário digitar "0"
            }

            System.out.print("Idade: ");
            int idade = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            String respostaSexo;
            do {
                // Solicita o sexo do aluno (M/F), convertendo para "Masculino" ou "Feminino"
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

            boolean problemaDeSaude = false; // Inicialize com false
            char respostaProblema;
            do {
                // Solicita se o aluno tem algum problema de saúde (S/N)
                System.out.print("Tem problema de Saúde (S/N): ");
                respostaProblema = scanner.nextLine().toUpperCase().charAt(0);
                if (respostaProblema == 'S') {
                    problemaDeSaude = true;
                } else if (respostaProblema != 'N') {
                    System.out.println("Resposta inválida. Digite S para Sim ou N para Não.");
                }
            } while (respostaProblema != 'S' && respostaProblema != 'N');

            // Cria um objeto Aluno com os dados informados
            Aluno aluno = new Aluno(nome, idade, respostaSexo, problemaDeSaude);
            aluno.setNome(nome);
            aluno.setIdade(idade);
            aluno.setSexo(respostaSexo);
            aluno.setProblemaDeSaude(problemaDeSaude);

            // Chama o método de cadastro do AlunoDAO para inserir o aluno no banco de dados
            alunoDAO.cadastrarAluno(aluno);

            // Pergunta se deseja cadastrar outro aluno
            System.out.print("Deseja cadastrar outro aluno? (S/N): ");
            char respostaContinuar = scanner.nextLine().toUpperCase().charAt(0);
            if (respostaContinuar != 'S') {
                break; // Sai do loop se não desejar cadastrar outro aluno
            }
        }
    }

    // MÉTODO PARA LISTAR TODOS OS ALUNOS CADASTRADOS

    public void listarAlunos(AlunoDAO alunoDAO) {

        List<Aluno> alunos = alunoDAO.listarAlunos();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            System.out.println("Lista de Alunos:");
            for (Aluno aluno : alunos) {
                System.out.println(aluno);
                System.out.println("-------------");
            }
        }

        pressioneEnterParaContinuar();
    }

    // MÉTODO PARA EDITAR AS INFORMAÇÕES DE UM ALUNO

    public void editarAluno(Connection connection) {
        AlunoDAO alunoDAO = new AlunoDAO(connection);

        try {
            while (true) {
                // Solicita o nome do aluno que deseja editar
                System.out.println("Digite o nome do aluno que deseja editar (ou 0 para sair): ");
                System.out.print("\nNome -> ");
                String nome = scanner.nextLine();

                if (nome.equals("0")) {
                    // Encerra a edição de alunos se 0 for inserido
                    System.out.println("Encerrando a edição de alunos.");
                    return;
                }

                // Chama o método buscarAlunoPorNome do objeto AlunoDAO para obter informações
                // do aluno
                Aluno aluno = alunoDAO.buscarAlunoPorNome(nome);

                if (aluno != null) {
                    // Exibe informações atuais do aluno
                    System.out.println("\nInformações atuais do aluno:");
                    System.out.println(aluno);

                    // Solicita as novas informações do aluno

                    // Novo nome
                    System.out.print("\nNovo nome: ");
                    String novoNome = scanner.nextLine();

                    // Nova idade
                    System.out.print("Nova idade: ");
                    int novaIdade = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer

                    // Novo sexo
                    String respostaSexo;
                    do {
                        // Solicita o sexo do aluno (M/F), convertendo para "Masculino" ou "Feminino"
                        System.out.print("Novo sexo (M/F): ");
                        respostaSexo = scanner.nextLine();

                        if (respostaSexo.equalsIgnoreCase("M")) {
                            respostaSexo = "Masculino";
                        } else if (respostaSexo.equalsIgnoreCase("F")) {
                            respostaSexo = "Feminino";
                        } else {
                            System.out.println("Resposta inválida. Digite 'M' para homem ou 'F' para mulher.");
                        }
                    } while (!respostaSexo.equalsIgnoreCase("Masculino") && !respostaSexo.equalsIgnoreCase("Feminino"));

                    // Novo status de problema de saúde
                    char respostaProblema;
                    do {
                        System.out.print("Novo problema de Saúde (S/N): ");
                        respostaProblema = scanner.nextLine().toUpperCase().charAt(0);
                    } while (respostaProblema != 'S' && respostaProblema != 'N');

                    // Atualiza os dados do aluno no objeto aluno
                    aluno.setNome(novoNome);
                    aluno.setIdade(novaIdade);
                    aluno.setSexo(respostaSexo);
                    aluno.setProblemaDeSaude(respostaProblema == 'S');

                    // Chama o método editarAluno do objeto AlunoDAO para atualizar os dados no
                    // banco de dados
                    alunoDAO.editarAluno(aluno);

                    // Exibe uma mensagem de sucesso
                    System.out.println("\nAluno editado com sucesso!");

                    // Pergunta se deseja editar outro aluno

                    char continuar;
                    do {
                        System.out.print("\nDeseja editar outro aluno? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');

                    if (continuar == 'N') {
                        // Encerra a edição de alunos se não desejar editar outro aluno
                        System.out.println("Encerrando a edição de alunos.");
                        return;
                    }
                } else {
                    // Exibe uma mensagem se o aluno não for encontrado no banco de dados

                    System.out.println("Aluno não encontrado.");

                    // Pergunta se deseja tentar editar outro aluno

                    char continuar;
                    do {
                        System.out.print("\nDeseja tentar editar outro aluno? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');

                    if (continuar == 'N') {
                        // Encerra a edição de alunos se não desejar editar outro aluno
                        System.out.println("Encerrando a edição de alunos.");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Trata exceções imprimindo informações de erro
        }
    }

    // MÉTODO PARA EXCLUIR INFORMAÇÕES DE UM ALUNO

    public void excluirAluno(Connection connection) {
        AlunoDAO alunoDAO = new AlunoDAO(connection);

        try {
            while (true) {
                // Solicita o nome do aluno que deseja excluir
                System.out.print("Digite o nome do aluno que deseja excluir (ou 0 para sair): ");
                System.out.print("\nNome -> ");
                String nome = scanner.nextLine();

                if (nome.equals("0")) {
                    // Encerra a exclusão de alunos se 0 for inserido
                    System.out.println("Encerrando a exclusão de alunos.");
                    return;
                }

                // Chama o método buscarAlunoPorNome do objeto AlunoDAO para obter informações
                // do aluno
                Aluno aluno = alunoDAO.buscarAlunoPorNome(nome);

                if (aluno != null) {
                    // Exibe informações do aluno a ser excluído
                    System.out.println("\nInformações do aluno a ser excluído:");
                    System.out.println(aluno);

                    // Solicita confirmação da exclusão

                    char confirmacao;
                    do {
                        System.out.print("Tem certeza que deseja excluir este aluno? (S/N): ");
                        confirmacao = scanner.nextLine().toUpperCase().charAt(0);
                    } while (confirmacao != 'S' && confirmacao != 'N');

                    if (confirmacao == 'S') {
                        // Chama o método excluirAluno do objeto AlunoDAO para excluir o aluno do banco
                        // de dados
                        alunoDAO.excluirAluno(aluno);

                        // Exibe uma mensagem de sucesso
                        System.out.println("Aluno excluído com sucesso!");
                    } else {
                        System.out.println("Exclusão cancelada.");
                    }

                    // Pergunta se deseja excluir outro aluno

                    char continuar;
                    do {
                        System.out.print("\nDeseja excluir outro aluno? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');

                    if (continuar == 'N') {
                        // Encerra a exclusão de alunos se não desejar excluir outro aluno
                        System.out.println("Encerrando a exclusão de alunos.");
                        return;
                    }
                } else {
                    // Exibe uma mensagem se o aluno não for encontrado no banco de dados

                    System.out.println("Aluno não encontrado.");

                    // Pergunta se deseja tentar excluir outro aluno

                    char continuar;
                    do {
                        System.out.print("\nDeseja tentar excluir outro aluno? (S/N): ");
                        continuar = scanner.nextLine().toUpperCase().charAt(0);
                    } while (continuar != 'S' && continuar != 'N');

                    if (continuar == 'N') {
                        // Encerra a exclusão de alunos se não desejar excluir outro aluno
                        System.out.println("Encerrando a exclusão de alunos.");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Trata exceções imprimindo informações de erro
        }
    }

    // MÉTODO PARA BUSCAR UM ALUNO PELO NOME

    public void buscarAlunoPorNome(Connection connection) {
        AlunoDAO alunoDAO = new AlunoDAO(connection);

        do {
            // Solicita o nome do aluno que deseja buscar
            System.out.print("\nDigite o nome do aluno que deseja buscar (ou '0' para sair): ");
            System.out.print("\nNome -> ");
            String nome = scanner.nextLine();

            if (nome.equals("0")) {
                // Encerra a busca de alunos se o usuário inserir "0"
                System.out.println("Encerrando a busca de alunos.");
                break;
            }

            try {
                // Chama o método buscarAlunoPorNome do objeto AlunoDAO para obter informações
                // do aluno
                Aluno aluno = alunoDAO.buscarAlunoPorNome(nome);

                if (aluno != null) {
                    // Exibe informações do aluno encontrado
                    System.out.println("\nAluno encontrado:");
                    System.out.println("Dados do aluno:");
                    System.out.println(aluno);
                } else {
                    // Exibe uma mensagem se o aluno não for encontrado no banco de dados
                    System.out.println("Aluno não encontrado.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
            }

            // Pergunta se deseja buscar outro aluno ou sair
            System.out.print("Deseja buscar outro aluno? (S/N): ");
            String resposta = scanner.nextLine().toUpperCase();
            if (!resposta.equals("S")) {
                System.out.println("Encerrando a busca de alunos.");
                break;
            }
        } while (true);
    }

    private static void pressioneEnterParaContinuar() {
        Scanner scanner = new Scanner(System.in);
        // Exibe uma mensagem instruindo o usuário a pressionar Enter para continuar
        System.out.println("\nPressione Enter para continuar...");

        // Aguarda até que o usuário pressione a tecla Enter
        scanner.nextLine();
    }
}
