package com.example.simulado.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.simulado.conexao.Conexao; 
import com.example.simulado.model.Produto;

public class ProdutoDAO {

    // ADICIONAR ou dar entrada no estoque
    public void adicionarProduto(String nome, int quantidade) {
        String sql = "INSERT INTO produtos (nome, quantidade) VALUES (?, ?) " +
                     "ON DUPLICATE KEY UPDATE quantidade = quantidade + ?";
        
        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            
            stmt.setString(1, nome); 
            stmt.setInt(2, quantidade); 
            stmt.setInt(3, quantidade); // Corrigido para somar a quantidade no ON DUPLICATE KEY
            
            stmt.executeUpdate(); 
            
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    // RETIRAR produto do estoque
    public void retirarProduto(int id, int quantidadeRetirada) {
        String sql = "UPDATE produtos SET quantidade = quantidade - ? WHERE id = ? AND quantidade >= ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quantidadeRetirada);
            stmt.setInt(2, id);
            stmt.setInt(3, quantidadeRetirada); // Garante que não vai ficar negativo
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CONSULTAR dados internos
    public void consultarProdutos() {
        String sql = "SELECT * FROM produtos";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                   " | Nome: " + rs.getString("nome") + 
                                   " | Quantidade: " + rs.getInt("quantidade"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean adicionar(Produto produto) {
    String sql = "INSERT INTO produtos (nome, descricao, preco, quantidade) VALUES (?, ?, ?, ?)";
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setDouble(3, produto.getPreco());
        stmt.setInt(4, produto.getQuantidadeEstoque());

        stmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
