# Kotlin Regex Benchmark

Este projeto contém benchmarks para comparar diferentes abordagens de uso de Regex em Kotlin:

1. Recriando o objeto Regex a cada uso
2. Usando uma instância compartilhada (objeto singleton)
3. Usando o objeto MessageChunker como fornecido

## Estrutura do Benchmark

Dois tipos de benchmarks são fornecidos:

1. **RegexBenchmark**: Compara as três abordagens em operações de splitting de texto em parágrafos e sentenças.
2. **RegexOneMillionBenchmark**: Executa 1 milhão de interações para cada abordagem para medir a diferença de performance em escala.

## Como Executar

### Windows
```
.\run_benchmark.bat
```

### Linux/Mac
```
chmod +x run_benchmark.sh
./run_benchmark.sh
```

Ou usando Gradle diretamente:
```
./gradlew benchmark
```

## Resultados Esperados

Os resultados do benchmark serão exibidos no terminal após a execução.

Espera-se que o uso de instâncias compartilhadas (abordagens 2 e 3) tenha performance significativamente melhor que a recriação do objeto a cada uso, especialmente no benchmark de 1 milhão de interações.

## Explicação

A diferença de performance ocorre porque:

1. **Recriação de Regex**: A cada chamada, o padrão de regex precisa ser compilado novamente, o que é uma operação relativamente cara.
2. **Instância Compartilhada**: O objeto Regex é compilado apenas uma vez e reutilizado em todas as chamadas, economizando o custo de compilação.

O padrão object em Kotlin é uma forma eficiente de garantir uma única instância compartilhada (singleton).


## Resultados
bench\BENCHMARK_ANALYSIS.md