package Conversor;

public class GerenciadorServicoMoeda {
    private static GerenciadorServicoMoeda instancia;
    private ServicoMoeda servicoMoeda;

    private GerenciadorServicoMoeda() {
        // Inicialização será feita quando a chave da API for configurada
    }

    public static synchronized GerenciadorServicoMoeda obterInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorServicoMoeda();
        }
        return instancia;
    }

    public void configurarChaveApi(String chaveApi) {
        this.servicoMoeda = new ServicoExchangeRateApi(chaveApi);
    }

    public ServicoMoeda obterServicoMoeda() {
        if (servicoMoeda == null) {
            throw new IllegalStateException("Chave da API não foi configurada. Use configurarChaveApi() primeiro.");
        }
        return servicoMoeda;
    }
}
