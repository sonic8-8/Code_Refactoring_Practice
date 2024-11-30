package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class CellState {
    private boolean isFlagged;
    private boolean isOpened;

    private CellState(boolean isFlagged, boolean isOpened) {
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }

    public static CellState initialize() {
        return new CellState(false, false);
    }

    void flag() {
        this.isFlagged = true;
    }

    void open() {
        this.isOpened = true;
    }

    boolean isChecked() {
        return isFlagged || isOpened;
    }

    boolean isOpened() {
        return isOpened;
    }

    boolean isFlagged() {
        return isFlagged;
    }
}
