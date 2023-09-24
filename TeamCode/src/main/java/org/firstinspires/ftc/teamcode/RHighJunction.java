package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name="RHighJunction", group="Autonomous")
public class RHighJunction extends LinearOpMode {


    HardwarePushbot robot = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();
    double gripPosition = 0.5;

    static final double TURN_SPEED = 0.4;
    static final double armSpeed  =  2;
    public final static double CLAW_HOME = 0.0; //Starting position
    public final static double CLAW_MIN_RANGE = 0.0; //Minimum value allowed
    public final static double CLAW_MAX_RANGE = 0.4; //Maximum Range: It might break past this point
    double SERVOposition =CLAW_HOME;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        closeClaw();
        StrafeLeft(1400);
        MoveForward(2200);
        StrafeRight(800);
        sleep(500);
        liftArm(4200);
        sleep(250);
        MoveForward(100);
        sleep(300);
        openClaw();
        MoveBackward(100);
        lowerArm(2750);
        StrafeRight(900);
        TurnRight(1300);
        sleep(300);
        MoveForward(700);
        sleep(300);
        closeClaw();
        sleep(300);
        liftArm(2000);
        sleep(300);
        MoveBackward(1500);
    }
    //Motor 1: Reverse
    //Motor 2: Normal
    //Motor 3: Normal
    //Motor 4: Reverse
    public void MoveForward(long timeoutA) {
        if (opModeIsActive()) {

            robot.setMotorPowers(-TURN_SPEED, TURN_SPEED, 1.15*TURN_SPEED, -TURN_SPEED, 0);
            runtime.reset();
            sleep(timeoutA);
            robot.setMotorPowers(0);
        }
    }


    public void StrafeLeft(long timeoutC) {

        if (opModeIsActive()) {

            robot.setMotorPowers(TURN_SPEED, TURN_SPEED, 1.15*TURN_SPEED, TURN_SPEED, 0); //changed ratio just for this because for some reason it was going crazy
            runtime.reset();
            sleep(timeoutC);
            robot.setMotorPowers(0);

        }

    }


    public void MoveBackward(long timeoutB) {

        if (opModeIsActive()) {

            robot.setMotorPowers(TURN_SPEED, -TURN_SPEED, -1.2*TURN_SPEED, TURN_SPEED, 0);
            runtime.reset();
            sleep(timeoutB);
            robot.setMotorPowers(0);


        }



    }

    public void StrafeRight(long timeoutD) {

        if (opModeIsActive()) {

            robot.setMotorPowers(-TURN_SPEED, -TURN_SPEED, -1.15 * TURN_SPEED, -TURN_SPEED, 0);
            runtime.reset();
            sleep(timeoutD);
            robot.setMotorPowers(0);
        }}
    public void TurnRight(long timeoutE){

        if (opModeIsActive()) {

            robot.setMotorPowers(-TURN_SPEED, -TURN_SPEED, TURN_SPEED, TURN_SPEED, 0);
            runtime.reset();
            sleep(timeoutE);
            robot.setMotorPowers(0);
        }
    }

    public void TurnLeft(long timeoutF){

        if (opModeIsActive()) {

            robot.setMotorPowers(TURN_SPEED, TURN_SPEED, -TURN_SPEED, -TURN_SPEED, 0);
            runtime.reset();
            sleep(timeoutF);
            robot.setMotorPowers(0);
        }
    }

    public void liftArm(long timeoutG) {
        if (opModeIsActive()) {
            robot.arm.setPower(armSpeed);
            sleep(timeoutG); // change
            robot.arm.setPower(0);
        }
    }
    public void lowerArm(long timeoutH) {
        if (opModeIsActive()) {
            robot.arm.setPower(-armSpeed);
            sleep(timeoutH); // change
            robot.arm.setPower(0);
        }
    }

    public void openClaw() {
        if (opModeIsActive()) {
            gripPosition=gripPosition+0.2;
            SERVOposition = Range.clip(gripPosition, CLAW_MIN_RANGE, CLAW_MAX_RANGE);
            robot.claw.setPosition(SERVOposition);
            sleep(500);
        }
    }

    public void closeClaw() {
        if (opModeIsActive()) {
            gripPosition=gripPosition-0.2;
            SERVOposition = Range.clip(gripPosition, CLAW_MIN_RANGE, CLAW_MAX_RANGE);
            robot.claw.setPosition(SERVOposition);
            sleep(500);
        }
    }

}






