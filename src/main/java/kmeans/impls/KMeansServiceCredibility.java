package kmeans.impls;

import kmeans.models.KMeans;
import kmeans.services.KMeansService;

import static kmeans.utils.KMeansUtils.completeListData;
import static kmeans.utils.KMeansUtils.getPointsOfClusterWithMaxValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KMeansServiceCredibility implements KMeansService {

    @Override
    public List<Float> execute(List<Float> data) {

        long start = System.nanoTime();

        int k = 4;
        int maxIterations = 100;

        completeListData(data, k);

        KMeans kmeans = new KMeans(k, maxIterations);

        int[] clusters_of_points = kmeans.fitPredict(data);

        // Exibição de dados
        Map<Integer, List<Float>> clusterMap = new HashMap<>();

        for (int i = 0; i < data.size(); i++) {
            int cluster = clusters_of_points[i];
            clusterMap.computeIfAbsent(cluster, key -> new ArrayList<>()).add(data.get(i));
        }

        System.out.println("Clusters designados:");
        for (Map.Entry<Integer, List<Float>> entry : clusterMap.entrySet()) {
            System.out.println("Cluster " + entry.getKey() + ":");
            for (Float point : entry.getValue()) {
                System.out.println("  Ponto " + point);
            }
        }

        List<Float> points_in_cluster_with_max_value = getPointsOfClusterWithMaxValue(data, clusters_of_points);

        System.out.println("\nPontos no cluster que contém o maior valor:");
        for (float point : points_in_cluster_with_max_value) {
            System.out.print(point + " ");
        }

        long end = System.nanoTime();
        double durationInSeconds = (end - start) / 1_000_000_000.0;
        System.out.printf("\n\nTempo de execução: %f segundos\n", durationInSeconds);

        return points_in_cluster_with_max_value;

    }

}
