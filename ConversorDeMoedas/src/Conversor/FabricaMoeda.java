package Conversor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Exception.*;

public class FabricaMoeda {
    private static final Map<String, String> NOMES_MOEDAS_CACHE = new HashMap<>();

    public static Moeda criarMoeda(String codigo) throws ExcecaoMoedaInvalida {
        String codigoMaiusculo = codigo.toUpperCase();

        // Verificar se o código tem formato válido (3 letras)
        if (!codigoMaiusculo.matches("^[A-Z]{3}$")) {
            throw new ExcecaoMoedaInvalida("Código de moeda deve ter 3 letras: " + codigo);
        }

        // Usar nome do cache se disponível, senão usar formato genérico
        String nome = NOMES_MOEDAS_CACHE.getOrDefault(codigoMaiusculo, "Moeda " + codigoMaiusculo);

        return new Moeda(codigoMaiusculo, nome);
    }

    // Método para atualizar cache de nomes das moedas
    public static void atualizarCacheNomes(List<Moeda> moedas) {
        NOMES_MOEDAS_CACHE.clear();
        for (Moeda moeda : moedas) {
            NOMES_MOEDAS_CACHE.put(moeda.codigo(), moeda.nome());
        }
    }
}
