package estruturas;

import java.util.ArrayList;
import java.util.Collections;

import modelos.Produto;

public class HeapProduto {
    private ArrayList<Produto> items;

    public HeapProduto() {
        items = new ArrayList<>();
        items.add(null);
    }

    public void insert(Produto produto) {
        items.add(produto);
        int i = items.size() - 1;

        while (i > 1) {
            int pai = i / 2;

            if (items.get(i).getEstoque() > items.get(pai).getEstoque()) {
                trocar(i, pai);
                i = pai;
            } else {
                break;
            }
        }
    }

    public void insertAll(ArrayList<Produto> lista) {
        for (Produto produto : lista) {
            insert(produto);
        }
    }

    public Produto remove() {
        if (items.size() <= 1) {
            return null;
        }

        Produto raiz = items.get(1);
        Produto ultimo = items.remove(items.size() - 1);

        if (items.size() == 1) {
            return raiz;
        }

        items.set(1, ultimo);

        int i = 1;
        while (2 * i < items.size()) {
            int c = 2 * i;

            if (c + 1 < items.size() &&
                    items.get(c + 1).getEstoque() > items.get(c).getEstoque()) {
                c++;
            }

            if (items.get(i).getEstoque() >= items.get(c).getEstoque()) {
                break;
            }

            trocar(i, c);
            i = c;
        }

        return raiz;
    }

    private void trocar(int i, int j) {
        Produto temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
    }

    public boolean isEmpty() {
        return items.size() <= 1;
    }

    public void imprimirHeap() {
        System.out.println("Heap (ordenada por estoque):");
        for (int i = 1; i < items.size(); i++) {
            Produto p = items.get(i);
            System.out.println("- " + p.getNome() + " | Estoque: " + p.getEstoque());
        }
    }

    public void ordenarPorPreco() {
        ArrayList<Produto> novaLista = new ArrayList<>();
        novaLista.add(null); // índice 0 não usado

        ArrayList<Produto> produtosTemporarios = new ArrayList<>();
        while (!isEmpty()) {
            produtosTemporarios.add(remove());
        }

        for (Produto produto : produtosTemporarios) {
            novaLista.add(produto);
            int i = novaLista.size() - 1;

            while (i > 1) {
                int pai = i / 2;
                if (novaLista.get(i).getPreco() > novaLista.get(pai).getPreco()) {
                    Produto temp = novaLista.get(i);
                    novaLista.set(i, novaLista.get(pai));
                    novaLista.set(pai, temp);
                    i = pai;
                } else {
                    break;
                }
            }
        }

        this.items = novaLista;
    }

    public ArrayList<Produto> getOrdenado(boolean crescente, int limite, boolean inOrderPreco) {
        HeapProduto copia = new HeapProduto();

        for (int i = 1; i < items.size(); i++) {
            copia.insert(items.get(i));
        }

        if (inOrderPreco) {
            copia.ordenarPorPreco();
        }

        ArrayList<Produto> resultado = new ArrayList<>();
        for (int i = 0; i < limite && !copia.isEmpty(); i++) {
            resultado.add(copia.remove());
        }

        if (crescente) {
            Collections.reverse(resultado);
        }

        return resultado;
    }

}
