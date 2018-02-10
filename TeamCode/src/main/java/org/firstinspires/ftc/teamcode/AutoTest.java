package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/15/2018.7:21 PM.
 **/

@Autonomous(name = "Front Autonomous", group = "bambusa")
public class AutoTest extends LinearOpMode {

    private Definitions robot = new Definitions();
    private ElapsedTime runtime = new ElapsedTime();

    private boolean jewelGotten = false;
    private String teamC = null;
    private String whichJewel = null;
    private boolean rotation = false;
    private boolean teamColor = false;
    private int pictograph = 0;

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

            //RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            if (!teamColor) {
                if (robot.teamColor.red() > robot.teamColor.blue()) {
                    teamC = "RED";
                    teamColor = true;
                } else if (robot.teamColor.red() < robot.teamColor.blue()) {
                    teamC = "BLUE";
                    teamColor = true;
                } else if (robot.teamColor.red() == 0 && robot.teamColor.blue() == 0)
                    telemetry.addData("Team Color", "Finiding Team Color");
            } else {
                telemetry.addData("Team Color", "is: " + teamC);
            }
                if (!jewelGotten) {
                    telemetry.addData("Jewel", "Not Gotten");
                    robot.jewelDown();
                    if (robot.jewelColor.red() > robot.jewelColor.blue()) {
                        robot.setDriveForward();
                        robot.posInch(5);
                        robot.setPower(0.3);
                        robot.waitForDriveMotorStop();
                        robot.setPower(0);
                        robot.jewelUp();

                        whichJewel = "LEFT";
                        jewelGotten = true;
                        telemetry.addData("JewelColor", "Red");
                    } else if (robot.jewelColor.red() < robot.jewelColor.blue()) {
                        robot.backwardINCH(5, 0.3);
                        robot.jewelUp();

                        whichJewel = "RIGHT";
                        jewelGotten = true;
                        telemetry.addData("JewelColor", "Blue");
                    }
                } else {
                    telemetry.addData("Jewel", "Gotten");
                }

                /*if (jewelGotten) {
                    telemetry.addData("Pictograph", "Looking For Pictograph");
                    robot.rotRightDeg(10,0.3);
                    robot.rotLeftDeg(20,0.3);
                    robot.rotRightDeg(10,0.3);

                    if (vuMark == RelicRecoveryVuMark.LEFT) pictograph = 1;
                    else if (vuMark == RelicRecoveryVuMark.CENTER) pictograph = 2;
                    else if (vuMark == RelicRecoveryVuMark.RIGHT) pictograph = 3;

                    rotation = true;
                } else telemetry.addData("Pictograph","Waiting for jewel");*/

                /*if (teamC.equals("RED") && jewelGotten && rotation) {
                    if (whichJewel.equals("LEFT")) {
                        if (pictograph == 1) {
                            robot.forwardINCH(39,0.5);
                        }
                        else if (pictograph == 2) {
                            robot.forwardINCH(31,0.5);
                        }
                        else if (pictograph == 3) {
                            robot.forwardINCH(23, 0.5);
                        }
                        else {
                            robot.forwardINCH(31,0.5);
                        }
                     }
                    else if (whichJewel.equals("RIGHT")) {
                        if (pictograph == 1) {
                            robot.forwardINCH(49,0.5);
                        }
                        else if (pictograph == 2) {
                            robot.forwardINCH(41,0.5);
                        }
                        else if (pictograph == 3) {
                            robot.forwardINCH(33, 0.5);
                        }
                        else {
                            robot.forwardINCH(41,0.5);
                        }
                    }

                } else if (teamC.equals("BLUE")) {

                }*/

                telemetry.addLine()
                        .addData("", "Jewel Color")
                        .addData("Red", robot.jewelColor.red())
                        .addData("Blue", robot.jewelColor.blue());
                telemetry.addLine()
                        .addData("", "Team Color")
                        .addData("Red", robot.teamColor.red())
                        .addData("Blue", robot.teamColor.blue());
                telemetry.addData("Status", "Running");
                telemetry.update();
            }
        }
    }
