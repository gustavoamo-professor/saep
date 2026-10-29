package com.example.simulado.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuController {

    @FXML
    private Button botaoConsultarDados;

    @FXML
    public void initialize() {
        // Garante que o botão está ativo
        botaoConsultarDados.setDisable(false);
        
        // Define o que acontece quando o botão for clicado
        botaoConsultarDados.setOnAction(event -> abrirIndexTemplate());
    }

    private void abrirIndexTemplate() {
        try {
            // 1. Carrega o arquivo FXML da nova tela (seu template/index)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/templetes/Principal.fxml"));
            Parent root = loader.load();

            // 2. Pega a janela (Stage) atual através do próprio botão
            Stage stage = (Stage) botaoConsultarDados.getScene().getWindow();

            // 3. Define a nova cena com o template carregado
            stage.setScene(new Scene(root));
            stage.setTitle("Consulta de Dados - Index");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela index.fxml. Verifique o caminho do arquivo.");
        }
    }

    
}
