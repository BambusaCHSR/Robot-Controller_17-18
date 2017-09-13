package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elijah on 9/10/17.
 **/
@Autonomous(name = "autonomus", group = "autonomus OpMode")
public class autonomus extends LinearOpMode {
    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    private Definitions robot = new Definitions();

    private int pictograph;

    private VuforiaLocalizer vuforia;

    private VuforiaTrackables   targets;

    @Override public void runOpMode() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        //VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AZF2lOf/////AAAAGZdyB/URWUiOnaMiusXbFewrxLtzRtisy59mfpdVezCnIXp8rWZ11Cb0ZTuoI/MHLVYgYGlIVsliaAH/rbXkv5GDRTV+56P9+M2TyoaXZDHaNzYHSlNeq62YbO7jjTX3Tqiq0wIHaqD8gr1lqFsS6uFOXWFobrN2zmFcEMToo+r6ApaIWeRPGiVM/7C3I3jQbAqDR38RoiMCbRwCQrZJJxPoi7tjcI46brqhI0td4WjLPhh3jgP8eihOfLMTY65IRfYg3x35l521JKkW4/2GE6aAY+/AIDDjWqgil9MbRf37vESExidfFYWDmTZy4WwG7jNFyMs8VzXROJA5v34+Wy/jxHcENssN2TNArxk2TQjl";
        parameters.useExtendedTracking = false;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        targets = vuforia.loadTrackablesFromAsset("FTC-robo");
        targets.get(0).setName("Red-Blue");
        targets.get(1).setName("Blue-Red");

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targets);

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();


        relicTrackables.activate();

        while (opModeIsActive()) {

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            if (vuMark == RelicRecoveryVuMark.LEFT) {
                pictograph = 0;
                telemetry.addData("Pictograph", "LEFT Pictograph is visible");
            }
            else if (vuMark == RelicRecoveryVuMark.CENTER) {
                pictograph = 1;
                telemetry.addData("Pictograph", "CENTER Pictograph is visible");
            }
            else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                pictograph = 2;
                telemetry.addData("Pictograph", "RIGHT Pictograph is visible");
            }
            else {
                telemetry.addData("Pictograph", "NO Pictograph is visible");
                pictograph = 3;
            }
            telemetry.update();

            if (pictograph == 0) {
                telemetry.addData("GlyphPlacement", "Placing glyph in LEFT column");
                //do certain instructions
            }

             else if (pictograph == 1) {
                telemetry.addData("GlyphPlacement", "Placing glyph in CENTER column");
                //do certain instructions
            }
            else if (pictograph == 2) {
                telemetry.addData("GlyphPlacement", "Placing glyph in RIGHT column");
                //do certain instructions
            }
            else {
                telemetry.addData("GlyphPlacement", "Moving to get better view of pictograph");
                //do certain instructions
            }
            telemetry.update();
        }
    }
}
