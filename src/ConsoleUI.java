/* Module and purpose: ConsoleUI: all console reading/printing; delegates every rule to Game.
 * Student name/number and implemented contributions: João Alves
 * Collaborator/reviewer and contribution: Carlos Lima
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner in = new Scanner(System.in);

    public static void main(String[] args) { new ConsoleUI().run(); }

    public void run() {
        System.out.println("=== CORRIDA DO DOURO ===");
        boolean manual = chooseMode();
        DiceSource dice = new RandomDice();
        Game game = newGame();
        while (true) {
            printBoard(game);
            if (game.isFinished()) {
                System.out.println("Posições finais: " + game.positions());
                System.out.print("[r] recomeçar  [q] sair > ");
                String cmd = in.nextLine().trim().toLowerCase();
                if (cmd.equals("r")) { game.restart(); continue; }
                if (cmd.equals("q")) return;
                System.out.println("Comando inválido.");
                continue;
            }
            System.out.print(game.currentPlayer().getName() + (manual
                    ? ", introduz o dado (1-6) [r] recomeçar [q] sair > "
                    : ", ENTER para lançar [r] recomeçar [q] sair > "));
            if (!in.hasNextLine()) return;
            String text = in.nextLine().trim();
            if (text.equalsIgnoreCase("q")) { System.out.println("Até à próxima!"); return; }
            if (text.equalsIgnoreCase("r")) { game.restart(); System.out.println("Jogo reiniciado."); continue; }
            int roll;
            if (manual) {
                OptionalInt parsed = InputParser.parseRoll(text);
                if (parsed.isEmpty()) { System.out.println("Entrada inválida: usa um número de 1 a 6."); continue; }
                roll = parsed.getAsInt();
            } else {
                roll = dice.next();
                System.out.println("Dado aleatório: " + roll);
            }
            printTurn(game.playTurn(roll));
        }
    }

    private boolean chooseMode() {
        while (true) {
            System.out.print("Modo de dado: [1] aleatório  [2] manual > ");
            String t = in.nextLine().trim();
            if (t.equals("1")) return false;
            if (t.equals("2")) return true;
            System.out.println("Opção inválida.");
        }
    }

    private Game newGame() {
        return new Game(Board.douro(), List.of(new Player("Jogador 1"), new Player("Jogador 2")), 30);
    }

    private void printTurn(TurnResult r) {
        System.out.println(r.player() + " tirou " + r.roll() + ": " + r.from() + " -> " + r.landed());
        for (String e : r.effects()) System.out.println("  Efeito! " + e);
        if (!r.effects().isEmpty()) System.out.println("  Posição final do turno: " + r.to());
        if (!r.outcome().isEmpty()) System.out.println(r.outcome());
    }

    private void printBoard(Game g) {
        Board b = g.getBoard();
        int p1 = g.getPlayers().get(0).getPosition(), p2 = g.getPlayers().get(1).getPosition();
        System.out.println();
        System.out.println("Ronda " + g.getRound() + "/" + g.getMaxRounds() + "   Posições " + g.positions());
        for (int row = 0; row < b.size() / 10; row++) {
            StringBuilder sb = new StringBuilder();
            for (int n = row * 10 + 1; n <= row * 10 + 10; n++) {
                String pawns = (p1 == n ? "1" : "") + (p2 == n ? "2" : "");
                sb.append(String.format("[%2d%c%-2s]", n, b.cellAt(n).symbol(), pawns));
            }
            System.out.println(sb);
        }
        System.out.println("(+ avança, - recua; 1/2 = peões; início = 0)");
    }
}
