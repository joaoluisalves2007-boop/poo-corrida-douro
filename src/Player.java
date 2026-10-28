
public class Player {
    private final String name;
    private int position;

    public Player(String name) { this(name, 0); }

    public Player(String name, int startPosition) {
        if (startPosition < 0) throw new IllegalArgumentException("Posição Negativa");
        this.name = name;
        this.position = startPosition;
    }

    public String getName() { return name; }
    public int getPosition() { return position; }
    void moveTo(int newPosition) { this.position = newPosition; }
}
