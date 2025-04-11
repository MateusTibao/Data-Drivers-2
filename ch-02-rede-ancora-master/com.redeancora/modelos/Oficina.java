 package modelos;

public class Oficina {
	private static int contadorId = 0;
    private int id;
    private final String nome;
    private final String endereco;
    private String email;
    private String telefone;
    private Pessoa representante;

    public Oficina(String nome, String endereco, String email, String telefone, Pessoa representante) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio.");
        }
        if (telefone == null || telefone.isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser nulo ou vazio.");
        }
        this.id = contadorId++;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        this.representante = representante;
    }

    public String getNome() {
        return nome;
    }

    public void exibirInformacoesOficina() {
        System.out.println("Informações da Oficina:");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Endereço: " + endereco);
        System.out.println("Email: " + email);
        System.out.println("Telefone: " + telefone);
        if (representante != null) {
            System.out.println("Representante: " + representante.getNome());
        } else {
            System.out.println("Representante não informado.");
        }
    }
    
    public static int getContadorId() {
		return contadorId;
	}

	public static void setContadorId(int contadorId) {
		Oficina.contadorId = contadorId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Pessoa getRepresentante() {
		return representante;
	}

	public void setRepresentante(Pessoa representante) {
		this.representante = representante;
	}

	public int getId() {
		return id;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setId(int Id) {
		this.id = Id;
	}

}
