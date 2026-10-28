# Corrida Douro — regras, mapa e design (checkpoint de outubro, rascunho)

## Regras
- 2 jogadores, tabuleiro de 30 casas (1–30); início = posição 0 (fora do tabuleiro). Joga primeiro o Jogador 1, depois alternam.
- Dado 1–6: aleatório ou manual (escolhido no início). Ambos chamam `Game.playTurn(int)`.
- Entrada manual inválida: mensagem de erro, estado inalterado, mesmo jogador joga de novo.
- Meta: chegar à casa 30 ou ultrapassá-la (ultrapassar = vitória, peão fica na 30).
- Casas partilhadas: os dois peões podem estar na mesma casa; não há captura.
- Efeitos: avançar (+) e recuar (−) para casa fixa. Efeitos encadeiam-se (máx. 5 por turno).
  Os alvos de recuo são sempre ≥ 1, logo nunca saem do tabuleiro.
- Fim sem jogo infinito: limite de 30 rondas; vence quem estiver mais avançado, igualdade = empate.
- Comandos: `q` sair, `r` recomeçar (em qualquer momento).

## Mapa de efeitos
Casas de Avanço (+)
Casa 3: Eléctrico 22 → avança para a Casa 8
Casa 9: Barco Rabelo → avança para a Casa 14
Casa 10: Funicular dos Guindais → avança para a Casa 15
Casa 19: Metro D. Luís I → avança para a Casa 24
Casa 22: Vento a favor → avança para a Casa 27

Casas de Recuo (−)
Casa 14: Nevoeiro → recua para a Casa 10
Casa 17: Escadas da Ribeira → recua para a Casa 11
Casa 25: Maré cheia → recua para a Casa 20
Casa 28: Greve de transportes → recua para a Casa 21
Cadeia exemplo: casa 9 → 14 → 10 → 15 (3 efeitos num turno).

## Tabela de representações 
Peão do J1 na casa 22
Consola: [22+ 1]
Modelo: Player("J1").getPosition() == 22

Estado dos dois peões
Consola: Posições (22, 24)
Modelo: game.positions() devolve "(22, 24)"

Casa de avanço
Consola: símbolo +
Modelo: instância da classe AdvanceCell

Casa de recuo
Consola: símbolo -
Modelo: instância da classe ReturnCell

Vitória
Consola: mensagem "chegou à meta"
Modelo: game.isFinished() a true e leitura de getWinner()


## UML (inicial)
mermaid
classDiagram
  class Cell { <<abstract>> +getNumber() +hasEffect() +apply(int) +describe() +symbol() }
  class NormalCell
  class JumpCell { <<abstract>> -target -name }
  class AdvanceCell
  class ReturnCell
  class Board { -List~Cell~ cells +cellAt(int) +size() +douro() }
  class Player { -name -position +getPosition() ~moveTo(int) }
  class Game { +playTurn(int) +restart() +positions() }
  class DiceSource { <<interface>> +next() }
  class RandomDice
  class ScriptedDice
  class InputParser { +parseRoll(String) }
  class ConsoleUI { +run() }
  class TurnResult { <<record>> }
  Cell <|-- NormalCell
  Cell <|-- JumpCell
  JumpCell <|-- AdvanceCell
  JumpCell <|-- ReturnCell
  DiceSource <|.. RandomDice
  DiceSource <|.. ScriptedDice
  Board o-- Cell
  Game o-- Board
  Game o-- Player
  Game ..> TurnResult
  ConsoleUI ..> Game
  ConsoleUI ..> DiceSource
  ConsoleUI ..> InputParser

Justificações: herança em `Cell` (polimorfismo de `apply`), `JumpCell` evita duplicar a lógica de destino;
`DiceSource` permite dados reais e programados; sobrecarga de construtor `Player(String)`/`Player(String,int)`
para definir estados iniciais nos testes; `List<Cell>`/`List<Player>` tipadas.

## Distribuição de tarefas (proposta — ajustem)
João Alves - Board, Cell e subclasses, do sistema de dados (DiceSource, RandomDice, ScriptedDice) e conceção das regras;

Carlos Lima - Player, Game, TurnResult, interface e validação de comandos (ConsoleUI, InputParser), reinício/fim, elaboração do UML, relatório e AI_USE.md.

## Estado
Funciona: regras, consola, ambos os modos, efeitos, validação, reinício.

