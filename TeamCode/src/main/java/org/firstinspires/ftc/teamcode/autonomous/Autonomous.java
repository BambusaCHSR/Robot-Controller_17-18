package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import com.qualcomm.robotcore.eventloop.opmode.*;
import org.firstinspires.ftc.teamcode.definitions.Definitions;

/**
 * Created by elijah on 12/9/17.
 **/
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Main Auto", group="Bambusa")
public class Autonomous extends LinearOpMode {
    private Definitions robot = new Definitions();
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Jewel", "Not Gotten");
        telemetry.update();

        boolean jewelGotten = false;
        boolean STOP = false;
        int AutoMode = 0;
        boolean cLed = true;

        robot.init(hardwareMap);
        robot.vuforiaInit();
        robot.servoInit();
        robot.cLed(cLed);
        robot.encoderInit();

        VuforiaTrackables relicTrackables = this.robot.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        RelicRecoveryVuMark center = RelicRecoveryVuMark.CENTER;
        RelicRecoveryVuMark left = RelicRecoveryVuMark.LEFT;
        RelicRecoveryVuMark right = RelicRecoveryVuMark.RIGHT;


        waitForStart();
        runtime.reset();


        while (opModeIsActive() && !STOP) {
            robot.cLed(cLed);
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            if (runtime.seconds() > 30 || gamepad1.back) {
                STOP = true;
            }
            else {
                telemetry.addData("Status", "Running");
                STOP = false;
            }

            if (runtime.seconds() > 10 && !jewelGotten) {
                jewelGotten = true;
                telemetry.addData("Jewel", "Skipped");
            }
            else if (!jewelGotten && runtime.seconds() < 10) {
                jewelGotten = false;
            }

            //for red team
            if (!jewelGotten && AutoMode == 0) {
                robot.jewels.setPosition(0.5);
                if (robot.jewelColor.red() > robot.jewelColor.blue()) {
                    robot.knockJewelOff("LEFT",100, 0, 0.5);
                    jewelGotten = true;
                    telemetry.addData("Jewel", "Gotten");
                }
                else if (robot.jewelColor.red() < robot.jewelColor.blue()) {
                    robot.knockJewelOff("RIGHT",100, 0, 0.5);
                    jewelGotten = true;
                    telemetry.addData("Jewel", "Gotten");
                }
                else {
                    jewelGotten = false;
                }
            }
            else if (jewelGotten && AutoMode == 0) {

                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                    if (vuMark == center) {
                        telemetry.addData("VuMark", "Center visible", vuMark);
                        AutoMode = 1;
                    } else if (vuMark == left) {
                        telemetry.addData("VuMark", "Left visible", vuMark);
                        AutoMode = 2;
                    } else if (vuMark == right) {
                        telemetry.addData("VuMark", "Right visible", vuMark);
                        AutoMode = 3;
                    } else {
                        telemetry.addData("VuMark", "Idk what I am seeing");
                        AutoMode = 0;
                    }
                }
                else {
                    telemetry.addData("VuMark", "No pictogram seen");
                }
            }

            if (AutoMode == 1) {
                telemetry.addData("VuMark", "Already Read");

            }
            else if (AutoMode == 2) {
                telemetry.addData("VuMark", "Already Read");

            }
            else if (AutoMode == 3) {
                telemetry.addData("VuMark", "Already Read");

            }

            telemetry.addData("Run Time", runtime.toString());
            telemetry.addLine()
                    .addData("Color sensor", null)
                    .addData("Red", robot.jewelColor.red())
                    .addData("Blue", robot.jewelColor.blue())
                    .addData("Green", robot.jewelColor.green())
                    .addData("Alpha", robot.jewelColor.alpha());
            telemetry.update();
        }
    }
}
