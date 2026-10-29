package br.com.GestaoEstoque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.GestaoEstoque.conexao.Conexao;
import br.com.GestaoEstoque.modelo.Usuario;
import br.com.GestaoEstoque.seguranca.Criptografia;

public class UsuarioDAO {

    public Usuario autenticar(String login, String senha) throws SQLException {
        String sql = """
            SELECT id, nome, login, perfil, ativo
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

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nome, login, perfil, ativo FROM usuarios ORDER BY nome";

        try (Connection connection = Conexao.conectar();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("login"),
                    rs.getString("perfil"),
                    rs.getBoolean("ativo")
                ));
            }
        }
        return lista;
    }

    public void inserir(Usuario usuario, String senha) throws SQLException {
        String sql = """
            INSERT INTO usuarios (nome, login, senha_hash, perfil, ativo)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection connection = Conexao.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getLogin());
            statement.setString(3, Criptografia.gerarSHA256(senha));
            statement.setString(4, usuario.getPerfil());
            statement.setBoolean(5, usuario.isAtivo());
            statement.executeUpdate();
        }
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = """
            UPDATE usuarios
            SET nome = ?, login = ?, perfil = ?, ativo = ?
            WHERE id = ?
            """;

        try (Connection connection = Conexao.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getLogin());
            statement.setString(3, usuario.getPerfil());
            statement.setBoolean(4, usuario.isAtivo());
            statement.setInt(5, usuario.getId());
            statement.executeUpdate();
        }
    }

    public void atualizarSenha(int id, String novaSenha) throws SQLException {
        String sql = "UPDATE usuarios SET senha_hash = ? WHERE id = ?";

        try (Connection connection = Conexao.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, Criptografia.gerarSHA256(novaSenha));
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection connection = Conexao.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public boolean loginExiste(String login, Integer idIgnorar) throws SQLException {
        String sql = idIgnorar == null
            ? "SELECT COUNT(*) FROM usuarios WHERE login = ?"
            : "SELECT COUNT(*) FROM usuarios WHERE login = ? AND id <> ?";

        try (Connection connection = Conexao.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            if (idIgnorar != null) {
                statement.setInt(2, idIgnorar);
            }

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
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