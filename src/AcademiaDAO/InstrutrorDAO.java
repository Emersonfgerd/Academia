package src.AcademiaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.Academia.Instrutor;

public class InstrutorDAO {
    private Connection connection;

    public InstrutorDAO(Connection connection) {
        this.connection = connection;
    }

    public void cadastrarInstrutor(Instrutor instrutor) {
        try {
            String sql = "INSERT INTO instrutores (nome, idade, sexo, turno) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, instrutor.getNome());
            statement.setInt(2, instrutor.getIdade());
            statement.setString(3, instrutor.getSexo());
            statement.setString(4, instrutor.getTurnoDeTrabalho());
            statement.executeUpdate();
            statement.close();
            System.out.println("Instrutor cadastrado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarInstrutores() {
        try {
            String sql = "SELECT nome, idade, sexo, turno FROM instrutores";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Nenhum instrutor cadastrado.");
            } else {
                System.out.println("Lista de Instrutores:\n");
                while (resultSet.next()) {
                    Instrutor instrutor = new Instrutor(
                            resultSet.getString("nome"),
                            resultSet.getInt("idade"),
                            resultSet.getString("sexo"),
                            resultSet.getString("turno"));

                    System.out.println(instrutor);
                    System.out.println("-------------");
                }
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editarInstrutor(String nome, String novoNome, int novaIdade, String novoTurno) throws SQLException {
        String updateSQL = "UPDATE instrutores SET nome=?, idade=?, turno=? WHERE nome=?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
        updateStatement.setString(1, novoNome);
        updateStatement.setInt(2, novaIdade);
        updateStatement.setString(3, novoTurno);
        updateStatement.setString(4, nome);

        updateStatement.executeUpdate();
        updateStatement.close();
    }

    public void excluirInstrutor(Instrutor instrutor) throws SQLException {
        String deleteSQL = "DELETE FROM instrutores WHERE nome = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {
            deleteStatement.setString(1, instrutor.getNome());
            deleteStatement.executeUpdate();
        }
    }

    public Instrutor buscarInstrutorPorNome(String nome) {
        Instrutor instrutor = null;

        try {
            String sql = "SELECT * FROM instrutores WHERE nome=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                instrutor = new Instrutor(
                        resultSet.getString("nome"),
                        resultSet.getInt("idade"),
                        resultSet.getString("sexo"),
                        resultSet.getString("turno"));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instrutor;
    }
}