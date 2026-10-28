
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Cell> cells = new ArrayList<>();

    
    public Board(int size, List<Cell> specials) {
        if (size < 2) throw new IllegalArgumentException("Board too small");
        for (int i = 1; i <= size; i++) cells.add(new NormalCell(i));
        for (Cell c : specials) {
            if (c.getNumber() >= size) throw new IllegalArgumentException("Special cell outside board");
            cells.set(c.getNumber() - 1, c);
        }
    }

    public int size() { return cells.size(); }

    public Cell cellAt(int position) {
        if (position < 1 || position > size()) throw new IllegalArgumentException("No cell " + position);
        return cells.get(position - 1);
    }

    
    public static Board douro() {
        return new Board(30, List.of(
            new AdvanceCell(3, 8, "Eléctrico 22"),
            new AdvanceCell(9, 14, "Barco Rabelo"),
            new AdvanceCell(10, 15, "Funicular dos Guindais"),
            new AdvanceCell(19, 24, "Metro D. Luís I"),
            new AdvanceCell(22, 27, "Vento a favor"),
            new ReturnCell(14, 10, "Nevoeiro"),
            new ReturnCell(17, 11, "Escadas da Ribeira"),
            new ReturnCell(25, 20, "Maré cheia"),
            new ReturnCell(28, 21, "Greve de transportes")));
    }
}
