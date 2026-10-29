package com.example.simulado.view;

import com.example.simulado.dao.UsuarioDAO;
import com.example.simulado.model.Usuario;
import com.example.simulado.seguranca.Sessao;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

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
        setSize(430, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }

    private void adicionarComponentes() {
        JPanel painelFormulario = new JPanel(new GridLayout(3, 2, 10, 15));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        
        painelFormulario.add(new JLabel("Senha atual:"));
        painelFormulario.add(campoSenhaAtual);
        painelFormulario.add(new JLabel("Nova senha:"));
        painelFormulario.add(campoNovaSenha);
        painelFormulario.add(new JLabel("Confirmar nova senha:"));
        painelFormulario.add(campoConfirmarSenha);
        
        JButton botaoSalvar = new JButton("Alterar senha");
        JButton botaoCancelar = new JButton("Cancelar");
        
        botaoSalvar.addActionListener(evento -> alterarSenha());
        botaoCancelar.addActionListener(evento -> dispose());
        
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);
        
        add(painelFormulario, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void alterarSenha() {
        String senhaAtual = new String(campoSenhaAtual.getPassword());
        String novaSenha = new String(campoNovaSenha.getPassword());
        String confirmarSenha = new String(campoConfirmarSenha.getPassword());
        
        if (senhaAtual.isBlank() || novaSenha.isBlank() || confirmarSenha.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (novaSenha.length() < 6) {
            JOptionPane.showMessageDialog(this, "A nova senha deve possuir pelo menos 6 caracteres.", "Senha inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!novaSenha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "A nova senha e a confirmação não são iguais.", "Senha inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (senhaAtual.equals(novaSenha)) {
            JOptionPane.showMessageDialog(this, "A nova senha deve ser diferente da senha atual.", "Senha inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Usuario usuario = Sessao.getUsuarioLogado();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        try {
            boolean senhaCorreta = usuarioDAO.verificarSenha(usuario.getId(), senhaAtual);
            if (!senhaCorreta) {
                JOptionPane.showMessageDialog(this, "A senha atual está incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
                campoSenhaAtual.setText("");
                campoSenhaAtual.requestFocus();
                return;
            }
            
            boolean alterou = usuarioDAO.alterarSenha(usuario.getId(), novaSenha);
            if (alterou) {
                JOptionPane.showMessageDialog(this, "Senha alterada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível alterar a senha.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados: \n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}