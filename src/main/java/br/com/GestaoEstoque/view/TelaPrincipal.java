package br.com.GestaoEstoque.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.GestaoEstoque.modelo.Usuario;
import br.com.GestaoEstoque.seguranca.Sessao;

public class TelaPrincipal extends JFrame {

    private final JLabel labelUsuario;
    private final JButton botaoLogin;
    private final JButton botaoLogout;
    private final JButton botaoCadastrar;
    private final JButton botaoConsultarDados;
    private final JButton botaoRelatorios;
    private final JButton botaoGerenciarUsuarios;
    private final JButton botaoAlterarSenha;   

    public TelaPrincipal() {
        labelUsuario = new JLabel("", JLabel.CENTER);

        botaoLogin = criarBotaoEstilizado("Realizar login", new Color(0, 123, 255));
        botaoLogout = criarBotaoEstilizado("Sair da conta", new Color(220, 53, 69));
        botaoCadastrar = criarBotaoEstilizado("Cadastrar", new Color(108, 117, 125));
        botaoConsultarDados = criarBotaoEstilizado("Consultar dados internos", new Color(108, 117, 125));
        botaoRelatorios = criarBotaoEstilizado("Relatórios gerenciais", new Color(108, 117, 125));
        botaoGerenciarUsuarios = criarBotaoEstilizado("Gerenciar usuários", new Color(108, 117, 125));
        botaoAlterarSenha = criarBotaoEstilizado("Alterar senha", new Color(108, 117, 125)); 

        configurarJanela();
        adicionarComponentes();
        configurarEventos();
        atualizarTela();
    }

    private JButton criarBotaoEstilizado(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(280, 42));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void configurarJanela() {
        setTitle("Sistema de Controle de Acesso");
        setSize(700, 560); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(245, 247, 250));
    }

    private void adicionarComponentes() {
        JPanel painelCabecalho = new JPanel(new GridLayout(2, 1, 5, 8));
        painelCabecalho.setBackground(new Color(33, 37, 41));
        painelCabecalho.setBorder(new EmptyBorder(25, 20, 25, 20));

        JLabel titulo = new JLabel("Sistema de Controle de Acesso", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        labelUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        labelUsuario.setForeground(new Color(200, 200, 200));

        painelCabecalho.add(titulo);
        painelCabecalho.add(labelUsuario);

        JPanel painelOpcoes = new JPanel(new GridLayout(5, 1, 12, 12));
        painelOpcoes.setBackground(new Color(245, 247, 250));
        painelOpcoes.setBorder(new EmptyBorder(20, 100, 15, 100));

        painelOpcoes.add(botaoCadastrar);
        painelOpcoes.add(botaoConsultarDados);
        painelOpcoes.add(botaoRelatorios);
        painelOpcoes.add(botaoGerenciarUsuarios);
        painelOpcoes.add(botaoAlterarSenha);   

       
        JPanel painelAcesso = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        painelAcesso.setBackground(new Color(245, 247, 250));
        painelAcesso.setBorder(new EmptyBorder(10, 10, 25, 10));
        painelAcesso.add(botaoLogin);
        painelAcesso.add(botaoLogout);

        add(painelCabecalho, BorderLayout.NORTH);
        add(painelOpcoes, BorderLayout.CENTER);
        add(painelAcesso, BorderLayout.SOUTH);
    }

    private void configurarEventos() {
        botaoLogin.addActionListener(e -> abrirTelaLogin());
        botaoLogout.addActionListener(e -> realizarLogout());

        botaoCadastrar.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Esta opção pode ser acessada sem login.",
                "Cadastrar",
                JOptionPane.INFORMATION_MESSAGE)
        );

        botaoConsultarDados.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Dados internos consultados com sucesso.",
                "Consulta",
                JOptionPane.INFORMATION_MESSAGE)
        );

        botaoRelatorios.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Área de relatórios gerenciais.",
                "Relatórios",
                JOptionPane.INFORMATION_MESSAGE)
        );

        botaoGerenciarUsuarios.addActionListener(e -> {
            TelaCrudUsuarios tela = new TelaCrudUsuarios(this);
            tela.setVisible(true);
        });

        botaoAlterarSenha.addActionListener(e -> {
            TelaAlterarSenha tela = new TelaAlterarSenha(this);
            tela.setVisible(true);
        });
    }

    private void abrirTelaLogin() {
        TelaLogin telaLogin = new TelaLogin(this);
        telaLogin.setVisible(true);
    }

    private void realizarLogout() {
        int resposta = JOptionPane.showConfirmDialog(this,
            "Deseja realmente sair da conta?",
            "Confirmar logout",
            JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            Sessao.encerrar();
            atualizarTela();
            JOptionPane.showMessageDialog(this,
                "Logout realizado com sucesso.",
                "Logout",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void atualizarTela() {
        boolean usuarioEstaLogado = Sessao.estaLogado();

        botaoLogin.setVisible(!usuarioEstaLogado);
        botaoLogout.setVisible(usuarioEstaLogado);

        botaoCadastrar.setEnabled(true);
        botaoConsultarDados.setEnabled(false);
        botaoRelatorios.setEnabled(false);
        botaoGerenciarUsuarios.setEnabled(false);
        botaoAlterarSenha.setEnabled(usuarioEstaLogado);

        if (!usuarioEstaLogado) {
            labelUsuario.setText("Usuário não autenticado. Acesso limitado.");
            return;
        }

        Usuario usuario = Sessao.getUsuarioLogado();
        labelUsuario.setText("Usuário: " + usuario.getNome() + "  |  Perfil: " + usuario.getPerfil());

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