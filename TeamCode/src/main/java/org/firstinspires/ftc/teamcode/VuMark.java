package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elijah on 9/11/17.
 **/

public class VuMark {
    // Constants
    private static final int MAX_PICTOGRAPHS = 1;
    private static final double ON_AXIS = 10;      // Within 1.0 cm of target center-line
    private static final double CLOSE_ENOUGH = 20;      // Within 2.0 cm of final target standoff

    // Select which camera you want use.  The FRONT camera is the one on the same side as the screen.  Alt. is BACK
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = VuforiaLocalizer.CameraDirection.BACK;

    public static final double YAW_GAIN = 0.018;   // Rate at which we respond to heading error
    public static final double LATERAL_GAIN = 0.0027;  // Rate at which we respond to off-axis error
    public static final double AXIAL_GAIN = 0.0017;  // Rate at which we respond to target distance errors

    private LinearOpMode myOpMode;
    private mainOp myRobot;
    private VuforiaTrackables targets;

    private boolean pictographFound;
    private String pictographName;

    private double robotX;         // X displacement from target center
    private double robotY;         // Y displacement from target center
    private double robotBearing;   // Robot's rotation around the Z axis (CCW is positive)
    private double targetRange;    // Range from robot's center to target in mm
    private double targetBearing;  // Heading of the target , relative to the robot's unrotated center
    private double relativeBearing;// Heading to the target from the robot's current bearing. eg: a Positive RelativeBearing means the robot must turn CCW to point at the target image.

    private VuforiaLocalizer vuforia;

    public VuMark() {

        pictographFound = false;
        pictographName = null;
        targets = null;

        robotX = 0;
        robotY = 0;
        targetRange = 0;
        targetBearing = 0;
        robotBearing = 0;
        relativeBearing = 0;
    }

    /***
     * Send telemetry data to indicate navigation status
     */

    public void addNavTelemetry() {
        if (pictographFound)
        {
            // Display the current visible target name, robot info, target info, and required robot action.
            myOpMode.telemetry.addData("Visible", pictographName);
            myOpMode.telemetry.addData("Robot", "[X]:[Y] (B) [%5.0fmm]:[%5.0fmm] (%4.0f°)", robotX, robotY, robotBearing);
            myOpMode.telemetry.addData("Target", "[R] (B):(RB) [%5.0fmm] (%4.0f°):(%4.0f°)", targetRange, targetBearing, relativeBearing);
            myOpMode.telemetry.addData("- Turn    ", "%s %4.0f°",  relativeBearing < 0 ? ">>> CW " : "<<< CCW", Math.abs(relativeBearing));
            myOpMode.telemetry.addData("- Strafe  ", "%s %5.0fmm", robotY < 0 ? "LEFT" : "RIGHT", Math.abs(robotY));
            myOpMode.telemetry.addData("- Distance", "%5.0fmm", Math.abs(robotX));
        }
        else
        {
            myOpMode.telemetry.addData("Visible", "- - - -" );
        }
    }

    /***
     * Start tracking Vuforia images
     */

    public void activateTracking() {

        // Start tracking any of the defined targets
        if (targets != null)
            targets.activate();
    }

    public void initVuforia(LinearOpMode opMode, mainOp robot) {

        // Save reference to OpMode and Hardware map
        myOpMode = opMode;
        myRobot = robot;

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);  // Use this line to see camera display
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();                             // OR... Use this line to improve performance

        parameters.vuforiaLicenseKey = "AZF2lOf/////AAAAGZdyB/URWUiOnaMiusXbFewrxLtzRtisy59mfpdVezCnIXp8rWZ11Cb0ZTuoI/MHLVYgYGlIVsliaAH/rbXkv5GDRTV+56P9+M2TyoaXZDHaNzYHSlNeq62YbO7jjTX3Tqiq0wIHaqD8gr1lqFsS6uFOXWFobrN2zmFcEMToo+r6ApaIWeRPGiVM/7C3I3jQbAqDR38RoiMCbRwCQrZJJxPoi7tjcI46brqhI0td4WjLPhh3jgP8eihOfLMTY65IRfYg3x35l521JKkW4/2GE6aAY+/AIDDjWqgil9MbRf37vESExidfFYWDmTZy4WwG7jNFyMs8VzXROJA5v34+Wy/jxHcENssN2TNArxk2TQjl";

        parameters.cameraDirection = CAMERA_CHOICE;
        parameters.useExtendedTracking = false;
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        targets = vuforia.loadTrackablesFromAsset("FTC_robo");
        targets.get(0).setName("Red on Right");
        targets.get(1).setName("Red on Left");

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targets);

    }

    public boolean targetsAreVisible()  {

        int targetTestID = 0;

        // Check each target in turn, but stop looking when the first target is found.
        while ((targetTestID < MAX_PICTOGRAPHS) && !targetIsVisible(targetTestID)) {
            targetTestID++ ;
        }

        return (pictographFound);
    }

    public boolean targetIsVisible(int targetId) {

        VuforiaTrackable target = targets.get(targetId);
        VuforiaTrackableDefaultListener listener = (VuforiaTrackableDefaultListener)target.getListener();
        OpenGLMatrix location  = null;

        // if we have a target, look for an updated robot position
        if ((target != null) && (listener != null) && listener.isVisible()) {
            pictographFound = true;
            pictographName = target.getName();
        }
        else  {
            // Indicate that there is no target visible
            pictographFound = false;
            pictographName = "None";
        }

        return pictographFound;
    }
}