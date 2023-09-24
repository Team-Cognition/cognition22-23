package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.sun.tools.javac.jvm.ClassWriter;

@TeleOp(name = "Servocode", group = "Arnavcomputer")
public class servocode extends LinearOpMode {
    HardwarePushbot robot = new HardwarePushbot();
    double SERVRposition =robot.CLAW_HOME;
    double SERVLposition = robot.CLAW_HOME;
    final double SERVspeed = 0.01;
    @Override
    public void runOpMode() {
        double left;
        double right;

        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad2.dpad_right)
                SERVRposition -= SERVspeed;
            else if (gamepad2.dpad_left)
                SERVRposition += SERVspeed;
        }
        if (gamepad2.dpad_right)
            SERVLposition += SERVspeed;
        else if (gamepad2.dpad_left)
            SERVLposition -= SERVspeed;

        SERVRposition = Range.clip(SERVRposition, robot.CLAWR_MIN_RANGE, robot.CLAWR_MAX_RANGE);
        SERVLposition = Range.clip(SERVLposition, robot.CLAWL_MAX_RANGE, robot.CLAWL_MIN_RANGE);
        robot.clawServoRight.setPosition(SERVRposition);
        robot.clawServoLeft.setPosition(SERVLposition);

    }

}


