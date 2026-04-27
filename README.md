# SOFT-IoT-KMeans

Este bundle fornece uma implementação nativa em Java do algoritmo de agrupamento **K-Means** (com inicialização **K-Means++**) para o sistema de reputação do projeto SOFT-IoT.

## Descrição do Projeto

Originalmente, o cálculo de credibilidade no sistema de reputação dependia de um script Python externo. Esta implementação Java foi desenvolvida para eliminar a sobrecarga de comunicação entre processos (via `ProcessBuilder`), resultando numa execução aproximadamente **5830 vezes mais rápida** e com maior estabilidade de desempenho.

O bundle é responsável por agrupar os nós avaliadores com base em seus valores de credibilidade, permitindo identificar o cluster dos nós mais credíveis para o cálculo da reputação final.

## Funcionalidades

- **Algoritmo K-Means Unidimensional**: Otimizado para agrupar valores de credibilidade (float).
- **Inicialização KMeans++**: Implementa uma seleção probabilística de centróides iniciais para evitar resultados inconsistentes e reduzir o tempo de convergência.
- **Arquitetura OSGi**: Desenvolvido como um bundle modular para ser executado em gateways baseados em Java (como o Apache Karaf).
- **Interface de Serviço**: Expõe o serviço `KMeansService` via Blueprint para fácil integração com outros módulos do sistema.

## Estrutura do Código

- `kmeans.services.KMeansService`: Interface que define o contrato do serviço.
- `kmeans.impls.KMeansServiceCredibility`: Implementação do serviço voltada para análise de credibilidade, configurada com `k=4` e 100 iterações máximas.
- `kmeans.models.KMeans`: Lógica central do algoritmo e inicialização KMeans++.
- `kmeans.utils.KMeansUtils`: Operações auxiliares, como o tratamento de listas curtas e recuperação do cluster com o valor máximo.

## Requisitos e Compilação

O projeto utiliza o **Maven** para gestão de dependências e empacotamento.

- **Java**: 1.8 ou superior.
- **Maven**: 3.8.1+.
- **Dependências**: Apenas bibliotecas padrão do Java.

Para compilar e gerar o bundle:
```bash
mvn clean install
