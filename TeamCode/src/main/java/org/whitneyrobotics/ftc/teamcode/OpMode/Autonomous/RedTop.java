package org.whitneyrobotics.ftc.teamcode.OpMode.Autonomous;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.pedroPathing.PedroDrive;

@Autonomous(name = "RedTop")
public class RedTop extends OpModeEx {

    private PedroDrive drive;
    private Pose startPose = new Pose(120,120,Math.toRadians(45));
    private Pose leavePose = new Pose(84,40,Math.toRadians(90));
    private Path leave;

    public void buildPaths(){
        leave = new Path(new BezierLine(startPose, leavePose));
        leave.setLinearHeadingInterpolation(startPose.getHeading(), leavePose.getHeading());
    }

    @Override
    public void initInternal(){
        drive = new PedroDrive(hardwareMap);
        buildPaths();
        drive.setStartingPose(startPose);
    }

    @Override
    public void loopInternal(){
        drive.followPath(leave);
        drive.updateAuto();
    }
}
