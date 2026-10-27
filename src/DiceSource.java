/* Module and purpose: DiceSource: abstraction for a source of rolls (random or scripted).
 * Student name/number and implemented contributions: João Alves
 * Collaborator/reviewer and contribution: Carlos Lima
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

public interface DiceSource {
    /** Next roll, always in 1..6. */
    int next();
}
