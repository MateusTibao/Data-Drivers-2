package modelos;

public class Funcionario extends Pessoa {
    private String funcional;
    private Gerente gerente;

    public Funcionario(String nome, String telefone, String cpf, String funcional) {
        super(nome, telefone, cpf);
        this.funcional = funcional;
    }

    public String getFuncional() {
        return funcional;
    }

    public void setFuncional(String funcional) {
        this.funcional = funcional;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }
}
