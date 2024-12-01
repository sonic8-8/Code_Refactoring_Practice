package cleancode.studycafe.tobeLecture;

import cleancode.studycafe.tobeLecture.exception.AppException;
import cleancode.studycafe.tobeLecture.io.StudyCafeIOHandler;
import cleancode.studycafe.tobeLecture.model.order.StudyCafePassOrder;
import cleancode.studycafe.tobeLecture.model.pass.StudyCafePassType;
import cleancode.studycafe.tobeLecture.model.pass.StudyCafeSeatPass;
import cleancode.studycafe.tobeLecture.model.pass.StudyCafeSeatPasses;
import cleancode.studycafe.tobeLecture.model.pass.locker.StudyCafeLockerPass;
import cleancode.studycafe.tobeLecture.model.pass.locker.StudyCafeLockerPasses;
import cleancode.studycafe.tobeLecture.provider.LockerPassProvider;
import cleancode.studycafe.tobeLecture.provider.SeatPassProvider;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final LockerPassProvider lockerPassProvider;
    private final SeatPassProvider seatPassProvider;

    public StudyCafePassMachine(SeatPassProvider seatPassProvider, LockerPassProvider lockerPassProvider) {
        this.seatPassProvider = seatPassProvider;
        this.lockerPassProvider = lockerPassProvider;
    }

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafeSeatPass selectedPass = selectPass();
            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            StudyCafePassOrder passOrder = StudyCafePassOrder.of(
                    selectedPass,
                    optionalLockerPass.orElse(null)
            );

            ioHandler.showPassOrderSummary(passOrder);

        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeSeatPass selectPass() {
        StudyCafePassType passType = ioHandler.askPassTypeSelecting();
        List<StudyCafeSeatPass> passCandidates = findPassCandidatesBy(passType);

        return ioHandler.askPassSelecting(passCandidates);
    }

    private List<StudyCafeSeatPass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        StudyCafeSeatPasses allPasses = seatPassProvider.readStudyCafePasses();
        return allPasses.findPassBy(studyCafePassType);
    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafeSeatPass selectedPass) {
        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }
        Optional<StudyCafeLockerPass> lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate.isPresent()) {
            StudyCafeLockerPass lockerPass = lockerPassCandidate.get();

            boolean isLockerSelected = ioHandler.askLockerPass(lockerPass);

            if (isLockerSelected) {
                return Optional.of(lockerPass);
            }
        }

        return Optional.empty();
    }

    private Optional<StudyCafeLockerPass> findLockerPassCandidateBy(StudyCafeSeatPass pass) {
        StudyCafeLockerPasses allLockerPasses = lockerPassProvider.readLockerPasses();
        return allLockerPasses.findLockerPassBy(pass);
    }

}
