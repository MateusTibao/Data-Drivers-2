package integracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import modelos.Pessoa;
import oracle.jdbc.OraclePreparedStatement;

public class PessoaDAO {

    public void inserirPessoa(Pessoa pessoa, Connection conn) throws SQLException {
        String sql = "INSERT INTO pessoa (nome, telefone, cpf) VALUES (?, ?, ?) RETURNING id INTO ?";
        
        try (OraclePreparedStatement stmt = (OraclePreparedStatement) conn.prepareStatement(sql)) {
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getTelefone());
            stmt.setString(3, pessoa.getCpf());
            stmt.registerReturnParameter(4, Types.INTEGER);  // captura do ID gerado

            stmt.executeUpdate();

            // Recupera o ID gerado
            int idGerado = stmt.getReturnResultSet().next() ? stmt.getReturnResultSet().getInt(1) : -1;
            pessoa.setId(idGerado);

            System.out.println("Pessoa " + pessoa.getNome() + " inserida com sucesso! ID gerado: " + idGerado);
        } catch (SQLException e) {
            System.out.println("❌ ERRO ao inserir pessoa: " + pessoa.getNome());
            throw e;
        }
    }
}
