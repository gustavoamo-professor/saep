package com.example;

public class Operador extends Usuario {
    public Operador(String nome, String username, String senha) {
        super(nome, username, senha, "OPERADOR");
    }

    public void consultarDados(String sistema) {
        System.out.println(getNome() + " está consultando dados do sistema " + sistema + ".");
    }

    public void inserirDados(String sistema) {
        System.out.println(getNome() + " está inserindo dados no sistema " + sistema + ".");
    }

    public void atualizarDados(String sistema) {
        System.out.println(getNome() + " está atualizando dados do sistema " + sistema + ".");
    }
}
