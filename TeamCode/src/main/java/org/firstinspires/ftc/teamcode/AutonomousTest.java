package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by elija on 2/9/18.
 */

@Disabled
@Autonomous(name = "AuTOOtest", group = "bambusa")
public class AutonomousTest extends OpMode {

    private Definitions robot = new Definitions();
    private ElapsedTime runtime = new ElapsedTime();

    boolean jewelGotten = false;
    String teamC = null;

    boolean teamColor = false;
    int pictograph = 0;

    //private VuforiaTrackables relicTrackables = this.robot.vuforia.loadTrackablesFromAsset("RelicVuMark");
    //private VuforiaTrackable relicTemplate = relicTrackables.get(0);

    //RelicRecoveryVuMark center = RelicRecoveryVuMark.CENTER;
    //RelicRecoveryVuMark left = RelicRecoveryVuMark.LEFT;
    //RelicRecoveryVuMark right = RelicRecoveryVuMark.RIGHT;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        robot.hardwareMapInit(hardwareMap);
        robot.vuforiaInit();
        robot.servoInit();
        //robot.encoderInit();
        robot.driveInitAuto();

        //relicTemplate.setName("relicVuMarkTemplate");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void init_loop() {
        //RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        if (!teamColor) {
            if (robot.teamColor.red() > robot.teamColor.blue()) {teamC = "RED"; teamColor = true;}
            else if (robot.teamColor.red() < robot.teamColor.blue()) {teamC = "BLUE"; teamColor = true;}
            else if (robot.teamColor.red() == 0 && robot.teamColor.blue() == 0  )
            telemetry.addData("Team Color", "Finiding Team Color");
        }
        else {
            telemetry.addData("Team Color", "Team Color is: "+teamC);
        }

       /* if (pictograph == 0) {
            if (vuMark == center) {
                telemetry.addData("VuMark", "Center visible", vuMark);
                pictograph = 1;
            } else if (vuMark == left) {
                telemetry.addData("VuMark", "Left visible", vuMark);
                pictograph = 2;
            } else if (vuMark == right) {
                telemetry.addData("VuMark", "Right visible", vuMark);
                pictograph = 3;
            } else {
                telemetry.addData("VuMark", "Idk what I am seeing");
                pictograph = 0;
            }
        } else {telemetry.addData("VuMark", "No pictogram seen");}*/


    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        //if (runtime.seconds() > 5) jewelGotten = true;

        if (teamC.equals("RED")) {

            if (!jewelGotten) {
                telemetry.addData("Jewel", "Not Gotten");
                robot.jewelDown();
                if (robot.jewelColor.red() == 0 && robot.jewelColor.blue() == 0 && !jewelGotten) {
                    robot.setRotLeft();
                    robot.setPower(0.1);
                }
                else if (robot.jewelColor.red() > robot.jewelColor.blue()) {
                    robot.setDriveForward();
                    robot.posInch(5);
                    robot.runToPos();
                    robot.setPower(0.2);
                    robot.waitForDriveMotorStop();
                    robot.setPower(0);
                    robot.jewelUp();

                    jewelGotten = true;
                    telemetry.addData("JewelColor", "Red");
                }
                else if (robot.jewelColor.red() < robot.jewelColor.blue()) {
                    robot.setDriveBackward();
                    robot.posInch(5);
                    robot.runToPos();
                    robot.setPower(0.2);
                    robot.waitForDriveMotorStop();
                    robot.setPower(0);
                    robot.jewelUp();

                    jewelGotten = true;
                    telemetry.addData("JewelColor", "Blue");
                }
            }
            else {
                telemetry.addData("Jewel", "Gotten");
            }

        }
        else if (teamC.equals("BLUE")) {

        }
        else {

        }
    }
}
