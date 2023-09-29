package Academia;
import java.util.Objects;
public class Instrutor {
    private int id;
    private String nome;
    private String turnoTrabalho;

    public Instrutor(int id, String nome,String turnoTrabalho){
        this.id = id;
        this.nome = nome;
        this.turnoTrabalho = turnoTrabalho;

    }
    public int getId(){
        return id;
    }
    public String getNome(){
        return nome;
    }
    public String getTurnoTrabalho(){
        return turnoTrabalho;
    }

    public void editarInstrutor(String novoNome, String novoTurnoTrabalho){
        this.nome = novoNome;
        this.turnoTrabalho = novoTurnoTrabalho;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Instrutor instrutor = (Instrutor) o;
         return id == instrutor.id;
    }
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
    @Override
    public String toString(){
        return "ID:" + id + "\nNome:" + nome + "\nTurno de trabalho:" + turnoTrabalho;
    }


}
