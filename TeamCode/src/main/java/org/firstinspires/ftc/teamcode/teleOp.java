package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Elijah Sauder for Bambusa in ftc_app, on 01/27/2018.12:14 AM.
 */
//Tells the app that this is a TeleOp and not autonomous or a generic class
@TeleOp(name = "test", group = "bambusa")
//Creates the class which extends LinearOpMode so that we can use the pre made functions provided us
public class teleOp extends LinearOpMode {

    //Imports Definitions.java so that we can use the methods and objects we made there
    private Definitions robot = new Definitions();
    //creates a Elapsed time object so we can see how long the robot has been running
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    //This is run when you press the init button on the driver station
    @Override
    public void runOpMode() throws InterruptedException {
        //Prints out on the driver station that the robot is being initialized
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        //Initializes variables we use in the code
        boolean STOP = false; //Is used to create an emergency stop to the loop controlled from the controllers
        boolean toggle = true; //Is used in a toggle we use for the glyph grab
        boolean servos = false; //Is used in a toggle we use for the glyph grab
        boolean toggleon = false; //Is used to check if the toggle is currently engaged or not

        //Imports the hardware map from the definitions file
        robot.hardwareMapInit(hardwareMap);

        //Initializes the servos using the method from the definitions file
        robot.servoInit();

        //Prints out on the driver station that the robot is fully initialized
        telemetry.addData("Status", "Initilized");
        telemetry.update();

        //Waits for the driver to press the play button on the driver station
        waitForStart();
        //resets the runtime (elapsed time) when the driver hits the play button
        runtime.reset();

        //Starts the loop once the driver hits the play button
        while (opModeIsActive() && !STOP) { //The !STOP is the emergency stop which is assigned
                                            //to the back buttons on the driver controllers
            /**-----------------
             * Gamepad Variables
             * ------------------*/
            //changeing all gamepad inputs to variables
            //driving gamepad input
            double gamex = gamepad1.left_stick_x;
            double gamey = gamepad1.left_stick_y;
            double gamer = gamepad1.right_stick_x;

            //glyph lifter gamepad inputs
            boolean bumpR = gamepad2.right_bumper;
            boolean bumpL = gamepad2.left_bumper; //buffalo wings was here

            //Glyph grabber toggle gamepad input
            boolean a = gamepad2.a;

            //Emergency stop gamepad inputs
            boolean back = gamepad1.back && gamepad2.back;

            /**Drive values**/
            //sets the the variables for the motor power to the following equations and clips it to 1.
            double DFR = -gamey - gamex - gamer; //This will clip the outputs to the motors
            double DFL = gamey - gamex - gamer;//to 1 making sure they don't burn out.
            double DBR = -gamey + gamex - gamer;
            double DBL = gamey + gamex - gamer;

            /**------------------------------------------
             * Movement and any other actual robot actions
             * ------------------------------------------*/
            //Takes the variables from above and sets them to the drive motors to give them power.
            robot.driveFrontRight.setPower(DFR); //Motor controller for front motors: AL00VXSF (Front Motors)
            robot.driveFrontLeft.setPower(DFL);
            robot.driveBackRight.setPower(DBR); //Motor controller for back motors: AL00VXUD (Back Motors)
            robot.driveBackLeft.setPower(DBL);

            telemetry.addData("", DFR).addData("", DFL).addData("",DBR).addData("", DBL);
            telemetry.addLine().addData("",robot.driveFrontRight.getPower()).addData("", robot.driveFrontLeft.getPower()).addData("", robot.driveBackRight.getPower()).addData("", robot.driveBackLeft.getPower());
            //Sets the movement of the glyph lifter to the two bumpers on the gamepad
            if (bumpR && bumpL) robot.lift.setPower(0); //Adds a safety in case the gamepad operator presses both bumpers
            else if (bumpR) robot.lift.setPower(-0.5); //Makes sure that only one of the bumpers is being pressed for it to raise.
            else if (bumpL) robot.lift.setPower(0.5); //tells the robot to do nothing with the glyph lifter if no bumpers are pressed.
            else robot.lift.setPower(0); //Makes sure that if something other then what is given is done it doesn't move

            //Toggle for the glyph grabber
            if (toggle && a) { //If the toggle is off and the user is pressing the b button start the toggle, start the if statement
                toggle = false; //sets the toggle to false once you start it to make sure that it doesn't call the beginning of the toggle again
                if (servos) { //if the servo has not been closed close them
                    servos= false; //Sets servos to false to make sure they don't try and close twice
                    toggleon = true; //Sets toggle on to true so that we can test for that later
                    robot.closeArms(); //Closes the glyph grab arms
                }
                else { //if the servo is closed when you run the toggle open the grippers
                    servos= true; //Sets the servo variable to true, saying the grippers are closed in preparation for the next toggle press.
                    toggleon = false; //sets toggleon to false so that we know that the toggle is not active
                    robot.openArms(); //opens the robot arms
                }
            } else if (!a) {    //If you aren't pressing the toggle button make sure the toggle variable is set to false
                                //to make sure the toggle isn't activated accidentally.
                toggle = true;
            }

            //Individual movement of each glyph grab arm
            //This is where we test for wether the toggle is on or off to make sure we don't run this
            //while the toggle is running
            /* Opens and closes the left arm if you press x on the gameoad */
            if (gamepad2.x && !toggleon) robot.glyphGrabLeft.setPosition(0.7);
            else if (!gamepad2.x && !toggleon)robot.glyphGrabLeft.setPosition(1);

            /* Opens and closes the right arm if you press b on the gamepad */
            if (gamepad2.b && !toggleon) robot.glyphGrabRight.setPosition(0.3);
            else if (!gamepad2.b && !toggleon)robot.glyphGrabRight.setPosition(0);


            /** testing the rotation by degrees**/
            //if (gamepad1.a) robot.rotLeftDeg(90, 0.7); //(Not being used atm)

            /**Emergency Stop**/
            STOP = back;

            //adds the telemetry of the robot to the controller screen.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update(); //updates the telemetry
        }
    }
}
