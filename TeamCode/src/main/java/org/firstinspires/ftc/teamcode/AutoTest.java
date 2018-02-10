package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by elija on 2/9/18.
 */

@Autonomous(name = "tesssssssssssssssssssst", group = "bambusa")
public class AutoTest extends LinearOpMode {

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
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        robot.hardwareMapInit(hardwareMap);
        robot.vuforiaInit();
        robot.servoInit();
        robot.driveInitAuto();

        //relicTemplate.setName("relicVuMarkTemplate");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            if (!teamColor) {
                if (robot.teamColor.red() > robot.teamColor.blue()) {teamC = "RED"; teamColor = true;}
                else if (robot.teamColor.red() < robot.teamColor.blue()) {teamC = "BLUE"; teamColor = true;}
                else if (robot.teamColor.red() == 0 && robot.teamColor.blue() == 0  )
                    telemetry.addData("Team Color", "Finiding Team Color");
            }
            else {
                telemetry.addData("Team Color", "Team Color is: "+teamC);
            }

            if (teamC.equals("RED")) {

                if (!jewelGotten) {
                    telemetry.addData("Jewel", "Not Gotten");
                    robot.jewelDown();
                    sleep(1000);
                    if (robot.jewelColor.red() == 0 && robot.jewelColor.blue() == 0 && !jewelGotten) {
                        robot.setRotLeft();
                        robot.setPower(0.1);
                    } else if (robot.jewelColor.red() > robot.jewelColor.blue()) {
                        robot.setDriveForward();
                        robot.posInch(5);
                        robot.runToPos();
                        robot.setPower(0.2);
                        robot.waitForDriveMotorStop();
                        robot.setPower(0);
                        robot.jewelUp();

                        jewelGotten = true;
                        telemetry.addData("JewelColor", "Red");
                    } else if (robot.jewelColor.red() < robot.jewelColor.blue()) {
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
                } else {
                    telemetry.addData("Jewel", "Gotten");
                }

            } else if (teamC.equals("BLUE")) {

            }

            telemetry.addLine()
                    .addData("","Jewel Color")
                    .addData("Red", robot.jewelColor.red())
                    .addData("Blue", robot.jewelColor.blue());
            telemetry.addLine()
                    .addData("","Team Color")
                    .addData("Red", robot.teamColor.red())
                    .addData("Blue", robot.teamColor.blue());
            telemetry.update();
        }
    }
}
