
public abstract class Cell {
    private final int number;

    protected Cell(int number) {
        if (number < 1) throw new IllegalArgumentException("Cell number must be >= 1");
        this.number = number;
    }

    public int getNumber() { return number; }

    public abstract boolean hasEffect();

    public abstract int apply(int position);

    public abstract String describe();

    public abstract char symbol();
}
