import java.sql.Connection;
import java.sql.SQLException;

import integracao.CompraDAO;
import integracao.Conexao;
import integracao.FuncionarioDAO;
import integracao.GerenteDAO;
import integracao.OficinaDAO;
import integracao.ProdutoDAO;
import modelos.*;

public class Teste {
    public static void main(String[] args) {
    	
        try (Connection conn = Conexao.obterConexao()) {

            // 1. Criar o gerente
            Gerente gerente = new Gerente("Ana Souza", "11 91111-1111", "88888888888", "G01");

            // 2. Criar os funcionários e associar ao gerente
            Funcionario func1 = new Funcionario("João Silva", "11 92222-2222", "77777777777", "F01");
            Funcionario func2 = new Funcionario("Maria Oliveira", "11 93333-3333", "66666666666", "F02");
            Funcionario func3 = new Funcionario("Pedro Santos", "11 94444-4444", "55555555555", "F03");

            gerente.adicionarFuncionario(func1);
            gerente.adicionarFuncionario(func2);
            gerente.adicionarFuncionario(func3);

            // 3. Inserir o gerente
            GerenteDAO gerenteDAO = new GerenteDAO();
            gerenteDAO.inserirGerente(gerente, conn); // <–– só insere o gerente

            // 4. Inserir os funcionários com FK para o gerente
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            funcionarioDAO.inserirFuncionario(func1, conn);
            funcionarioDAO.inserirFuncionario(func2, conn);
            funcionarioDAO.inserirFuncionario(func3, conn);

            System.out.println("Gerente e funcionários inseridos com sucesso no banco!");
            
            // Agora sim, representando a remoção:
            System.out.println("\nRemovendo um Funcionário...");
            gerente.removerFuncionario(func2); // remove da lista (Java)
            funcionarioDAO.removerFuncionarioPorId(func2.getId(), conn); // remove do banco
            System.out.println("Remoção finalizada.");
            
            
            System.out.println("\nVinculando um representante para a Oficina...");
            Oficina oficina = new Oficina("Mecanica Booz", "Rua dos Mecânicos, 123", "jorge.booz@email.com", "987654321", func1);
            System.out.println("\nCliente criado: " + oficina.getNome());

            OficinaDAO oficinaDAO = new OficinaDAO();
            oficinaDAO.inserirOficina(oficina, conn); // passando a conexão ativa

            System.out.println("\nCriando Produtos...");
            Produto pneu = new Produto("Pneu", "Pneu radiais para carros de passeio, 17 polegadas.", 350.00, 50);
            Produto bateria = new Produto("Bateria", "Bateria 12V, 60Ah, ideal para carros de médio porte.", 250.00, 30);
            Produto farol = new Produto("Farol", "Farol dianteiro com tecnologia LED, para melhor visibilidade à noite.", 120.00, 20);
            Produto oleo = new Produto("Óleo de Motor", "Óleo sintético de alta performance, 5W-30.", 35.00, 100);

            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.inserirProduto(pneu, conn);
            produtoDAO.inserirProduto(bateria, conn);
            produtoDAO.inserirProduto(farol, conn);
            produtoDAO.inserirProduto(oleo, conn);

            System.out.println("Produtos inseridos no banco com sucesso!");

            produtoDAO.listarProdutos(conn);


            System.out.println("\nIniciando uma Compra para o Cliente...");
            Compra compra = new Compra(oficina);
            System.out.println("\nCompra iniciada para o cliente: " + oficina.getNome());

            // Adicionando produtos
            compra.adicionarProduto(pneu, 5);
            compra.adicionarProduto(bateria, 3);
            compra.adicionarProduto(farol, 2);
            compra.adicionarProduto(oleo, 10);

            // Finalizando
            compra.finalizarCompra();

            System.out.println("\nSalvando compra no banco...");
            CompraDAO compraDAO = new CompraDAO();
            compraDAO.inserirCompra(compra, conn);

            System.out.println("\nCompra registrada com sucesso no banco para o cliente: " + oficina.getNome());

            System.out.println("\nPedido após adicionar produtos:");
            compra.exibirPedido();
            System.out.println("\nValor Total: R$ " + compra.calcularValorTotal());

            System.out.println("\nRemovendo 1 unidade da Bateria...");
            compra.removerProduto(bateria, 1);
            compra.exibirPedido();
            System.out.println("\nValor Total após remoção: R$ " + compra.calcularValorTotal());

            System.out.println("\nTentando remover 5 unidades do Pneu...");
            compra.removerProduto(pneu, 5);
            compra.removerProduto(pneu, 1);

            System.out.println("\nFinalizando a compra...");
            compra.finalizarCompra();
            
            System.out.println("\nSalvando compra no banco...");
            CompraDAO compraDAO1 = new CompraDAO();
            compraDAO.inserirCompra(compra, conn);
            System.out.println("\nCompra registrada com sucesso!");
            
            System.out.println("\nTentando adicionar mais produtos após finalização...");
            compra.adicionarProduto(oleo, 5);

            System.out.println("\nTentando remover 2 unidades do Farol após finalização...");
            compra.removerProduto(farol, 2);

            System.out.println("\nPedido final após tentativa de adicionar e remover produtos após finalização:");
            compra.exibirPedido();

            System.out.println("\nExibindo estoque dos produtos após a compra:");
            compra.exibirEstoque();

            System.out.println(">>> Fim do try principal.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    


    }
}
