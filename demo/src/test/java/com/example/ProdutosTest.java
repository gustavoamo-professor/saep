package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class ProdutosTest {

    @Test
    void deveFiltrarProdutosPorNome() {
        Produtos produtos = new Produtos();

        List<Produto> resultado = produtos.listarProdutos("mouse", null, 1, 10);

        assertEquals(1, resultado.size());
        assertEquals("Mouse", resultado.get(0).getNome());
    }

    @Test
    void deveFiltrarProdutosPorData() {
        Produtos produtos = new Produtos();

        List<Produto> resultado = produtos.listarProdutos(null, LocalDate.of(2026, 10, 12), 1, 10);

        assertEquals(1, resultado.size());
        assertEquals("Cadeira", resultado.get(0).getNome());
    }

    @Test
    void devePaginarListaDeProdutos() {
        Produtos produtos = new Produtos();

        List<Produto> resultado = produtos.listarProdutos(null, null, 2, 2);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getNome() != null));
    }

    @Test
    void deveRegistrarSaidaQuandoHouverEstoqueSuficiente() {
        Produtos produtos = new Produtos();
        produtos.adicionarProduto(new Produto("Caixa de parafusos", LocalDate.now(), 20.00, 10));

        boolean resultado = produtos.registrarSaida("Caixa de parafusos", 4);

        assertTrue(resultado);
        assertEquals(6, produtos.buscarProdutoPorNome("Caixa de parafusos").getQuantidadeEstoque());
    }

    @Test
    void naoDeveRegistrarSaidaQuandoEstoqueForInsuficiente() {
        Produtos produtos = new Produtos();
        produtos.adicionarProduto(new Produto("Fita isolante", LocalDate.now(), 15.00, 3));

        boolean resultado = produtos.registrarSaida("Fita isolante", 5);

        assertTrue(!resultado);
        assertEquals(3, produtos.buscarProdutoPorNome("Fita isolante").getQuantidadeEstoque());
    }
}
