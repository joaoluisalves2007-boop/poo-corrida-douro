
public class NormalCell extends Cell {
    public NormalCell(int number) { super(number); }
    @Override public boolean hasEffect() { return false; }
    @Override public int apply(int position) { return position; }
    @Override public String describe() { return "Casa normal"; }
    @Override public char symbol() { return ' '; }
}
