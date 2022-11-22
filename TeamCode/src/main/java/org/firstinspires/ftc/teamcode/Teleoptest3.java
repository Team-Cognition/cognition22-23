package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp

public class Teleoptest3 extends OpMode {
    private DcMotor arm = null;
    double armposition;

    @Override
    public void init() {
        arm = hardwareMap.dcMotor.get("arm"); //Calling the arm
        arm.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void arm_code() { //function for the arm
        double armpower = 0.5;
        double armpower2 = -0.5;
        if (gamepad2.dpad_up) {
            arm.setPower(armpower);
        } else if (gamepad2.dpad_down) {
            arm.setPower(armpower2);
        }

    }

    @Override
    public void loop() {
        arm_code();
    }

    @Override
    public void stop() {
    }
}
