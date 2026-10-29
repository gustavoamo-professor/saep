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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.com.GestaoEstoque.dao.UsuarioDAO;
import br.com.GestaoEstoque.modelo.Usuario;

public class TelaCrudUsuarios extends JDialog {

    private final JTextField campoNome = new JTextField();
    private final JTextField campoLogin = new JTextField();
    private final JPasswordField campoSenha = new JPasswordField();
    private final JComboBox<String> comboPerfil = new JComboBox<>(new String[]{"FUNCIONARIO", "GERENTE", "ADMIN"});
    private final JCheckBox checkAtivo = new JCheckBox("Usuário ativo", true);

    private final DefaultTableModel modeloTabela;
    private final JTable tabela;
    private Integer idSelecionado = null;

    private final UsuarioDAO dao = new UsuarioDAO();

    public TelaCrudUsuarios(JFrame parent) {
        super(parent, "Gerenciamento de Usuários", true);

        modeloTabela = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Login", "Perfil", "Ativo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setRowHeight(28);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        configurarJanela();
        montarInterface();
        carregarTabela();
        configurarEventos();
    }

    private void configurarJanela() {
        setSize(820, 580);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(12, 12));
        getContentPane().setBackground(new Color(245, 247, 250));
    }

    private void montarInterface() {
        JLabel titulo = new JLabel("CRUD de Usuários", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(new EmptyBorder(15, 10, 10, 10));
        titulo.setForeground(new Color(33, 37, 41));

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(Color.WHITE);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(15, 20, 15, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        adicionarCampo(painelForm, gbc, 0, "Nome:", campoNome);
        adicionarCampo(painelForm, gbc, 1, "Login:", campoLogin);
        adicionarCampo(painelForm, gbc, 2, "Senha:", campoSenha);
        adicionarCampo(painelForm, gbc, 3, "Perfil:", comboPerfil);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        painelForm.add(checkAtivo, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        painelBotoes.setBackground(new Color(245, 247, 250));

        JButton btnNovo = criarBotao("Novo", new Color(108, 117, 125));
        JButton btnSalvar = criarBotao("Salvar", new Color(40, 167, 69));
        JButton btnExcluir = criarBotao("Excluir", new Color(220, 53, 69));
        JButton btnLimpar = criarBotao("Limpar", new Color(0, 123, 255));

        btnNovo.addActionListener(e -> limparFormulario());
        btnSalvar.addActionListener(e -> salvar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparFormulario());

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            "Usuários cadastrados",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13)
        ));

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBackground(new Color(245, 247, 250));
        centro.setBorder(new EmptyBorder(0, 20, 10, 20));
        centro.add(painelForm, BorderLayout.NORTH);
        centro.add(painelBotoes, BorderLayout.CENTER);
        centro.add(scroll, BorderLayout.SOUTH);

        add(titulo, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String label, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        painel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        if (campo instanceof JTextField || campo instanceof JPasswordField) {
            campo.setPreferredSize(new Dimension(280, 28));
        }
        painel.add(campo, gbc);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setPreferredSize(new Dimension(110, 36));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private void configurarEventos() {
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() >= 0) {
                int linha = tabela.getSelectedRow();
                idSelecionado = (Integer) modeloTabela.getValueAt(linha, 0);
                campoNome.setText((String) modeloTabela.getValueAt(linha, 1));
                campoLogin.setText((String) modeloTabela.getValueAt(linha, 2));
                comboPerfil.setSelectedItem(modeloTabela.getValueAt(linha, 3));
                checkAtivo.setSelected("Sim".equals(modeloTabela.getValueAt(linha, 4)));
                campoSenha.setText("");
            }
        });
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        try {
            List<Usuario> lista = dao.listarTodos();
            for (Usuario u : lista) {
                modeloTabela.addRow(new Object[]{
                    u.getId(),
                    u.getNome(),
                    u.getLogin(),
                    u.getPerfil(),
                    u.isAtivo() ? "Sim" : "Não"
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar usuários:\n" + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFormulario() {
        idSelecionado = null;
        campoNome.setText("");
        campoLogin.setText("");
        campoSenha.setText("");
        comboPerfil.setSelectedIndex(0);
        checkAtivo.setSelected(true);
        tabela.clearSelection();
        campoNome.requestFocus();
    }

    private void salvar() {
        String nome = campoNome.getText().trim();
        String login = campoLogin.getText().trim();
        String senha = new String(campoSenha.getPassword());
        String perfil = (String) comboPerfil.getSelectedItem();
        boolean ativo = checkAtivo.isSelected();

        if (nome.isBlank() || login.isBlank()) {
            JOptionPane.showMessageDialog(this, "Nome e Login são obrigatórios.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (dao.loginExiste(login, idSelecionado)) {
                JOptionPane.showMessageDialog(this, "Já existe um usuário com este login.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (idSelecionado == null) {
                if (senha.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Informe a senha para o novo usuário.", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Usuario novo = new Usuario(0, nome, login, perfil, ativo);
                dao.inserir(novo, senha);
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Usuario atualizado = new Usuario(idSelecionado, nome, login, perfil, ativo);
                dao.atualizar(atualizado);

                if (!senha.isBlank()) {
                    dao.atualizarSenha(idSelecionado, senha);
                }
                JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }

            limparFormulario();
            carregarTabela();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar:\n" + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (idSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int conf = JOptionPane.showConfirmDialog(this,
            "Deseja realmente excluir este usuário?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION);

        if (conf == JOptionPane.YES_OPTION) {
            try {
                dao.excluir(idSelecionado);
                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparFormulario();
                carregarTabela();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao excluir:\n" + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}