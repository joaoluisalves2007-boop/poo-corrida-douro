/* Module and purpose: Cell: abstract board cell; defines the polymorphic effect contract.
 * Student name/number and implemented contributions: João Alves
 * Collaborator/reviewer and contribution: Carlos Lima
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

public abstract class Cell {
    private final int number;

    protected Cell(int number) {
        if (number < 1) throw new IllegalArgumentException("Cell number must be >= 1");
        this.number = number;
    }

    public int getNumber() { return number; }

    /** True if landing here triggers an effect. */
    public abstract boolean hasEffect();

    /** Position of the pawn after the effect (same position if none). */
    public abstract int apply(int position);

    /** Short text shown in turn feedback. */
    public abstract String describe();

    /** One-char symbol for the console board. */
    public abstract char symbol();
}
