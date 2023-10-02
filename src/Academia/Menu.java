package src.Academia;

import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

import conexao.Conexao;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static Connection conexao; 

    public static void main(String[] args) {
        // Obtendo uma conexão com o banco de dados usando a classe Conexao
        conexao = Conexao.getConexao();

        int escolha;
        while (true) {
            limparConsole();
            System.out.println("\nMenu Principal:");
            System.out.println("1. Menu Aluno");
            System.out.println("2. Menu Instrutor");
            System.out.println("3. Sair do Programa");
            System.out.print("Escolha uma opção: ");
            escolha = lerOpcao();

            switch (escolha) {
                case 1:
                    menuAluno();
                    break;
                case 2:
                    menuInstrutor();
                    break;
                case 3:
                    System.out.println("Saindo do programa...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void menuAluno() {
        int escolha;
        while (true) {
            limparConsole();
            System.out.println("\nMenu Aluno:");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Listar Alunos");
            System.out.println("3. Editar Aluno");
            System.out.println("4. Excluir Aluno");
            System.out.println("5. Tornar Aluno Membro");
            System.out.println("6. Remover Aluno Membro");
            System.out.println("7. Listar Alunos Membros");
            System.out.println("8. Buscar Aluno por ID");
            System.out.println("9. Obter Treino");
            System.out.println("10. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            escolha = lerOpcao();

            switch (escolha) {
                case 1:
                limparConsole();
                AcademiaApp.cadastrarAluno(conexao); // Passe a conexão como argumento
                break;
                case 2:
                limparConsole();
                    AcademiaApp.listarAlunos(conexao);
                    break;
                case 3:
                limparConsole();
                AcademiaApp.editarAluno(conexao);
                break;
                case 4:
                limparConsole();
                    AcademiaApp.removerAluno(conexao);
                    break;
                case 5:
                limparConsole();
                    AcademiaApp.tornarAlunoMembro(conexao);
                    break;
                case 6:
                limparConsole();
                    AcademiaApp.removerAlunoMembro(conexao);
                    break;
                case 7:
                limparConsole();
                    AcademiaApp.listarAlunosMembros(conexao);
                    break;
                case 8:
                limparConsole();
                    AcademiaApp.buscarAlunoPorId(conexao);
                    break;
                case 9:
                limparConsole();
                    AcademiaApp.obterTreino();
                    break;
                case 10:
                limparConsole();
                    return; // Voltar ao menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void menuInstrutor() {
        int escolha;
        while (true) {
            limparConsole();
            System.out.println("\nMenu Instrutor:");
            System.out.println("1. Cadastrar Instrutor");
            System.out.println("2. Listar Instrutores");
            System.out.println("3. Editar Instrutor");
            System.out.println("4. Excluir Instrutor");
            System.out.println("5. Buscar Instrutor por ID");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            escolha = lerOpcao();

            switch (escolha) {
                case 1:
                limparConsole();
                    AcademiaApp.cadastrarInstrutor(conexao);
                    break;
                case 2:
                limparConsole();
                    AcademiaApp.listarInstrutores(conexao);
                    break;
                case 3:
                limparConsole();
                    AcademiaApp.editarInstrutor(conexao);
                    break;
                case 4:
                limparConsole();
                    AcademiaApp.excluirInstrutor(conexao);
                    break;
                case 5:
                limparConsole();
                    AcademiaApp.buscarInstrutorPorId(conexao);
                    break;
                case 6:
                    return; // Voltar ao menu principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

   // Função para ler uma opção numérica do usuário

    private static int lerOpcao() {
        int opcao;
        do {
            try {
                opcao = scanner.nextInt(); // Lê um número inteiro da entrada padrão (teclado)
                if (opcao < 1) {
                    System.out.println("Opção deve ser maior ou igual a 1. Tente novamente.");
                    // Se a opção for menor que 1, exibe uma mensagem de erro
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Opção inválida. Tente novamente.");
                opcao = 0;
                // Se a entrada não for um número inteiro válido, exibe uma mensagem de erro e define opcao como 0
            } finally {
                scanner.nextLine(); // Limpa o buffer de entrada
            }
        } while (opcao < 1); // Continua solicitando até que uma opção válida (>= 1) seja fornecida
        return opcao; // Retorna a opção válida
    }

  // Método para limpar o console

    public static void limparConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // Verifica se o sistema operacional é Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                // Executa o comando "cls" no prompt de comando do Windows para limpar o console.
            } else {
                // Se não for Windows, assume-se que é um sistema Unix-like (Linux ou macOS).
                new ProcessBuilder("clear").inheritIO().start().waitFor();
                // Executa o comando "clear" em sistemas Unix-like para limpar o console.
            }
        } catch (IOException | InterruptedException e) {
        }
    }
}
