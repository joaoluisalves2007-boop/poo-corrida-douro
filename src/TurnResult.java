/* Module and purpose: TurnResult: immutable description of one turn, consumed by the console.
 * Student name/number and implemented contributions: [Student A / nº] (draft) – to be completed
 * Collaborator/reviewer and contribution: [Student B / nº] – review pending
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

import java.util.List;

public record TurnResult(String player, int roll, int from, int landed,
                         List<String> effects, int to, boolean gameOver, String outcome) { }
