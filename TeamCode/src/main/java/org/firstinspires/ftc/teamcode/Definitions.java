package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Elijah Sauder for Bambusa in ftc_app, on 01/27/2018.12:10 AM.
 */

public class Definitions {

    //Initiates variables that are used later on to do calculations
    private int motorTicks = 1120;
    private double wheelDiamater = 4;

    //Creates the drive motors
    DcMotor driveFrontRight = null;
    DcMotor driveFrontLeft = null;
    DcMotor driveBackRight = null;
    DcMotor driveBackLeft = null;

    //creates the glyph lifter motor
    DcMotor lift = null;

    //Creates the glyph grabber servos
    Servo glyphGrabRight = null;
    Servo glyphGrabLeft = null;

    //Creates the servo we use to knock the jewels off
    Servo jewelKnocker = null;

    //Creates the two color sensors we use for our autonomous
    ColorSensor jewelColor = null; //Used to detect jewel color
    ColorSensor teamColor = null; //Used to detect our team color from the balancing stone.

    //Creates the i2c addresses for the color sensors that we set in the Modern Robotics Core Device Discovery application
    I2cAddr i2cAddressJewelColor = I2cAddr.create8bit(0x3c);
    I2cAddr i2cAddressTeamColor = I2cAddr.create8bit(0x4c);

    //Creates an instance of vuforia to read the pictographs
    VuforiaLocalizer vuforia = null;


    private int cameraMonitorViewId;

    /** ===========
     * Hardware Map
     * ============*/

    //Instead of making a hardware map for every file we make we keep it here so we just have to call it
    public void hardwareMapInit(HardwareMap Map) {
        //assigns the drive motors to their robot configuration names
        driveFrontRight = Map.dcMotor.get("driveFrontRight");
        driveFrontLeft = Map.dcMotor.get("driveFrontLeft");
        driveBackRight = Map.dcMotor.get("driveRightBack");
        driveBackLeft = Map.dcMotor.get("driveBackLeft");

        //Assigns the lift motor to its robot configuration name
        lift = Map.dcMotor.get("lift");

        //Assigns the glyph grab servos to their robot configuration names
        glyphGrabRight = Map.servo.get("glyphGrabRight");
        glyphGrabLeft = Map.servo.get("glyphGrabLeft");

        //Assigns the jewel knocker servo to its robot configuration name
        jewelKnocker = Map.servo.get("jewelKnocker");

        //Assigns the color sensors to their robot configuration name
        jewelColor = Map.colorSensor.get("jewelColor");
        teamColor = Map.colorSensor.get("teamColor");

        //Assigns the isc addresses to the color sensors
        jewelColor.setI2cAddress(i2cAddressJewelColor);
        jewelColor.setI2cAddress(i2cAddressTeamColor);

        //Assigns all the values to for our vuforia instance
        cameraMonitorViewId = Map.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", Map.appContext.getPackageName());
    }

    /* Method to initialize the servos to their default positions */
    public void servoInit() {
        openArms();
        jewelUp();
    }

    /* Method to initialize vuforia instance */
    public void vuforiaInit() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AS/00FX/////AAAAGRywycu7JEUJh4rz/rwORDkZJabbBKzxMuMKtBDf4H4wwDJtDTdfM4a0JXDJiPhDkK9SUSl9j8qDz8nbFunZwugJW7SdZvehZLe9J7Irk1Eu6w4+gmSEIvRBSxite8W+7xjTTtLyvMgxKbMacL8k0uuhfFJzwTS3qZ5FCm8HP7F8jqXgH5e2LLcRyJ+MYdNqPPx7wpISBVFY3rYGqxFJgAs1S1dReiziGOeyckCt1pcJXA6IroX5mgAVNwLWx9Wafoe3jd3dNdD+dxSep2E1MhmcA+WIcKIZ9615uMPfon5M7fyzjB6CFu5srJTpcgoVPbbr6LAZt7WiNPLLCuoFNx246V29fO1DeiTErTCbuL/Q";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
    }

    /* Method to initialize the encoders in autonomous */
    public void driveInitAuto() {
        resetEncoders();
        runModePos();
    }

    /* Used in driveInitAuto() to tell the motors to use encoders */
    private void useEncoders() {
        driveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private void dontUseEncoders() {
        driveFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /* Called once at the beginning of our autonomous inside of driveINitAuto() to make sure the encoders are 0ed out */
    private void resetEncoders() {
        driveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /* Sets the motor's drive mode to run to position so we can tell them to run to encoder positions */
    private void runModePos() {
        driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /* Method used to calculate the position we want the robot to move to in inches*/
    public void posInch(double pos) {
        /*Calculating the ratio between the circumference of the wheels and the encoder ticks to move
        the right amount forward in inches. It is cast to Int because encoders only take an int value while
        we still want the higher acuracy of the double value of pos and the decimal value of PI */
        int toInch = (int) (pos*(motorTicks/(wheelDiamater*Math.PI)));

        //Sets the target position of the motors to the current position plus the newly calculated position.
        //This lets us only reset the encoders once (This is executed wrong but we did not have time to fix it)
        driveFrontRight.setTargetPosition(toInch + driveFrontRight.getCurrentPosition());
        driveFrontLeft.setTargetPosition(toInch + driveFrontLeft.getCurrentPosition());
        driveBackRight.setTargetPosition(toInch + driveBackRight.getCurrentPosition());
        driveBackLeft.setTargetPosition(toInch + driveBackLeft.getCurrentPosition());
    }
    /* Method used to calculate the position we want to robot to rotate to in degrees */
    public void posDeg(double degreesOfTurning) {
        double robotDiamater = 19; //The diagonal (in inches) between two of the wheels of a 4 wheeled robot.

        //Formula we made which compares the ratio of the robot diameter to wheel diameter and multiplies conversion
        //of motor ticks to degrees. This lets us move close to exact degrees with our robot.
        int poss = (int) ((robotDiamater/wheelDiamater)*((motorTicks/360)*degreesOfTurning));

        //Sets the target position of the motors to the current position plus the newly calculated position.
        //This lets us only reset the encoders once (This is executed wrong but we did not have time to fix it)
        driveFrontRight.setTargetPosition(poss + driveFrontRight.getCurrentPosition());
        driveFrontLeft.setTargetPosition(poss + driveFrontLeft.getCurrentPosition());
        driveBackRight.setTargetPosition(poss + driveBackRight.getCurrentPosition());
        driveBackLeft.setTargetPosition(poss + driveBackLeft.getCurrentPosition());
    }

    /* A generic set position method if we want to use custom positions*/
    public void setPos(int pos) {
        //Sets the target position of the motors to the current position plus the newly calculated position.
        //This lets us only reset the encoders once (This is executed wrong but we did not have time to fix it)
        driveFrontRight.setTargetPosition(pos + driveFrontRight.getCurrentPosition());
        driveFrontLeft.setTargetPosition(pos + driveFrontLeft.getCurrentPosition());
        driveBackRight.setTargetPosition(pos + driveBackRight.getCurrentPosition());
        driveBackLeft.setTargetPosition(pos + driveBackLeft.getCurrentPosition());
    }

    //Sets the robot drive motors to run to positions instead of moving freely
    public void runToPos() {
        driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /* A safety to make sure that the motors stop moving while we are using encoders */
    public void waitForDriveMotorStop() {
        while (true) {                              //while the motors are moving check if one of the drive motors are busy
            if (!(driveFrontLeft.isBusy())) break;  //Once it is not busy end to the loop and continue on with the code
        }
    }

    /* method that we can call to rotate left a certain number of degrees */
    public void rotLeftDeg(double degrees, double powerStart) {
        setRotLeft();
        posDeg(degrees);
        runToPos();
        setPower(powerStart);
        waitForDriveMotorStop();
        setPower(0);
    }
    /* method that we can call to move forward a certain number of inches */
    public void forwardINCH(double posInINCH, double powerStart) {
        setDriveForward();
        posInch(posInINCH);
        setPower(powerStart);
        waitForDriveMotorStop();
        setPower(0);
    }
    /* method that we can call to rotate right a certain number of degrees */
    public void rotRightDeg(double degrees, double powerStart) {
        setRotRight();
        posDeg(degrees);
        runToPos();
        setPower(powerStart);
        waitForDriveMotorStop();
        setPower(0);
    }
    /* method that we can call to move backwards a certain number of inches */
    public void backwardINCH(double posInINCH, double powerStart) {
        setDriveBackward();
        posInch(posInINCH);
        runToPos();
        setPower(powerStart);
        waitForDriveMotorStop();
        setPower(0);
    }

    /* Method that we use to open the glyph grab arms */
    public void openArms() {
        glyphGrabLeft.setPosition(1);
        glyphGrabRight.setPosition(0);
    }

    /* Method that we use to close the glyph grab arms */
    public void closeArms() {
        glyphGrabLeft.setPosition(0.7);
        glyphGrabRight.setPosition(0.3);
    }
    /*Method that we use to set the jewel knocker to the down position */
    public void jewelDown() {
        jewelKnocker.setPosition(0.75);
    }
    /*Method that we use to set the jewel knocker to the up position */
    public void jewelUp() {
        jewelKnocker.setPosition(0);
    }

    //Sets the motors to drive forward
    public void setDriveForward() {
        driveFrontRight.setDirection(DcMotor.Direction.FORWARD);
        driveFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        driveBackRight.setDirection(DcMotor.Direction.FORWARD);
        driveBackLeft.setDirection(DcMotor.Direction.REVERSE);
    }
    //sets the motors to drive backward
    public void setDriveBackward() {
        driveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        driveFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        driveBackRight.setDirection(DcMotor.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotor.Direction.FORWARD);
    }
    //sets the motors to rotate left
    public void setRotLeft() {
        driveFrontRight.setDirection(DcMotor.Direction.FORWARD);
        driveFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        driveBackRight.setDirection(DcMotor.Direction.FORWARD);
        driveBackLeft.setDirection(DcMotor.Direction.FORWARD);
    }
    //sets the motors to rotate right
    public void setRotRight() {
        driveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        driveFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        driveBackRight.setDirection(DcMotor.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    //sets the power of all the motors
    public void setPower(double power) {
        driveFrontRight.setPower(power);
        driveFrontLeft.setPower(power);
        driveBackRight.setPower(power);
        driveBackLeft.setPower(power);
    }

    //A method that is in testing to simplify the knocking off of the jewels
    public void knockJewelOff(String whichJewel, int motorPosDeg, double motorPower) {
        if (whichJewel.equals("LEFT")) {
            rotLeftDeg(motorPosDeg, motorPower);
            jewelUp();
        }
        else if (whichJewel.equals("RIGHT")) {
            rotRightDeg(motorPosDeg, motorPower);
            jewelUp();
        }
    }
}
