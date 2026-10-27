/* Module and purpose: InputParser: validates manual console input (pure function, easy to test).
 * Student name/number and implemented contributions: [Student A / nº] (draft) – to be completed
 * Collaborator/reviewer and contribution: [Student B / nº] – review pending
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

import java.util.OptionalInt;

public final class InputParser {
    private InputParser() { }

    /** Returns the roll if the text is an integer 1-6, otherwise empty. */
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
