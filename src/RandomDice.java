
import java.util.Random;

public class RandomDice implements DiceSource {
    private final Random random;
    public RandomDice() { this(new Random()); }
    public RandomDice(long seed) { this(new Random(seed)); }
    private RandomDice(Random r) { this.random = r; }
    @Override public int next() { return random.nextInt(6) + 1; }
}
