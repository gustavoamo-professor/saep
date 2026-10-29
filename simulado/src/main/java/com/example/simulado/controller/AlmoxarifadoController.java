package com.example.simulado.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.simulado.dao.ProdutoDAO;
import com.example.simulado.model.Produto;

public class AlmoxarifadoController implements Initializable {

    @FXML
    private TextField txtNomeProduto;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtPreco;

    private ProdutoDAO produtoDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.produtoDAO = new ProdutoDAO();
    }

    @FXML
    private void handleAdicionarProduto() {
        if (produtoDAO == null) {
            System.out.println("Erro: ProdutoDAO ainda está nulo!");
            return;
        }

        try {
            String nome = txtNomeProduto.getText();
            String descricao = txtDescricao.getText();
            int qtd = Integer.parseInt(txtQuantidade.getText());
            double preco = Double.parseDouble(txtPreco.getText());

            // Ajuste a ordem dos parâmetros do construtor conforme a sua classe Produto
            Produto novoProduto = new Produto(nome, descricao, preco, qtd);

            boolean sucesso = produtoDAO.adicionar(novoProduto);

            if (sucesso) {
                System.out.println("Produto adicionado com sucesso!");
                limparCampos();
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Preencha os campos de quantidade e preço com números válidos!");
        }
    }

    private void limparCampos() {
        txtNomeProduto.clear();
        txtQuantidade.clear();
        txtDescricao.clear();
        txtPreco.clear();
    }
}
