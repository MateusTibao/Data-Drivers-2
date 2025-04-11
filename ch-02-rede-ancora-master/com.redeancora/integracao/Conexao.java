package integracao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection obterConexao() throws SQLException {
        String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
        String user = "rm552596";
        String password = "020503";

        try {
            System.out.println("Tentando conectar ao banco...");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso!");
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar no banco:");
            e.printStackTrace();
            throw e; // repropaga para o Main
        }
    }
}
