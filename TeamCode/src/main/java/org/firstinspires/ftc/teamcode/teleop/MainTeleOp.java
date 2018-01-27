package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.definitions.EncodersDef;
import org.firstinspires.ftc.teamcode.definitions.Movements;
import org.firstinspires.ftc.teamcode.definitions.Ramp;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/26/2018.7:49 PM.
 **/

@TeleOp(name="main", group="bambusa")
public class MainTeleOp extends LinearOpMode {

    private EncodersDef encoder = new EncodersDef();
    private Movements move = new Movements();

    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Pre-initialization");
        telemetry.update();

        boolean STOP = false;
        boolean toggle = false;
        boolean servos = true;

        move.hardwareMapInit(hardwareMap);
        move.servoInit();

        move.jewelColor.enableLed(true);
        move.teamColor.enableLed(false);

        waitForStart();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        runtime.reset();

        while (opModeIsActive() && !STOP) {
            /**-----------------
             * Gamepad Variables
             * ------------------*/
            //changeing all gamepad inputs to variables
            //driving gamepad input
            double gamex = gamepad1.left_stick_x;
            double gamey = gamepad1.left_stick_y;
            double gamer = gamepad1.right_stick_x;

            //arm gamepad inputs
            double lower = 0.6*gamepad2.left_stick_x;
            double upper = 0.4*gamepad2.right_stick_x;

            //glyph lifter gamepad inputs
            boolean bumpR = gamepad2.right_bumper;
            boolean bumpL = gamepad2.left_bumper;

            //Glyph grabber toggle gamepad input
            boolean b = gamepad2.b;

            //Emergency stop gamepad inputs
            boolean back = gamepad1.back && gamepad2.back;

            /**Drive values**/
            //sets the the variables for the motor power to the following equations and clips it to 1.
            double DFR = Range.clip(Ramp.sRamp(-gamey - gamex - gamer, 1),-1,1); //This will clip the outputs to the motors
            double DFL = Range.clip(Ramp.sRamp(gamey - gamex - gamer,1),-1,1);//to 1 making sure they don't burn out.
            double DBR = Range.clip(Ramp.sRamp(-gamey + gamex - gamer,1),-1,1);
            double DBL = Range.clip(Ramp.sRamp(gamey + gamex - gamer,1),-1,1);

            /**------------------------------------------
             * Movement and any other actual robot actions
             * ------------------------------------------*/
            //Takes the variables from above and sets them to the drive motors to give them power.
            move.driveFrontRight.setPower(DFR); //Motor controller for front motors: AL00VXSF (Front Motors)
            move.driveFrontLeft.setPower(DFL);
            move.driveBackRight.setPower(DBR); //Motor controller for back motors: AL00VXUD (Back Motors)
            move.driveBackLeft.setPower(DBL);

            //Sets the movement of the glyph lifter to the two bumpers on the gamepad
            if (bumpR && bumpL) move.lift.setPower(0); //Adds a safety in case the gamepad operator presses both bumpers
            else if (bumpR) move.lift.setPower(-0.5); //Makes sure that only one of the bumpers is being pressed for it to raise.
            else if (bumpL) move.lift.setPower(0.5); //tells the robot to do nothing with the glyphlifter if no bumpers are pressed.
            else move.lift.setPower(0);

            if (toggle && b) { toggle = false;
                if (servos) { servos= false;
                    move.openArms();
                } else { servos= true;
                    move.closeArms();
                }
            } else if(!toggle) toggle = true;

            /** testing **/
            if (gamepad1.a) encoder.rotLeftDeg(90, 0.7);

            /**Emergency Stop**/
            STOP = back;

            //adds the telemetry of the robot to the controller screen.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
