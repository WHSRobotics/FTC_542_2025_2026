package org.whitneyrobotics.ftc.teamcode.OpMode.Autonomous;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.pedroPathing.Constants;
import org.whitneyrobotics.ftc.teamcode.pedroPathing.PedroDrive;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "Blue Bottom")
public class BlueBottom extends OpModeEx {

    private PedroDrive drive;
    private Pose startPose = new Pose(65.5,16,Math.toRadians(90));
    private Pose leavePose = new Pose(65.5,40,Math.toRadians(90));
    private Pose betterLeavePose = new Pose(24, 16, Math.toRadians(90));
    private Path leave;

    public void buildPaths(){
        leave = new Path(new BezierLine(startPose, betterLeavePose));
        leave.setLinearHeadingInterpolation(startPose.getHeading(), leavePose.getHeading());
    }

    @Override
    public void initInternal() {
        drive = new PedroDrive(hardwareMap);
        buildPaths();
        drive.setStartingPose(startPose);
    }

    @Override
    protected void loopInternal() {
        drive.followPath(leave);
        drive.updateAuto();
    }
}
