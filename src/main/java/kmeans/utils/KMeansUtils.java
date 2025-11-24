package kmeans.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe utilitária para operações auxiliares relacionadas ao algoritmo K-Means.
 */
public class KMeansUtils {

    /**
     * Completa a lista de dados com o valor -1.0f até que seu tamanho seja igual ou maior que k.
     * Útil para garantir que a lista tenha pelo menos k elementos antes da aplicação do K-Means.
     *
     * @param data lista de dados a ser completada.
     * @param k número mínimo de elementos que a lista deve conter.
     */
    public static void completeListData(List<Float> data, int k) {
        while (data.size() < k) {
            data.add(-1.0f);
        }
    }

    /**
     * Retorna uma lista de valores únicos pertencentes ao mesmo cluster
     * que contém o maior valor da lista de dados fornecida.
     * Valores com o marcador -1.0f (preenchimento artificial) são ignorados.
     * Valores duplicados não são incluídos mais de uma vez no resultado.
     *
     * @param data Lista de valores a serem analisados.
     * @param clusterAssignment Vetor indicando o cluster de cada ponto da lista.
     * @return Lista de valores únicos pertencentes ao cluster do maior valor (sem repetições e sem -1.0f).
     */
    public static List<Float> getPointsOfClusterWithMaxValue(List<Float> data, int[] clusterAssignment) {
        // Encontrar o índice do maior valor da lista
        int maxIndex = 0;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i) > data.get(maxIndex)) {
                maxIndex = i;
            }
        }

        // Identificar a qual cluster pertence o maior valor
        int clusterOfMax = clusterAssignment[maxIndex];

        // Usar um Set para garantir que os valores sejam únicos
        Set<Float> result = new HashSet<>();

        // Adicionar ao resultado apenas valores únicos do mesmo cluster e diferentes de -1.0f
        for (int i = 0; i < data.size(); i++) {
            float value = data.get(i);
            if (clusterAssignment[i] == clusterOfMax && value != -1.0f) {
                result.add(value);
            }
        }

        // Converter o conjunto em lista antes de retornar
        return new ArrayList<>(result);
    }
    
}
