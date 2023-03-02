package yourprograms;

import api.control.RobotOperations;
import api.nav.Position;
import api.programs.RunnableProgram;

/**
 * Klausur Ende 2022 übersetzt in die eigene API
 */
public class RFSKlausur2Vereinfacht implements RunnableProgram {

    //Punkte
    private final Position PSTART = new Position(420.0, 0.0, 300.0, 180, 0, 180);
    private final Position P5 = new Position(420.0, 0.0, 135.0, 180.0, 0.0, 180.0);
    private final Position P11 = new Position(33.81, -495.72, 130.15, 180.0,0.0,180.0);
    private Position PAUF; //wird später initialisiert
    private Position PAB;  //wird später initialisiert

    //Unterprogramme
    private final RunnableProgram START = new START();
    private final RunnableProgram HOLEN = new HOLEN();
    private final RunnableProgram ABLAGE = new ABLAGE();

    /**
     * Hauptprogramm
     */
    @Override
    public void runProgram(RobotOperations robot) {
        robot.setSafePosition(PSTART);
        robot.enableServo();
        PAUF = P11.copy();
        PAB = P5.copy();
        PAUF.setZ(PAUF.getZ() + 27.0); //9x3

        robot.runProgram(START);

        for (int i = 1; i <= 7; i++) {  //oder i = 0; 0 < 7 (äquivalent, aber zum besseren Verständnis)
            robot.runProgram(HOLEN);
            robot.runProgram(ABLAGE);
        }

        for (int i = 8; i <= 10; i++) { //oder i = 0; 0 < 3 (äquivalent, aber zum besseren Verständnis)
            robot.runProgram(HOLEN);
            PAB.setC(PAB.getC() + 45.0);
            robot.runProgram(ABLAGE);
        }

        robot.disableServo();
    }

    /**
     * Unteraufgabe a)
     */
    private class START implements RunnableProgram {
        @Override
        public void runProgram(RobotOperations robot) {
            robot.movToSafePosition();
        }
    }

    /**
     * Unteraufgabe b)
     */
    private class HOLEN implements RunnableProgram {
        @Override
        public void runProgram(RobotOperations robot) {
            robot.movToPositionWithSafeTravel(PAUF);
            robot.grab();
            robot.mvsToPosition(PAUF.alterZ(-50));
            PAUF.setZ(PAUF.getZ() - 3.0);
        }
    }

    /**
     * Unteraufgabe c)
     */
    private class ABLAGE implements RunnableProgram {
        @Override
        public void runProgram(RobotOperations robot) {
            robot.movToPositionWithSafeTravel(PAB);
            robot.drop();
            robot.mvsToPosition(PAB.alterZ(-50));
            PAB.setZ(PAB.getZ() + 3.0);
        }
    }
}