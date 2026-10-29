package main.java.com.app.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;

import main.java.com.app.Connection.ConnectionFactory;

public class Admin {
    private int id;
    private String username;
    private String password;


    public Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }   

    public String getUsername() {return username;}
    public int getId() {return id;}
    public String getPassword() {return password;}

    public void setUsername(String newName) {this.username = newName;}
    public void setPassword(String newPassword) {this.password = newPassword;}




    //Login Validation
    public boolean LoginValidate(String username, String password) {
        String verifyUser = "SELECT FROM admin WHERE username = ? AND password = ?";
        boolean status = false;

        try (Connection conn = ConnectionFactory.createConnection();
            PreparedStatement pstm = conn.prepareStatement(verifyUser)) {
        
            pstm.setString(1, username);
            pstm.setString(2, password);
            ResultSet myRes = pstm.executeQuery();   
            conn.close();
            status = myRes.next();  
            conn.close();  
        } catch(Exception e) {
          e.printStackTrace();
        }
        return status;
    }

    //Methods
    public void insertMaterial(String name, double quantity) throws SQLException {
        String sql = "INSERT INTO materiaisestoque(name, quantity) VALUES(?,?)";
            

        try (Connection conn = ConnectionFactory.createConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(2, name);
            pstm.setDouble(3, quantity);
            pstm.execute();    

            conn.close();
        } catch(Exception e) {
          e.printStackTrace();
        }
    }

    public void getMaterial() {
        String sql = "SELECT * FROM materialestoque";

        try {
        Connection conn = ConnectionFactory.createConnection();
        Statement stm = conn.createStatement();
        
        ResultSet myRes = stm.executeQuery(sql);
        while(myRes.next()) {
            System.out.println(myRes);
        }

        conn.close();

        }catch(Exception e) {
          e.printStackTrace();
        }
    }
}
