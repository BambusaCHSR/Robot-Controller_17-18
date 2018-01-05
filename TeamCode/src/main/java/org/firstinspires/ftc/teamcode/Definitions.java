package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.MotorConfiguration;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.Objects;

/**
 * Created by Bambusa on 11/11/17.
 **/

class Definitions {
    /**
     * ---------------
     * initializations
     * ---------------
     */
    /* Tells the program what to call the motors whenever you use them (call them) in the code. */

    //initializes the Drive Motors.
    DcMotor driveFrontRight = null;
    DcMotor driveFrontLeft = null;
    DcMotor driveBackRight = null;
    DcMotor driveBackLeft = null;

    MotorConfiguration armLower = null;
    //initializes Relic arm motors.
    DcMotor armLower1 = null;
    DcMotor armLower2 = null;
    DcMotor armUpper = null;

    //initializes the glyph lifter motor.
    DcMotor lift = null;

    //initializes servos for grabbing the glyphs.
    Servo glyphGrabLeft = null;
    Servo glyphGrabRight = null;

    //initializes servo for hitting the jewel in autonomous.
    Servo jewels = null;

    //initializes sensor for detecting the jewel color
    ColorSensor jewelColor = null;

    VuforiaLocalizer vuforia;

    private int cameraMonitorViewId;

    private waitForDriveMotors wait1 = new waitForDriveMotors();


    void init(HardwareMap Map) {

        /** -----------
         * Hardware Map
         * ------------*/
        /* Lets the app communicate (See) the motors, servos, and sensors from the robot configuration */

        //Drive Motors.
        driveFrontRight = Map.dcMotor.get("driveFrontRight");
        driveFrontLeft = Map.dcMotor.get("driveFrontLeft");
        driveBackRight = Map.dcMotor.get("driveBackRight");
        driveBackLeft = Map.dcMotor.get("driveBackLeft");

        //Relic arm Motors.
        armLower1 = Map.dcMotor.get("armLower1");
        armLower2 = Map.dcMotor.get("armLower2");
        armUpper = Map.dcMotor.get("armUpper");

        //Glyph lifter Motors.
        lift = Map.dcMotor.get("winch");

        //Servos for grabbing Glyphs.
        glyphGrabLeft = Map.servo.get("glyphGrabLeft");
        glyphGrabRight = Map.servo.get("glyphGrabRight");

        //Servo for hitting the jewel off in autonomous.
        jewels = Map.servo.get("jewels");

        //sensor for detecting the jewel color.
        jewelColor = Map.colorSensor.get("jewelColor");

        cameraMonitorViewId = Map.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", Map.appContext.getPackageName());
    }

    //function used to set the default servo positions for all the servos.
    void servoInit() {
        //initialize servo positions
        glyphGrabLeft.setPosition(1);
        glyphGrabRight.setPosition(0);
        jewels.setPosition(0.96);
    }

    void vuforiaInit() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AS/00FX/////AAAAGRywycu7JEUJh4rz/rwORDkZJabbBKzxMuMKtBDf4H4wwDJtDTdfM4a0JXDJiPhDkK9SUSl9j8qDz8nbFunZwugJW7SdZvehZLe9J7Irk1Eu6w4+gmSEIvRBSxite8W+7xjTTtLyvMgxKbMacL8k0uuhfFJzwTS3qZ5FCm8HP7F8jqXgH5e2LLcRyJ+MYdNqPPx7wpISBVFY3rYGqxFJgAs1S1dReiziGOeyckCt1pcJXA6IroX5mgAVNwLWx9Wafoe3jd3dNdD+dxSep2E1MhmcA+WIcKIZ9615uMPfon5M7fyzjB6CFu5srJTpcgoVPbbr6LAZt7WiNPLLCuoFNx246V29fO1DeiTErTCbuL/Q";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
    }

    void setDriveForward() {
        driveFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        driveFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    void setDriveBackward() {
        driveFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    void setRotLeft() {
        driveFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        driveFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    void setRotRight() {
        driveFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setPower(double power) {
        driveFrontRight.setPower(power);
        driveFrontLeft.setPower(power);
        driveBackRight.setPower(power);
        driveBackLeft.setPower(power);
    }

    void cLed(boolean cLed) {
        jewelColor.enableLed(cLed);
    }

    void encoderInit() {
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    void setPos(int pos) {
        driveFrontRight.setTargetPosition(pos);
        driveFrontLeft.setTargetPosition(pos);
        driveBackRight.setTargetPosition(pos);
        driveBackLeft.setTargetPosition(pos);
    }

    void runToPos() {
        driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    void resetEncoders() {
        driveFrontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveFrontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveBackLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveBackRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }

    /*void waitForDriveMotorStop() {
        while (true) {
            if (!(driveFrontLeft.isBusy())) break;
        }
    }*/

    void waitDriveStop() {
        wait1.start();
    }

    void knockJewelOff(String whichJewel, int motorPos, double servoPos, double motorPower) {
        if (whichJewel.equals("LEFT")) {
            setRotLeft();
            resetEncoders();
            setPos(motorPos);
            runToPos();
            setPower(motorPower);
            waitDriveStop();
            setPower(0);
            jewels.setPosition(servoPos);
        }
        else if (whichJewel.equals("RIGHT")) {
            setRotRight();
            resetEncoders();
            setPos(motorPos);
            runToPos();
            setPower(motorPower);
            waitDriveStop();
            setPower(0);
            jewels.setPosition(servoPos);
        }
    }

}

class waitForDriveMotors extends Thread {
    private Definitions robot = new Definitions();

    @Override
    public void run() {
        while (robot.driveFrontLeft.isBusy() && robot.driveFrontRight.isBusy() && robot.driveBackLeft.isBusy() && robot.driveBackRight.isBusy()) {
            if (!robot.driveFrontLeft.isBusy() && !robot.driveFrontRight.isBusy() && robot.driveBackLeft.isBusy() && robot.driveBackRight.isBusy()) break;
        }
    }
}

