package br.com.GestaoEstoque.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import br.com.GestaoEstoque.dao.UsuarioDAO;
import br.com.GestaoEstoque.modelo.Usuario;
import br.com.GestaoEstoque.seguranca.Sessao;

import java.awt.*;
import java.sql.SQLException;

public class TelaLogin extends JDialog {

    private final JTextField campoLogin;
    private final JPasswordField campoSenha;
    private final TelaPrincipal telaPrincipal;

    public TelaLogin(TelaPrincipal telaPrincipal) {
        super(telaPrincipal, "Login do sistema", true);
        this.telaPrincipal = telaPrincipal;

        campoLogin = new JTextField();
        campoSenha = new JPasswordField();

        configurarJanela();
        montarInterface();
    }

    private void configurarJanela() {
        setSize(440, 320);
        setLocationRelativeTo(telaPrincipal);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void montarInterface() {
        // Título
        JLabel titulo = new JLabel("Autenticação de usuário", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(33, 37, 41));
        titulo.setBorder(new EmptyBorder(25, 10, 15, 10));

        // Formulário
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(Color.WHITE);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(25, 35, 25, 35)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelForm.add(lblLogin, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        campoLogin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoLogin.setPreferredSize(new Dimension(220, 32));
        painelForm.add(campoLogin, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelForm.add(lblSenha, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoSenha.setPreferredSize(new Dimension(220, 32));
        painelForm.add(campoSenha, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        painelBotoes.setBackground(new Color(245, 247, 250));

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoEntrar.setBackground(new Color(40, 167, 69));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setFocusPainted(false);
        botaoEntrar.setBorderPainted(false);
        botaoEntrar.setPreferredSize(new Dimension(120, 38));
        botaoEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoEntrar.setName("botaoEntrar");
        botaoEntrar.addActionListener(e -> realizarLogin());

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoCancelar.setBackground(new Color(108, 117, 125));
        botaoCancelar.setForeground(Color.WHITE);
        botaoCancelar.setFocusPainted(false);
        botaoCancelar.setBorderPainted(false);
        botaoCancelar.setPreferredSize(new Dimension(120, 38));
        botaoCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoCancelar.addActionListener(e -> dispose());

        painelBotoes.add(botaoEntrar);
        painelBotoes.add(botaoCancelar);

        // Montagem
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(new Color(245, 247, 250));
        centro.setBorder(new EmptyBorder(0, 40, 0, 40));
        centro.add(painelForm, BorderLayout.CENTER);

        add(titulo, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(botaoEntrar);
    }

    private void realizarLogin() {
        String login = campoLogin.getText().trim();
        String senha = new String(campoSenha.getPassword());

        if (login.isBlank() || senha.isBlank()) {
            JOptionPane.showMessageDialog(this,
                "Informe o login e a senha.",
                "Campos obrigatórios",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            Usuario usuario = usuarioDAO.autenticar(login, senha);

            if (usuario == null) {
                JOptionPane.showMessageDialog(this,
                    "Login ou senha incorretos.",
                    "Falha na autenticação",
                    JOptionPane.ERROR_MESSAGE);
                campoSenha.setText("");
                campoSenha.requestFocus();
                return;
            }

            Sessao.iniciar(usuario);
            telaPrincipal.atualizarTela();

            JOptionPane.showMessageDialog(this,
                "Bem-vindo, " + usuario.getNome() + "!",
                "Login realizado",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Não foi possível conectar ao banco de dados.\nDetalhes: " + e.getMessage(),
                "Erro de conexão",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}