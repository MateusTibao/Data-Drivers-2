package modelos;

public class Produto {
    private static int contadorId = 0;
    private final int id;
    private final String nome;
    private final String descricao;
    private double preco;
    private int estoque;

    public Produto(String nome, String descricao, double preco, int estoque) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        if (estoque < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo.");
        }
        this.id = contadorId++;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
    }


    public int getId() {
		return id;
	}


	public void setPreco(double preco) {
		this.preco = preco;
	}


	public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public void exibirInformacoesProduto() {
        System.out.println("Informações do Produto:");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Descrição: " + descricao);
        System.out.println("Preço: R$ " + preco);
        System.out.println("Estoque: " + estoque + " unidades");
    }
}
