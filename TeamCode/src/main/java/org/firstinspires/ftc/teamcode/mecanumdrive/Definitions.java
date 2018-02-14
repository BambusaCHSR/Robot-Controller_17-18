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

    private int WD = 0;
    private double RD = 0;
    private int MT = 0;
    private int motors = 0;
    private boolean hardwareMapInt = false;

    public void mecanumOmniMap(HardwareMap Map) {
        frontLeft = Map.dcMotor.get("frontLeft");
        frontRight = Map.dcMotor.get("frontRight");
        backLeft = Map.dcMotor.get("backLeft");
        backRight = Map.dcMotor.get("backRight");

        motors = 4;
        hardwareMapInt = true;
    }

    public void tankDifferentialMap(HardwareMap Map) {
        driveLeft = Map.dcMotor.get("driveLeft");
        driveRight = Map.dcMotor.get("driveRight");

        motors = 2;
        hardwareMapInt = true;
    }

    public void robotDetails(int wheelDiamater, double robotDiamater, int motorTicks) {
        WD = wheelDiamater;
        RD = robotDiamater;
        MT = motorTicks;
    }

    public void resetDriveEncoders() {
        if (motors == 4) {
            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        else if (motors == 2) {
            driveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            driveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        else {
            throw new IllegalArgumentException("No hardware map detected");
        }
    }

    public void runWithoutEncoders() {
        if (motors == 4) {
            frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        else if (motors == 2) {
            driveLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        else {
            throw new IllegalArgumentException("No hardware map detected");
        }
    }

    public void runToPosition() {
        if (motors == 4) {
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else if (motors == 2) {
            driveLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            driveRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else {
            throw new IllegalArgumentException("No hardware map detected");
        }
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

        if (!hardwareMapInt) {
            throw new IllegalArgumentException("No hardware map detected");
        }
    }

    void omniDrive(double x, double y, double r) {
        frontLeft.setPower(y + x + r);
        frontRight.setPower(-y + x + r);
        backLeft.setPower(y - x + r);
        backRight.setPower(-y - x + r);
    }
    void tankDrive(double Left, double Right) {
        driveLeft.setPower(Left);
        driveRight.setPower(Right);
    }

    public void arcadeDrive(double x, double r) {
        driveLeft.setPower(x + r);
        driveRight.setPower(-x + r);
    }

    public void setpower(double power) {
        if (motors == 4) {
            frontLeft.setPower(power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(power);
        }
        else if (motors == 2) {
            driveLeft.setPower(power);
            driveRight.setPower(power);
        }
    }

    public void moveMecanum(String direction, double power, double positionInch) {
        int pos = (int) (positionInch*(MT/(WD*Math.PI)));
        if (direction.equals("FORWARD")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() - pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() - pos);
        }
        else if (direction.equals("BACKWARD")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() + pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() - pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() + pos);
        }
        else if (direction.equals("LEFT")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() + pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() - pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() - pos);
        }
        else if (direction.equals("RIGHT")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() - pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() + pos);
        }

        setpower(power);
    }
    public void moveMecanum(String direction, double positionInch) {
        this.moveMecanum(direction,0.5,positionInch);
    }

    public void moveOmniWheel(String direction, double power, double positionInch) {
        int pos = (int) (Math.sqrt(2) * (positionInch*(MT/(WD*Math.PI))));
        if (direction.equals("FORWARD")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() - pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() - pos);
        }
        else if (direction.equals("BACKWARD")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() + pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() - pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() + pos);
        }
        else if (direction.equals("LEFT")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() - pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() + pos);
        }
        else if (direction.equals("RIGHT")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() + pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() - pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() - pos);
        }

        setpower(power);
    }
    public void moveOmniWheel(String direction, double positionInch) {
        this.moveOmniWheel(direction,0.5,positionInch);
    }

    public void moveTankArcade(String direction, double power, double positionInch) {
        int pos = (int) (positionInch * (MT / (WD * Math.PI)));
        if (direction.equals("FOWARD")) {
            driveLeft.setTargetPosition(driveLeft.getCurrentPosition() + pos);
            driveLeft.setTargetPosition(driveLeft.getCurrentPosition() - pos);
        }
        else if (direction.equals("BACKWARD")) {
            driveLeft.setTargetPosition(driveLeft.getCurrentPosition() - pos);
            driveLeft.setTargetPosition(driveLeft.getCurrentPosition() + pos);
        }

        setpower(power);
    }
    public void moveTankArcade(String direction, double positionInch) {
        this.moveTankArcade(direction,0.5,positionInch);
    }

    public void rotateOmniMecanum(String direction, double power, double positionDegrees) {
        int pos = (int) ((RD / WD) * (MT / 360) * positionDegrees);
        if (direction.equals("LEFT")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() - pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() - pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() - pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() - pos);
        }
        else if (direction.equals("RIGHT")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + pos);
            frontRight.setTargetPosition(frontRight.getCurrentPosition() + pos);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + pos);
            backRight.setTargetPosition(backRight.getCurrentPosition() + pos);
        }

        setpower(power);
    }
    public void rotateOmniMecanum(String direction, double positionInch) {
        this.rotateOmniMecanum(direction, 0.5, positionInch);
    }

    public void rotateTankArcade(String direction, double power, double positionDegrees) {
        int pos = (int) ((RD / WD) * (MT / 360) * positionDegrees);
        if (direction.equals("LEFT")) {
            driveLeft.setTargetPosition(driveLeft.getCurrentPosition() - pos);
            driveLeft.setTargetPosition(driveLeft.getCurrentPosition() - pos);
        }
        else if (direction.equals("RIGHT")) {
            driveLeft.setTargetPosition(driveLeft.getCurrentPosition() + pos);
            driveLeft.setTargetPosition(driveLeft.getCurrentPosition() + pos);
        }

        setpower(power);
    }
    public void rotateTankArcade(String direction, double positionInch) {
        this.rotateTankArcade(direction,0.5,positionInch);
    }

    public void waitForDriveMotorsStop() {
        while (true) {
            if (motors == 4) {
                if (!(frontLeft.isBusy()) && !(frontRight.isBusy()) && !(backLeft.isBusy()) && !(backRight.isBusy())) break;
            }
            else if (motors == 2) {
                if (!(driveLeft.isBusy()) && !(driveRight.isBusy())) break;
            }
        }
    }
}
