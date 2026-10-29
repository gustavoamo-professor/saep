package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Produtos {
    private final List<Produto> produtosDisponiveis = new ArrayList<>();
    private final List<String> registrosSaida = new ArrayList<>();

    public Produtos() {
        produtosDisponiveis.add(new Produto("Teclado", LocalDate.of(2026, 10, 5), 150.00, 10));
        produtosDisponiveis.add(new Produto("Mouse", LocalDate.of(2026, 10, 8), 90.00, 12));
        produtosDisponiveis.add(new Produto("Monitor", LocalDate.of(2026, 10, 10), 800.00, 6));
        produtosDisponiveis.add(new Produto("Notebook", LocalDate.of(2026, 10, 11), 3500.00, 3));
        produtosDisponiveis.add(new Produto("Cadeira", LocalDate.of(2026, 10, 12), 620.00, 8));
        produtosDisponiveis.add(new Produto("Impressora", LocalDate.of(2026, 10, 14), 1200.00, 4));
    }

    public void adicionarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto inválido");
        }
        produtosDisponiveis.add(produto);
    }

    public Produto buscarProdutoPorNome(String nomeProduto) {
        if (nomeProduto == null || nomeProduto.isBlank()) {
            return null;
        }

        return produtosDisponiveis.stream()
                .filter(produto -> produto.getNome().equalsIgnoreCase(nomeProduto.trim()))
                .findFirst()
                .orElse(null);
    }

    public boolean registrarSaida(String nomeProduto, int quantidade) {
        Produto produto = buscarProdutoPorNome(nomeProduto);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }

        boolean saiu = produto.registrarSaida(quantidade);
        if (saiu) {
            registrosSaida.add("Saída registrada: " + produto.getNome() + " - quantidade: " + quantidade +
                    " - saldo restante: " + produto.getQuantidadeEstoque());
        }

        return saiu;
    }

    public List<String> getRegistrosSaida() {
        return List.copyOf(registrosSaida);
    }

    public List<Produto> listarProdutos(String nomeFiltro, LocalDate dataFiltro, int pagina, int tamanhoPagina) {
        if (pagina < 1) {
            throw new IllegalArgumentException("A página deve ser maior que zero");
        }
        if (tamanhoPagina < 1) {
            throw new IllegalArgumentException("O tamanho da página deve ser maior que zero");
        }

        List<Produto> produtosFiltrados = produtosDisponiveis.stream()
                .filter(produto -> nomeFiltro == null || nomeFiltro.isBlank()
                        || produto.getNome().toLowerCase().contains(nomeFiltro.toLowerCase()))
                .filter(produto -> dataFiltro == null || produto.getDataCadastro().equals(dataFiltro))
                .sorted(Comparator.comparing(Produto::getDataCadastro).reversed())
                .toList();

        int inicio = (pagina - 1) * tamanhoPagina;
        if (inicio >= produtosFiltrados.size()) {
            return List.of();
        }

        int fim = Math.min(inicio + tamanhoPagina, produtosFiltrados.size());
        return produtosFiltrados.subList(inicio, fim);
    }

    public List<Produto> listarProdutosDisponiveis() {
        return List.copyOf(produtosDisponiveis);
    }
}
