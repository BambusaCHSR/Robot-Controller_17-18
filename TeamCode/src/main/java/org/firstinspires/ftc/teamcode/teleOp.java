package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Elijah Sauder for Bambusa in ftc_app, on 01/27/2018.12:14 AM.
 */
@TeleOp(name = "test", group = "bambusa")
public class teleOp extends LinearOpMode {

    private Definitions robot = new Definitions();
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Pre-initialization");
        telemetry.update();

        boolean STOP = false;
        boolean toggle = false;
        boolean servos = true;

        robot.hardwareMapInit(hardwareMap);
        robot.servoInit();
        //robot.encoderInit();

        //robot.jewelColor.enableLed(true);
        //robot.teamColor.enableLed(false);

        waitForStart();
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

            //glyph lifter gamepad inputs
            boolean bumpR = gamepad2.right_bumper;
            boolean bumpL = gamepad2.left_bumper; //buffalo wings was here

            //Glyph grabber toggle gamepad input
            boolean b = gamepad2.b;

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
            else if (bumpL) robot.lift.setPower(0.5); //tells the robot to do nothing with the glyphlifter if no bumpers are pressed.
            else robot.lift.setPower(0);

            if (b) {
                robot.closeArms();
            }
            else {
                robot.openArms();
            }

            /*if (toggle && b) {
                toggle = false;
                if (servos) {
                    servos= false;
                    robot.glyphGrabLeft.setPosition(0);
                    robot.glyphGrabRight.setPosition(1);
                }
                else {
                    servos= true;
                    robot.glyphGrabLeft.setPosition(1);
                    robot.glyphGrabRight.setPosition(0);
                }
            }
            else if(!toggle) {
                toggle = true;
            }*/

            /** testing **/
            //if (gamepad1.a) robot.rotLeftDeg(90, 0.7);

            /**Emergency Stop**/
            STOP = back;

            //adds the telemetry of the robot to the controller screen.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
