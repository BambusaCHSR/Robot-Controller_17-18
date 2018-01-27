package org.firstinspires.ftc.teamcode.definitions;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.MotorConfiguration;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by ELijah Sauder for Bambusa in Robot-Controller_17-18 on 11/11/17.
 **/

public class Initialization {

    private DrivingDef driving = new DrivingDef();
    /**
     * ---------------
     * initializations
     * ---------------
     */
    /* Tells the program what to call the motors whenever you use them (call them) in the code. */
    //initializes the Drive Motors.
    public DcMotor driveFrontRight = null;
    public DcMotor driveFrontLeft = null;
    public DcMotor driveBackRight = null;
    public DcMotor driveBackLeft = null;

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
    public ColorSensor teamColor = null;

    public VuforiaLocalizer vuforia;
    // hacked by theo
    private int cameraMonitorViewId;

    public void hardwareMapInit(HardwareMap Map) {

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
        //teamColor = Map.colorSensor.get("teamColor");

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

    public void encoderInit() {
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        driving.resetEncoders();
    }

    public void cLed(boolean cLed) {
        jewelColor.enableLed(cLed);
        teamColor.enableLed(cLed);
    }

    public void closeArms() {
        glyphGrabLeft.setPosition(0.7);
        glyphGrabRight.setPosition(0.3);
    }
    public void openArms() {
        glyphGrabLeft.setPosition(1);
        glyphGrabRight.setPosition(0);
    }

    public void knockJewelOff(String whichJewel, int motorPos, double servoPos, double motorPower) {
        if (whichJewel.equals("LEFT")) {
            driving.setRotLeft();
            driving.setPos(motorPos);
            driving.runToPos();
            driving.setPower(motorPower);
            driving.waitForDriveMotorStop();
            driving.setPower(0);
            jewels.setPosition(servoPos);
        } else if (whichJewel.equals("RIGHT")) {
            driving.setRotRight();
            driving.setPos(motorPos);
            driving.runToPos();
            driving.setPower(motorPower);
            driving.waitForDriveMotorStop();
            driving.setPower(0);
            jewels.setPosition(servoPos);
        }
    }

    /*public void intmotor(AngleUnit testBR, AngleUnit testBL, AngleUnit testFR, AngleUnit testFL) {
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
    }*/
}

