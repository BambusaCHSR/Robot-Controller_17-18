package org.firstinspires.ftc.teamcode.definitions;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Hardware;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/26/2018.7:48 PM.
 */

public class Initilization {

    public DcMotorEx driveFrontRight = null;
    public DcMotorEx driveFrontLeft = null;
    public DcMotorEx driveBackRight = null;
    public DcMotorEx driveBackLeft = null;

    public DcMotorEx lift = null;

    public DcMotorEx relicGrabRot = null;

    public Servo glyphGrabRight = null;
    public Servo glyphGrabLeft = null;

    public Servo jewelKnocker = null;
    public Servo relicGrab = null;

    public ColorSensor jewelColor = null;
    public ColorSensor teamColor = null;

    public VuforiaLocalizer vuforia = null;


    private int cameraMonitorViewId;

    public void hardwareMapInit(HardwareMap Map) {
        driveFrontRight = (DcMotorEx)Map.dcMotor.get("driveFrontRight");
        driveFrontLeft = (DcMotorEx)Map.dcMotor.get("driveFrontLeft");
        driveBackRight = (DcMotorEx)Map.dcMotor.get("driveBackRight");
        driveBackLeft = (DcMotorEx)Map.dcMotor.get("driveBackLeft");

        lift = (DcMotorEx)Map.dcMotor.get("lift"); //make sure to change robot config from winch to lift

        glyphGrabRight = Map.servo.get("glyphGrabRight");
        glyphGrabLeft = Map.servo.get("glyphGrabLeft");

        jewelKnocker = Map.servo.get("jewelKnocker"); //make sure to change robot config from jewels to jewelKnocker
    }
}
