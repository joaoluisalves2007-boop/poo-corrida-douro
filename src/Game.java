/* Module and purpose: Game: rules engine (turn order, movement, overshoot, effect chaining, end conditions). No console I/O.
 * Student name/number and implemented contributions: João Alves
 * Collaborator/reviewer and contribution: Carlos Lima
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int MAX_CHAIN = 5;
    private final Board board;
    private final List<Player> players;
    private final int maxRounds;
    private int current = 0;
    private int round = 1;
    private boolean finished = false;
    private Player winner = null;

    public Game(Board board, List<Player> players, int maxRounds) {
        if (players.size() != 2) throw new IllegalArgumentException("Two players required");
        this.board = board;
        this.players = new ArrayList<>(players);
        this.maxRounds = maxRounds;
    }

    /**
     * Plays one turn with the given roll. Invalid roll or finished game throws and leaves the state unchanged.
     * Rules: reaching or passing the last cell wins; effects chain (max MAX_CHAIN);
     * shared cells allowed; after maxRounds the pawn further ahead wins (equal = draw).
     */
    public TurnResult playTurn(int roll) {
        if (finished) throw new IllegalStateException("Game is over");
        if (roll < 1 || roll > 6) throw new IllegalArgumentException("Roll must be 1-6");
        Player p = players.get(current);
        int from = p.getPosition();
        int pos = from + roll;
        List<String> effects = new ArrayList<>();
        if (pos >= board.size()) {
            p.moveTo(board.size());
            finished = true;
            winner = p;
            return new TurnResult(p.getName(), roll, from, board.size(), effects, board.size(), true,
                    p.getName() + " chegou à meta e venceu!");
        }
        int landed = pos;
        for (int i = 0; i < MAX_CHAIN; i++) {
            Cell c = board.cellAt(pos);
            if (!c.hasEffect()) break;
            effects.add(c.describe());
            pos = c.apply(pos);
        }
        p.moveTo(pos);
        String outcome = "";
        current = (current + 1) % players.size();
        if (current == 0) {
            if (round >= maxRounds) {
                finished = true;
                Player a = players.get(0), b = players.get(1);
                if (a.getPosition() == b.getPosition()) outcome = "Limite de rondas atingido: empate!";
                else {
                    winner = a.getPosition() > b.getPosition() ? a : b;
                    outcome = "Limite de rondas atingido: " + winner.getName() + " vence por estar mais avançado!";
                }
            } else round++;
        }
        return new TurnResult(p.getName(), roll, from, landed, effects, pos, finished, outcome);
    }

    /** Resets positions, turn order and round counter. */
    public void restart() {
        for (Player p : players) p.moveTo(0);
        current = 0; round = 1; finished = false; winner = null;
    }

    public Player currentPlayer() { return players.get(current); }
    public List<Player> getPlayers() { return List.copyOf(players); }
    public Board getBoard() { return board; }
    public int getRound() { return round; }
    public int getMaxRounds() { return maxRounds; }
    public boolean isFinished() { return finished; }
    public Player getWinner() { return winner; }

    /** Model view, e.g. "(22, 24)". */
    public String positions() {
        return "(" + players.get(0).getPosition() + ", " + players.get(1).getPosition() + ")";
    }
}
