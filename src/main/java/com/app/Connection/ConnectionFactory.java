package main.java.com.app.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection createConnection() throws SQLException {
        String url = "jdbc:mysql://http://localhost:8080/db_almox";
        String user = "root";
        String password = "root";

        Connection conexao = null;
        conexao = DriverManager.getConnection(url, user, password);

        return conexao;
    }
}
