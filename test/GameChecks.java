/* Module and purpose: Repeatable automated checks (predetermined rolls); each prints start state, input, expected, result.
 * Student name/number and implemented contributions: [Student A / nº] (draft) – to be completed
 * Collaborator/reviewer and contribution: [Student B / nº] – review pending
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */
import java.util.List;
import java.util.function.Supplier;

public class GameChecks {
    private static int passed = 0, failed = 0;

    private static Game game(int p1, int p2, int maxRounds) {
        return new Game(Board.douro(), List.of(new Player("J1", p1), new Player("J2", p2)), maxRounds);
    }

    private static void check(String name, String start, String input, String expected, Supplier<String> run) {
        String actual;
        try { actual = run.get(); } catch (RuntimeException e) { actual = "EXC " + e.getClass().getSimpleName(); }
        boolean ok = expected.equals(actual);
        if (ok) passed++; else failed++;
        System.out.printf("%s %s%n   start=%s input=%s expected=%s actual=%s%n",
                ok ? "[PASS]" : "[FAIL]", name, start, input, expected, actual);
    }

    public static void main(String[] args) {
        check("Normal movement", "(0,0)", "roll 4", "(4, 0)", () -> { Game g = game(0, 0, 30); g.playTurn(4); return g.positions(); });
        check("Turn alternation", "(0,0)", "rolls 4,2", "(4, 2)", () -> { Game g = game(0, 0, 30); g.playTurn(4); g.playTurn(2); return g.positions(); });
        check("Advance effect", "(0,0)", "roll 3", "(8, 0)", () -> { Game g = game(0, 0, 30); g.playTurn(3); return g.positions(); });
        check("Return effect", "(15,0)", "roll 2 (lands 17)", "(11, 0)", () -> { Game g = game(15, 0, 30); g.playTurn(2); return g.positions(); });
        check("Return then advance (14->10->15)", "(12,0)", "roll 2 (lands 14)", "(15, 0)", () -> { Game g = game(12, 0, 30); g.playTurn(2); return g.positions(); });
        check("Effect chaining 9->14->10->15", "(6,0)", "roll 3", "(15, 0)", () -> { Game g = game(6, 0, 30); g.playTurn(3); return g.positions(); });
        check("Chain reports 3 effects", "(6,0)", "roll 3", "3", () -> { Game g = game(6, 0, 30); return "" + g.playTurn(3).effects().size(); });
        check("Boundary: exact finish", "(26,0)", "roll 4", "J1", () -> { Game g = game(26, 0, 30); g.playTurn(4); return g.getWinner().getName(); });
        check("Boundary: overshoot wins, pawn on 30", "(29,0)", "roll 6", "(30, 0)", () -> { Game g = game(29, 0, 30); g.playTurn(6); return g.positions(); });
        check("Boundary: return never below 1", "(0,0)", "board built with return target 0 rejected", "EXC IllegalArgumentException", () -> new ReturnCell(5, 0, "x").describe());
        check("Shared cell allowed", "(0,5)", "J1 rolls 5", "(5, 5)", () -> { Game g = game(0, 5, 30); g.playTurn(5); return g.positions(); });
        check("Invalid roll 0 rejected", "(5,5)", "roll 0", "EXC IllegalArgumentException", () -> { Game g = game(5, 5, 30); g.playTurn(0); return "ok"; });
        check("Invalid roll 7 leaves state", "(5,6)", "roll 7", "(5, 6)", () -> { Game g = game(5, 6, 30); try { g.playTurn(7); } catch (IllegalArgumentException e) { } return g.positions(); });
        check("Invalid roll keeps turn", "(5,6)", "roll 7 then roll 1", "J1", () -> { Game g = game(5, 6, 30); try { g.playTurn(7); } catch (IllegalArgumentException e) { } return g.currentPlayer().getName(); });
        check("Manual parse 'abc'", "-", "abc", "empty", () -> InputParser.parseRoll("abc").isEmpty() ? "empty" : "value");
        check("Manual parse '' ", "-", "''", "empty", () -> InputParser.parseRoll("").isEmpty() ? "empty" : "value");
        check("Manual parse ' 6 '", "-", "' 6 '", "6", () -> "" + InputParser.parseRoll(" 6 ").getAsInt());
        check("Manual parse '7'", "-", "7", "empty", () -> InputParser.parseRoll("7").isEmpty() ? "empty" : "value");
        check("Random mode values in 1..6", "-", "1000 seeded rolls", "true", () -> {
            DiceSource d = new RandomDice(42); boolean ok = true;
            for (int i = 0; i < 1000; i++) { int v = d.next(); ok &= v >= 1 && v <= 6; } return "" + ok; });
        check("Both modes same logic", "(0,0)", "scripted 4 vs manual-parsed 4", "(4, 0)", () -> {
            Game a = game(0, 0, 30), b = game(0, 0, 30);
            a.playTurn(new ScriptedDice(4).next()); b.playTurn(InputParser.parseRoll("4").getAsInt());
            return a.positions().equals(b.positions()) ? a.positions() : "DIFF"; });
        check("Termination: round limit, leader wins", "(0,0), maxRounds=2", "J1:2,J2:1,J1:2,J2:1", "J1", () -> {
            Game g = game(0, 0, 2); for (int r : new int[]{2, 1, 2, 1}) g.playTurn(r);
            return g.isFinished() ? g.getWinner().getName() : "running"; });
        check("Termination: round limit, draw", "(0,0), maxRounds=1", "1,1", "draw", () -> {
            Game g = game(0, 0, 1); g.playTurn(1); g.playTurn(1);
            return g.isFinished() && g.getWinner() == null ? "draw" : "other"; });
        check("No play after game over", "(29,0)", "roll 6 then roll 1", "EXC IllegalStateException", () -> { Game g = game(29, 0, 30); g.playTurn(6); g.playTurn(1); return "ok"; });
        check("Restart", "(29,0) finished", "restart", "(0, 0) running J1", () -> {
            Game g = game(29, 0, 30); g.playTurn(6); g.restart();
            return g.positions() + (g.isFinished() ? " finished" : " running") + " " + g.currentPlayer().getName(); });
        check("Complete game (scripted)", "(0,0)", "scripted 3,6,6,6,... until end", "J1 wins (30, 24)", () -> {
            Game g = game(0, 0, 30); DiceSource d = new ScriptedDice(3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6);
            while (!g.isFinished()) g.playTurn(d.next());
            return g.getWinner().getName() + " wins " + g.positions(); });
        System.out.printf("%nResult: %d passed, %d failed%n", passed, failed);
        System.exit(failed == 0 ? 0 : 1);
    }
}
