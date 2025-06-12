package Conversor;

import Exception.*;
import java.util.ArrayList;
import java.util.List;

public class ConversorMoeda {
    private final ServicoMoeda servicoMoeda;
    private final List<ResultadoConversao> historicoConversoes;

    public ConversorMoeda() {
        this.servicoMoeda = GerenciadorServicoMoeda.obterInstancia().obterServicoMoeda();
        this.historicoConversoes = new ArrayList<>();
    }

    public ResultadoConversao converter(String codigoOrigem, String codigoDestino, double valor) throws ExcecaoMoeda, ExcecaoApi {
        Moeda moedaOrigem = FabricaMoeda.criarMoeda(codigoOrigem);
        Moeda moedaDestino = FabricaMoeda.criarMoeda(codigoDestino);

        ResultadoConversao resultado = servicoMoeda.converter(moedaOrigem, moedaDestino, valor);
        historicoConversoes.add(resultado);

        return resultado;
    }

    public List<ResultadoConversao> obterHistoricoConversoes() {
        return new ArrayList<>(historicoConversoes);
    }

    public void limparHistorico() {
        historicoConversoes.clear();
    }

    public List<Moeda> obterMoedasSuportadas() throws ExcecaoMoeda, ExcecaoApi {
        return servicoMoeda.obterMoedasSuportadas();
    }
}
