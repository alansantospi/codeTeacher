
import java.util.ArrayList;
import java.util.List;

public class Aluno {

    public String nome;
    public String matricula;
    public String cpf;

    public Aluno(String nome, String matricula, String cpf) {
        this.nome = nome;
        this.matricula = matricula;
        this.cpf = cpf;
    }

    public String getnome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;

    }

    public String getmatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;

    }

    public String getcpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;

    }

    @Override
    public String toString() {
        return this.nome + "," + this.matricula + "," + this.cpf;
    }
}
