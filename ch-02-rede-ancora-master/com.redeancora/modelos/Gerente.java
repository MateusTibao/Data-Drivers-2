package modelos;

import java.util.ArrayList;

public class Gerente extends Pessoa {

	private String funcional;
    private ArrayList<Funcionario> funcionarios;

    public Gerente(String nome, String telefone, String cpf, String funcional) {
        super(nome, telefone, cpf);
        this.funcional = funcional;
        this.funcionarios = new ArrayList<>();
    }

    public void exibirFuncionarios() {
        System.out.println("Informações do Gerente:");
        System.out.println("Nome: " + getNome() + ", Funcional: " + funcional);

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário associado ao gerente.");
        } else {
            System.out.println("Lista de funcionários do gerente:");
            for (Funcionario funcionario : funcionarios) {
                System.out.println("Nome: " + funcionario.getNome() + ", Funcional: " + funcionario.getFuncional());
            }
        }
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        if (!funcionarios.contains(funcionario)) {
            funcionarios.add(funcionario);
            System.out.println("Funcionário " + funcionario.getNome() + " adicionado com sucesso.");
        } else {
            System.out.println("Funcionário " + funcionario.getNome() + " já está na lista.");
        }
    }

    public void removerFuncionario(Funcionario funcionario) {
        if (funcionarios.contains(funcionario)) {
            funcionarios.remove(funcionario);
            System.out.println("Funcionário " + funcionario.getNome() + " removido com sucesso.");
        } else {
            System.out.println("Funcionário " + funcionario.getNome() + " não encontrado na lista.");
        }
    }
    
    public String getFuncional() {
		return funcional;
	}

	public void setFuncional(String funcional) {
		this.funcional = funcional;
	}

	public ArrayList<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(ArrayList<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}


}
