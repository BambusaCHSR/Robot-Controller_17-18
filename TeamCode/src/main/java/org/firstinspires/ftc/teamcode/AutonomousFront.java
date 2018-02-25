package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Elijah Sauder for Bambusa in Robot-Controller_17-18, on 01/15/2018.7:21 PM.
 **/

@Autonomous(name = "Jewels", group = "bambusa")
public class AutonomousFront extends LinearOpMode {

    //Imports the Definitions file
    private Definitions robot = new Definitions();
    private ElapsedTime runtime = new ElapsedTime();

    //Initializes variables that we will use in the program.
    private boolean movement = false; //Tells us if we have moved to the cryptobox
    private boolean jewelGotten = false; //Tells us if we have gotten the jewel
    private boolean teamColor = false; //Tells us if we have detected our team color
    private String teamC = "NULL"; //Tells us our team color from the balancing stone

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        //Initializes the robot
        robot.hardwareMapInit(hardwareMap); //imports the hardware map from definitions
        robot.servoInit(); //sets the beginning servo positions
        robot.driveInitAuto(); //Initializes the drive encoders
        robot.closeArms(); //Sets the arms to start closed so that we can put a block in

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Waits for the driver to press play
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            /** Finds our team color from the drive-base color sensor **/
            if (!teamColor) { //makes sure it only runs until we find the team color
                if (robot.teamColor.red() > robot.teamColor.blue()) { //Tests for the Balancing stone color
                    teamC = "RED"; //Sets our team color to RED
                    teamColor = true; //makes sure that we don't find the color again later when we move to the mats
                } else if (robot.teamColor.red() < robot.teamColor.blue()) { //Tests for the Balancing stone color
                    teamC = "BLUE"; //Sets our team color to BLUE
                    teamColor = true; //makes sure that we don't find the color again later when we move to the mats
                }
                telemetry.addData("Team Color", "Finiding Team Color");
            } else {
                telemetry.addData("Team Color", "is: " + teamC);
            }

            if (teamC.equals("RED")) { //If our team color is Red run these methods
                if (!jewelGotten) { //Makes sure we only try and get the jewel once
                    telemetry.addData("Jewel", "Not Gotten");
                    robot.jewelDown(); //Puts down the arm that we use to knock off the jewels
                    if (robot.jewelColor.red() < robot.jewelColor.blue()) { //tests for the jewel color
                        robot.forwardINCH(5, 0.3); //moves forward to knock off the blue jewel
                        robot.jewelUp(); //puts the arm back up
                        robot.backwardINCH(5, 0.3);      //attempts to move back on to
                                                                            //the balancing stone so later movements are correct
                        jewelGotten = true; //Tells us that we have already gotten the jewel and dont need to do it again
                        telemetry.addData("JewelColor", "Red");
                    } else if (robot.jewelColor.red() > robot.jewelColor.blue()) { //tests for the jewel color
                        robot.backwardINCH(5, 0.3); //Moves backwards to knock the blue jewel off
                        robot.jewelUp(); //sets our arm up
                        robot.forwardINCH(5, 0.3);   //Attempts to move back on to the
                                                                        //balancing stone so later movements are correct
                        jewelGotten = true; //Tells us that we have already gotten the jewel and don't need to do it again
                        telemetry.addData("JewelColor", "Blue");
                    } else { //If no colors are detected moves the robot forward a little till it detects some color
                        robot.setDriveForward();
                        robot.setPos(50);
                        robot.setPower(0.2);
                    }
                }
                /**moving to Cryptobox and attempts to place block**/
                //All the values here are from pre determined path
                else if (jewelGotten && !movement){ //Tests to make sure we don't move before we get the jewel and don't move twice
                    telemetry.addData("Jewel", "Gotten");
                    robot.forwardINCH(42, 0.5); //moves forward to the Taped zone
                    robot.rotRightDeg(90, 0.4); //Rotates to face the cryptobox
                    robot.forwardINCH(24, 0.5); //Moves forward to place block
                    robot.openArms(); //places block
                    robot.backwardINCH(5, 0.2); //moves back
                    telemetry.addData("Move", "Moving");
                    movement = true; //Tells us we have all ready attempted to place the block, don't do it again
                }
            }

            /**all the same comments from above **/
            else if (teamC.equals("BLUE")) {
                if (!jewelGotten) {
                    telemetry.addData("Jewel", "Not Gotten");
                    robot.jewelDown();
                    if (robot.jewelColor.red() > robot.jewelColor.blue()) {
                        robot.forwardINCH(5, 0.3);
                        robot.jewelUp();
                        robot.backwardINCH(5,0.3);
                        jewelGotten = true;
                        telemetry.addData("JewelColor", "Red");
                    } else if (robot.jewelColor.red() < robot.jewelColor.blue()) {
                        robot.backwardINCH(5, 0.3);
                        robot.jewelUp();
                        robot.forwardINCH(5, 0.3);
                        jewelGotten = true;
                        telemetry.addData("JewelColor", "Blue");
                    } else {
                        robot.setDriveForward();
                        robot.setPos(50);
                        robot.setPower(0.1);
                    }
                } else if (jewelGotten && !movement){
                    telemetry.addData("Jewel", "Gotten");
                    telemetry.addData("Move", "Moving");
                    robot.backwardINCH(42, 0.5);
                    robot.rotRightDeg(90,0.4);
                    robot.forwardINCH(24, 0.5);
                    robot.openArms();
                    robot.backwardINCH(5, 0.2);
                    movement = true;
                }
            }

            telemetry.addLine()
                    .addData("", "Jewel Color")
                    .addData("Red", robot.jewelColor.red())
                    .addData("Blue", robot.jewelColor.blue());
            telemetry.addLine()
                    .addData("", "Team Color")
                    .addData("Red", robot.teamColor.red())
                    .addData("Blue", robot.teamColor.blue());
            telemetry.addData("Status", "Running");
            telemetry.update();

            if (!opModeIsActive()) {
                break;
            }
        }
    }
}
