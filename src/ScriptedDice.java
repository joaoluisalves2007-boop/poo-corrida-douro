
import java.util.ArrayDeque;
import java.util.Deque;

public class ScriptedDice implements DiceSource {
    private final Deque<Integer> rolls = new ArrayDeque<>();
    public ScriptedDice(int... values) { for (int v : values) rolls.add(v); }
    @Override public int next() {
        if (rolls.isEmpty()) throw new IllegalStateException("Script esgotado");
        return rolls.poll();
    }
}
