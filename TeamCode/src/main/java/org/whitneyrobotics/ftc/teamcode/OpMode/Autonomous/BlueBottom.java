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
    private Pose startPose = new Pose(9,33,0);
    private Pose leavePose = new Pose(21,33,0);
    private Path leave;

    public void buildPaths(){
        leave = new Path(new BezierLine(startPose, leavePose));
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
