package modelos;

public class Pessoa {
    private static int contadorId = 0;
	private int id;
    private final String nome;
    private final String telefone;
    private final String cpf;

    public Pessoa(String nome, String telefone, String cpf) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        if (telefone == null || telefone.isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser nulo ou vazio.");
        }
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio.");
        }
        this.id = ++contadorId;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
    }


    public String getNome() {
        return nome;
    }
    
    
    public static int getContadorId() {
		return contadorId;
	}


	public static void setContadorId(int contadorId) {
		Pessoa.contadorId = contadorId;
	}


	public int getId() {
		return id;
	}


	public String getTelefone() {
		return telefone;
	}


	public String getCpf() {
		return cpf;
	}


	public void setId(int Id) {
		this.id = Id;		
	}




}
