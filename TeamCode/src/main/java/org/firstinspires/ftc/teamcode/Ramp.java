package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.Range;

/**
 * Created by Elijah Sauder for Bambusa in ftc_app, on 01/27/2018.12:15 AM.
 */

public class Ramp {
    private Ramp() {}

    //Sin wave ramp, it looks like an S
    public static double sRamp(double number, double max) {
        //A sin function between [0,0] and [rate,max]
        double sinFunc = (Math.abs(max)/2)*(1+Math.sin(((number)*Math.PI)-(Math.PI/2)));
        //if statements making sure that it only gives the sinfunc when the input number is above or below 0
        if (number < 0) {
            return -sinFunc;
        }
        else if (number > 0) {
            return sinFunc;
        }
        return 0;
    }

    //Parabola function ramp.
    public static double pRamp(double number, double max, double rate) {
        //parabola function between [0,0] and [rate, x]
        double parbolaFunc = (rate/2)*Math.pow(number, 2);
        if (number < 0 || number > 0) {
            //clips the number to the max given
            return Range.clip(parbolaFunc, -max, max);
        }
        return 0;
    }
}
