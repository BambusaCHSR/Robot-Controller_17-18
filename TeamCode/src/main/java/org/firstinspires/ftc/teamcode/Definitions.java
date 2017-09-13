package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Elijah Sauder on 1/20/17.
 **/

/* Creates the definition java file (Class) */
public class Definitions {
    
    /** Initiates all the motors, servos, and sensors **/
    /*Initiates all the motors */
    //Initiates the drive motors
    DcMotor motorDriveFrontRight;
    DcMotor motorDriveFrontLeft;
    DcMotor motorDriveBackRight;
    DcMotor motorDriveBackLeft;

    //-----------------------------------------------//
    
    /* Initiates all the servos */
    //initiates the button pressing servos
    Servo servoJewelHitterExtender;
    Servo servoJewelHitter;

    /* Initiates all the sensors */
    //Initiates the color sensors
    ColorSensor sensorJewelColor;

    //initiates the distance sensors
    UltrasonicSensor sensorDistanceArm;
    
    //initiates the gyro sensor
    GyroSensor sensorGyro;

    //===============================================//
    
    /** Initiates variables **/
    //Initiates local variables that are used in TeleOp
    float x, y, r, m, n;

    //===============================================//

    /** creates all the defined actions called upon in the other programs **/
    /* Creates a hardware map */
    public void init(HardwareMap Map) {
        /** Initiates all the motor, servo, and sensor names **/
        /* Initiates all the motors */
        //initiates the drive motor names
        motorDriveFrontRight = Map.dcMotor.get("motorDriveFrontRight");
        motorDriveFrontLeft = Map.dcMotor.get("motorDriveFrontLeft");
        motorDriveBackRight = Map.dcMotor.get("motorDriveBackRight");
        motorDriveBackLeft = Map.dcMotor.get("motorDriveBackLeft");

        //--------------------------------------------------------//

        /*Initiates the names of all the servos*/
        //sets name for Servos
        servoJewelHitterExtender = Map.servo.get("jewelHitterExtender");
        servoJewelHitter = Map.servo.get("JewelHitter");

        //--------------------------------------------------------//

        /*Initiates the names of all the sensors*/
        //Initiates the names of the color sensors
        sensorJewelColor = Map.colorSensor.get("sensorJewelColor");

        //initiates the names of the distance sensors
        sensorDistanceArm = Map.ultrasonicSensor.get("sensorDistanceArm");

        //initiates the names of the gyro sensors
        sensorGyro = Map.gyroSensor.get("sensorGyro");
    }

    /*-----------------------------------------------------------------------*/
    /* ********************** */ /**Autonomous**/ /* *********************** */
    /*-----------------------------------------------------------------------*/

    /**
     * Movement Directions
     **/
    void setDriveForward() {
        motorDriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorDriveFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        motorDriveBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorDriveBackLeft.setDirection(DcMotor.Direction.FORWARD);

    }

    void setDriveBackward() {
        motorDriveFrontRight.setDirection(DcMotor.Direction.FORWARD);
        motorDriveFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorDriveBackRight.setDirection(DcMotor.Direction.FORWARD);
        motorDriveBackLeft.setDirection(DcMotor.Direction.REVERSE);

    }

    void setDriveRight() {
        motorDriveFrontRight.setDirection(DcMotor.Direction.FORWARD);
        motorDriveFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        motorDriveBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorDriveBackLeft.setDirection(DcMotor.Direction.REVERSE);

    }

    void setDriveLeft() {
        motorDriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorDriveFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorDriveBackRight.setDirection(DcMotor.Direction.FORWARD);
        motorDriveBackLeft.setDirection(DcMotor.Direction.FORWARD);

    }

    /**
     * Rotate
     **/
    void setDriveRotateLeft() {
        motorDriveFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorDriveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorDriveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorDriveBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void setDriveRotateRight() {
        motorDriveFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorDriveBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorDriveFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorDriveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    /**
     * restart encoders
     **/
    void restartDriveEncoders() {
        motorDriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorDriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorDriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorDriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    /**
     * movement distance
     **/
    void setDriveDistance(int distance) {
        motorDriveFrontLeft.setTargetPosition(distance);
        motorDriveFrontRight.setTargetPosition(distance);
        motorDriveBackLeft.setTargetPosition(distance);
        motorDriveBackRight.setTargetPosition(distance);

    }

    /**
     * run to position
     **/
    void runToPosition() {
        motorDriveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorDriveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorDriveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorDriveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * drive speed
     **/
    void setPower(double power) {
        motorDriveFrontLeft.setPower(Range.clip(power,-1.0,1.0));
        motorDriveFrontRight.setPower(Range.clip(power,-1.0,1.0));
        motorDriveBackLeft.setPower(Range.clip(power,-1.0,1.0));
        motorDriveBackRight.setPower(Range.clip(power,-1.0,1.0));
    }

    /**
     * wait
     **/

    void waitForDriveMotorStop() {
        while (true) {
            if (!(motorDriveFrontLeft.isBusy())) break;
        }
    }

    void driveForwardAndOrBack(int distance, double power) {
        setDriveForward();
        restartDriveEncoders();
        setDriveDistance(distance);
        runToPosition();
        setPower(Range.clip(power,-1.0,1.0));
        waitForDriveMotorStop();
        setPower(0);
    }
}


