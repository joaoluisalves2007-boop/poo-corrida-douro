/* Module and purpose: DiceSource: abstraction for a source of rolls (random or scripted).
 * Student name/number and implemented contributions: [Student A / nº] (draft) – to be completed
 * Collaborator/reviewer and contribution: [Student B / nº] – review pending
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

public interface DiceSource {
    /** Next roll, always in 1..6. */
    int next();
}
