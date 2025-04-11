package integracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import modelos.Produto;

public class ProdutoDAO {

	public void inserirProduto(Produto produto, Connection conn) throws SQLException {
	    String sql = "INSERT INTO produto (nome, descricao, preco, estoque) VALUES (?, ?, ?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, produto.getNome());
	        stmt.setString(2, produto.getDescricao());
	        stmt.setDouble(3, produto.getPreco());
	        stmt.setInt(4, produto.getEstoque());
	        stmt.executeUpdate();

	        System.out.println("Produto " + produto.getNome() + " inserido com sucesso!");
	    } catch (SQLException e) {
	        System.out.println(">>> ERRO ao inserir produto: " + produto.getNome());
	        e.printStackTrace(); 
	        throw e;
	    }
	}
    
    public void listarProdutos(Connection conn) throws SQLException {
        String sql = "SELECT nome FROM produto";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            System.out.println("\nProdutos cadastrados no banco:");
            while (rs.next()) {
                String nome = rs.getString("nome");
                System.out.println("- " + nome);
            }
        }
    }

	public ArrayList<Produto> buscarTodosProdutos(Connection conn) throws SQLException {
		String sql = "SELECT nome, descricao, preco, estoque FROM produto";
		ArrayList<Produto> produtos = new ArrayList<>();

		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				String nome = rs.getString("nome");
				String descricao = rs.getString("descricao");
				double preco = rs.getDouble("preco");
				int estoque = rs.getInt("estoque");

				Produto produto = new Produto(nome, descricao, preco, estoque);
				produtos.add(produto);
			}
		}

		return produtos;
	}


	public ArrayList<Produto> orderBy(ArrayList<Produto> lista, String criterio, String ordem) {
		boolean crescente = ordem.equalsIgnoreCase("C");

		return quickSort(lista, criterio.toLowerCase(), crescente);
	}

	private ArrayList<Produto> quickSort(ArrayList<Produto> array, String criterio, boolean crescente) {
		if (array.size() < 2) return array;

		Produto pivo = array.get(0);
		ArrayList<Produto> menores = new ArrayList<>();
		ArrayList<Produto> maiores = new ArrayList<>();

		for (int i = 1; i < array.size(); i++) {
			Produto atual = array.get(i);
			boolean condicao;

			if (criterio.equals("preco")) {
				condicao = crescente ? atual.getPreco() < pivo.getPreco() : atual.getPreco() >= pivo.getPreco();
			} else if (criterio.equals("estoque")) {
				condicao = crescente ? atual.getEstoque() < pivo.getEstoque() : atual.getEstoque() >= pivo.getEstoque();
			} else {
				throw new IllegalArgumentException("Critério inválido. Use 'preco' ou 'estoque'.");
			}

			if (condicao) {
				menores.add(atual);
			} else {
				maiores.add(atual);
			}
		}

		ArrayList<Produto> ordenado = new ArrayList<>();
		ordenado.addAll(quickSort(menores, criterio, crescente));
		ordenado.add(pivo);
		ordenado.addAll(quickSort(maiores, criterio, crescente));
		return ordenado;
	}


}
