package com.example;

import java.time.LocalDate;

public class Produto {
    private String nome;
    private LocalDate dataCadastro;
    private double preco;
    private int quantidadeEstoque;

    public Produto(String nome, LocalDate dataCadastro, double preco) {
        this(nome, dataCadastro, preco, 0);
    }

    public Produto(String nome, LocalDate dataCadastro, double preco, int quantidadeEstoque) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa");
        }

        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public boolean registrarSaida(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade de saída deve ser maior que zero");
        }

        if (quantidade > quantidadeEstoque) {
            return false;
        }

        quantidadeEstoque -= quantidade;
        return true;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                '}';
    }
}
