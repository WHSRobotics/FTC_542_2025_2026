package org.whitneyrobotics.ftc.teamcode.OpMode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.pedroPathing.Constants;
import org.whitneyrobotics.ftc.teamcode.pedroPathing.PedroDrive;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;

import java.util.function.Supplier;

@TeleOp(name = "Demo Teleop")
public class DemoTeleop extends OpModeEx {

    public boolean robotCentric = true;
//    public PedroDrive drive;
    public Follower follower;
    private Supplier<PathChain> pathChain;

    @Override
    public void initInternal() {
//        drive = new PedroDrive(hardwareMap);
//        drive.initialize();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(0,0,0));
        pathChain = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(45, 98))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(45), 0.8))
                .setBrakingStart(2.5)
                .setBrakingStrength(1)
                .build();
        follower.update();
    }

    @Override
    public void start() {
//        drive.initialize();
//        drive.startDrive();
        follower.startTeleopDrive();
    }

    @Override
    protected void loopInternal() {
        follower.update();
        gamepad1.BUMPER_RIGHT.onPress(() -> robotCentric = !robotCentric);
        follower.setTeleOpDrive(gamepad1.LEFT_STICK_Y.value(), -gamepad1.LEFT_STICK_X.value(), -gamepad1.RIGHT_STICK_X.value(), robotCentric);
        telemetryPro.addData("robotCentric: ", robotCentric);
//        gamepad1.BUMPER_RIGHT.onPress(() -> drive.driveMode());
//        drive.update(gamepad1);
//        telemetryPro.addData("robotCentric: ", drive.getDriveMode());
    }
}