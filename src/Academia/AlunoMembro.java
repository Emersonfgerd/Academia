package src.Academia;

public class AlunoMembro extends Aluno {

    public AlunoMembro(int id, String nome, int idade, boolean problemaSaude, String sexo, String tipoPagamento) {
        // Este é o construtor da classe AlunoMembro, que recebe informações do aluno membro como parâmetros.
        // Ele chama o construtor da superclasse (Aluno) usando 'super' para inicializar os atributos herdados.
        super(id, nome, idade, problemaSaude, sexo, tipoPagamento);
    }

    @Override
    public String toString() {
        // Este método sobrescrito converte o objeto AlunoMembro em uma representação de string.
        return super.toString(); // Chama o método toString da superclasse (Aluno) para obter uma representação de string do aluno membro.
    }
}