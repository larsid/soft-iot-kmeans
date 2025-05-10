package kmeans.utils;

import java.util.ArrayList;
import java.util.List;

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
     * Retorna os pontos do cluster que contém o maior valor da lista de dados,
     * excluindo os elementos com valor igual a -1.0f (considerados placeholders).
     *
     * @param data lista de dados original.
     * @param clusterAssignment array que indica o cluster de cada ponto.
     * @return lista contendo apenas os pontos diferentes de -1.0f do cluster que possui o maior valor.
     */
    public static List<Float> getPointsOfClusterWithMaxValue(List<Float> data, int[] clusterAssignment) {
        // Encontrar o índice do maior valor
        int maxIndex = 0;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i) > data.get(maxIndex)) {
                maxIndex = i;
            }
        }

        // Identificar o cluster ao qual o maior valor pertence
        int clusterOfMax = clusterAssignment[maxIndex];

        // Coletar todos os dados desse cluster, exceto os com valor -1.0f
        List<Float> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (clusterAssignment[i] == clusterOfMax && data.get(i) != -1.0f) {
                result.add(data.get(i));
            }
        }

        return result;
    }
}
