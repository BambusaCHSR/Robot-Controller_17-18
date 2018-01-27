package org.firstinspires.ftc.teamcode.definitions;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/26/2018.8:18 PM.
 */

public class Movements extends Initilization {

    private EncodersDef encoders = new EncodersDef();

    public void openArms() {
        glyphGrabLeft.setPosition(1);
        glyphGrabRight.setPosition(0);
    }

    public void closeArms() {
        glyphGrabLeft.setPosition(0.7);
        glyphGrabRight.setPosition(0.3);
    }

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
            encoders.rotLeftDeg(motorPosDeg, motorPower);
            jewelUp();
        }
        else if (whichJewel.equals("RIGHT")) {
            jewelDown();
            encoders.rotRightDeg(motorPosDeg, motorPower);
            jewelUp();
        }
    }
}
