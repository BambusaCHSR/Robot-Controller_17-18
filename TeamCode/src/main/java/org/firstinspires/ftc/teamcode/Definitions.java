package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Elijah Sauder for Bambusa in ftc_app, on 01/27/2018.12:10 AM.
 */

public class Definitions {

    private int motorTicks = 1120;
    private double wheelDiamater = 4;

    DcMotor driveFrontRight = null;
    DcMotor driveFrontLeft = null;
    DcMotor driveBackRight = null;
    DcMotor driveBackLeft = null;

    DcMotor lift = null;
    DcMotor relicGrabRot = null;

    Servo glyphGrabRight = null;
    Servo glyphGrabLeft = null;

    Servo jewelKnocker = null;
    public Servo relicGrab = null;

    ColorSensor jewelColor = null;
    ColorSensor teamColor = null;

    VuforiaLocalizer vuforia = null;


    private int cameraMonitorViewId;

    public void hardwareMapInit(HardwareMap Map) {
        driveFrontRight = Map.dcMotor.get("driveFrontRight");
        driveFrontLeft = Map.dcMotor.get("driveFrontLeft");
        driveBackRight = Map.dcMotor.get("driveRightBack");
        driveBackLeft = Map.dcMotor.get("driveBackLeft");

        lift = Map.dcMotor.get("lift"); //make sure to change robot config from winch to lift
        relicGrabRot = Map.dcMotor.get("relicGrabRot");

        glyphGrabRight = Map.servo.get("glyphGrabRight");
        glyphGrabLeft = Map.servo.get("glyphGrabLeft");

        jewelKnocker = Map.servo.get("jewelKnocker"); //make sure to change robot config from jewels to jewelKnocker

        jewelColor = Map.colorSensor.get("jewelColor");
        teamColor = Map.colorSensor.get("teamColor");

        cameraMonitorViewId = Map.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", Map.appContext.getPackageName());
    }

    public void servoInit() {
        glyphGrabRight.setPosition(1);
        glyphGrabLeft.setPosition(0);

        //glyphGrabLeft.setPosition(0.7);
        //glyphGrabRight.setPosition(0.3);

        //jewelKnocker.setPosition(0.96);
    }

    public void vuforiaInit() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AS/00FX/////AAAAGRywycu7JEUJh4rz/rwORDkZJabbBKzxMuMKtBDf4H4wwDJtDTdfM4a0JXDJiPhDkK9SUSl9j8qDz8nbFunZwugJW7SdZvehZLe9J7Irk1Eu6w4+gmSEIvRBSxite8W+7xjTTtLyvMgxKbMacL8k0uuhfFJzwTS3qZ5FCm8HP7F8jqXgH5e2LLcRyJ+MYdNqPPx7wpISBVFY3rYGqxFJgAs1S1dReiziGOeyckCt1pcJXA6IroX5mgAVNwLWx9Wafoe3jd3dNdD+dxSep2E1MhmcA+WIcKIZ9615uMPfon5M7fyzjB6CFu5srJTpcgoVPbbr6LAZt7WiNPLLCuoFNx246V29fO1DeiTErTCbuL/Q";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
    }

    public void encoderInit() {
        useEncoders();
        resetEncoders();
    }

    public void useEncoders() {
        driveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void resetEncoders() {
        driveFrontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveFrontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveBackRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveBackLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }

    /*public void posInch(double pos) {
        double toInch = motorTicks/(wheelDiamater*Math.PI);

        int poss = (int) (pos*toInch);
        driveFrontRight.setTargetPosition(poss + driveFrontRight.getCurrentPosition());
        driveFrontLeft.setTargetPosition(poss + driveFrontLeft.getCurrentPosition());
        driveBackRight.setTargetPosition(poss + driveBackRight.getCurrentPosition());
        driveBackLeft.setTargetPosition(poss + driveBackLeft.getCurrentPosition());
    }
*/
    public void posDeg(double degreesOfTurning) {
        int T = motorTicks; //Encoder ticks per revolution of the motor
        double Dr = 15.25; //The diagonal between two of the wheels of a 4 wheeled robot.
        double Dw = wheelDiamater; //The diameter of the wheels of the robot.

        int poss = (int) ((Dr/Dw)*(T/360));

        driveFrontRight.setTargetPosition(poss + driveFrontRight.getCurrentPosition());
        driveFrontLeft.setTargetPosition(poss + driveFrontLeft.getCurrentPosition());
        driveBackRight.setTargetPosition(poss + driveBackRight.getCurrentPosition());
        driveBackLeft.setTargetPosition(poss + driveBackLeft.getCurrentPosition());
    }
/*
    public void setPos(int pos) {
        driveFrontRight.setTargetPosition(pos + driveFrontRight.getCurrentPosition());
        driveFrontLeft.setTargetPosition(pos + driveFrontLeft.getCurrentPosition());
        driveBackRight.setTargetPosition(pos + driveBackRight.getCurrentPosition());
        driveBackLeft.setTargetPosition(pos + driveBackLeft.getCurrentPosition());
    }

    public void runToPos() {
        driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void waitForDriveMotorStop() {
        while (true) {
            if (!(driveFrontLeft.isBusy())) break;
        }
    }

    public void rotLeftDeg(double degrees, double powerStart) {
        setRotLeft();
        posDeg(degrees);
        runToPos();
        setPower(powerStart);
        waitForDriveMotorStop();
        setPower(0);
    }
    public void forwardINCH(double posInINCH, double powerStart) {
        setDriveForward();
        posInch(posInINCH);
        runToPos();
        setPower(powerStart);
        waitForDriveMotorStop();
        setPower(0);
    }
    public void rotRightDeg(double degrees, double powerStart) {
        setRotRight();
        posDeg(degrees);
        runToPos();
        setPower(powerStart);
        waitForDriveMotorStop();
        setPower(0);
    }
    public void backwardINCH(double posInINCH, double powerStart) {
        setDriveBackward();
        posInch(posInINCH);
        runToPos();
        setPower(powerStart);
        waitForDriveMotorStop();
        setPower(0);
    }*/

    public void openArms() {
        glyphGrabLeft.setPosition(0);
        glyphGrabRight.setPosition(0.96);
    }

    public void closeArms() {
        glyphGrabLeft.setPosition(0.7);
        glyphGrabRight.setPosition(0.7);
    }

    /*
    public void jewelDown() {
        jewelKnocker.setPosition(0);
    }
    public void jewelUp() {
        jewelKnocker.setPosition(0.96);
    }

    public void setDriveForward() {
        driveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        driveFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        driveBackRight.setDirection(DcMotor.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotor.Direction.FORWARD);
    }
    public void setDriveBackward() {
        driveFrontRight.setDirection(DcMotor.Direction.FORWARD);
        driveFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        driveBackRight.setDirection(DcMotor.Direction.FORWARD);
        driveBackLeft.setDirection(DcMotor.Direction.REVERSE);
    }
    public void setRotLeft() {
        driveFrontRight.setDirection(DcMotor.Direction.FORWARD);
        driveFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        driveBackRight.setDirection(DcMotor.Direction.FORWARD);
        driveBackLeft.setDirection(DcMotor.Direction.FORWARD);
    }
    public void setRotRight() {
        driveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        driveFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        driveBackRight.setDirection(DcMotor.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setPower(double power) {
        driveFrontRight.setPower(power);
        driveFrontLeft.setPower(power);
        driveBackRight.setPower(power);
        driveBackLeft.setPower(power);
    }

    public void knockJewelOff(String whichJewel, int motorPosDeg, double servoPos, double motorPower) {
        if (whichJewel.equals("LEFT")) {
            jewelDown();
            rotLeftDeg(motorPosDeg, motorPower);
            jewelUp();
        }
        else if (whichJewel.equals("RIGHT")) {
            jewelDown();
            rotRightDeg(motorPosDeg, motorPower);
            jewelUp();
        }
    }*/
}
