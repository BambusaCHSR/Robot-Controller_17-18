package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/15/2018.7:21 PM.
 **/
@Disabled
@Autonomous(name = "BackAutonomous", group = "bambusa")
public class AutonomousBack extends LinearOpMode {

    private Definitions robot = new Definitions();
    private ElapsedTime runtime = new ElapsedTime();

    private boolean jewelGotten = false;
    private boolean teamColor = false;
    private String teamC = null;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        robot.hardwareMapInit(hardwareMap);
        robot.servoInit();
        robot.driveInitAuto();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");

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
                        robot.backwardINCH(5, 0.3);
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

                if (jewelGotten) {
                    robot.rotLeftDeg(30,0.5);
                    robot.forwardINCH(40, 0.5);
                    robot.rotLeftDeg(30, 0.4);
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

                if (jewelGotten) {
                    robot.rotLeftDeg(30,0.5);
                    robot.forwardINCH(40, 0.5);
                    robot.rotRightDeg(30, 0.4);
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
