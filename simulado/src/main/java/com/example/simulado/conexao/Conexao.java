package com.example.simulado.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/empresa";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    private Conexao() {
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
// O método PRECISA ser público, estático e ter o nome exatamente igual
    public static Connection getConexao() {
        Connection conn = null;
        try {
            // Sua lógica de conexão aqui (DriverManager.getConnection...)
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}