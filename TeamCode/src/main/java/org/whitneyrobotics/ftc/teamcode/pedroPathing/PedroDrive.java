package org.whitneyrobotics.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

public class PedroDrive {

    private Follower follower;
    private boolean robotCentric = false;
    public DcMotorEx fr, fl, br, bl;

    public PedroDrive(HardwareMap hardwareMap) {
        follower = Constants.createFollower(hardwareMap);
    }

    public void initialize(){
        follower.setStartingPose(new Pose(0,0,0));
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