package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Elijah Sauder on 1/4/2018.
 **/
@Autonomous
public class TestAuto extends LinearOpMode {
    
    private drive drive = new drive();
    private sensors sensors = new sensors();
    private accessories excessories = new accessories();
    private Definitions robot = new Definitions();
    
    @Override
    public void runOpMode() throws InterruptedException {
        while(opModeIsActive()) {

        }
    }
}

class drive extends Thread {
    @Override
    public void run(){

    }
}

class sensors extends Thread {
    @Override
    public void run(){

    }
}

class accessories extends Thread {
    @Override
    public void run(){

    }
}

class opmode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        while (opModeIsActive()) {

        }
    }
}