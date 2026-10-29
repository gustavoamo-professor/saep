import java.time.LocalDate;

public class Produto {
    private static int contadorId = 1;
    
    private int id;
    private String descricao;
    private double valorUnitario;
    private int quantidade;
    private LocalDate dataCadastro;

    public Produto(String descricao, double valorUnitario, int quantidade) {
        this.id = contadorId++;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.dataCadastro = LocalDate.now();
    }

    // Getters para podermos ler os dados nos métodos de consulta/atualização
    public int getId() { return id; }
    public String getDescricao() { return descricao; }
    public double getValorUnitario() { return valorUnitario; }
    public int getQuantidade() { return quantidade; }
    public LocalDate getDataCadastro() { return dataCadastro; }

    // Setters para podermos atualizar os dados depois
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setValorUnitario(double valorUnitario) { this.valorUnitario = valorUnitario; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public void exibirDados() {
        System.out.println("ID: " + id + " | " + descricao + " | Valor: R$" + valorUnitario + " | Qtd: " + quantidade + " | Cadastro: " + dataCadastro);
    }

    void exibirDados1() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
