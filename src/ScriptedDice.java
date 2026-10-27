/* Module and purpose: ScriptedDice: predetermined rolls for automated checks.
 * Student name/number and implemented contributions: João Alves
 * Collaborator/reviewer and contribution: Carlos Lima
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

import java.util.ArrayDeque;
import java.util.Deque;

public class ScriptedDice implements DiceSource {
    private final Deque<Integer> rolls = new ArrayDeque<>();
    public ScriptedDice(int... values) { for (int v : values) rolls.add(v); }
    @Override public int next() {
        if (rolls.isEmpty()) throw new IllegalStateException("Script exhausted");
        return rolls.poll();
    }
}
