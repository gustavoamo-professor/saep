package com.example.simulado.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.simulado.model.Usuario;
import com.example.simulado.seguranca.Criptografia;
import com.example.simulado.conexao.*;

public class UsuarioDAO {

    public Usuario autenticar(String login, String senha) throws SQLException {
        String sql = """
            SELECT
                id,
                nome,
                login,
                perfil,
                ativo
            FROM usuarios
            WHERE login = ?
              AND senha_hash = ?
              AND ativo = TRUE
        """;
        
        String senhaCriptografada = Criptografia.gerarSHA256(senha);
        
        try (Connection connection = Conexao.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setString(1, login);
            statement.setString(2, senhaCriptografada);
            
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return new Usuario(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("login"),
                        resultado.getString("perfil"),
                        resultado.getBoolean("ativo")
                    );
                }
            }
        }
        return null;
    }

    public boolean verificarSenha(int idUsuario, String senhaAtual) throws SQLException {
        String sql = """
            SELECT id
            FROM usuarios
            WHERE id = ?
              AND senha_hash = ?
              AND ativo = TRUE
        """;
        
        String senhaCriptografada = Criptografia.gerarSHA256(senhaAtual);
        
        try (Connection connection = Conexao.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setInt(1, idUsuario);
            statement.setString(2, senhaCriptografada);
            
            try (ResultSet resultado = statement.executeQuery()) {
                return resultado.next();
            }
        }
    }

    public boolean alterarSenha(int idUsuario, String novaSenha) throws SQLException {
        String sql = """
            UPDATE usuarios
            SET senha_hash = ?
            WHERE id = ?
              AND ativo = TRUE
        """;
        
        String senhaCriptografada = Criptografia.gerarSHA256(novaSenha);
        
        try (Connection connection = Conexao.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setString(1, senhaCriptografada);
            statement.setInt(2, idUsuario);
            
            return statement.executeUpdate() > 0;
        }
    }
}