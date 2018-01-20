package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.definitions.DrivingDef;
import org.firstinspires.ftc.teamcode.definitions.Initialization;
import org.firstinspires.ftc.teamcode.definitions.Ramp;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/15/2018.7:17 PM.
 **/

@TeleOp(name="opMode", group="Bambusa")
public class TestTeleOp extends LinearOpMode {

    //loads the Initialization file.
    private Initialization robot = new Initialization();
    private DrivingDef robot2 = new DrivingDef();

    //Sets up Elapsed Time so we can see the runtime of the robot program.
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    @Override
    public void runOpMode() throws InterruptedException {

        /**---------------
         * Initialization
         * ---------------*/
        //Lets us see information about the robot as it is running on the Controller phone.
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Initializes the boolean values not used in gamepad inputs.
        boolean STOP = false; //Sets the default Emergency stop to false so the program doesn't automatically stop when we start it.
        boolean toggle = false; //Both toggle and servo are values used in a toggle for the glyph grabber
        boolean servo = true; //

        //initializes the Hardware map from the definitions file.
        robot.hardwareMapInit(hardwareMap);

        //calls the Initializations for the servos from the definitions file
        robot.servoInit();

        //Turns on the color sensor light on.
        robot.cLed(true);

        //waits for the you to hit the start button on the phone and resets the runtime clock in case it had been running.
        waitForStart();
        runtime.reset();

        //loop for running the robot once the start button is pressed
        while (opModeIsActive() && !STOP) { //the !STOP is the boolean variable for the emergency stop. Once the emergency
                                            //stop is triggered it will change to true and remove one of the
                                            //loop's dependencies causing the loop to stop
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
            robot.driveFrontRight.setPower(DFR); //Motor controller for front motors: AL00VXSF (Front Motors)
            robot.driveFrontLeft.setPower(DFL);
            robot.driveBackRight.setPower(DBR); //Motor controller for back motors: AL00VXUD (Back Motors)
            robot.driveBackLeft.setPower(DBL);

            robot.armLower1.setPower(-lower); //Motor controller for the lower arm: AL00XQNP (Arm Lower)
            robot.armLower2.setPower(lower);
            robot.armUpper.setPower(upper); //Motor controller for the upper arm and glyph lifter: AL00XUYT (Arm Upper and Winch)

            //Sets the movement of the glyph lifter to the two bumpers on the gamepad
            if (bumpR && bumpL) robot.lift.setPower(0); //Adds a safety in case the gamepad operator presses both bumpers
            else if (bumpR) robot.lift.setPower(-0.5); //Makes sure that only one of the bumpers is being pressed for it to raise.
            else if (bumpL) robot.lift.setPower(0.5); //tells the robot to do nothing with the glyphlifter if no bumpers are pressed.
            else robot.lift.setPower(0);

            //Second take at the toggle for the glyph grabber
            if (toggle && b) { toggle = false;
                if (servo) { servo= false;
                    robot.openArms();
                } else { servo= true;
                    robot.closeArms();
                }
            } else if(!toggle) toggle = true;

            /** testing **/
            if (gamepad1.a) {
                robot2.rotLeftINCH(90, 0.7);
            }
            else if (!gamepad1.a){
            }



            //Toggle for the glyph grabber
            /*if (!toggle && b) { //If the toggle is off and the user is pressing the b button start the toggle, start the if statement
                toggle = true; //sets the toggle to true once you start it
                if (servo) { //if the servo has not been closed (if it is false) close them
                    servo = true; //sets the servo variable to true, saying the grippers are closed
                    robot.glyphGrabLeft.setPosition(0.7); //sets the positions to which the servos move to.
                    robot.glyphGrabRight.setPosition(0.3);
                } else { //if the servo is closed when you run the toggle open the grippers
                    servo = false; //Sets the servo variable to false, saying the grippers are closed in preparation for the next toggle press.
                    robot.glyphGrabLeft.setPosition(1); //sets the positions to which the servos return to.
                    robot.glyphGrabRight.setPosition(0);
                }
            }
            else if (!b) { //If you aren't pressing the toggle button make sure the toggle variable is set to false
                toggle = false;
            }*/

            /**Emergency Stop**/
                STOP = back;

            //adds the telemetry of the robot to the controller screen.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addLine()
                    .addData("Controller #1", null)
                    .addData("Drive F/B", gamepad1.left_stick_y)
                    .addData("Drive R/L", gamepad1.left_stick_x)
                    .addData("Drive Rotation", gamepad1.right_stick_x);
            telemetry.addLine()
                    .addData("Controller #2", null)
                    .addData("Glyphs", null)
                    .addData("Up", gamepad2.right_bumper ? "Up" : "Null")
                    .addData("Down", gamepad2.left_bumper ? "Down" : "Null")
                    .addData("Grab", toggle ? "Off" : "On")
                    .addData("Arm", null)
                    .addData("Lower", gamepad2.left_stick_x)
                    .addData("Upper", gamepad2.right_stick_x);
            telemetry.addLine()
                    .addData("Color sensor", null)
                    .addData("Red", robot.jewelColor.red())
                    .addData("Blue", robot.jewelColor.blue())
                    .addData("Green", robot.jewelColor.green())
                    .addData("Alpha", robot.jewelColor.alpha());
            telemetry.addData("Drive Motors", null);
            telemetry.addLine()
                    .addData("Drive FR", robot.driveFrontRight.getPower())
                    .addData("Drive FL", robot.driveFrontLeft.getPower());
            telemetry.addLine()
                    .addData("Drive BR", robot.driveBackRight.getPower())
                    .addData("Drive BL", robot.driveBackLeft.getPower());
            telemetry.addLine()
                    .addData("Other Motors", null)
                    .addData("Arm Lower", robot.armLower2.getPower())
                    .addData("Arm Upper", robot.armUpper.getPower());
            telemetry.update();
        }
    }
}
