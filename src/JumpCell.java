
public abstract class JumpCell extends Cell {
    private final int target;
    private final String name;

    protected JumpCell(int number, int target, String name) {
        super(number);
        if (target < 1) throw new IllegalArgumentException("Target must be >= 1");
        if (target == number) throw new IllegalArgumentException("Target must differ from cell");
        this.target = target;
        this.name = name;
    }

    public int getTarget() { return target; }
    protected String getName() { return name; }
    @Override public boolean hasEffect() { return true; }
    @Override public int apply(int position) { return target; }
}
