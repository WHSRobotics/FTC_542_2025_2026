package org.whitneyrobotics.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

import java.util.function.Supplier;

public class PedroDrive {

    public Follower follower;
    private boolean robotCentric = false;
    private Supplier<PathChain> pathChain;

    public PedroDrive(HardwareMap hardwareMap) {
        follower = Constants.createFollower(hardwareMap);
    }

    public void initialize(){
        follower.setStartingPose(new Pose(0,0,0));
        follower.update();
        pathChain = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(45, 98))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(45), 0.8))
                .build();
    }

    public void startDrive(){
        follower.startTeleopDrive();
    }

    public void update(GamepadEx gamepadEx) {
        follower.setTeleOpDrive(gamepadEx.LEFT_STICK_Y.value(), -gamepadEx.LEFT_STICK_X.value(), -gamepadEx.RIGHT_STICK_X.value(), robotCentric);
        follower.update();
    }

    public void driveMode(){
        robotCentric = !robotCentric;
    }

    public boolean getDriveMode(){
        return robotCentric;
    }

    public Pose getPose(){
        return follower.getPose();
    }

    public boolean isBusy(){
        return follower.isBusy();
    }
}