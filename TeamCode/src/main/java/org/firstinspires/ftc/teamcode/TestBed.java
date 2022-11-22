package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class TestBed {
    public DcMotor testMotor = null;
    public Servo testServo = null;
    HardwareMap hwMap           =  null;
    /* Constructor */
    public final static double CLAW_HOME = 0.0; //Starting position
    public final static double CLAW_MIN_RANGE = 0.0; //Minimum value allowed
    public final static double CLAW_MAX_RANGE = 0.5; //Maximum Range: It might break past this point
    public TestBed(){
    }

    /* Initialize standard Hardware interfaces */


    public void init(HardwareMap ahwMap) {

        hwMap = ahwMap;

        testMotor = hwMap.dcMotor.get("testMotor");
        testServo = hwMap.servo.get("testServo");

        testServo.setPosition(CLAW_HOME);


        // Set all motors to zero power'
        testMotor.setPower(0);


        testMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setMotorPowers(double Power) {
        testMotor.setPower(Power);


    }
}