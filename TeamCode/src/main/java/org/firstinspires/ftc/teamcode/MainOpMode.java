package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Bambusa on 11/11/17.
 **/
@Disabled
@TeleOp(name="Bambusa Op", group="Bambusa")
public class MainOpMode extends LinearOpMode {

    //loads the Definitions file.
    private Definitions robot = new Definitions();

    //Sets up Elapsed Time so we can see the runtime of the robot program.
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    private ElapsedTime rampTime_x = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    private ElapsedTime rampTime_y = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    private ElapsedTime rampTime_r = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    //Opens the runOpMode function where we can define everything and run the loop.
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
        boolean toggle = true; //   Both of these values are used in the glyph lifter
        boolean toggle2 = true;
        boolean servo = false; // toggle.
        boolean led = false;
        boolean cLed = true;

        double ramp_x, ramp_y, ramp_r;

        int rampInt = Math.abs(3);

        //initializes the Hardware map from the definitions file.
        robot.init(hardwareMap);

        //calls the Initializations for the servos from the definitions file
        robot.servoInit();

        //waits for the you to hit the start button on the phone and resets the runtime clock in case it had been running.
        waitForStart();
        runtime.reset();

        //loop for running the robot once the start button is pressed
        while (opModeIsActive() && !STOP) { //the !STOP is the boolean variable for the emergency stop. Once the emergency
                                            //stop is triggered it will change to true and remove one of the
                                            //loop's dependencies causing the loop to stop.

            /** ----------
             *  Motor ramp
             *  ---------- */

            double rampDivi = 1000 * rampInt;

            if (gamepad1.left_stick_x != 0) {
                if (rampTime_x.milliseconds() > 1000) {
                    ramp_x = 1;
                }
                else {
                    ramp_x = rampTime_x.milliseconds() / rampDivi;
                }
            }
            else {
                ramp_x = 0;
                rampTime_x.reset();
            }
            if (gamepad1.left_stick_y != 0) {
                if (rampTime_y.milliseconds() > 1000) {
                    ramp_y = 1;
                }
                else {
                    ramp_y = rampTime_y.milliseconds() / rampDivi;
                }
            }
            else {
                ramp_y = 0;
                rampTime_y.reset();
            }
            if (gamepad1.right_stick_x != 0) {
                if (rampTime_r.milliseconds() > 1000) {
                    ramp_r = 1;
                }
                else {
                    ramp_r = rampTime_r.milliseconds() / rampDivi;
                }
            }
            else {
                ramp_r = 0;
                rampTime_r.reset();
            }

            /**-----------------
             * Gamepad Variables
             * ------------------*/
            //changeing all gamepad inputs to variables
            //driving gamepad input

            double gamex = gamepad1.left_stick_x;
            double gamey = gamepad1.left_stick_y;
            double gamer = gamepad1.right_stick_x;

            //arm gamepad inputs
            float lower = gamepad2.left_stick_x;
            float upper = gamepad2.right_stick_x;

            //glyph lifter gamepad inputs
            boolean bumpR = gamepad2.right_bumper;
            boolean bumpL = gamepad2.left_bumper;

            //Glyph grabber toggle gamepad input
            boolean b = gamepad2.b;
            boolean g1X = gamepad1.x;

            //Emergency stop gamepad inputs
            boolean back = gamepad1.back && gamepad2.back;

            /**Drive values**/

            //multiplies the value of the gamepad by the ramp amount
            double x = (ramp_x * gamex);
            double y = (ramp_y * gamey);
            double r = (ramp_r * gamer);

            //sets the the variables for the motor power to the following equations and clips it to 1.
            double DFR = Range.clip( -y - x - r,-1,1); //This will clip the outputs to the motors
            double DFL = Range.clip(y - x - r,-1,1);//to 1 making sure they don't burn out.
            double DBR = Range.clip(-y + x - r,-1,1);
            double DBL = Range.clip(y + x - r,-1,1);

            /** Color sensor LED on/off**/

            robot.cLed(cLed);

            /**------------------------------------------
             * Movement and any other actual robot actions
             * ------------------------------------------*/
            //Takes the variables from above and sets them to the drive motors to give them power.
            robot.driveFrontRight.setPower(DFR); //Motor controller for front motors: AL00VXSF (Front Motors)
            robot.driveFrontLeft.setPower(DFL);
            robot.driveBackRight.setPower(DBR); //Motor controller for back motors: AL00VXUD (Back Motors)
            robot.driveBackLeft.setPower(DBL);

            robot.armLower1.setPower(0.6 * -lower); //Motor controller for the lower arm: AL00XQNP (Arm Lower)
            robot.armLower2.setPower(0.6 * lower);
            robot.armUpper.setPower(0.4 * upper); //Motor controller for the upper arm and glyph lifter: AL00XUYT (Arm Upper and Winch)

            //Sets the movement of the glyph lifter to the two bumpers on the gamepad
            if (bumpR && bumpL) { //Adds a safety in case the gamepad operator presses both bumpers
                robot.lift.setPower(0);
            }
            else if (bumpR && !bumpL) { //Makes sure that only one of the bumpers is being pressed for it to raise.
                robot.lift.setPower(-0.5);
            }
            else if (bumpL && !bumpR) { //Makes sure that only one of the bumpers is being pressed for it to lower.
                robot.lift.setPower(0.5);
            }
            else {                      //tells the robot to do nothing with the glyphlifter if no bumpers are pressed.
                robot.lift.setPower(0);
            }

            //Toggle for the glyph lifter
            if (toggle && b) {    //If the toggle is on and the user is pressing the b button start the if statement
                toggle = false;     //sets the toggle to false once you start it.
                if (servo) {        //if the servo boolean variable is true when you press the button move the grippers in.
                    servo = false;      //sets the boolean variable to false to make sure the program doesn't try to loop this part if you keep pressing b.
                    robot.glyphGrabLeft.setPosition(0.7); //sets the positions to which the servos move to.
                    robot.glyphGrabRight.setPosition(0.3);
                } else {            //if the servo variable is false when you press the button close the grippers.
                    servo = true;       //sets the variable to true for the next time you press the button.
                    robot.glyphGrabLeft.setPosition(1); //sets the positions to which the servos return to.
                    robot.glyphGrabRight.setPosition(0);
                }
            }
            //makes sure that if you are not pressing the b button the toggle variable is true so the toggle doesnt auto trigger.
            else if (!b) {
                toggle = true;
            }

            if (toggle2 && g1X) {
                toggle2 = false;
                if (led) {
                    led = false;
                    cLed = false;
                }
                else {
                    led = true;
                    cLed = true;
                }
            }
            else if (!g1X) {
                toggle2 = true;
            }

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
                    .addData("Alpha", robot.jewelColor.alpha())
                    .addData("LED", cLed ? "On" : "Off");
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