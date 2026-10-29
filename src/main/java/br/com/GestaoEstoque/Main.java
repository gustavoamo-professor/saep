/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
*/
package br.com.GestaoEstoque;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import br.com.GestaoEstoque.view.TelaPrincipal;
public class Main {
public static void main(String[] args) {
configurarAparencia();
SwingUtilities.invokeLater(() -> {
TelaPrincipal telaPrincipal = new TelaPrincipal();
telaPrincipal.setVisible(true);
});
}
private static void configurarAparencia() {
try {
UIManager.setLookAndFeel(
UIManager.getSystemLookAndFeelClassName()
);
} catch (Exception e) {
JOptionPane.showMessageDialog(
null,
"Não foi possível carregar a aparência do sistema.",
"Aviso",
JOptionPane.WARNING_MESSAGE
);
}
}
}
