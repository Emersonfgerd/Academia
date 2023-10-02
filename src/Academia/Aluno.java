package src.Academia;

import java.util.Objects;

public class Aluno {
    // Atributos da classe Aluno
    private int id;
    private String nome;
    private int idade;
    private String sexo;
    private boolean problemaSaude;
    private String tipoPagamento;
    private boolean membro;

    // Construtor da classe Aluno
    public Aluno(int id, String nome, int idade, boolean problemaSaude, String sexo, String tipoPagamento) {
        // Inicialização dos atributos com os valores passados como argumento
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.problemaSaude = problemaSaude;
        this.tipoPagamento = tipoPagamento;
        this.membro = false; // Inicialmente, o aluno não é um membro
    }

    // Métodos de acesso para os atributos da classe

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSexo() {
        return sexo;
    }

    public int getIdade() {
        return idade;
    }

    public boolean isProblemaSaude() {
        return problemaSaude;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public boolean isMembro() {
        return membro;
    }

    public void editarAluno(String nome, int idade, boolean problemaSaude, String tipoPagamento) {
        // Este método permite editar as informações de um aluno, recebendo como parâmetros o novo nome, idade, estado de saúde e tipo de pagamento.
        this.nome = nome;             // Define o novo nome do aluno.
        this.idade = idade;           // Define a nova idade do aluno.
        this.problemaSaude = problemaSaude; // Define o novo estado de saúde do aluno (verdadeiro ou falso).
        this.tipoPagamento = tipoPagamento; // Define o novo tipo de pagamento do aluno.
    }

    @Override
    public boolean equals(Object o) {
        // Este método sobrescrito verifica se dois objetos Aluno são iguais, comparando seus IDs.
        if (this == o) return true; // Se os objetos forem os mesmos, são iguais.
        if (o == null || getClass() != o.getClass()) return false; // Se o objeto não for da mesma classe, não são iguais.
        Aluno aluno = (Aluno) o;   // Converte o objeto genérico para um objeto Aluno.
        return id == aluno.id;      // Compara os IDs dos alunos para determinar a igualdade.
    }

    @Override
    public int hashCode() {
        // Este método sobrescrito gera um código hash para o objeto Aluno com base no seu ID.
        return Objects.hash(id);    // Gera o código hash usando o ID do aluno.
    }

    @Override
    public String toString() {
        // Este método sobrescrito converte o objeto Aluno em uma representação de string formatada.
        return "ID: " + id +                // Exibe o ID do aluno.
                "\nNome: " + nome +          // Exibe o nome do aluno.
                "\nIdade: " + idade +        // Exibe a idade do aluno.
                "\nSexo: " + sexo +          // Exibe o sexo do aluno (se existir).
                "\nProblema de Saúde: " + (problemaSaude ? "Sim" : "Não") +  // Exibe o estado de saúde do aluno como "Sim" ou "Não".
                "\nTipo de Pagamento: " + tipoPagamento +                  // Exibe o tipo de pagamento do aluno.
                "\nMembro: " + (membro ? "Sim" : "Não");                 // Exibe se o aluno é membro ou não.
    }

    public void setMembro(boolean membro2) {
        // Este método permite definir se um aluno é membro ou não com base no parâmetro booleano fornecido.
        // Não está claro como esse método é usado, já que ele não faz nada com o parâmetro 'membro2'.
    }
}

