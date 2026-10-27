/* Module and purpose: NormalCell: cell without effect.
 * Student name/number and implemented contributions: [Student A / nº] (draft) – to be completed
 * Collaborator/reviewer and contribution: [Student B / nº] – review pending
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

public class NormalCell extends Cell {
    public NormalCell(int number) { super(number); }
    @Override public boolean hasEffect() { return false; }
    @Override public int apply(int position) { return position; }
    @Override public String describe() { return "Casa normal"; }
    @Override public char symbol() { return ' '; }
}
