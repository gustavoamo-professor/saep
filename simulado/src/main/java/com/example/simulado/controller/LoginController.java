package com.example.simulado.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private Button btnConsultar; // Seu botão "Consultar dados internos"

    @FXML
    void handleConsultarDados(ActionEvent event) {
        try {
            // 1. Carrega o FXML da tela de produtos do almoxarifado
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProdutosAlmoxarifado.fxml"));
            Parent produtosView = loader.load();

            // 2. Obtém o Stage (janela) atual a partir do evento do botão
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. Cria a nova cena com a tela de produtos
            Scene scene = new Scene(produtosView);

            // 4. Define a nova cena no stage e exibe
            stage.setScene(scene);
            stage.setTitle("Almoxarifado - Consulta de Produtos");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Aqui você pode tratar o erro (ex: mostrar um alerta na tela)
        }
    }
}
