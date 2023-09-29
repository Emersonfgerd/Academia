package Academia;
import java.util.Objects;

public class Aluno {
    private int id;
    private String nome;
    private int idade;
    private String sexo;
    private boolean problemaSaude;
    private String tipoPagamento;
    private boolean membro;

    public Aluno(int id, String nome, int idade, boolean problemaSaude, String sexo, String tipoPagamento){
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.problemaSaude= problemaSaude;
        this.tipoPagamento = tipoPagamento;
        this.membro = false;
    }

    public int getId() {
        return id;
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

    public boolean isProblemaSaude() {
        return problemaSaude;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public boolean isMembro() {
        return membro;
    }
    
    public void tornarMembro(){
        this.membro = true;
    }

    public void removerMembro(){
        this.membro = false;
    }
    public void editarAluno(String nome, int idade, boolean problemaSaude, String tipoPagamento){
        this.nome = nome;
        this.idade = idade;
        this.problemaSaude = problemaSaude;
        this.tipoPagamento = tipoPagamento;
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return id == aluno.id;
    }

    @Override
    public int hasCode(){
        return Objects.hash(id);
    }

    @Override
    public String toString (){
        return "ID: " + id +
        "\nNome: " + nome +
        "\nIdade: " + idade +
        "\nSexo: " + sexo +
        "\nProblema de Saude: " + (problemaSaude ? "Sim" : "Nao") +
        "\nTipo de pagamento: " + tipoPagamento +
        "\nMembro: " + (membro ? "Sim" : "Nao");


    }
}
