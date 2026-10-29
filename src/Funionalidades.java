import java.util.ArrayList;
import java.util.Scanner;

public class Funionalidades {

    // Criamos a lista aqui fora para que todos os métodos (inserir, consultar, etc.) possam acessá-la
    private static ArrayList<Produto> listaDeProdutos = new ArrayList<>();
    
    // Variável global para controlar se o usuário logado é administrador
    private static boolean ehAdmin = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // --- SISTEMA DE LOGIN NO INÍCIO ---
        System.out.println("=== SISTEMA DE LOGIN ===");
        System.out.println("Como deseja logar?");
        System.out.println("1. Administrador ");
        System.out.println("2. Operador de Almoxarifado ");
        System.out.print("Escolha uma opção de login: ");
        
        int tipoLogin = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        
        if (tipoLogin == 1) {
            ehAdmin = true;
            System.out.println("Logado como Administrador com sucesso!");
        } else {
            ehAdmin = false;
            System.out.println("Logado como Operador de Almoxarifado.");
        }
        // ----------------------------------

        int opcao = 0;

        do {
            System.out.println("\n=== MENU DE OPÇÕES ===");
            System.out.println("1. Consultar dados");
            System.out.println("2. Inserir Dados");
            System.out.println("3. Atualizar Dados");
            
            // Visualmente mostra se o usuário tem ou não acesso à exclusão
            if (ehAdmin) {
                System.out.println("4. Deletar Dados [Disponível]");
            } else {
                System.out.println("4. Deletar Dados [BLOQUEADO para operador de almoxarifado]");
            }
            
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer

                switch (opcao) {
                    case 1:
                        consultarDados(scanner);
                        break;
                    case 2:
                        inserirDados(scanner);
                        break;
                    case 3:
                        atualizarDados(scanner);
                        break;
                    case 4:
                        // Chama o método que valida se o usuário realmente pode deletar
                        deletarDados(scanner);
                        break;
                    case 0:
                        System.out.println("Encerrando o programa...");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } else {
                System.out.println("Por favor, digite um número válido.");
                scanner.next(); 
            }

        } while (opcao != 0);

        scanner.close();
    }

    // --- SUBROTINAS / MÉTODOS PARA CADA FUNCIONALIDADE ---

    private static void consultarDados(Scanner scanner) {
        System.out.println("\n--- CONSULTAR PRODUTOS ---");
        if (listaDeProdutos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado no sistema.");
            return;
        }
        
        for (Produto p : listaDeProdutos) {
            System.out.println("ID: " + p.getId());
            System.out.println("Descrição: " + p.getDescricao());
            System.out.println("Valor unitário: R$" + p.getValorUnitario());
            System.out.println("Quantidade: " + p.getQuantidade());
            System.out.println("----------------------------------------");
        }
    }

    private static void inserirDados(Scanner scanner) {
        System.out.println("\n--- INSERIR NOVO PRODUTO ---");
        
        System.out.print("Digite a descrição: ");
        String desc = scanner.nextLine();
        
        System.out.print("Digite o valor unitário: ");
        double valor = scanner.nextDouble();

        if (valor < 0) {
            System.out.println("Valor unitário não pode ser negativo. Operação cancelada.");
            scanner.nextLine(); 
            return;
        }
        
        System.out.print("Digite a quantidade: ");
        int qtd = scanner.nextInt();
        scanner.nextLine(); 

        if (qtd < 0) {
            System.out.println("Quantidade não pode ser negativa. Operação cancelada.");
            scanner.nextLine(); 
            return;
        }

        // Cria o produto (ID e Data automáticos) e adiciona na lista
        Produto novo = new Produto(desc, valor, qtd);
        listaDeProdutos.add(novo);
        
        System.out.println("Produto cadastrado com sucesso! ID gerado: " + novo.getId());
    }

    private static void atualizarDados(Scanner scanner) {
        System.out.println("\n--- ATUALIZAR PRODUTO ---");
        if (listaDeProdutos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado para atualizar.");
            return;
        }

        System.out.print("Digite o ID do produto que deseja alterar: ");
        int idProcurado = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        // Busca o produto pelo ID na lista
        Produto produtoEncontrado = null;
        for (Produto p : listaDeProdutos) {
            if (p.getId() == idProcurado) {
                produtoEncontrado = p;
                break;
            }
        }

        if (produtoEncontrado != null) {
            System.out.print("Nova descrição (Atual: " + produtoEncontrado.getDescricao() + "): ");
            String novaDesc = scanner.nextLine();
            
            System.out.print("Novo valor unitário (Atual: R$" + produtoEncontrado.getValorUnitario() + "): ");
            double novoValor = scanner.nextDouble();
            
            System.out.print("Nova quantidade (Atual: " + produtoEncontrado.getQuantidade() + "): ");
            int novaQtd = scanner.nextInt();
            scanner.nextLine();

            produtoEncontrado.setDescricao(novaDesc);
            produtoEncontrado.setValorUnitario(novoValor);
            produtoEncontrado.setQuantidade(novaQtd);

            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Produto com ID " + idProcurado + " não foi encontrado.");
        }
    }

    private static void deletarDados(Scanner scanner) {
        System.out.println("\n--- DELETAR PRODUTO ---");
        
        if (!ehAdmin) {
            System.out.println("ERRO: Apenas usuários Administradores podem deletar dados!");
            return;
        }

        if (listaDeProdutos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado para deletar.");
            return;
        }

        System.out.print("Digite o ID do produto que deseja deletar: ");
        int idProcurado = scanner.nextInt();
        scanner.nextLine();

        boolean removido = listaDeProdutos.removeIf(produto -> produto.getId() == idProcurado);

        if (removido) {
            System.out.println("Produto deletado com sucesso!");
        } else {
            System.out.println("Produto com ID " + idProcurado + " não foi encontrado.");
        }
    }
}
