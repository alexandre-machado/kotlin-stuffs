#!/bin/bash
cd "$(dirname "$0")"
echo "Executando o Benchmark de Regex..."
echo
./gradlew :benchmark
echo
echo "Benchmark concluído!"
read -p "Pressione Enter para continuar..."
