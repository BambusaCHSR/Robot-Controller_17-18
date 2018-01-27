package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.definitions.EncodersDef;
import org.firstinspires.ftc.teamcode.definitions.Initialization;
import org.firstinspires.ftc.teamcode.definitions.Movements;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/26/2018.7:49 PM.
 */

@Autonomous(name="Front Auto", group="Bambusa")
class AutonomousFront extends LinearOpMode{
    private EncodersDef encoders = new EncodersDef();
    private Movements robot = new Movements();
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Jewel", "Not Gotten");
        telemetry.update();

        boolean jewelGotten = false;
        boolean STOP = false;
        int AutoMode = 0;
        String teamC = null;

        robot.hardwareMapInit(hardwareMap);
        robot.vuforiaInit();
        robot.servoInit();
        robot.teamColor.enableLed(true);
        robot.jewelColor.enableLed(true);
        robot.encoderInit();

        VuforiaTrackables relicTrackables = this.robot.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        RelicRecoveryVuMark center = RelicRecoveryVuMark.CENTER;
        RelicRecoveryVuMark left = RelicRecoveryVuMark.LEFT;
        RelicRecoveryVuMark right = RelicRecoveryVuMark.RIGHT;

        while (teamC == null) {
            if (robot.teamColor.red() > robot.teamColor.blue()) teamC = "RED";
            else if (robot.teamColor.red() < robot.teamColor.blue()) teamC = "BLUE";
        }

        waitForStart();
        runtime.reset();

        while (opModeIsActive() && !STOP) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            if (runtime.seconds() > 30 || gamepad1.back) {STOP = true;}
            else {telemetry.addData("Status", "Running"); STOP = false;}

            if (runtime.seconds() > 10 && !jewelGotten) {jewelGotten = true; telemetry.addData("Jewel", "Skipped");}
            else if (!jewelGotten && runtime.seconds() < 10) {jewelGotten = false;}

            if (teamC.equals("RED")) {
                if (!jewelGotten) {
                    robot.jewelKnocker.setPosition(0.5);
                    if (robot.jewelColor.red() > robot.jewelColor.blue()) {
                        robot.knockJewelOff("RIGHT", 300, 0, 0.5);
                        jewelGotten = true;
                        telemetry.addData("Jewel", "Gotten");
                    }
                    else if (robot.jewelColor.red() < robot.jewelColor.blue()) {
                        robot.knockJewelOff("LEFT",300, 0, 0.5);
                        jewelGotten = true;
                        telemetry.addData("Jewel", "Gotten");
                    }
                    else if (robot.jewelColor.red() == 0 && robot.jewelColor.blue() == 0){
                        jewelGotten = false;
                        robot.setRotLeft();
                        robot.setPower(0.2);
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
                    encoders.rotLeftDeg(90,0.5);
                    encoders.forwardINCH(36,0.5);
                    encoders.rotRightDeg(90,0.5);
                    encoders.forwardINCH(11,0.5);
                    robot.openArms();
                    encoders.backwardINCH(5,0.2);
                }
                else if (AutoMode == 2) {
                    telemetry.addData("VuMark", "Already Read");
                    encoders.rotLeftDeg(90,0.5);
                    encoders.forwardINCH(28,0.5);
                    encoders.rotRightDeg(90,0.5);
                    encoders.forwardINCH(11,0.5);
                    robot.openArms();
                    encoders.backwardINCH(5,0.2);
                }
                else if (AutoMode == 3) {
                    telemetry.addData("VuMark", "Already Read");
                    encoders.rotLeftDeg(90,0.5);
                    encoders.forwardINCH(44,0.5);
                    encoders.rotRightDeg(90,0.5);
                    encoders.forwardINCH(11,0.5);
                    robot.openArms();
                    encoders.backwardINCH(5,0.2);
                }
            }
            /**This is the blue side**/
            else {
                if (!jewelGotten) {
                    robot.jewelKnocker.setPosition(0.5);
                    if (robot.jewelColor.red() > robot.jewelColor.blue()) {
                        robot.knockJewelOff("LEFT", 300, 0, 0.5);
                        jewelGotten = true;
                        telemetry.addData("Jewel", "Gotten");
                    }
                    else if (robot.jewelColor.red() < robot.jewelColor.blue()) {
                        robot.knockJewelOff("RIGHT",300, 0, 0.5);
                        jewelGotten = true;
                        telemetry.addData("Jewel", "Gotten");
                    }
                    else if (robot.jewelColor.red() == 0 && robot.jewelColor.blue() == 0){
                        jewelGotten = false;
                        robot.setRotLeft();
                        robot.setPower(0.2);
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
                    encoders.rotRightDeg(90,0.5);
                    encoders.forwardINCH(36,0.5);
                    encoders.rotLeftDeg(14,0.5);
                    encoders.forwardINCH(11,0.5);
                    robot.openArms();
                    encoders.backwardINCH(5,0.2);
                }
                else if (AutoMode == 2) {
                    telemetry.addData("VuMark", "Already Read");
                    encoders.rotRightDeg(90,0.5);
                    encoders.forwardINCH(28,0.5);
                    encoders.rotLeftDeg(90,0.5);
                    encoders.forwardINCH(11,0.5);
                    robot.openArms();
                    encoders.backwardINCH(5,0.2);
                }
                else if (AutoMode == 3) {
                    telemetry.addData("VuMark", "Already Read");
                    encoders.rotRightDeg(90,0.5);
                    encoders.forwardINCH(44,0.5);
                    encoders.rotLeftDeg(90,0.5);
                    encoders.forwardINCH(11,0.5);
                    robot.openArms();
                    encoders.backwardINCH(5,0.2);
                }
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
