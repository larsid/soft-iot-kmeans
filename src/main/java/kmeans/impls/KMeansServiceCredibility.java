package kmeans.impls;

import kmeans.models.KMeans;
import kmeans.services.KMeansService;

import static kmeans.utils.KMeansUtils.completeListData;
import static kmeans.utils.KMeansUtils.getPointsOfClusterWithMaxValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementação do serviço KMeansService voltada para análise de credibilidade.
 * Executa o algoritmo K-Means sobre uma lista de dados unidimensionais,
 * imprime os clusters formados e retorna os pontos do cluster que contém o maior valor.
 */
public class KMeansServiceCredibility implements KMeansService {

    /**
     * Executa o algoritmo K-Means com k = 4 e no máximo 100 iterações.
     *
     * - Garante que a lista tenha pelo menos k elementos preenchendo com -1.0f se necessário.
     * - Inicializa e executa o algoritmo de agrupamento.
     * - Imprime os clusters formados.
     * - Retorna os pontos pertencentes ao cluster que contém o maior valor da lista, excluindo valores -1.0f.
     *
     * @param data Lista de valores (credibilidades) a serem agrupados.
     * @return Lista de pontos do cluster que contém o maior valor.
     */
    @Override
    public List<Float> execute(List<Float> data) {
        long start = System.nanoTime();

        int k = 4;                 // Número de clusters
        int maxIterations = 100;  // Máximo de iterações permitidas

        // Preenche a lista com -1.0f até ter pelo menos k elementos
        completeListData(data, k);

        // Instancia e executa o algoritmo K-Means
        KMeans kmeans = new KMeans(k, maxIterations);
        int[] clusters_of_points = kmeans.fitPredict(data);

        // Organiza os pontos por cluster para exibição
        Map<Integer, List<Float>> clusterMap = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            int cluster = clusters_of_points[i];
            clusterMap.computeIfAbsent(cluster, key -> new ArrayList<>()).add(data.get(i));
        }

        // Exibe os clusters e seus respectivos pontos
        System.out.println("Clusters designados:");
        for (Map.Entry<Integer, List<Float>> entry : clusterMap.entrySet()) {
            System.out.println("Cluster " + entry.getKey() + ":");
            for (Float point : entry.getValue()) {
                System.out.println("  Ponto " + point);
            }
        }

        // Recupera os pontos do cluster que contém o maior valor (ignorando -1.0f)
        List<Float> points_in_cluster_with_max_value = getPointsOfClusterWithMaxValue(data, clusters_of_points);

        System.out.println("\nPontos no cluster que contém o maior valor:");
        for (float point : points_in_cluster_with_max_value) {
            System.out.print(point + " ");
        }

        // Exibe o tempo total de execução
        long end = System.nanoTime();
        double durationInSeconds = (end - start) / 1_000_000_000.0;
        System.out.printf("\n\nTempo de execução: %f segundos\n", durationInSeconds);

        return points_in_cluster_with_max_value;
    }
}
