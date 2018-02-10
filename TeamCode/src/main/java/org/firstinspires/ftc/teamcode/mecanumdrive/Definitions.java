package org.firstinspires.ftc.teamcode.mecanumdrive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Definitions {

    //Motors for Mecanum and OmniWheel
    public DcMotor frontLeft = null;
    public DcMotor frontRight = null;
    public DcMotor backLeft = null;
    public DcMotor backRight = null;

    public DcMotor driveLeft = null;
    public DcMotor driveRight = null;

    int WD = 0;
    double RD = 0;
    int MT = 0;

    void mecanumOmniMap(HardwareMap Map) {
        frontLeft = Map.dcMotor.get("frontLeft");
        frontRight = Map.dcMotor.get("frontRight");
        backLeft = Map.dcMotor.get("backLeft");
        backRight = Map.dcMotor.get("backRight");
    }

    void tankDifferentialMap(HardwareMap Map) {
        driveLeft = Map.dcMotor.get("left");
        driveRight = Map.dcMotor.get("right");
    }

    void robotDetails(int wheelDiamater, double robotDiamater, int motorTicks) {
        WD = wheelDiamater;
        RD = robotDiamater;
        MT = motorTicks;
    }

    void resetDriveEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        driveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    void runWithoutEncoders() {
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    void runToPosition() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    void teleOpMotorInit() {
        resetDriveEncoders();
        runWithoutEncoders();
    }

    void autonomousMotorInit() {
        resetDriveEncoders();
        runToPosition();
    }

    void mecanumDrive(double x, double y, double r) {
        frontLeft.setPower(y - x + r);
        frontRight.setPower(-y - x + r);
        backLeft.setPower(y + x + r);
        backRight.setPower(-y + x + r);
    }

    void omniDrive(double x, double y, double r) {
        frontLeft.setPower(y + x + r);
        frontRight.setPower(-y + x + r);
        backLeft.setPower(y - x + r);
        backRight.setPower(-y - x + r);
    }
    void tankDriveTwoMotor(double Left, double Right) {
        driveLeft.setPower(Left);
        driveRight.setPower(Right);
    }

    void arcadeDrive(double x, double r) {
        driveLeft.setPower(x + r);
        driveRight.setPower(-x + r);
    }

    void setpower(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);

        driveLeft.setPower(power);
        driveRight.setPower(power);
    }

    void setPositionInch(double inches, double wheelDiamaterInch, int motorTicks) {
        int pos = (int) (inches*(motorTicks/(wheelDiamaterInch*Math.PI)));
    }

    void moveMecanum(String direction, double power, double positionInch) {
        int pos = (int) (positionInch*(MT/(WD*Math.PI)));
        if (direction.equals("FORWARD")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() - pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() - pos);
        }
        else if (direction.equals("BACKWARD")) {

        }
        else if (direction.equals("LEFT")) {

        }
        else if (direction.equals("RIGHT")) {

        }
    }
}
