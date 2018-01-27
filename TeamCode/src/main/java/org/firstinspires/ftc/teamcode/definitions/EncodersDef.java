package org.firstinspires.ftc.teamcode.definitions;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/26/2018.7:49 PM.
 **/

public class EncodersDef extends Initilization {

    private Movements drive = new Movements();

    private int motorTicks = 1120;
    private double wheelDiamater = 4;

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

        int poss = (int) ((Dr/Dw)*(T/360));

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


    public void rotLeftDeg(double degrees, double powerStart) {
        drive.setRotLeft();
        posDeg(degrees);
        runToPos();
        drive.setPower(powerStart);
        waitForDriveMotorStop();
        drive.setPower(0);
    }
    public void forwardINCH(double posInINCH, double powerStart) {
        drive.setDriveForward();
        posInch(posInINCH);
        runToPos();
        drive.setPower(powerStart);
        waitForDriveMotorStop();
        drive.setPower(0);
    }
    public void rotRightDeg(double degrees, double powerStart) {
        drive.setRotRight();
        posDeg(degrees);
        runToPos();
        drive.setPower(powerStart);
        waitForDriveMotorStop();
        drive.setPower(0);
    }
    public void backwardINCH(double posInINCH, double powerStart) {
        drive.setDriveBackward();
        posInch(posInINCH);
        runToPos();
        drive.setPower(powerStart);
        waitForDriveMotorStop();
        drive.setPower(0);
    }
}
