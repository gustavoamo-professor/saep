package br.com.GestaoEstoque.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import br.com.GestaoEstoque.dao.UsuarioDAO;
import br.com.GestaoEstoque.modelo.Usuario;
import br.com.GestaoEstoque.seguranca.Sessao;

public class TelaAlterarSenha extends JDialog {

    private final JPasswordField campoSenhaAtual;
    private final JPasswordField campoNovaSenha;
    private final JPasswordField campoConfirmarSenha;

    public TelaAlterarSenha(JFrame telaPrincipal) {
        super(telaPrincipal, "Alterar senha", true);

        campoSenhaAtual = new JPasswordField();
        campoNovaSenha = new JPasswordField();
        campoConfirmarSenha = new JPasswordField();

        configurarTela();
        adicionarComponentes();
    }

    private void configurarTela() {
        setSize(460, 340);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));
    }

    private void adicionarComponentes() {
        // Título
        JLabel titulo = new JLabel("Alterar Senha", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(33, 37, 41));
        titulo.setBorder(new EmptyBorder(20, 10, 10, 10));

        // Formulário
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(Color.WHITE);
        painelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(25, 30, 20, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        adicionarCampo(painelFormulario, gbc, 0, "Senha atual:", campoSenhaAtual);
        adicionarCampo(painelFormulario, gbc, 1, "Nova senha:", campoNovaSenha);
        adicionarCampo(painelFormulario, gbc, 2, "Confirmar nova senha:", campoConfirmarSenha);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        painelBotoes.setBackground(new Color(245, 247, 250));

        JButton botaoSalvar = criarBotao("Alterar senha", new Color(40, 167, 69));
        JButton botaoCancelar = criarBotao("Cancelar", new Color(108, 117, 125));

        botaoSalvar.addActionListener(e -> alterarSenha());
        botaoCancelar.addActionListener(e -> dispose());

        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        // Montagem
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(new Color(245, 247, 250));
        centro.setBorder(new EmptyBorder(0, 40, 0, 40));
        centro.add(painelFormulario, BorderLayout.CENTER);

        add(titulo, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(botaoSalvar);
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String label, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.weightx = 0;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(220, 32));
        painel.add(campo, gbc);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(140, 38));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void alterarSenha() {
        String senhaAtual = new String(campoSenhaAtual.getPassword());
        String novaSenha = new String(campoNovaSenha.getPassword());
        String confirmarSenha = new String(campoConfirmarSenha.getPassword());

        if (senhaAtual.isBlank() || novaSenha.isBlank() || confirmarSenha.isBlank()) {
            JOptionPane.showMessageDialog(this,
                "Preencha todos os campos.",
                "Campos obrigatórios",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (novaSenha.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "A nova senha deve possuir pelo menos 6 caracteres.",
                "Senha inválida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!novaSenha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this,
                "A nova senha e a confirmação não são iguais.",
                "Senha inválida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (senhaAtual.equals(novaSenha)) {
            JOptionPane.showMessageDialog(this,
                "A nova senha deve ser diferente da senha atual.",
                "Senha inválida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = Sessao.getUsuarioLogado();
        if (usuario == null) {
            JOptionPane.showMessageDialog(this,
                "Nenhum usuário logado.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            boolean senhaCorreta = usuarioDAO.verificarSenha(usuario.getId(), senhaAtual);

            if (!senhaCorreta) {
                JOptionPane.showMessageDialog(this,
                    "A senha atual está incorreta.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                campoSenhaAtual.setText("");
                campoSenhaAtual.requestFocus();
                return;
            }

            boolean alterou = usuarioDAO.alterarSenha(usuario.getId(), novaSenha);

            if (alterou) {
                JOptionPane.showMessageDialog(this,
                    "Senha alterada com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Não foi possível alterar a senha.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao acessar o banco de dados:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}