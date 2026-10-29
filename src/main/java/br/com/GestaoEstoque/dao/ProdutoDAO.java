package br.com.GestaoEstoque.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutoDAO implements OperacoesDao {
    private Connection conexao;

    public ProdutoDAO() {
        conexao = ConexaoBanco.conectar();
    }

    @Override
    public void inserir(ProdutoDAO produto) {
        String sql = "INSERT INTO produto(nome, descricao, valorUnitario, quantidade, dataCadastro) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setString(1, produto.getNome());
            comando.setString(2, produto.getDescricao());
            comando.setDouble(3, produto.getValorUnitario());
            comando.setInt(4, produto.getQuantidade());
            comando.setString(5, produto.getDataCadastro());

            comando.executeUpdate();
        } catch (Exception erro) {
            System.out.println("Erro ao inserir produto!");
            System.out.println(erro.getMessage());
        }
    }

    @Override
    public void atualizar(ProdutoDAO produto) {
        String sql = "UPDATE produto SET nome = ?, descricao = ?, valorUnitario = ? quantidade = ?, dataCadastro = ? WHERE id = ?";
        try {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setString(1, produto.getNome());
            comando.setString(2, produto.getDescricao());
            comando.setDouble(3, produto.getValorUnitario());
            comando.setInt(4, produto.getQuantidade());
            comando.setString(5, produto.getDataCadastro());
            comando.setLong(4, produto.getId());
            comando.executeUpdate();
        } catch (Exception erro) {
            System.out.println("Erro ao atualizar produto!");
            System.out.println(erro.getMessage());
        }
    }

    @Override
    public void excluir(Long id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try {
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setLong(1, id);
            comando.executeUpdate();
        } catch (Exception erro) {
            System.out.println("Erro ao excluir produto!");
            System.out.println(erro.getMessage());
        }
    }

}