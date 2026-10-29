package br.com.GestaoEstoque.modelo;

public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private Integer valorUnitario;
    private Integer quantidade;
    private String dataCadastro;

    public Produto(String nome, String descricao, Integer valorUnitario, Integer quantidade, String dataCadastro) {
        this.nome = nome;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.dataCadastro = dataCadastro;
    }

    public Produto(Long id, String nome, String descricao, Integer valorUnitario, Integer quantidade, String dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.dataCadastro = dataCadastro;

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Integer getValorUnitario() { return valorUnitario; }
    public Integer getQuantidade() { return quantidade; }
    public String getDataCadastro() { return dataCadastro; }
}