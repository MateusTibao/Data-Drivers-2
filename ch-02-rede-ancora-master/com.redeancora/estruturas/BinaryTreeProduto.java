package estruturas;

import modelos.Produto;

import java.util.ArrayList;
import java.util.Collections;

public class BinaryTreeProduto {

    static class Node {
        Produto data;
        Node left;
        Node right;

        Node(Produto data) {
            this.data = data;
        }
    }

    private Node root;

    public void insert(Produto produto) {
        root = insertRec(root, new Node(produto));
    }

    public void insertAll(ArrayList<Produto> produtos) {
        for (Produto produto : produtos) {
            insert(produto);
        }
    }

    private Node insertRec(Node root, Node node) {
        if (root == null) return node;

        if (node.data.getNome().compareToIgnoreCase(root.data.getNome()) < 0) {
            root.left = insertRec(root.left, node);
        } else {
            root.right = insertRec(root.right, node);
        }

        return root;
    }

    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(Node root) {
        if (root == null) return;

        inOrderRec(root.left);
        root.data.exibirInformacoesProduto();
        inOrderRec(root.right);
    }

    public Produto search(String nome) {
        Node result = searchRec(root, nome);
        return result != null ? result.data : null;
    }

    private Node searchRec(Node root, String nome) {
        if (root == null || root.data.getNome().equalsIgnoreCase(nome)) return root;

        if (nome.compareToIgnoreCase(root.data.getNome()) < 0)
            return searchRec(root.left, nome);
        else
            return searchRec(root.right, nome);
    }

    public ArrayList<String> autocomplete(String prefix) {
        ArrayList<String> result = new ArrayList<>();
        buscarPrefixo(root, prefix.toLowerCase(), result);
        Collections.sort(result);
        return result;
    }

    private void buscarPrefixo(Node node, String prefix, ArrayList<String> result) {
        if (node == null) return;

        String nome = node.data.getNome().toLowerCase();

        if (nome.startsWith(prefix)) {
            result.add(node.data.getNome());
        }

        if (node.left != null && nome.compareTo(prefix) >= 0) {
            buscarPrefixo(node.left, prefix, result);
        }

        if (node.right != null && nome.substring(0, Math.min(nome.length(), prefix.length())).compareTo(prefix) <= 0) {
            buscarPrefixo(node.right, prefix, result);
        }
    }
}

