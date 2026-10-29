package com.app.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Material {
    private int id;
    private String name;
    private double quantity;


    public Material(int id, String name, double quantity) {
        this.name = name;
        this.id = id;
        this.quantity = quantity;
    }

    public String getName() {return name;}
    public int getId() {return id;}
    public double getQuantity() {return quantity;}

    public void setNome(String newName) {this.name = newName;}
    public void setQuantity(double newQuantity) {this.quantity = newQuantity;}
    
}

