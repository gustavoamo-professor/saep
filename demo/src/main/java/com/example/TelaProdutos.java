package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TelaProdutos extends JFrame {
    private final Produtos produtosService = new Produtos();
    private final JTextField campoNome = new JTextField(20);
    private final JTextField campoData = new JTextField(10);
    private final JLabel labelPagina = new JLabel("Página 1");
    private final DefaultTableModel modeloTabela;
    private int paginaAtual = 1;
    private final int tamanhoPagina = 3;

    public TelaProdutos() {
        super("Consulta de Produtos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 440);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        modeloTabela = new DefaultTableModel(new Object[]{"Nome", "Data", "Preço"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modeloTabela);
        tabela.setRowHeight(28);
        tabela.getTableHeader().setReorderingAllowed(false);

        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        painelFiltro.setBorder(BorderFactory.createTitledBorder("Filtros"));
        painelFiltro.add(new JLabel("Nome:"));
        painelFiltro.add(campoNome);
        painelFiltro.add(new JLabel("Data (yyyy-MM-dd):"));
        painelFiltro.add(campoData);

        JButton botaoFiltrar = new JButton("Filtrar");
        JButton botaoLimpar = new JButton("Limpar");
        painelFiltro.add(botaoFiltrar);
        painelFiltro.add(botaoLimpar);

        JPanel painelNavegacao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botaoAnterior = new JButton("Anterior");
        JButton botaoProximo = new JButton("Próximo");
        painelNavegacao.add(labelPagina);
        painelNavegacao.add(botaoAnterior);
        painelNavegacao.add(botaoProximo);

        add(painelFiltro, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(painelNavegacao, BorderLayout.SOUTH);

        botaoFiltrar.addActionListener(e -> {
            paginaAtual = 1;
            carregarProdutos();
        });

        botaoLimpar.addActionListener(e -> {
            campoNome.setText("");
            campoData.setText("");
            paginaAtual = 1;
            carregarProdutos();
        });

        botaoAnterior.addActionListener(e -> {
            if (paginaAtual > 1) {
                paginaAtual--;
                carregarProdutos();
            }
        });

        botaoProximo.addActionListener(e -> {
            paginaAtual++;
            carregarProdutos();
        });

        carregarProdutos();
    }

    private void carregarProdutos() {
        String nomeFiltro = campoNome.getText() == null ? "" : campoNome.getText().trim();
        LocalDate dataFiltro = null;

        if (!campoData.getText().trim().isEmpty()) {
            try {
                dataFiltro = LocalDate.parse(campoData.getText().trim());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Data inválida. Use o formato yyyy-MM-dd.",
                        "Erro de validação",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        List<Produto> produtos = produtosService.listarProdutos(
                nomeFiltro.isEmpty() ? null : nomeFiltro,
                dataFiltro,
                paginaAtual,
                tamanhoPagina
        );

        modeloTabela.setRowCount(0);
        for (Produto produto : produtos) {
            modeloTabela.addRow(new Object[]{
                    produto.getNome(),
                    produto.getDataCadastro(),
                    String.format("R$ %.2f", produto.getPreco())
            });
        }

        labelPagina.setText("Página " + paginaAtual);

        if (produtos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum produto encontrado para os filtros informados.",
                    "Resultado vazio",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
