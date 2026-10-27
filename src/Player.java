/* Module and purpose: Player: name and position (encapsulated); constructor overload lets tests set a starting state.
 * Student name/number and implemented contributions: João Alves
 * Collaborator/reviewer and contribution: Carlos Lima
 * AI assistance: initial draft generated with Claude; see AI_USE.md
 */

public class Player {
    private final String name;
    private int position;

    public Player(String name) { this(name, 0); }

    public Player(String name, int startPosition) {
        if (startPosition < 0) throw new IllegalArgumentException("Negative position");
        this.name = name;
        this.position = startPosition;
    }

    public String getName() { return name; }
    public int getPosition() { return position; }
    void moveTo(int newPosition) { this.position = newPosition; }
}
