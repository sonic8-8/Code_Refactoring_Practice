package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.Beginner;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.gameLevel.VeryBeginner;

public class GameApplication {
    public static void main(String[] args) {
        GameLevel gameLevel = new VeryBeginner();

        Minesweeper minesweeper = new Minesweeper(gameLevel);
        minesweeper.run();
    }
}