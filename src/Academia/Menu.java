package src.Academia;

import java.util.Scanner;

import src.AcademiaDAO.AlunoDAO;
import src.conexao.Conexao;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            limparConsole();
            System.out.println("\n   MENU ACADEMIA");
            System.out.println("-------------------");
            System.out.println(" 1. Menu Aluno");
            System.out.println(" 2. Menu Instrutor");
            System.out.println(" 3. Menu Treino");
            System.out.println(" 0. Sair");
            System.out.println("-------------------");
            System.out.print("Escolha uma opção: ");

            int opcaoPrincipal = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcaoPrincipal) {
                case 1:
                    menuAluno();
                    break;
                case 2:
                    menuInstrutor();
                    break;
                case 3:
                    menuTreino();
                    break;
                case 0:
                    System.out.println("Encerrando o programa.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuAluno() {
        // Cria um objeto Aluno com valores iniciais
        Aluno aluno = new Aluno("", 0, null, false);
        // Cria um objeto Treino com valores iniciais e um Scanner para entrada do
        // usuário
        Treino treino = new Treino(null, null, 0, null);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            limparConsole();
            System.out.println("\n    MENU ALUNO");
            System.out.println("-----------------------");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Listar Alunos");
            System.out.println("3. Editar Aluno");
            System.out.println("4. Excluir Aluno");
            System.out.println("5. Buscar Aluno");
            System.out.println("6. Buscar meu Treino");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("-----------------------");
            System.out.print("Escolha uma opção: ");

            int opcaoAluno = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcaoAluno) {
                case 1:
                    limparConsole();
                    aluno.cadastrarAluno(Conexao.getConexao());
                    break;
                case 2:
                    limparConsole();
                    AlunoDAO alunoDAO = new AlunoDAO(Conexao.getConexao());
                    aluno.listarAlunos(alunoDAO);

                    break;
                case 3:
                    limparConsole();
                    aluno.editarAluno(Conexao.getConexao());
                    break;
                case 4:
                    limparConsole();
                    aluno.excluirAluno(Conexao.getConexao());
                    break;
                case 5:
                    limparConsole();
                    aluno.buscarAlunoPorNome(Conexao.getConexao());
                    break;
                case 6:
                    limparConsole();
                    treino.buscarTreinoPorNome(Conexao.getConexao());
                    break;
                case 0:
                    return; // Voltar ao Menu Principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuInstrutor() {
        // Cria um objeto Instrutor com valores iniciais vazios
        Instrutor instrutor = new Instrutor(null, 0, null, null);
        // Cria um objeto Treino com valores iniciais vazios e um Scanner para entrada
        Treino treino = new Treino(null, null, 0, null);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            limparConsole();
            System.out.println("\n    MENU INSTRUTOR");
            System.out.println("-----------------------");
            System.out.println("1. Cadastrar Instrutor");
            System.out.println("2. Listar Instrutores");
            System.out.println("3. Editar Instrutor");
            System.out.println("4. Excluir Instrutor");
            System.out.println("5. Buscar Instrutor");
            System.out.println("6. Cadastrar Treino");
            System.out.println("7. Listar Treinos");
            System.out.println("8. Editar Treino");
            System.out.println("9. Excluir Treino");
            System.out.println("10. Buscar Treino");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("-----------------------");
            System.out.print("Escolha uma opção: ");

            int opcaoInstrutor = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcaoInstrutor) {
                case 1:
                    limparConsole();
                    instrutor.cadastrarInstrutor(Conexao.getConexao());
                    break;
                case 2:
                    limparConsole();
                    instrutor.listarInstrutores(Conexao.getConexao());
                    break;
                case 3:
                    limparConsole();
                    instrutor.editarInstrutor(Conexao.getConexao());
                    break;
                case 4:
                    limparConsole();
                    instrutor.excluirInstrutor(Conexao.getConexao());
                    break;
                case 5:
                    limparConsole();
                    instrutor.buscarInstrutorPorNome(Conexao.getConexao());
                    break;
                case 6:
                    limparConsole();
                    treino.cadastrarTreino(Conexao.getConexao());
                    break;
                case 7:
                    limparConsole();
                    treino.listarTreinos(Conexao.getConexao());
                    break;
                case 8:
                    limparConsole();
                    treino.editarTreino(Conexao.getConexao());
                    break;
                case 9:
                    limparConsole();
                    treino.excluirTreino(Conexao.getConexao());
                    break;
                case 10:
                    limparConsole();
                    treino.buscarTreinoPorNome(Conexao.getConexao());
                    break;
                case 0:
                    return; // Voltar ao Menu Principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuTreino() {
        // Cria um objeto Treino com valores iniciais vazios e um Scanner para entrada
        Treino treino = new Treino(null, null, 0, null);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            limparConsole();
            System.out.println("\n      MENU TREINO");
            System.out.println("-----------------------");
            System.out.println("1. Cadastrar Treino");
            System.out.println("2. Listar Treinos");
            System.out.println("3. Editar Treino");
            System.out.println("4. Excluir Treino");
            System.out.println("5. Buscar Treino");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("-----------------------");
            System.out.print("Escolha uma opção: ");

            int opcaoTreino = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcaoTreino) {
                case 1:
                    limparConsole();
                    treino.cadastrarTreino(Conexao.getConexao());
                    break;
                case 2:
                    limparConsole();
                    treino.listarTreinos(Conexao.getConexao());
                    break;
                case 3:
                    limparConsole();
                    treino.editarTreino(Conexao.getConexao());
                    break;
                case 4:
                    limparConsole();
                    treino.excluirTreino(Conexao.getConexao());
                    break;
                case 5:
                    limparConsole();
                    treino.buscarTreinoPorNome(Conexao.getConexao());
                    break;
                case 0:
                    return; // Voltar ao Menu Principal
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void limparConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
