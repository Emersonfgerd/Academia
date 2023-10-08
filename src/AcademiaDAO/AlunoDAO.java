package src.AcademiaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.Academia.Aluno;
import src.conexao.Conexao;

public class AlunoDAO {
    private Connection connection;

    public AlunoDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarAluno(Aluno aluno) {
        try {
            // Prepara a consulta SQL para cadastrar o aluno no banco de dados
            String sql = "INSERT INTO alunos (nome, idade, sexo, problema_de_saude) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, aluno.getNome());
            statement.setInt(2, aluno.getIdade());
            statement.setString(3, aluno.getSexo());
            statement.setBoolean(4, aluno.temProblemaDeSaude());
            statement.executeUpdate();
            statement.close();
            System.out.println("Aluno cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
        }
    }

    public List<Aluno> listarAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        try {
            // Prepara a consulta SQL para selecionar todos os alunos
            String sql = "SELECT nome, idade, sexo, problema_de_saude FROM alunos";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            // Verifica se há instrutores no resultado

            while (resultSet.next()) {
                // Cria um objeto Aluno com os dados obtidos do banco de dados
                Aluno aluno = new Aluno(
                        resultSet.getString("nome"),
                        resultSet.getInt("idade"),
                        resultSet.getString("sexo"),
                        resultSet.getBoolean("problema_de_saude"));
                alunos.add(aluno);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
        }
        return alunos;
    }

    public void editarAluno(Aluno aluno) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = Conexao.getConexao(); // Substitua por sua lógica de obtenção de conexão

            // Consulta SQL para atualizar os dados de um aluno
            String sql = "UPDATE alunos SET idade=?, sexo=?, problema_de_saude=? WHERE nome=?";
            preparedStatement = connection.prepareStatement(sql);

            // Define os valores dos parâmetros na consulta
            preparedStatement.setInt(1, aluno.getIdade());
            preparedStatement.setString(2, aluno.getSexo());
            preparedStatement.setBoolean(3, aluno.temProblemaDeSaude());
            preparedStatement.setString(4, aluno.getNome());

            // Executa a consulta de atualização
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
        } finally {
            // Fecha a conexão e o PreparedStatement
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void excluirAluno(Aluno aluno) throws SQLException {
        // Prepara uma consulta SQL para excluir o aluno do banco de dados
        String deleteSQL = "DELETE FROM alunos WHERE nome = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {
            deleteStatement.setString(1, aluno.getNome());
            deleteStatement.executeUpdate();
        }
    }

    public Aluno buscarAlunoPorNome(String nome) throws SQLException {
        // Prepara uma consulta SQL para buscar o aluno com o nome fornecido
        String sql = "SELECT * FROM alunos WHERE nome=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Cria um objeto Aluno com as informações do aluno encontrado no banco de dados
                Aluno aluno = new Aluno(
                        resultSet.getString("nome"),
                        resultSet.getInt("idade"),
                        resultSet.getString("sexo"),
                        resultSet.getBoolean("problema_de_saude"));

                return aluno;
            } else {
                return null; // Retorna null se o aluno não for encontrado
            }
        }
    }
}