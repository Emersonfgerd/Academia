package src.Academia;

import java.util.Objects;

public class Instrutor {
    // Atributos da classe Instrutor
    private int id;
    private String nome;
    private String turnoTrabalho;

    // Construtor da classe Instrutor
    public Instrutor(int id, String nome, String turnoTrabalho) {
        // Inicialização dos atributos com os valores passados como argumento
        this.id = id;
        this.nome = nome;
        this.turnoTrabalho = turnoTrabalho;
    }

    // Métodos de acesso para os atributos da classe

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTurnoTrabalho() {
        return turnoTrabalho;
    }
    public void editarInstrutor(String novoNome, String novoTurnoTrabalho) {
        // Este método permite editar as informações de um instrutor, recebendo um novo nome e turno de trabalho como parâmetros.
        this.nome = novoNome; // Define o novo nome do instrutor.
        this.turnoTrabalho = novoTurnoTrabalho; // Define o novo turno de trabalho do instrutor.
    }
    
    @Override
    public boolean equals(Object o) {
        // Este método sobrescrito verifica se dois objetos Instrutor são iguais com base em seus IDs.
        if (this == o) return true; // Se o objeto comparado for o próprio objeto, eles são iguais.
        if (o == null || getClass() != o.getClass()) return false; // Se o objeto comparado for nulo ou não for da mesma classe, eles são diferentes.
        Instrutor instrutor = (Instrutor) o; // Faz o cast do objeto comparado para Instrutor.
        return id == instrutor.id; // Compara os IDs dos instrutores para determinar a igualdade.
    }
    
    @Override
    public int hashCode() {
        // Este método sobrescrito calcula um valor hash com base no ID do instrutor.
        return Objects.hash(id); // Calcula o hash usando o ID do instrutor.
    }
    
    @Override
    public String toString() {
        // Este método sobrescrito converte o objeto Instrutor em uma representação de string.
        return "ID: " + id + "\nNome: " + nome + "\nTurno de trabalho: " + turnoTrabalho;
        // Retorna uma representação de string que inclui o ID, nome e turno de trabalho do instrutor.
    }    
}
