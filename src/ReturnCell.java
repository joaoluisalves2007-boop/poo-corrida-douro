/* Module and purpose: ReturnCell: effect that sends the pawn back.
 * Student name/number and implemented contributions: [Student A / nº] (draft) – to be completed
 * Collaborator/reviewer and contribution: [Student B / nº] – review pending
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

public class ReturnCell extends JumpCell {
    public ReturnCell(int number, int target, String name) {
        super(number, target, name);
        if (target >= number) throw new IllegalArgumentException("Return target must be behind");
    }
    @Override public String describe() { return getName() + ": recua da " + getNumber() + " para a " + getTarget(); }
    @Override public char symbol() { return '-'; }
}
