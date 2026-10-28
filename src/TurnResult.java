
import java.util.List;

public record TurnResult(String player, int roll, int from, int landed,
                         List<String> effects, int to, boolean gameOver, String outcome) { }
