package br.com.GestaoEstoque.view;

public class TelaCadastrar {

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaCadastrar extends JFrame {
    private JTextField campoId, campoNome, campoDescricao, campoValorUnitario, campoQuantidade, campoDataCadastro;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaCadastrar() {
        setTitle("Gerenciamento de Produtos- CRUD");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        int xLabel = 30, xCampo = 140, larguraCampo = 220, altura = 25;

        adicionarComponente(new JLabel("ID:"), xLabel, 30, 100, altura);
        campoId = new JTextField(); campoId.setBounds(xCampo, 30, 80, altura); campoId.setEditable(false); add(campoId);

        adicionarComponente(new JLabel("Nome:"), xLabel, 70, 100, altura);
        campoNome = new JTextField(); campoNome.setBounds(xCampo, 70, larguraCampo, altura); add(campoNome);

        adicionarComponente(new JLabel("Descrição:"), xLabel, 110, 100, altura);
        campoDescricao = new JTextField(); campodescricao.setBounds(xCampo, 110, larguraCampo, altura); add(campoDescricao);
        
        adicionarComponente(new JLabel("Valor Unitário:"), xLabel, 150, 100, altura);
        campoValorUnitario = new JTextField(); campoValorUnitario.setBounds(xCampo, 150, larguraCampo, altura); add(campoValorUnitario);

        adicionarComponente(new JLabel("Quantidade:"), xLabel, 150, 100, altura);
        CampoQuantidade = new JTextField(); CampoQuantidade.setBounds(xCampo, 150, larguraCampo, altura); add(CampoQuantidade);

        adicionarComponente(new JLabel("Data de Cadastro:"), xLabel, 150, 100, altura);
        campoDataCadastro = new JTextField(); campoDataCadastro.setBounds(xCampo, 150, larguraCampo, altura); add(campoDataCadastro);


        JButton btnSalvar = new JButton("Salvar"); btnSalvar.setBounds(180, 240, 110, 35); add(btnSalvar);
        JButton btnAtualizar = new JButton("Atualizar"); btnAtualizar.setBounds(310, 240, 110, 35); add(btnAtualizar);
        JButton btnExcluir = new JButton("Excluir"); btnExcluir.setBounds(440, 240, 110, 35); add(btnExcluir);
        JButton btnLimpar = new JButton("Limpar"); btnLimpar.setBounds(570, 240, 110, 35); add(btnLimpar);

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID"); modeloTabela.addColumn("Nome"); modeloTabela.addColumn("Descrição");
        modeloTabela.addColumn("Valor Unitário"); modeloTabela.addColumn("Quantidade"); modeloTabela.addColumn("Data de Cadastro");

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 300, 775, 230);
        add(scroll);

        carregarTabela();

        btnSalvar.addActionListener(e -> cadastrarProduto());
        btnAtualizar.addActionListener(e -> atualizarProduto());
        btnExcluir.addActionListener(e -> excluirProduto());
        btnLimpar.addActionListener(e -> limparCampos());
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { preencherCampos(); }
        });
    }

    private void adicionarComponente(JLabel label, int x, int y, int w, int h) {
        label.setBounds(x, y, w, h);
        add(label);
    }

    private void cadastrarProduto() {
        if (validarCampos()) {
            Produto produto = new Produto(
                campoNome.getText(), campoDescricao.getText(), Integer.parseInt(campoValorUnitario.getText()), Integer.parseInt(campoQuantidade.getText()), campoDataCadastro.getText()
            );
            new ProdutoDAO().inserir(produto);
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        }
    }

    private void atualizarProduto() {
        if (campoId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um produto na tabela!");
            return;
        }
        if (validarCampos()) {
            Produto produto = new Produto(
                Long.parseLong(campoId.getText()),
                campoNome.getText(), campoDescricao.getText(), Integer.parseInt(campoValorUnitario.getText()), Integer.parseInt(campoQuantidade.getText()), campoDataCadastro.getText()
            );
            new ProdutoDAO().atualizar(produto);
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        }
    }

    private void excluirProduto() {
        if (campoId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um produto na tabela!");
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir este produto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            new ProdutoDAO().excluir(Long.parseLong(campoId.getText()));
            JOptionPane.showMessageDialog(null, "Produto excluído com sucesso!");
            limparCampos();
            carregarTabela();
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        ArrayList<Produto> lista = new ProdutoDAO().selecionarTodos();
        for (Produto d : lista) {
            modeloTabela.addRow(new Object[] { d.getId(), d.getNome(), d.getDescricao(), d.getValorUnitario(), d.getQuantidade(), d.getDataCadastro() });
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            campoId.setText(modeloTabela.getValueAt(linha, 0).toString());
            campoNome.setText(modeloTabela.getValueAt(linha, 1).toString());
            campoDescricao.setText(modeloTabela.getValueAt(linha, 2).toString());
            campoValorUnitario.setText(modeloTabela.getValueAt(linha, 3).toString());
            campoQuantidade.setText(modeloTabela.getValueAt(linha, 4).toString());
            campoDataCadastro.setText(modeloTabela.getValueAt(linha, 5).toString());
        }
    }

    private boolean validarCampos() {
        if (campoNome.getText().isEmpty() || campoDescricao.getText().isEmpty() || campoValorUnitario.getText().isEmpty() || campoQuantidade.getText().isEmpty() || campoDataCadastro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        campoId.setText(""); campoNome.setText(""); campoDescricao.setText(""); campoValorUnitario.setText(""); campoQuantidade.setText(""); campoDataCadastro.setText("");
        tabela.clearSelection();
    }
}
    
}
