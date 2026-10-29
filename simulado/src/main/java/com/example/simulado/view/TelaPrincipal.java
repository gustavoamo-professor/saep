package com.example.simulado.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.example.simulado.model.Usuario;
import com.example.simulado.seguranca.Sessao;
import com.example.simulado.conexao.*;

public class TelaPrincipal extends JFrame {
    private final JLabel labelUsuario;
    private final JButton botaoLogin;
    private final JButton botaoLogout;
    private final JButton botaoInformacoesPublicas;
    private final JButton botaoConsultarDados;
    private final JButton botaoRelatorios;
    private final JButton botaoGerenciarUsuarios;
    private final JButton botaoAlterarSenha;

    public TelaPrincipal() {
        labelUsuario = new JLabel("", JLabel.CENTER);
        botaoLogin = new JButton("Realizar login");
        botaoLogout = new JButton("Sair da conta");
        botaoInformacoesPublicas = new JButton("Informações públicas");
        botaoConsultarDados = new JButton("Consultar dados internos");
        botaoRelatorios = new JButton("Relatórios gerenciais");
        botaoGerenciarUsuarios = new JButton("Gerenciar usuários");
        botaoAlterarSenha = new JButton("Alterar senha");
        
        configurarJanela();
        adicionarComponentes();
        configurarEventos();
        atualizarTela();
    }

    private void configurarJanela() {
        setTitle("Sistema de Controle de Acesso");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
    }

    private void adicionarComponentes() {
        JPanel painelCabecalho = new JPanel(new GridLayout(2, 1, 5, 5));
        painelCabecalho.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel titulo = new JLabel("Sistema de Controle de Acesso", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelUsuario.setFont(new Font("Arial", Font.PLAIN, 15));
        
        painelCabecalho.add(titulo);
        painelCabecalho.add(labelUsuario);
        
        JPanel painelOpcoes = new JPanel(new GridLayout(5, 1, 10, 10));
        painelOpcoes.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));
        
        painelOpcoes.add(botaoInformacoesPublicas);
        painelOpcoes.add(botaoConsultarDados);
        painelOpcoes.add(botaoRelatorios);
        painelOpcoes.add(botaoGerenciarUsuarios);
        painelOpcoes.add(botaoAlterarSenha);
        
        JPanel painelAcesso = new JPanel();
        painelAcesso.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        painelAcesso.add(botaoLogin);
        painelAcesso.add(botaoLogout);
        
        add(painelCabecalho, BorderLayout.NORTH);
        add(painelOpcoes, BorderLayout.CENTER);
        add(painelAcesso, BorderLayout.SOUTH);
    }

    private void configurarEventos() {
        botaoLogin.addActionListener(evento -> abrirTelaLogin());
        botaoLogout.addActionListener(evento -> realizarLogout());
        
        botaoInformacoesPublicas.addActionListener(evento ->
            JOptionPane.showMessageDialog(this, "Esta opção pode ser acessada sem login.", "Informações públicas", JOptionPane.INFORMATION_MESSAGE)
        );
        botaoConsultarDados.addActionListener(evento ->
            JOptionPane.showMessageDialog(this, "Dados internos consultados com sucesso.", "Consulta", JOptionPane.INFORMATION_MESSAGE)
        );
        botaoRelatorios.addActionListener(evento ->
            JOptionPane.showMessageDialog(this, "Área de relatórios gerenciais.", "Relatórios", JOptionPane.INFORMATION_MESSAGE)
        );
        botaoGerenciarUsuarios.addActionListener(evento ->
            JOptionPane.showMessageDialog(this, "Área de gerenciamento de usuários.", "Administração", JOptionPane.INFORMATION_MESSAGE)
        );
        botaoAlterarSenha.addActionListener(evento -> {
            TelaAlterarSenha tela = new TelaAlterarSenha(this);
            tela.setVisible(true);
        });
    }

    private void abrirTelaLogin() {
        TelaLogin telaLogin = new TelaLogin(this);
        telaLogin.setVisible(true);
    }

    private void realizarLogout() {
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente sair da conta?", "Confirmar logout", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            Sessao.encerrar();
            atualizarTela();
            JOptionPane.showMessageDialog(this, "Logout realizado com sucesso.", "Logout", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void atualizarTela() {
        boolean usuarioEstaLogado = Sessao.estaLogado();
        
        botaoLogin.setVisible(!usuarioEstaLogado);
        botaoLogout.setVisible(usuarioEstaLogado);
        botaoInformacoesPublicas.setEnabled(true);
        botaoConsultarDados.setEnabled(false);
        botaoRelatorios.setEnabled(false);
        botaoGerenciarUsuarios.setEnabled(false);
        botaoAlterarSenha.setEnabled(usuarioEstaLogado);
        
        if (!usuarioEstaLogado) {
            labelUsuario.setText("Usuário não autenticado. Acesso limitado.");
            return;
        }
        
        Usuario usuario = Sessao.getUsuarioLogado();
        labelUsuario.setText("Usuário: " + usuario.getNome() + " | Perfil: " + usuario.getPerfil());
        liberarOpcoesPorPerfil(usuario);
    }

    private void liberarOpcoesPorPerfil(Usuario usuario) {
        botaoConsultarDados.setEnabled(true);
        if (usuario.isGerente() || usuario.isAdministrador()) {
            botaoRelatorios.setEnabled(true);
        }
        if (usuario.isAdministrador()) {
            botaoGerenciarUsuarios.setEnabled(true);
        }
    }
}