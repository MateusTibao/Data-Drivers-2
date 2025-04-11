package integracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

import modelos.Oficina;

public class OficinaDAO {


	public void inserirOficina(Oficina oficina, Connection conn) throws SQLException {
	    String sql = "INSERT INTO oficina (nome, endereco, email, telefone, id_representante) " +
	                 "VALUES (?, ?, ?, ?, ?) RETURNING id INTO ?";

	    try (OraclePreparedStatement stmt = (OraclePreparedStatement) conn.prepareStatement(sql)) {
	        stmt.setString(1, oficina.getNome());
	        stmt.setString(2, oficina.getEndereco());
	        stmt.setString(3, oficina.getEmail());
	        stmt.setString(4, oficina.getTelefone());
	        stmt.setInt(5, oficina.getRepresentante().getId());

	        stmt.registerReturnParameter(6, OracleTypes.NUMBER);

	        stmt.executeUpdate();

	        try (ResultSet rs = stmt.getReturnResultSet()) {
	            if (rs.next()) {
	                int idGerado = rs.getInt(1);
	                oficina.setId(idGerado);
	                System.out.println("✅ Oficina inserida com ID: " + idGerado);
	            } else {
	                throw new SQLException("Falha ao obter o ID gerado da oficina.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("❌ Erro ao inserir oficina: " + oficina.getNome());
	        e.printStackTrace();
	        throw e;
	    }
	}
}
