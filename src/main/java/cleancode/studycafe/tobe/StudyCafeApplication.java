package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.io.ConsoleInputHandler;
import cleancode.studycafe.tobe.io.ConsoleOutputHandler;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;

public class StudyCafeApplication {

    public static void main(String[] args) {
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(inputHandler, outputHandler);

        studyCafePassMachine.run();
    }

}
