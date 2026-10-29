package com.example;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaProdutos tela = new TelaProdutos();
            tela.setVisible(true);
        });
    }
}