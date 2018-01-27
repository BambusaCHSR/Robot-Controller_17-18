package org.firstinspires.ftc.teamcode.definitions;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/26/2018.7:48 PM.
 **/

public class Initialization {
    private EncodersDef encoders = new EncodersDef();

    public DcMotor driveFrontRight = null;
    public DcMotor driveFrontLeft = null;
    public DcMotor driveBackRight = null;
    public DcMotor driveBackLeft = null;

    public DcMotor lift = null;
    public DcMotor relicGrabRot = null;

    public Servo glyphGrabRight = null;
    public Servo glyphGrabLeft = null;

    public Servo jewelKnocker = null;
    public Servo relicGrab = null;

    public ColorSensor jewelColor = null;
    public ColorSensor teamColor = null;

    public VuforiaLocalizer vuforia = null;


    private int cameraMonitorViewId;

    public void hardwareMapInit(HardwareMap Map) {
        driveFrontRight = Map.dcMotor.get("driveFrontRight");
        driveFrontLeft = Map.dcMotor.get("driveFrontLeft");
        driveBackRight = Map.dcMotor.get("driveBackRight");
        driveBackLeft = Map.dcMotor.get("driveBackLeft");

        lift = Map.dcMotor.get("lift"); //make sure to change robot config from winch to lift
        relicGrabRot = Map.dcMotor.get("relicGrabRot");

        glyphGrabRight = Map.servo.get("glyphGrabRight");
        glyphGrabLeft = Map.servo.get("glyphGrabLeft");

        jewelKnocker = Map.servo.get("jewelKnocker"); //make sure to change robot config from jewels to jewelKnocker

        jewelColor = Map.colorSensor.get("jewelColor");

        cameraMonitorViewId = Map.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", Map.appContext.getPackageName());
    }

    public void servoInit() {
        glyphGrabRight.setPosition(1);
        glyphGrabLeft.setPosition(0);

        jewelKnocker.setPosition(0.96);
    }

    public void vuforiaInit() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AS/00FX/////AAAAGRywycu7JEUJh4rz/rwORDkZJabbBKzxMuMKtBDf4H4wwDJtDTdfM4a0JXDJiPhDkK9SUSl9j8qDz8nbFunZwugJW7SdZvehZLe9J7Irk1Eu6w4+gmSEIvRBSxite8W+7xjTTtLyvMgxKbMacL8k0uuhfFJzwTS3qZ5FCm8HP7F8jqXgH5e2LLcRyJ+MYdNqPPx7wpISBVFY3rYGqxFJgAs1S1dReiziGOeyckCt1pcJXA6IroX5mgAVNwLWx9Wafoe3jd3dNdD+dxSep2E1MhmcA+WIcKIZ9615uMPfon5M7fyzjB6CFu5srJTpcgoVPbbr6LAZt7WiNPLLCuoFNx246V29fO1DeiTErTCbuL/Q";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
    }

    public void encoderInit() {
        encoders.useEncoders();
        encoders.resetEncoders();
    }
}