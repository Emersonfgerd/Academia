package src.AcademiaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.Academia.Treino;


public class TreinoDAO {

    // Método para cadastrar um treino no banco de dados
    public void cadastrarTreino(Connection connection, String nomeTreino, String diaTreino, int exercicios, String tipoTreino) {
        try {
            // Preparar a instrução SQL para inserção dos dados do treino no banco de dados
            String sql = "INSERT INTO treinos (nome_treino, dia_treino, exercicios, tipo_treino) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nomeTreino);
            statement.setString(2, diaTreino);
            statement.setInt(3, exercicios);
            statement.setString(4, tipoTreino);

            // Executa a instrução SQL para inserir os dados do treino no banco de dados
            statement.executeUpdate();
            statement.close(); // Fecha a declaração preparada para liberar recursos
           
        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
        }
    }

    // Método para listar todos os treinos cadastrados
    public List<Treino> listarTreinos(Connection connection) {
        List<Treino> treinos = new ArrayList<>();

        try {
            // Prepara a instrução SQL para selecionar todos os dados dos treinos
            String sql = "SELECT nome_treino, dia_treino, exercicios, tipo_treino FROM treinos";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Executa a consulta SQL e obtém o resultado em um ResultSet
            ResultSet resultSet = statement.executeQuery();

            // Itera pelos resultados no ResultSet e cria objetos Treino
            while (resultSet.next()) {
                String nomeTreino = resultSet.getString("nome_treino");
                String diaTreino = resultSet.getString("dia_treino");
                int exercicios = resultSet.getInt("exercicios");
                String tipoTreino = resultSet.getString("tipo_treino");

                Treino treino = new Treino(nomeTreino, diaTreino, exercicios, tipoTreino);
                treinos.add(treino);
            }

            // Fecha o ResultSet e a declaração preparada para liberar recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Trata exceções SQL imprimindo informações de erro
        }

        return treinos;
    }

    // Método para editar um treino existente
    public static void editarTreino(Connection connection, Treino treino, String novoNome) throws SQLException {
        String sql = "UPDATE treinos SET nome_treino=?, dia_treino=?, exercicios=?, tipo_treino=? WHERE nome_treino=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, treino.getNomeTreino());
        statement.setString(2, treino.getDiaTreino());
        statement.setInt(3, treino.getExercicios());
        statement.setString(4, treino.getTipoTreino());
        statement.setString(5, novoNome);

        statement.executeUpdate();
        statement.close();
    }

    // Método para excluir um treino
    public static void excluirTreino(Connection connection, String nomeTreino) throws SQLException {
        String sql = "DELETE FROM treinos WHERE nome_treino=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nomeTreino);
        statement.executeUpdate();
        statement.close();
    }

    // Método para buscar um treino pelo nome
    public static Treino buscarTreinoPorNome(Connection connection, String nomeTreino) throws SQLException {
        String sql = "SELECT nome_treino, dia_treino, exercicios, tipo_treino FROM treinos WHERE nome_treino=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nomeTreino);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String diaTreino = resultSet.getString("dia_treino");
            int exercicios = resultSet.getInt("exercicios");
            String tipoTreino = resultSet.getString("tipo_treino");
            Treino treino = new Treino(nomeTreino, diaTreino, exercicios, tipoTreino);
            
            resultSet.close();
            statement.close();
            return treino;
        } else {
            resultSet.close();
            statement.close();
            return null;
        }
    }

    public void cadastrarTreino(Connection connection, Treino treino) {
    }
}