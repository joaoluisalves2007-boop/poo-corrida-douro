
import java.util.OptionalInt;

public final class InputParser {
    private InputParser() { }

    public static OptionalInt parseRoll(String text) {
        if (text == null) return OptionalInt.empty();
        try {
            int v = Integer.parseInt(text.trim());
            return (v >= 1 && v <= 6) ? OptionalInt.of(v) : OptionalInt.empty();
        } catch (NumberFormatException e) {
            return OptionalInt.empty();
        }
    }
}
