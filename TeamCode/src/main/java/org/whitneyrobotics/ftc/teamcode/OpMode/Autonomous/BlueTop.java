package org.whitneyrobotics.ftc.teamcode.OpMode.Autonomous;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.RobotImpl;
import org.whitneyrobotics.ftc.teamcode.pedroPathing.Constants;
import org.whitneyrobotics.ftc.teamcode.pedroPathing.PedroDrive;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "Blue Top")
public class BlueTop extends OpModeEx {
    private Follower follower;
    private PedroDrive drive;
    private RobotImpl robot;

    // Define all poses
    private Pose startPose = new Pose(35, 120, Math.toRadians(180));
    private Pose pose1 = new Pose(54, 84, Math.toRadians(130));
    private Pose pose2 = new Pose(55, 84, Math.toRadians(180));
    private Pose pose3 = new Pose(25, 84, Math.toRadians(180));
    private Pose pose4 = new Pose(54, 84, Math.toRadians(130));
    private Pose pose5 = new Pose(55, 60, Math.toRadians(180));
    private Pose pose6 = new Pose(25, 60, Math.toRadians(180));
    private Pose pose7 = new Pose(54, 84, Math.toRadians(130));
    private Pose pose8 = new Pose(55, 36, Math.toRadians(180));
    private Pose pose9 = new Pose(25, 36, Math.toRadians(180));
    private Pose pose10 = new Pose(54, 84, Math.toRadians(130));


    private PathChain path;

    private boolean unstarted;
    public void buildPaths() {
        path = follower.pathBuilder()
                // Path 1: Start to launch position
                .addPath(new Path(new BezierLine(startPose, pose1)))
                .setLinearHeadingInterpolation(startPose.getHeading(), pose1.getHeading())

                // Path 2: Launch to specimen pickup
                .addPath(new Path(new BezierLine(pose1, pose2)))
                .setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading())

                // Path 3: Move to wall
                .addPath(new Path(new BezierLine(pose2, pose3)))
                .setLinearHeadingInterpolation(pose2.getHeading(), pose3.getHeading())

                // Path 4: Return to launch
                .addPath(new Path(new BezierLine(pose3, pose4)))
                .setLinearHeadingInterpolation(pose3.getHeading(), pose4.getHeading())

                // Path 5: Move to second specimen
                .addPath(new Path(new BezierLine(pose4, pose5)))
                .setLinearHeadingInterpolation(pose4.getHeading(), pose5.getHeading())

                // Path 6: Move to wall
                .addPath(new Path(new BezierLine(pose5, pose6)))
                .setLinearHeadingInterpolation(pose5.getHeading(), pose6.getHeading())

                // Path 7: Return to launch
                .addPath(new Path(new BezierLine(pose6, pose7)))
                .setLinearHeadingInterpolation(pose6.getHeading(), pose7.getHeading())

                // Path 8: Move to third specimen
                .addPath(new Path(new BezierLine(pose7, pose8)))
                .setLinearHeadingInterpolation(pose7.getHeading(), pose8.getHeading())

                // Path 9: Move to wall
                .addPath(new Path(new BezierLine(pose8, pose9)))
                .setLinearHeadingInterpolation(pose8.getHeading(), pose9.getHeading())

                // Path 10: Final return to launch
                .addPath(new Path(new BezierLine(pose9, pose10)))
                .setLinearHeadingInterpolation(pose9.getHeading(), pose10.getHeading())

                .setTranslationalConstraint(5)
                .setTimeoutConstraint(750)
                .setVelocityConstraint(35)
                .build();
    }

    @Override
    public void initInternal() {
        robot = RobotImpl.getInstance(hardwareMap);
        drive = new PedroDrive(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
        unstarted=true;
    }

    @Override
    protected void loopInternal() {
        follower.update();
        if(unstarted){
            follower.followPath(path,true);
            unstarted=false;
        }
    }
}
