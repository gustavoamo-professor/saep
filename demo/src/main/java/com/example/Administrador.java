package com.example;

public class Administrador extends Usuario {
    public Administrador(String nome, String username, String senha) {
        super(nome, username, senha, "ADMINISTRADOR");
    }

    public void inserirDados(String sistema) {
        System.out.println(getNome() + " está inserindo dados no sistema " + sistema + ".");
    }

    public void atualizarDados(String sistema) {
        System.out.println(getNome() + " está atualizando dados do sistema " + sistema + ".");
    }

    public void deletarDados(String sistema) {
        System.out.println(getNome() + " está deletando dados do sistema " + sistema + ".");
    }
}
