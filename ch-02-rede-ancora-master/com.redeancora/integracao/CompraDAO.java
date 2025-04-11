package integracao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelos.Compra;
import modelos.ItemCompra;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;

public class CompraDAO {

    public void inserirCompra(Compra compra, Connection conn) throws SQLException {
        String sql = "INSERT INTO compra (id_oficina, data, valor_total, finalizada) " +
                     "VALUES (?, ?, ?, ?) RETURNING id INTO ?";
        OraclePreparedStatement stmt = (OraclePreparedStatement) conn.prepareStatement(sql);

        stmt.setInt(1, compra.getOficina().getId());
        stmt.setDate(2, java.sql.Date.valueOf(compra.getData()));
        stmt.setDouble(3, compra.getValorTotal());
        stmt.setString(4, compra.isFinalizada() ? "S" : "N");

        stmt.registerReturnParameter(5, OracleTypes.NUMBER);
        stmt.executeUpdate();

        try (ResultSet rs = stmt.getReturnResultSet()) {
            if (rs.next()) {
                int idGerado = rs.getInt(1);
                compra.setId(idGerado);
                System.out.println("Compra inserida com sucesso! ID: " + idGerado);
            }
        }
    }
}
