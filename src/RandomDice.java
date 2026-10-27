/* Module and purpose: RandomDice: random rolls 1-6 (seedable for repeatable runs).
 * Student name/number and implemented contributions: João Alves
 * Collaborator/reviewer and contribution: Carlos Lima
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

import java.util.Random;

public class RandomDice implements DiceSource {
    private final Random random;
    public RandomDice() { this(new Random()); }
    public RandomDice(long seed) { this(new Random(seed)); }
    private RandomDice(Random r) { this.random = r; }
    @Override public int next() { return random.nextInt(6) + 1; }
}
