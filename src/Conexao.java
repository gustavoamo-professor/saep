
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static void main(String[] args) {
        String url = "http://localhost:8088/empresa";

        String usuario = "root";
        String senha = "";

        try {
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão realizada com sucesso!");
            
            // Lembre-se de fechar a conexão após o uso
            conexao.close();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}