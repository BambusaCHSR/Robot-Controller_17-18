package org.firstinspires.ftc.teamcode.definitions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/19/2018.3:14 PM.
 **/

public class DrivingDef extends Initialization {

    private int motorTicks = 1120;
    private double wheelDiamater = 4;


    public void resetEncoders() {
        driveFrontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveFrontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveBackLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        driveBackRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
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
    }

    public void posInch(double pos) {
        double toInch = motorTicks/(wheelDiamater*Math.PI);

        int poss = (int) (pos*toInch);
        driveFrontRight.setTargetPosition(poss + driveFrontRight.getCurrentPosition());
        driveFrontLeft.setTargetPosition(poss + driveFrontLeft.getCurrentPosition());
        driveBackRight.setTargetPosition(poss + driveBackRight.getCurrentPosition());
        driveBackLeft.setTargetPosition(poss + driveBackLeft.getCurrentPosition());
    }
    public void posDeg(double degreesOfTurning) {
        int T = motorTicks; //Encoder ticks per revolution of the motor
        double Dr = 15.25; //The diagonal between two of the wheels of a 4 wheeled robot.
        double Dw = wheelDiamater; //The diameter of the wheels of the robot.

        int poss = (int) (((Dr/Dw)*(T/360))+0.5);

        driveFrontRight.setTargetPosition(poss + driveFrontRight.getCurrentPosition());
        driveFrontLeft.setTargetPosition(poss + driveFrontLeft.getCurrentPosition());
        driveBackRight.setTargetPosition(poss + driveBackRight.getCurrentPosition());
        driveBackLeft.setTargetPosition(poss + driveBackLeft.getCurrentPosition());
    }

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
}
