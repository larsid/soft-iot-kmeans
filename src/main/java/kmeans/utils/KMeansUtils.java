package kmeans.utils;

import java.util.ArrayList;
import java.util.List;

public class KMeansUtils {

    public static void completeListData(List<Float> data, int k) {
        while (data.size() < k) {
            data.add(-1.0f);
        }
    }

    public static List<Float> getPointsOfClusterWithMaxValue(List<Float> data, int[] clusterAssignment) {
        // Encontrar o índice do maior valor
        int maxIndex = 0;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i) > data.get(maxIndex)) {
                maxIndex = i;
            }
        }

        int clusterOfMax = clusterAssignment[maxIndex];

        // Coletar todos os dados que pertencem a esse cluster
        List<Float> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (clusterAssignment[i] == clusterOfMax) {
                result.add(data.get(i));
            }
        }

        return result;
    }

}
