package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;


@Autonomous(name="Main Right Autonomous", group="Autonomous")
public class Rauton2023 extends LinearOpMode {
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;
    //what camera do we have?
    // UNITS ARE METERS
    double tagsize = 0.166;

    // Tag ID 1, 2, 3 from the 36h11 family
    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;

    HardwarePushbot robot = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();
    double gripPosition = 0.5;

    static final double TURN_SPEED = 0.4;
    static final double armSpeed = 2;
    public final static double CLAW_HOME = 0.0; //Starting position
    public final static double CLAW_MIN_RANGE = 0.0; //Minimum value allowed
    public final static double CLAW_MAX_RANGE = 0.4; //Maximum Range: It might break past this point
    double SERVOposition = CLAW_HOME;
    public int zoneNumber = 0;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId); // See in Hardware Map camera stuff
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        telemetry.setMsTransmissionInterval(50);
        while (!isStarted() && !isStopRequested()) {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if (currentDetections.size() != 0) {
                boolean tagFound = false;

                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }
                if (tagFound) {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                } else {
                    telemetry.addLine("Don't see tag of interest :(");

                    if (tagOfInterest == null) {
                        telemetry.addLine("(The tag has never been seen)");
                    } else {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            } else {
                telemetry.addLine("Don't see tag of interest :(");

                if (tagOfInterest == null) {
                    telemetry.addLine("(The tag has never been seen)");
                } else {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
        }
        if (tagOfInterest == null || tagOfInterest.id == LEFT) {
            telemetry.addLine("1");
        } else if (tagOfInterest.id == MIDDLE) {
            telemetry.addLine("2");
        } else if (tagOfInterest.id == RIGHT) {
            telemetry.addLine("3");
        } else {
            telemetry.addLine("No April Tag Detected.");


        }



        waitForStart();

        if (tagOfInterest.id == LEFT) {
            zoneNumber=1;

        } else if (tagOfInterest.id==MIDDLE) {
        zoneNumber=2;

        } else if (tagOfInterest.id==RIGHT) {
            zoneNumber=3;

        } else {
            float xyz = 5;
        }


        closeClaw();
        StrafeLeft(1250);
        MoveForward(2100);
        StrafeRight(750);
        sleep(500);
        liftArm(4100);
        sleep(250);
        MoveForward(175);
        sleep(300);
        openClaw();
        MoveBackward(100);
        lowerArm(2700);
        StrafeRight(700);
        TurnRight(1100);
        sleep(300);
        MoveForward(1000);
        sleep(300);
        closeClaw();
        sleep(300);
        liftArm(1200);
        sleep(300);
        MoveBackward(1100);
        StrafeRight(700);
        MoveForward(150);
        sleep(300);
        openClaw();
if (zoneNumber == 1) {
    MoveBackward(200);
    TurnRight(100);
    StrafeRight(750);
    MoveBackward(1500);
    telemetry.addLine("Zone 1");
    }
else if (zoneNumber == 2) {
    MoveBackward(100);
    telemetry.addLine("Zone 2");

} else if (zoneNumber ==3) {
    MoveBackward(150);
    StrafeRight(750);
    MoveForward(1500);
    telemetry.addLine("Zone 3");
} else {
    MoveForward(0);
    telemetry.addLine("No Zone Detected");
 }

telemetry.update();

    }

    private void tagToTelemetry(AprilTagDetection tagOfInterest) {
    }

    public void MoveForward(long timeoutA) {
        if (opModeIsActive()) {

            robot.setMotorPowers(-TURN_SPEED, TURN_SPEED, 1.17*TURN_SPEED, -TURN_SPEED, 0);
            runtime.reset();
            sleep(timeoutA);
            robot.setMotorPowers(0);
        }
    }


    public void StrafeLeft(long timeoutC) {

        if (opModeIsActive()) {

            robot.setMotorPowers(TURN_SPEED, TURN_SPEED, 1.17*TURN_SPEED, TURN_SPEED, 0); //changed ratio just for this because for some reason it was going crazy
            runtime.reset();
            sleep(timeoutC);
            robot.setMotorPowers(0);

        }

    }


    public void MoveBackward(long timeoutB) {

        if (opModeIsActive()) {

            robot.setMotorPowers(TURN_SPEED, -TURN_SPEED, -1.17*TURN_SPEED, TURN_SPEED, 0);
            runtime.reset();
            sleep(timeoutB);
            robot.setMotorPowers(0);


        }



    }

    public void StrafeRight(long timeoutD) {

        if (opModeIsActive()) {

            robot.setMotorPowers(-TURN_SPEED, -TURN_SPEED, -1.17 * TURN_SPEED, -TURN_SPEED, 0);
            runtime.reset();
            sleep(timeoutD);
            robot.setMotorPowers(0);
        }}
    public void TurnRight(long timeoutE){

        if (opModeIsActive()) {

            robot.setMotorPowers(-TURN_SPEED, -TURN_SPEED, 1.17*TURN_SPEED, TURN_SPEED, 0);
            runtime.reset();
            sleep(timeoutE);
            robot.setMotorPowers(0);
        }
    }

    public void TurnLeft(long timeoutF){

        if (opModeIsActive()) {

            robot.setMotorPowers(TURN_SPEED, TURN_SPEED, -1.17*TURN_SPEED, -TURN_SPEED, 0);
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
