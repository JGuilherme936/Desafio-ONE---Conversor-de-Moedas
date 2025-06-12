package Main;

import Conversor.*;
import Exception.*;
import java.util.*;

public class AplicativoConversorMoeda {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configurar chave da API

        try (scanner) {
            System.out.println("=== Sistema de Conversão de Moedas ===");
            System.out.println("Integração com ExchangeRate-API");
            System.out.println();
            System.out.print("Digite sua chave da API ExchangeRate: ");
            String chaveApi = scanner.nextLine().trim();
            // Configurar o serviço

            GerenciadorServicoMoeda.obterInstancia().configurarChaveApi(chaveApi);
            ConversorMoeda conversor = new ConversorMoeda();

            // Tentar carregar moedas suportadas
            System.out.println("\nCarregando moedas suportadas...");
            try {
                List<Moeda> moedas = conversor.obterMoedasSuportadas();
                FabricaMoeda.atualizarCacheNomes(moedas);

                System.out.println("✓ Carregadas " + moedas.size() + " moedas suportadas");
                System.out.println("\nPrincipais moedas disponíveis:");
                moedas.stream()
                        .filter(m -> Arrays.asList("USD", "BRL", "EUR", "GBP", "JPY", "CAD").contains(m.codigo()))
                        .forEach(System.out::println);

            } catch (ExcecaoMoeda e) {
                System.err.println("⚠ Erro ao carregar moedas: " + e.getMessage());
                if (!"demo".equalsIgnoreCase(chaveApi.replace("-key-123", ""))) {
                    System.err.println("Verifique sua chave da API e conexão com a internet.");
                    return;
                }
            }

            // Menu principal
            while (true) {
                System.out.println("\n=== Menu ===");
                System.out.println("1. Converter moeda");
                System.out.println("2. Ver histórico");
                System.out.println("3. Limpar histórico");
                System.out.println("4. Listar todas as moedas");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opção: ");

                String entrada = scanner.nextLine();
                int opcao;

                try {
                    opcao = Integer.parseInt(entrada);
                } catch (NumberFormatException e) {
                    System.out.println("✗ Opção inválida! Digite um número entre 1 e 5.");
                    continue;
                }

                switch (opcao) {
                    case 1:
                        realizarConversao(conversor, scanner);
                        break;
                    case 2:
                        mostrarHistorico(conversor);
                        break;
                    case 3:
                        conversor.limparHistorico();
                        System.out.println("✓ Histórico limpo!");
                        break;
                    case 4:
                        listarTodasMoedas(conversor);
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("✗ Opção inválida! Digite um número entre 1 e 5.");
                }
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static void realizarConversao(ConversorMoeda conversor, Scanner scanner) {
        try {
            System.out.print("Moeda de origem (ex: USD): ");
            String codigoOrigem = scanner.nextLine().trim().toUpperCase();

            System.out.print("Moeda de destino (ex: BRL): ");
            String codigoDestino = scanner.nextLine().trim().toUpperCase();

            System.out.print("Valor a ser convertido: ");
            double valor = scanner.nextDouble();
            scanner.nextLine(); // Consumir quebra de linha

            System.out.println("\nProcessando conversão...");
            ResultadoConversao resultado = conversor.converter(codigoOrigem, codigoDestino, valor);

            System.out.println("\n✓ === Resultado da Conversão ===");
            System.out.println(resultado);

        } catch (ExcecaoMoeda e) {
            System.err.println("✗ Erro na conversão: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("✗ Erro inesperado: " + e.getMessage());
        }
    }

    private static void mostrarHistorico(ConversorMoeda conversor) {
        List<ResultadoConversao> historico = conversor.obterHistoricoConversoes();

        if (historico.isEmpty()) {
            System.out.println("Nenhuma conversão realizada ainda.");
            return;
        }

        System.out.println("\n=== Histórico de Conversões ===");
        for (int i = 0; i < historico.size(); i++) {
            System.out.println((i + 1) + ". " + historico.get(i));
        }
    }

    private static void listarTodasMoedas(ConversorMoeda conversor) throws ExcecaoApi {
        try {
            System.out.println("\nCarregando lista completa de moedas...");
            List<Moeda> moedas = conversor.obterMoedasSuportadas();

            System.out.println("\n=== Todas as Moedas Suportadas (" + moedas.size() + ") ===");

            // Mostrar em colunas
            int colunas = 3;
            for (int i = 0; i < moedas.size(); i++) {
                System.out.printf("%-25s", moedas.get(i).toString());
                if ((i + 1) % colunas == 0) {
                    System.out.println();
                }
            }
            if (moedas.size() % colunas != 0) {
                System.out.println();
            }

        } catch (ExcecaoMoeda e) {
            System.err.println("✗ Erro ao listar moedas: " + e.getMessage());
        }
    }
}
