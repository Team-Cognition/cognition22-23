package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Test: By Curious Monkey", group ="Test")
public class Teleoptest extends LinearOpMode {
    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor servoArm;
    Servo claw;
    DcMotor arm;
    double armPosition, gripPosition, contPower;
    double MIN_POSITION = 0, MAX_POSITION = 1;

    // Declare variables
    boolean lastA = false;                      // Use to track the prior button state.
    double servo_duckspinner_power;
    boolean secondHalf = false;                 // Use to hint the drivers for end game start
    final double HALF_TIME = 60.0;              // Wait this many seconds before alert for half-time
    ElapsedTime runtime = new ElapsedTime();    // Use to determine when end game is starting.

    static final double SERVO_DUCKSPINNER_STOP = 0;
    static final double SERVO_DUCKSPINNER_SPIN_FORWARD = 1;
    static final double SERVO_DUCKSPINNER_SPIN_REVERSE = -1;
    static final double SERVO_ARM_STOP = 0;
    static final double SERVO_ARM_UP = 0.25;
    static final double SERVO_ARM_DOWN = -0.25;
    static final double MOTOR_TICK_COUNT = 1120;
    static final double MAX_POS = 1.0;     // Maximum rotational position for gripper
    static final double MIN_POS = 0.0;     // Minimum rotational position for gripper
    double position = MIN_POS; // Start at minimum position position  for gripper
    static final double INCREMENT = 0.01;     // amount to slew servo each CYCLE_MS cycle
    double SERVOposition =CLAW_HOME;
    final double SERVOspeed = 0.01;
    public final static double CLAW_HOME = 0.0; //Starting position
    public final static double CLAW_MIN_RANGE = 0.0; //Minimum value allowed
    public final static double CLAW_MAX_RANGE = 0.5; //Maximum Range: It might break past this point
    public final double armpower = 0.5;
    public final double armpower2 = -0.5;

    Gamepad.RumbleEffect customRumbleEffect;    // Use to build a custom rumble sequence.

    public void runOpMode() throws InterruptedException {

        motorFrontLeft = hardwareMap.dcMotor.get("upperLeft"); //motorFrontLeft
        motorBackLeft = hardwareMap.dcMotor.get("lowerLeft"); //motorBackLeft
        motorFrontRight = hardwareMap.dcMotor.get("upperRight"); //motorFrontRight
        motorBackRight = hardwareMap.dcMotor.get("lowerRight"); //motorBackRight
        servoArm = hardwareMap.dcMotor.get("arm"); //arm
        claw = hardwareMap.servo.get("claw");
        //servoDuckSpinner = hardwareMap.get(Servo.class, "duckSpinner");
        //servoDuckSpinner = hardwareMap.get(Servo.class,"duckSpinner");
        //servoRightClaw = hardwareMap.get(Servo.class, "rightClaw");
        //gripServo = hardwareMap.get(Servo.class, "leftClaw");

        double quarterTurn = MOTOR_TICK_COUNT / 4;
        //servoArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //servoArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //sets the counter of ticks to 0
        //servoArm.setZeroPowerBehavior(DcMotor.ZeroPowerBrake.BRAKE);

        customRumbleEffect = new Gamepad.RumbleEffect.Builder()
                .addStep(0.0, 1.0, 500)  //  Rumble right motor 100% for 500 mSec
                .addStep(0.0, 0.0, 300)  //  Pause for 300 mSec
                .addStep(1.0, 0.0, 250)  //  Rumble left motor 100% for 250 mSec
                .addStep(0.0, 0.0, 250)  //  Pause for 250 mSec
                .addStep(1.0, 0.0, 250)  //  Rumble left motor 100% for 250 mSec
                .build();


        servo_duckspinner_power = SERVO_DUCKSPINNER_STOP;

        //servoDuckSpinner.setPower(servo_duckspinner_pos);

        //Reverse front right and back right motors
       motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
       //  motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
      //  motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        //servoArm.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("TeleOp>", "Press Start");
        telemetry.update();
        waitForStart();
        runtime.reset();    // Start game timer.

        telemetry.addData("TeleOp>", "Stage 1");
        telemetry.update();

        if (isStopRequested()) return;

        armPosition = .5;                   // set arm to half way up.
        gripPosition = MAX_POSITION;        // set grip to full open.

        arm = hardwareMap.dcMotor.get("arm"); //Calling the arm
        arm.setDirection(DcMotorSimple.Direction.FORWARD);


        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            if ((runtime.seconds() > HALF_TIME) && !secondHalf) {
                gamepad1.runRumbleEffect(customRumbleEffect);
                secondHalf = true;
            }

            if (!secondHalf) {
                telemetry.addData(">", "Halftime Alert Countdown: %3.0f Sec \n", (HALF_TIME - runtime.seconds()));
            }

            if (gamepad2.dpad_up) {
                arm.setPower(armpower);
            } else if (gamepad2.dpad_down) {
                arm.setPower(armpower2);
            } else if (!(gamepad2.dpad_down || gamepad2.dpad_up)) {
                arm.setPower(0);
            }

            if (gamepad2.left_bumper) {
                SERVOposition = Range.clip(SERVOposition, CLAW_MIN_RANGE, CLAW_MAX_RANGE);
                claw.setPosition(SERVOposition);
            }
            if (gamepad2.right_bumper) {
                SERVOposition = Range.clip(SERVOposition, CLAW_MAX_RANGE, CLAW_MIN_RANGE);
                claw.setPosition(SERVOposition);

            }

/*

*/
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;
            //Slower speed so that is easier to control
            motorFrontLeft.setPower(frontLeftPower * 0.5);
            motorBackLeft.setPower(backLeftPower * 0.5);
            motorFrontRight.setPower(frontRightPower * 0.5);
            motorBackRight.setPower(backRightPower * 0.5);


            telemetry.update();
        }

        telemetry.addData("Game>", "Over");
        telemetry.update();
    }
}