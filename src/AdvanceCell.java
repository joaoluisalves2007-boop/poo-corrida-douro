/* Module and purpose: AdvanceCell: effect that moves the pawn forward.
 * Student name/number and implemented contributions: [Student A / nº] (draft) – to be completed
 * Collaborator/reviewer and contribution: [Student B / nº] – review pending
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

public class AdvanceCell extends JumpCell {
    public AdvanceCell(int number, int target, String name) {
        super(number, target, name);
        if (target <= number) throw new IllegalArgumentException("Advance target must be ahead");
    }
    @Override public String describe() { return getName() + ": avança da " + getNumber() + " para a " + getTarget(); }
    @Override public char symbol() { return '+'; }
}
