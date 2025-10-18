package org.whitneyrobotics.ftc.teamcode.OpMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.pedroPathing.Constants;
import org.whitneyrobotics.ftc.teamcode.pedroPathing.PedroDrive;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;

@TeleOp(name = "work pls")
public class DemoTeleop extends OpModeEx {

    public boolean robotCentric = true;
    public PedroDrive drive;
    public Follower follower;

    @Override
    public void initInternal() {
//        drive = new PedroDrive(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
    }

    @Override
    public void start() {
//        drive.initialize();
//        drive.startDrive();
        follower.setStartingPose(new Pose(0,0,0));
        follower.startTeleopDrive();
    }

    @Override
    protected void loopInternal() {
        gamepad1.BUMPER_RIGHT.onPress(() -> robotCentric = !robotCentric);
        follower.setTeleOpDrive(gamepad1.LEFT_STICK_Y.value(), -gamepad1.LEFT_STICK_X.value(), -gamepad1.RIGHT_STICK_X.value(), robotCentric);
        follower.update();
        telemetryPro.addData("robotCentric: ", robotCentric);
//        gamepad1.BUMPER_RIGHT.onPress(() -> drive.driveMode());
//        drive.update(gamepad1);
//        telemetryPro.addData("robotCentric: ", drive.getDriveMode());
    }
}