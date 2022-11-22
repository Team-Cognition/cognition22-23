
package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.util.ElapsedTime;
        import com.qualcomm.robotcore.util.Range;

@Autonomous(name="DC Motor Test", group="Autonomous")
public class TestProgram extends LinearOpMode {

    TestBed robot = new TestBed();
    private ElapsedTime runtime = new ElapsedTime();
    double SERVOposition =robot.CLAW_HOME;
    final double SERVOspeed = 0.01;


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        waitForStart();
        RunMotors(1000000000);

    }

    public void RunMotors(long timeoutA) {
        if (opModeIsActive()) {

            SERVOposition = Range.clip(SERVOposition, robot.CLAW_MIN_RANGE, robot.CLAW_MAX_RANGE);
            robot.testServo.setPosition(SERVOposition);


            //            robot.setMotorPowers(0);
//            runtime.reset();
//            sleep(timeoutA);
//            robot.setMotorPowers(0);
        }
    }
}