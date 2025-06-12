package Conversor;

import java.util.List;
import java.util.Map;
import Exception.*;

public interface ServicoMoeda {
    ResultadoConversao converter(Moeda origem, Moeda destino, double valor) throws ExcecaoMoeda, ExcecaoApi;
    Map<String, Double> obterTaxasCambio(String moedaBase) throws ExcecaoMoeda, ExcecaoApi;
    List<Moeda> obterMoedasSuportadas() throws ExcecaoMoeda, ExcecaoApi;
}
