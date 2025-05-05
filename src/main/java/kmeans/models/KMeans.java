package kmeans.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KMeans {

    private final int k;
    private final int maxIterations;

    public KMeans(int k, int maxIterations) {
        this.k = k;
        this.maxIterations = maxIterations;
    }

    public float[] initCentroids(List<Float> data) {
        Random rand = new Random();
        int size_data = data.size();

        // Lista de centroides
        List<Float> centroids = new ArrayList<>();

        // Passo 1: escolher o primeiro centróide aleatoriamente
        float firstCentroid = data.get(rand.nextInt(size_data));
        centroids.add(firstCentroid);

        // Lista de mínimas distâncias para cada ponto
        double[] minDistances = new double[size_data];
        Arrays.fill(minDistances, Double.MAX_VALUE);

        // Passo 2: escolher os demais centróides
        while (centroids.size() < this.k) {
            // Para cada ponto, calcular a menor distância ao centróide mais próximo
            double total = 0.0; // Soma das menores distâncias para cada centróide
            float lastCentroid = centroids.get(centroids.size() - 1); // Último centróide adicionado

            for (int i = 0; i < size_data; i++) {
                float aux_data = data.get(i);
                double dist = Math.pow(aux_data - lastCentroid, 2); // Distância entre o ponto e o centróide

                if (dist < minDistances[i]) {
                    minDistances[i] = dist;
                }
                total += minDistances[i];
            }

            // Passo 3: escolher novo centróide com probabilidade proporcional à distância

            // Escolher um valor aleatório entre o total das distâncias, pensar na lógica de uma roleta
            double r = rand.nextDouble() * total;
            double cumulative = 0.0;

            for (int i = 0; i < size_data; i++) {
                cumulative += minDistances[i];
                if (cumulative >= r) {
                    centroids.add(data.get(i));
                    break;
                }
            }
        }

        // Converter lista para array de float
        float[] result = new float[centroids.size()];
        for (int i = 0; i < centroids.size(); i++) {
            result[i] = centroids.get(i);
        }

        return result;
    }

    public int[] fitPredict(List<Float> data) {
        Random rand = new Random();
        float[] centroids = Arrays.copyOf(initCentroids(data), this.k);
        float[] newCentroids = new float[this.k];
        int[] clusterAssignment = new int[data.size()];
        boolean converged = false;
        int iteration = 0;
        float tolerance = 1e-6f;

        while (!converged && iteration < this.maxIterations) {
            int[] clusterSizes = new int[k];
            Arrays.fill(newCentroids, 0);

            // Passo 1: Atribuir pontos aos clusters
            for (int i = 0; i < data.size(); i++) {
                float point = data.get(i);
                float minDist = Float.MAX_VALUE;
                int bestCluster = 0;

                for (int c = 0; c < k; c++) {
                    float dist = (point - centroids[c]) * (point - centroids[c]);
                    if (dist < minDist) {
                        minDist = dist;
                        bestCluster = c;
                    }
                }

                clusterAssignment[i] = bestCluster;
                newCentroids[bestCluster] += point;
                clusterSizes[bestCluster]++;
            }

            // Passo 2: Atualizar centróides e verificar convergência
            converged = true;
            for (int c = 0; c < k; c++) {
                if (clusterSizes[c] > 0) {
                    newCentroids[c] /= clusterSizes[c];
                } else {
                    // Heurística mais eficiente para clusters vazios
                    newCentroids[c] = data.get(rand.nextInt(data.size()));
                }

                if (Math.abs(newCentroids[c] - centroids[c]) > tolerance) {
                    converged = false;
                }
            }

            // Swap para evitar cópias
            float[] temp = centroids;
            centroids = newCentroids;
            newCentroids = temp;
            iteration++;
        }

        System.out.println("Iterações: " + iteration);
        return clusterAssignment;
    }

}