package kmeans.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Implementação do algoritmo K-Means com inicialização KMeans++ para dados unidimensionais (float).
 */
public class KMeans {

    private final int k;
    private final int maxIterations;

    /**
     * Construtor da classe KMeans.
     *
     * @param k número de clusters.
     * @param maxIterations número máximo de iterações permitidas.
     */
    public KMeans(int k, int maxIterations) {
        this.k = k;
        this.maxIterations = maxIterations;
    }

    /**
     * Inicializa os centróides utilizando o método KMeans++.
     *
     * @param data lista de pontos de dados a serem agrupados.
     * @return array de centróides iniciais.
     */
    public float[] initCentroids(List<Float> data) {
        Random rand = new Random();
        int size_data = data.size();
        List<Float> centroids = new ArrayList<>();

        // Passo 1: escolher o primeiro centróide aleatoriamente
        float firstCentroid = data.get(rand.nextInt(size_data));
        centroids.add(firstCentroid);

        // Lista de mínimas distâncias para cada ponto até o centróide mais próximo
        double[] minDistances = new double[size_data];
        Arrays.fill(minDistances, Double.MAX_VALUE);

        // Passo 2: selecionar os próximos centróides
        while (centroids.size() < this.k) {
            double total = 0.0;

            // Atualizar as distâncias mínimas de cada ponto ao centróide mais próximo
            float lastCentroid = centroids.get(centroids.size() - 1);
            for (int i = 0; i < size_data; i++) {
                float point = data.get(i);
                double dist = Math.pow(point - lastCentroid, 2);

                if (dist < minDistances[i]) {
                    minDistances[i] = dist;
                }
                total += minDistances[i];
            }

            // Passo 3: seleção probabilística do próximo centróide (roleta)
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

        // Converter lista para array
        float[] result = new float[centroids.size()];
        for (int i = 0; i < centroids.size(); i++) {
            result[i] = centroids.get(i);
        }

        return result;
    }

    /**
     * Executa o algoritmo K-Means nos dados fornecidos e retorna o cluster de cada ponto.
     *
     * @param data lista de pontos de dados a serem agrupados.
     * @return array de inteiros representando o índice do cluster de cada ponto.
     */
    public int[] fitPredict(List<Float> data) {
        Random rand = new Random();

        // Inicializa os centróides
        float[] centroids = Arrays.copyOf(initCentroids(data), this.k);
        float[] newCentroids = new float[this.k];

        int[] clusterAssignment = new int[data.size()];
        boolean converged = false;
        int iteration = 0;
        float tolerance = 1e-6f;

        // Loop principal do K-Means
        while (!converged && iteration < this.maxIterations) {
            int[] clusterSizes = new int[k];
            Arrays.fill(newCentroids, 0);

            // Etapa de atribuição: associa cada ponto ao centróide mais próximo
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

            // Etapa de atualização: calcula novos centróides e verifica convergência
            converged = true;
            for (int c = 0; c < k; c++) {
                if (clusterSizes[c] > 0) {
                    newCentroids[c] /= clusterSizes[c];
                } else {
                    // Se um cluster ficou vazio, reinicializa aleatoriamente
                    newCentroids[c] = data.get(rand.nextInt(data.size()));
                }

                if (Math.abs(newCentroids[c] - centroids[c]) > tolerance) {
                    converged = false;
                }
            }

            // Troca os arrays para a próxima iteração
            float[] temp = centroids;
            centroids = newCentroids;
            newCentroids = temp;

            iteration++;
        }

        System.out.println("Iterações: " + iteration);
        return clusterAssignment;
    }
}
