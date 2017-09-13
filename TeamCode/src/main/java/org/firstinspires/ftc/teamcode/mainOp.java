package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by elijah on 9/10/17.
 **/

public class mainOp {
        // Private Members
        private LinearOpMode myOpMode;

        private DcMotor  leftFrontDrive = null;
        private DcMotor  rightFrontDrive = null;
        private DcMotor  leftBackDrive = null;
        private DcMotor  rightBackDrive = null;

        private double  driveAxial = 0 ;   // Positive is forward
        private double  driveLateral = 0 ;   // Positive is right
        private double  driveYaw = 0 ;   // Positive is CCW

        /* Constructor */
        public mainOp(){

        }


        /* Initialize standard Hardware interfaces */
        public void initDrive(LinearOpMode opMode) {

            // Save reference to Hardware map
            myOpMode = opMode;

            // Define and Initialize Motors
            leftFrontDrive = myOpMode.hardwareMap.get(DcMotor.class, "leftFrontDrive");
            rightFrontDrive = myOpMode.hardwareMap.get(DcMotor.class, "rightFrontDrive");
            leftBackDrive = myOpMode.hardwareMap.get(DcMotor.class, "leftBackDrive");
            rightBackDrive = myOpMode.hardwareMap.get(DcMotor.class, "rightBackDrive");

            leftFrontDrive.setDirection(DcMotor.Direction.FORWARD); // Positive input rotates counter clockwise
            rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);// Positive input rotates counter clockwise
            leftBackDrive.setDirection(DcMotor.Direction.FORWARD); // Positive input rotates counter clockwise
            rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

            //use RUN_USING_ENCODERS because encoders are installed.
            setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public void manualDrive()  {
            setAxial(myOpMode.gamepad1.left_stick_y);
            setLateral(myOpMode.gamepad1.left_stick_x);
            setYaw(-myOpMode.gamepad1.right_stick_x);
        }


        /***
         * void moveRobot(double axial, double lateral, double yaw)
         * Set speed levels to motors based on axes requests
         * @param axial     Speed in Fwd Direction
         * @param lateral   Speed in lateral direction (+ve to right)
         * @param yaw       Speed of Yaw rotation.  (+ve is CCW)
         */
        public void moveRobot(double axial, double lateral, double yaw) {
            setAxial(axial);
            setLateral(lateral);
            setYaw(yaw);
            moveRobot();
        }

        /***
         * void moveRobot()
         * This method will calculate the motor speeds required to move the robot according to the
         * speeds that are stored in the three Axis variables: driveAxial, driveLateral, driveYaw.
         * This code is setup for a three wheeled OMNI-drive but it could be modified for any sort of omni drive.
         *
         * The code assumes the following conventions.
         * 1) Positive speed on the Axial axis means move FORWARD.
         * 2) Positive speed on the Lateral axis means move RIGHT.
         * 3) Positive speed on the Yaw axis means rotate COUNTER CLOCKWISE.
         *
         * This convention should NOT be changed.  Any new drive system should be configured to react accordingly.
         */
        public void moveRobot() {
            // calculate required motor speeds to acheive axis motions
            double frontRight = -driveAxial + driveLateral + (driveYaw * 0.5);
            double frontLeft = driveAxial + driveLateral + (driveYaw * 0.5);
            double backRight = -driveAxial - driveAxial + (driveYaw * 0.5);
            double backLeft = driveAxial - driveAxial + (driveYaw * 0.5);

            // normalize all motor speeds so no values exceeds 100%.
            double max = Math.max(Math.abs(backLeft), Math.abs(frontRight));
            double max2 = Math.max(Math.abs(backRight), Math.abs(frontLeft));
            max = Math.max(max, max2);

            if (max > 1.0) {
                backLeft /= max;
                backRight /= max;
                frontLeft /= max;
                frontRight /= max;

            }

            // Set drive motor power levels.
            leftBackDrive.setPower(backLeft);
            rightBackDrive.setPower(backRight);
            leftFrontDrive.setPower(frontLeft);
            rightFrontDrive.setPower(frontRight);

            // Display Telemetry
            myOpMode.telemetry.addData("Axes  ", "A[%+5.2f], L[%+5.2f], Y[%+5.2f]", driveAxial, driveLateral, driveYaw);
            myOpMode.telemetry.addData("Wheels", "FL[%+5.2f], FR[%+5.2f], BL[%+5.2f], BR[%+5.2f]", frontLeft, frontRight, backLeft, backRight);
        }

        public void setAxial(double axial)      {driveAxial = Range.clip(axial, -1, 1);}
        public void setLateral(double lateral)  {driveLateral = Range.clip(lateral, -1, 1); }
        public void setYaw(double yaw)          {driveYaw = Range.clip(yaw, -1, 1); }


        /***
         * void setMode(DcMotor.RunMode mode ) Set all drive motors to same mode.
         * @param mode    Desired Motor mode.
         */
        public void setMode(DcMotor.RunMode mode ) {
            leftFrontDrive.setMode(mode);
            rightFrontDrive.setMode(mode);
            leftBackDrive.setMode(mode);
            rightBackDrive.setMode(mode);
        }
}

