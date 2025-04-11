package integracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelos.Funcionario;

public class FuncionarioDAO {
    private PessoaDAO pessoaDAO = new PessoaDAO();

    public void inserirFuncionario(Funcionario funcionario, Connection conn) throws SQLException {
        try {
            // 1. Inserir a pessoa e capturar o ID
            pessoaDAO.inserirPessoa(funcionario, conn);
            int idPessoa = funcionario.getId(); // já vem preenchido após inserção

            // 2. Inserir na tabela funcionario com o mesmo ID
            String sql = "INSERT INTO funcionario (funcional, id_gerente, id_pessoa) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, funcionario.getFuncional());

                if (funcionario.getGerente() != null) {
                    stmt.setInt(2, funcionario.getGerente().getId());
                } else {
                    stmt.setNull(2, java.sql.Types.INTEGER);
                }

                stmt.setInt(3, idPessoa);

                stmt.executeUpdate();
                System.out.println("Funcionário " + funcionario.getNome() + " adicionado com sucesso.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir funcionário: " + funcionario.getNome());
            e.printStackTrace();
            throw e;
        }
    }

    public void removerFuncionarioPorId(int id, Connection conn) throws SQLException {
        try {
            // Remover primeiro os registros dependentes (ex: oficina)
            String sqlOficina = "DELETE FROM oficina WHERE id_representante = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlOficina)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // Remover da tabela funcionario (filha)
            String sqlFuncionario = "DELETE FROM funcionario WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlFuncionario)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            // Remover da tabela pessoa (mãe)
            String sqlPessoa = "DELETE FROM pessoa WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlPessoa)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            System.out.println("Funcionário com ID " + id + " removido com sucesso.");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao remover funcionário com ID: " + id);
            e.printStackTrace();
            throw e;
        }
    }
}
