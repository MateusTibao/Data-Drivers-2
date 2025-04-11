package modelos;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Compra {

    private int id;
    private Oficina oficina;
    private List<ItemCompra> itens;
    private LocalDate data;
    private double valorTotal;
    public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	private boolean finalizada;

    public Compra(Oficina oficina) {
        if (oficina == null) {
            throw new IllegalArgumentException("Oficina não pode ser nula.");
        }

        this.oficina = oficina;
        this.itens = new ArrayList<>();
        this.data = LocalDate.now();
        this.valorTotal = 0.0;
        this.finalizada = false;
    }

    public int getId() {
        return id;
    }

    public Oficina getOficina() {
        return oficina;
    }

    public LocalDate getData() {
        return data;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public List<ItemCompra> getItens() {
        return itens;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        if (finalizada) {
            System.out.println("Compra já finalizada. Não é possível adicionar mais produtos.");
            return;
        }

        if (produto.getEstoque() < quantidade) {
            System.out.println("Estoque insuficiente para o produto: " + produto.getNome());
            return;
        }

        for (ItemCompra item : itens) {
            if (item.getProduto().getId() == produto.getId()) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                produto.setEstoque(produto.getEstoque() - quantidade);
                return;
            }
        }

        itens.add(new ItemCompra(produto, quantidade));
        produto.setEstoque(produto.getEstoque() - quantidade);
    }

    public void removerProduto(Produto produto, int quantidade) {
        if (finalizada) {
            System.out.println("Compra já finalizada. Não é possível remover produtos.");
            return;
        }

        for (ItemCompra item : itens) {
            if (item.getProduto().getId() == produto.getId()) {
                if (quantidade >= item.getQuantidade()) {
                    produto.setEstoque(produto.getEstoque() + item.getQuantidade());
                    itens.remove(item);
                } else {
                    item.setQuantidade(item.getQuantidade() - quantidade);
                    produto.setEstoque(produto.getEstoque() + quantidade);
                }
                return;
            }
        }

        System.out.println("Produto não encontrado na compra.");
    }

    public void finalizarCompra() {
        if (!finalizada) {
            this.valorTotal = calcularValorTotal();
            this.finalizada = true;
            System.out.println("Compra finalizada com sucesso!");
        } else {
            System.out.println("Compra já foi finalizada.");
        }
    }

    public double calcularValorTotal() {
        double total = 0;
        for (ItemCompra item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void exibirPedido() {
        System.out.println("Itens do pedido:");
        for (ItemCompra item : itens) {
            System.out.println("- " + item.getProduto().getNome() +
                               " | Quantidade: " + item.getQuantidade() +
                               " | Subtotal: R$ " + item.getSubtotal());
        }
    }

    public void exibirEstoque() {
        for (ItemCompra item : itens) {
            Produto p = item.getProduto();
            System.out.println("- " + p.getNome() + ": " + p.getEstoque() + " unidades em estoque");
        }
    }

	public void setId(int Id) {
		this.id = Id;		
	}

}
