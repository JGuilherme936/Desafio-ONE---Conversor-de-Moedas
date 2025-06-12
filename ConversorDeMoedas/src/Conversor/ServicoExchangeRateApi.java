package Conversor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import Exception.*;
import com.google.gson.*;

public class ServicoExchangeRateApi implements ServicoMoeda {
    private static final String URL_BASE_API = "https://v6.exchangerate-api.com/v6/";
    private final String chaveApi;

    // Construtor requer a chave da API
    public ServicoExchangeRateApi(String chaveApi) {
        if (chaveApi == null || chaveApi.trim().isEmpty()) {
            throw new IllegalArgumentException("Chave da API é obrigatória");
        }
        this.chaveApi = chaveApi.trim();
    }

    @Override
    public ResultadoConversao converter(Moeda origem, Moeda destino, double valor) throws ExcecaoMoeda, ExcecaoApi {
        if (valor < 0) {
            throw new ExcecaoMoedaInvalida("O valor não pode ser negativo");
        }

        try {
            Map<String, Double> taxas = obterTaxasCambio(origem.codigo());

            if (!taxas.containsKey(destino.codigo())) {
                throw new ExcecaoMoedaInvalida("Moeda de destino não suportada: " + destino.codigo());
            }

            double taxa = taxas.get(destino.codigo());
            double valorConvertido = valor * taxa;
            String dataHora = java.time.LocalDateTime.now().toString();

            return new ResultadoConversao(origem, destino, valor, valorConvertido, taxa, dataHora);

        } catch (ExcecaoMoeda e) {
            // Re-lança exceções já tratadas
            throw e;
        } catch (Exception e) {
            throw new ExcecaoApi("Erro ao realizar conversão", e);
        }
    }

    @Override
    public Map<String, Double> obterTaxasCambio(String moedaBase) throws ExcecaoMoeda, ExcecaoApi {
        try {
            // Construir URL da API
            String urlString = URL_BASE_API + chaveApi + "/latest/" + moedaBase.toUpperCase();
            @SuppressWarnings("deprecation") URL url = new URL(urlString);

            // Fazer requisição HTTP
            HttpURLConnection requisicao = (HttpURLConnection) url.openConnection();
            requisicao.setRequestMethod("GET");
            requisicao.setConnectTimeout(10000); // 10 segundos
            requisicao.setReadTimeout(10000); // 10 segundos
            requisicao.connect();

            // Verificar código de resposta
            int codigoResposta = requisicao.getResponseCode();
            if (codigoResposta != 200) {
                throw new ExcecaoApi("Erro na API: Código " + codigoResposta);
            }

            // Ler resposta
            InputStreamReader leitor = new InputStreamReader(requisicao.getInputStream());

            // Processar JSON
            @SuppressWarnings("deprecation") JsonParser parser = new JsonParser();
            @SuppressWarnings("deprecation") JsonElement elemento = parser.parse(leitor);
            Map<String, Double> taxas = getStringDoubleMap(elemento);

            leitor.close();
            requisicao.disconnect();

            return taxas;

        } catch (MalformedURLException e) {
            throw new ExcecaoApi("URL da API inválida", e);
        } catch (IOException e) {
            throw new ExcecaoApi("Erro de conexão com a API", e);
        } catch (JsonSyntaxException e) {
            throw new ExcecaoApi("Erro ao processar resposta JSON da API", e);
        } catch (Exception e) {
            throw new ExcecaoApi("Erro inesperado ao obter taxas de câmbio", e);
        }
    }

    private static Map<String, Double> getStringDoubleMap(JsonElement elemento) throws ExcecaoApi {
        JsonObject objetoJson = elemento.getAsJsonObject();

        // Verificar resultado
        String resultado = objetoJson.get("result").getAsString();
        if (!"success".equals(resultado)) {
            String tipoErro = objetoJson.has("error-type") ?
                    objetoJson.get("error-type").getAsString() : "erro desconhecido";
            throw new ExcecaoApi("Erro na API: " + tipoErro);
        }

        // Extrair taxas de conversão
        JsonObject taxasJson = objetoJson.getAsJsonObject("conversion_rates");
        Map<String, Double> taxas = new HashMap<>();

        for (Map.Entry<String, JsonElement> entrada : taxasJson.entrySet()) {
            taxas.put(entrada.getKey(), entrada.getValue().getAsDouble());
        }
        return taxas;
    }

    @Override
    public List<Moeda> obterMoedasSuportadas() throws ExcecaoMoeda, ExcecaoApi {
        try {
            // Usar USD como base para obter todas as moedas suportadas
            Map<String, Double> taxas = obterTaxasCambio("USD");

            // Criar lista de moedas baseada nos códigos retornados pela API
            List<Moeda> moedasSuportadas = new ArrayList<>();

            // Adicionar moedas principais com nomes em português
            Map<String, String> nomesMoedas = obterNomesMoedasPrincipais();

            for (String codigo : taxas.keySet()) {
                String nome = nomesMoedas.getOrDefault(codigo, "Moeda " + codigo);
                moedasSuportadas.add(new Moeda(codigo, nome));
            }

            // Ordenar por código
            moedasSuportadas.sort(Comparator.comparing(Moeda::codigo));

            return moedasSuportadas;

        } catch (Exception e) {
            if (e instanceof ExcecaoMoeda) {
                throw e;
            }
            throw new ExcecaoApi("Erro ao obter moedas suportadas", e);
        }
    }

    private Map<String, String> obterNomesMoedasPrincipais() {
        Map<String, String> nomes = new HashMap<>();
        nomes.put("USD", "Dólar Americano");
        nomes.put("BRL", "Real Brasileiro");
        nomes.put("EUR", "Euro");
        nomes.put("GBP", "Libra Esterlina");
        nomes.put("JPY", "Iene Japonês");
        nomes.put("CAD", "Dólar Canadense");
        nomes.put("AUD", "Dólar Australiano");
        nomes.put("CHF", "Franco Suíço");
        nomes.put("CNY", "Yuan Chinês");
        nomes.put("INR", "Rupia Indiana");
        nomes.put("MXN", "Peso Mexicano");
        nomes.put("SGD", "Dólar de Singapura");
        nomes.put("HKD", "Dólar de Hong Kong");
        nomes.put("NOK", "Coroa Norueguesa");
        nomes.put("SEK", "Coroa Sueca");
        nomes.put("KRW", "Won Sul-Coreano");
        nomes.put("RUB", "Rublo Russo");
        nomes.put("ZAR", "Rand Sul-Africano");
        nomes.put("TRY", "Lira Turca");
        nomes.put("NZD", "Dólar Neozelandês");
        return nomes;
    }
}
