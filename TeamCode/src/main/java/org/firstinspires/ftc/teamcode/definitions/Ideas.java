package org.firstinspires.ftc.teamcode.definitions;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/15/2018.11:38 PM.
 */
@Disabled
public class Ideas {
    public static void Ideas() throws java.lang.Exception {
        double robotdiameter = 18;
        double wheeldiameter = 5;
        double divitionsinthecircle = 4;
        double ticks = 1120;

        double total = (robotdiameter / wheeldiameter) * (ticks / divitionsinthecircle);

        double rightturn = (18 * Math.PI) / 4;
        double toInch = 1120 / (5 * Math.PI);
        double finnel = rightturn * toInch;
        int test = (int) finnel;
    }
}
