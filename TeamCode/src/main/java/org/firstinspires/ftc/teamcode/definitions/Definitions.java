package org.firstinspires.ftc.teamcode.definitions;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.MotorConfiguration;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Bambusa on 11/11/17.
 **/

public class Definitions {
    /**
     * ---------------
     * initializations
     * ---------------
     */
    /* Tells the program what to call the motors whenever you use them (call them) in the code. */

    //initializes the Drive Motors.
    public DcMotorEx driveFrontRight = null;
    public DcMotorEx driveFrontLeft = null;
    public DcMotorEx driveBackRight = null;
    public DcMotorEx driveBackLeft = null;

    public MotorConfiguration armLower = null;
    //initializes Relic arm motors.
    public DcMotor armLower1 = null;
    public DcMotor armLower2 = null;
    public DcMotor armUpper = null;

    //initializes the glyph lifter motor.
    public DcMotor lift = null;

    //initializes servos for grabbing the glyphs.
    public Servo glyphGrabLeft = null;
    public Servo glyphGrabRight = null;

    //initializes servo for hitting the jewel in autonomous.
    public Servo jewels = null;

    //initializes sensor for detecting the jewel color
    public ColorSensor jewelColor = null;

    public VuforiaLocalizer vuforia;

    private int cameraMonitorViewId;

    public void init(HardwareMap Map) {

        /** -----------
         * Hardware Map
         * ------------*/
        /* Lets the app communicate (See) the motors, servos, and sensors from the robot configuration */

        //Drive Motors.
        driveFrontRight = (DcMotorEx)Map.dcMotor.get("driveFrontRight");
        driveFrontLeft = (DcMotorEx)Map.dcMotor.get("driveFrontLeft");
        driveBackRight = (DcMotorEx) Map.get("driveBackRight");
        driveBackLeft = (DcMotorEx)Map.dcMotor.get("driveBackLeft");

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
    public void servoInit() {
        //initialize servo positions
        glyphGrabLeft.setPosition(1);
        glyphGrabRight.setPosition(0);
        jewels.setPosition(0.96);
    }

    public void vuforiaInit() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AS/00FX/////AAAAGRywycu7JEUJh4rz/rwORDkZJabbBKzxMuMKtBDf4H4wwDJtDTdfM4a0JXDJiPhDkK9SUSl9j8qDz8nbFunZwugJW7SdZvehZLe9J7Irk1Eu6w4+gmSEIvRBSxite8W+7xjTTtLyvMgxKbMacL8k0uuhfFJzwTS3qZ5FCm8HP7F8jqXgH5e2LLcRyJ+MYdNqPPx7wpISBVFY3rYGqxFJgAs1S1dReiziGOeyckCt1pcJXA6IroX5mgAVNwLWx9Wafoe3jd3dNdD+dxSep2E1MhmcA+WIcKIZ9615uMPfon5M7fyzjB6CFu5srJTpcgoVPbbr6LAZt7WiNPLLCuoFNx246V29fO1DeiTErTCbuL/Q";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
    }

    public void setDriveForward() {
        driveFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        driveFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setDriveBackward() {
        driveFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setRotLeft() {
        driveFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        driveFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setRotRight() {
        driveFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPower(double power) {
        driveFrontRight.setPower(power);
        driveFrontLeft.setPower(power);
        driveBackRight.setPower(power);
        driveBackLeft.setPower(power);
    }

    public void cLed(boolean cLed) {
        jewelColor.enableLed(cLed);
    }

    public void encoderInit() {
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setPos(int pos) {
        double DFRpos = driveFrontRight.getCurrentPosition() + pos;
        double DFLpos = driveFrontLeft.getCurrentPosition() + pos;
        double DBRpos = driveBackRight.getCurrentPosition() + pos;
        double DBLpos = driveBackLeft.getCurrentPosition() + pos;
        driveFrontRight.setTargetPosition(pos);
        driveFrontLeft.setTargetPosition(pos);
        driveBackRight.setTargetPosition(pos);
        driveBackLeft.setTargetPosition(pos);
    }

    public void runToPos() {
        driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void resetEncoders() {
        driveFrontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveFrontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveBackLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveBackRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }

    public void waitForDriveMotorStop() {
        while (true) {
            if (!(driveFrontLeft.isBusy())) break;
        }
    }

    public void knockJewelOff(String whichJewel, int motorPos, double servoPos, double motorPower) {
        if (whichJewel.equals("LEFT")) {
            setRotLeft();
            resetEncoders();
            setPos(motorPos);
            runToPos();
            setPower(motorPower);
            waitForDriveMotorStop();
            setPower(0);
            jewels.setPosition(servoPos);
        } else if (whichJewel.equals("RIGHT")) {
            setRotRight();
            resetEncoders();
            setPos(motorPos);
            runToPos();
            setPower(motorPower);
            waitForDriveMotorStop();
            setPower(0);
            jewels.setPosition(servoPos);
        }
    }

    public void intmotor(AngleUnit testBR, AngleUnit testBL, AngleUnit testFR, AngleUnit testFL) {
        driveFrontLeft.setMotorEnable();
        driveFrontRight.setMotorEnable();
        driveBackLeft.setMotorEnable();
        driveBackRight.setMotorEnable();
        boolean intt = false;
        while(!intt) {
            if (driveBackRight.getVelocity(testBR) == 0 && driveBackLeft.getVelocity(testBL) == 0
                    && driveFrontRight.getVelocity(testFR) == 0 && driveFrontLeft.getVelocity(testFL) == 0) {
                setPower(0);
                resetEncoders();
                intt=true;
            }
            else {
                setPower(0.05);
                intt=false;
            }
        }
    }
}

