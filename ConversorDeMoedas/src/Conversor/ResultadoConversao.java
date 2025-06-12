package Conversor;

public class ResultadoConversao {
    private final Moeda moedaOrigem;
    private final Moeda moedaDestino;
    private final double valor;
    private final double valorConvertido;
    private final double taxa;
    private final String dataHora;

    public ResultadoConversao(Moeda moedaOrigem, Moeda moedaDestino,
                              double valor, double valorConvertido, double taxa, String dataHora) {
        this.moedaOrigem = moedaOrigem;
        this.moedaDestino = moedaDestino;
        this.valor = valor;
        this.valorConvertido = valorConvertido;
        this.taxa = taxa;
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s = %.2f %s (Taxa: %.4f) - %s",
                valor, moedaOrigem.codigo(),
                valorConvertido, moedaDestino.codigo(),
                taxa, dataHora);
    }
}
