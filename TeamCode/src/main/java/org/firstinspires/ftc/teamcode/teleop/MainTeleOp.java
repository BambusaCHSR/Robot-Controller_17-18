package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.definitions.Initialization;
import org.firstinspires.ftc.teamcode.definitions.Movements;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/26/2018.7:49 PM.
 */

@TeleOp(name="main", group="bambusa")
public class MainTeleOp extends LinearOpMode {

    private Movements move = new Movements();

    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Pre-initialization");
        telemetry.update();

        boolean STOP = false;
        boolean toggle = false;
        boolean servos = true;

        move.hardwareMapInit(hardwareMap);
        move.servoInit();

        move.jewelColor.enableLed(true);
        move.teamColor.enableLed(false);

        waitForStart();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        runtime.reset();

        while (opModeIsActive() && !STOP) {

        }
    }
}
