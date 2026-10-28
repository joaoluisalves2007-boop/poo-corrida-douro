
public class AdvanceCell extends JumpCell {
    public AdvanceCell(int number, int target, String name) {
        super(number, target, name);
        if (target <= number) throw new IllegalArgumentException("Advance target must be ahead");
    }
    @Override public String describe() { return getName() + ": avança da " + getNumber() + " para a " + getTarget(); }
    @Override public char symbol() { return '+'; }
}
