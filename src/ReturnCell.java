
public class ReturnCell extends JumpCell {
    public ReturnCell(int number, int target, String name) {
        super(number, target, name);
        if (target >= number) throw new IllegalArgumentException("O alvo de retorno deve estar atrás");
    }
    @Override public String describe() { return getName() + ": recua da " + getNumber() + " para a " + getTarget(); }
    @Override public char symbol() { return '-'; }
}
