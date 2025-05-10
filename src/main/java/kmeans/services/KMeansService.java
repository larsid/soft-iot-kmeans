package kmeans.services;

import java.util.List;

/**
 * Interface que define o contrato para serviços que executam o algoritmo K-Means.
 */
public interface KMeansService {

    /**
     * Executa o algoritmo K-Means sobre a lista de dados fornecida.
     *
     * @param data lista de pontos de dados unidimensionais a serem agrupados.
     * @return lista de pontos que pertencem ao cluster de interesse (por exemplo, o que contém o maior valor).
     */
    List<Float> execute(List<Float> data);
}
