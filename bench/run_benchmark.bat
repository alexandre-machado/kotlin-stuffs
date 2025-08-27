@echo off
cd %~dp0
echo Executando o Benchmark de Regex...
echo.
call gradlew :benchmark
echo.
echo Benchmark concluído!
pause
