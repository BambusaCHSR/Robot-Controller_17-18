package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/15/2018.7:21 PM.
 **/
@Disabled
@Autonomous(name = "Front Autonomous", group = "bambusa")
public class AutoTest extends LinearOpMode {

    private Definitions robot = new Definitions();
    private ElapsedTime runtime = new ElapsedTime();

    private boolean movementDone = false;
    private boolean jewelGotten = false;
    private boolean rotation = false;
    private boolean teamColor = false;
    private String teamC = null;
    private String whichJewel = null;
    private int pictograph = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        robot.hardwareMapInit(hardwareMap);
        robot.vuforiaInit();
        robot.servoInit();
        robot.driveInitAuto();

        VuforiaTrackables relicTrackables = this.robot.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        relicTrackables.activate();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            if (!teamColor) {
                if (robot.teamColor.red() > robot.teamColor.blue()) {
                    teamC = "RED";
                    teamColor = true;
                } else if (robot.teamColor.red() < robot.teamColor.blue()) {
                    teamC = "BLUE";
                    teamColor = true;
                } else if (robot.teamColor.red() == 0 && robot.teamColor.blue() == 0)
                    telemetry.addData("Team Color", "Finiding Team Color");
            } else telemetry.addData("Team Color", "is: " + teamC);

            if (teamC.equals("RED")) {
                if (!jewelGotten) {
                    telemetry.addData("Jewel", "Not Gotten");
                    robot.jewelDown();
                    if (robot.jewelColor.red() < robot.jewelColor.blue()) {
                        robot.forwardINCH(5, 0.3);
                        robot.jewelUp();
                        robot.backwardINCH(5,0.3);
                        jewelGotten = true;
                        telemetry.addData("JewelColor", "Red");
                    } else if (robot.jewelColor.red() > robot.jewelColor.blue()) {
                        robot.backwardINCH(5, 0.3);
                        robot.jewelUp();
                        robot.forwardINCH(5, 0.3);
                        jewelGotten = true;
                        telemetry.addData("JewelColor", "Blue");
                    } else {
                        robot.setDriveForward();
                        robot.setPos(50);
                        robot.setPower(0.2);
                    }
                } else telemetry.addData("Jewel", "Gotten");

                if (jewelGotten && !rotation) {
                    telemetry.addData("Pictograph", "Looking For Pictograph");
                    if (vuMark == RelicRecoveryVuMark.LEFT) pictograph = 1;
                    else if (vuMark == RelicRecoveryVuMark.CENTER) pictograph = 2;
                    else if (vuMark == RelicRecoveryVuMark.RIGHT) pictograph = 3;

                    rotation = true;
                }
                else telemetry.addData("Pictograph","Waiting for jewel");

                if (jewelGotten && rotation && !movementDone) {
                    if (pictograph == 1) {
                        robot.forwardINCH(44, 0.5);
                        robot.rotRightDeg(90,0.4);
                        robot.forwardINCH(22, 0.5);
                        robot.openArms();
                        robot.backwardINCH(4, 0.2);
                    } else if (pictograph == 2) {
                        robot.forwardINCH(36, 0.5);
                        robot.rotRightDeg(90,0.4);
                        robot.forwardINCH(22, 0.5);
                        robot.openArms();
                        robot.backwardINCH(4, 0.2);
                    } else if (pictograph == 3) {
                        robot.forwardINCH(28, 0.5);
                        robot.rotRightDeg(90,0.4);
                        robot.forwardINCH(22, 0.5);
                        robot.openArms();
                        robot.backwardINCH(4, 0.2);
                    } else {
                        robot.forwardINCH(36, 0.5);
                        robot.rotRightDeg(90,0.4);
                        robot.forwardINCH(12, 0.5);
                        movementDone = true;
                    }
                }
                else if (movementDone) {
                    telemetry.addData("Status", "Autonomous Done");
                    sleep(1000);
                    break;
                }
            }



            else if (teamC.equals("BLUE")) {
                if (!jewelGotten) {
                    telemetry.addData("Jewel", "Not Gotten");
                    robot.jewelDown();
                    if (robot.jewelColor.red() > robot.jewelColor.blue()) {
                        robot.forwardINCH(5, 0.3);
                        robot.jewelUp();
                        robot.backwardINCH(5,0.3);
                        jewelGotten = true;
                        telemetry.addData("JewelColor", "Red");
                    } else if (robot.jewelColor.red() < robot.jewelColor.blue()) {
                        robot.backwardINCH(5, 0.3);
                        robot.jewelUp();
                        robot.forwardINCH(5, 0.3);
                        jewelGotten = true;
                        telemetry.addData("JewelColor", "Blue");
                    } else {
                        robot.setDriveForward();
                        robot.setPos(50);
                        robot.setPower(0.2);
                    }
                } else telemetry.addData("Jewel", "Gotten");

                if (jewelGotten && !rotation) {
                    telemetry.addData("Pictograph", "Looking For Pictograph");
                    robot.rotRightDeg(10,0.3);
                    robot.rotLeftDeg(20,0.3);
                    robot.rotRightDeg(10,0.3);

                    //if (vuMark == RelicRecoveryVuMark.LEFT) pictograph = 1;
                    //else if (vuMark == RelicRecoveryVuMark.CENTER) pictograph = 2;
                    //else if (vuMark == RelicRecoveryVuMark.RIGHT) pictograph = 3;

                        if (pictograph > 0) {
                            rotation = true;
                        } else {
                            rotation = false;
                        }
                }
                else telemetry.addData("Pictograph","Waiting for jewel");

                if (jewelGotten && rotation) {
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

                if (jewelGotten && rotation && !movementDone) {
                    if (pictograph == 1) {
                        robot.backwardINCH(44, 0.5);
                        robot.rotRightDeg(90,0.4);
                        robot.forwardINCH(22, 0.5);
                        robot.openArms();
                        robot.backwardINCH(4, 0.2);
                    } else if (pictograph == 2) {
                        robot.backwardINCH(36, 0.5);
                        robot.rotRightDeg(90,0.4);
                        robot.forwardINCH(22, 0.5);
                        robot.openArms();
                        robot.backwardINCH(4, 0.2);
                    } else if (pictograph == 3) {
                        robot.backwardINCH(28, 0.5);
                        robot.rotRightDeg(90,0.4);
                        robot.forwardINCH(22, 0.5);
                        robot.openArms();
                        robot.backwardINCH(4, 0.2);
                    } else {
                        robot.backwardINCH(36, 0.5);
                        robot.rotRightDeg(90,0.4);
                        robot.forwardINCH(12, 0.5);
                        movementDone = true;
                    }
                }
                else if (movementDone) {
                    telemetry.addData("Status", "Autonomous Done");
                    wait(1000);
                    break;
                }
            }

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
