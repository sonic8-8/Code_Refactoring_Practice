package cleancode.studycafe.tobeLecture;

import cleancode.studycafe.tobeLecture.io.provider.LockerPassFileReader;
import cleancode.studycafe.tobeLecture.io.provider.SeatPassFileReader;
import cleancode.studycafe.tobeLecture.provider.LockerPassProvider;
import cleancode.studycafe.tobeLecture.provider.SeatPassProvider;

public class StudyCafeApplication {

    public static void main(String[] args) {
        SeatPassProvider seatPassProvider = new SeatPassFileReader();
        LockerPassProvider lockerPassProvider = new LockerPassFileReader();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(
                seatPassProvider, lockerPassProvider
        );
        studyCafePassMachine.run();
    }

}
