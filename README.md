# Corrida do Douro (POO – UAL 2026/27)
Java 17+ (testado com 21). Para ver acentos na consola use -Dstdout.encoding=UTF-8 (Java 19+).
```
mkdir -p out && javac -d out src/*.java test/*.java
java -Dstdout.encoding=UTF-8 -cp out ConsoleUI     # jogar
java -Dstdout.encoding=UTF-8 -cp out GameChecks    # testes (exit code 0 = todos passam)
```
