package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.*;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public StudyCafePassMachine(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType selectedPassType = inputHandler.getPassTypeSelectingUserAction();

            List<StudyCafePass> passList = getStudyCafePassListFrom(selectedPassType);
            outputHandler.showPassListForSelection(passList);

            StudyCafePass selectedPass = inputHandler.getSelectPass(passList);

            StudyCafeLockerPass lockerPass = getStudyCafeLockerPassFrom(selectedPass);
            boolean doesSelectLocker = false;

            if (doesUserSelectFixed(selectedPassType)) {
                outputHandler.askLockerPass(lockerPass);
                doesSelectLocker = inputHandler.getLockerSelection();
            }

            if (!doesSelectLocker) {
                lockerPass = null;
            }

            outputHandler.showPassOrderSummary(selectedPass, lockerPass);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private List<StudyCafePass> getStudyCafePassListFrom(StudyCafePassType studyCafePassType) {
        StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

        List<StudyCafePass> passes = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();

        return passes;
    }

    private StudyCafeLockerPass getStudyCafeLockerPassFrom(StudyCafePass selectedPass) {
        StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass lockerPass = lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
        return lockerPass;
    }

    private boolean doesUserSelectFixed(StudyCafePassType selectedPassType) {
        return selectedPassType == StudyCafePassType.FIXED;
    }

}
