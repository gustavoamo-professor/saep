package com.example.simulado.model;

import java.time.LocalDateTime;

public class Produto {
    // Atributos
    private Long id;
    private String nome;
    private String descricao;
    private double valor;
    private int quantidadeEstoque;
    private LocalDateTime dataCriacao;

    // 1. O construtor que faltava para resolver o seu erro: Produto(String, String, double, int)
    public Produto(String nome, String descricao, double preco, int quantidadeEstoque) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.dataCriacao = LocalDateTime.now(); // Boa prática: definir a data de criação aqui também
    }

    // 2. Construtor com parâmetros principais (Modificado para usar o construtor acima e evitar repetição)
    public Produto(String nome, double preco, int quantidadeEstoque) {
        this(nome, null, preco, quantidadeEstoque);
    }

    // 3. Construtor completo (Com ID)
    public Produto(Long id, String nome, String descricao, double preco, int quantidadeEstoque) {
        this(nome, descricao, preco, quantidadeEstoque);
        this.id = id;
    }

    // 4. Construtor padrão (Vazio) - Importante se você for usar frameworks como Hibernate/Spring
    public Produto() {
    }

    // Getters e Setters aqui embaixo...


    // Getters e Setters

       public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

      public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return valor;
    }

    public void setPreco(double preco) {
        this.valor = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // Método de exemplo para adicionar estoque
    public void adicionarEstoque(int quantidade) {
        this.quantidadeEstoque += quantidade;
    }

    // Método de exemplo para remover estoque
    public void removerEstoque(int quantidade) {
        if (quantidade <= this.quantidadeEstoque) {
            this.quantidadeEstoque -= quantidade;
        } else {
            System.out.println("Quantidade insuficiente em estoque!");
        }
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + valor +
                ", quantidadeEstoque=" + quantidadeEstoque +
                '}';
    }
}
