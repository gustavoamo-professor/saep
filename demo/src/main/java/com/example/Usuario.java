package com.example;

public class Usuario {
    private String nome;
    private String username;
    private String senha;
    private String tipoUsuario;

    public Usuario(String nome, String username, String senha, String tipoUsuario) {
        this.nome = nome;
        this.username = username;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public boolean autenticar(String username, String senha) {
        return this.username.equals(username) && this.senha.equals(senha);
    }
}
