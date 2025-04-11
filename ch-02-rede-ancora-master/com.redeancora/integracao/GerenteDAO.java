package integracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelos.Gerente;

public class GerenteDAO {
    private PessoaDAO pessoaDAO = new PessoaDAO();

    public void inserirGerente(Gerente gerente, Connection conn) throws SQLException {
        try {
            // 1. Inserir dados na tabela pessoa e capturar o ID
            System.out.println("Inserindo pessoa (gerente)...");
            pessoaDAO.inserirPessoa(gerente, conn); // seta o ID da pessoa no gerente

            // 2. Inserir na tabela gerente (sem mexer no id_gerente que é gerado)
            String sql = "INSERT INTO gerente (funcional, id_pessoa) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, gerente.getFuncional());
                stmt.setInt(2, gerente.getId()); // FK para pessoa

                stmt.executeUpdate();
                System.out.println("Gerente " + gerente.getNome() + " inserido com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("❌ ERRO ao inserir gerente: " + gerente.getNome());
            e.printStackTrace();
            throw e;
        }
    }
}
