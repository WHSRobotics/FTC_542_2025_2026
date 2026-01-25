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
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name = "Blue Top")
public class BlueTop extends OpModeEx {
    private Follower follower;
    private PedroDrive drive;
    private RobotImpl robot;
    public DcMotorEx transfer;


    // Define all poses
    private Pose startPose = new Pose(35, 120, Math.toRadians(180));
    private Pose pose1 = createPose(64, 90, Math.toRadians(134));
    private Pose pose2 = createPose(64, 86, Math.toRadians(180));
    private Pose pose3 = createPose(38, 86, Math.toRadians(180));
    private Pose pose4 = createPose(54, 90, Math.toRadians(134));
    private Pose pose5 = createPose(55, 68, Math.toRadians(180));
    private Pose pose6 = createPose(38, 68, Math.toRadians(180));
    private Pose pose7 = createPose(54, 90, Math.toRadians(134));
    private Pose pose8 = createPose(55, 40, Math.toRadians(180));
    private Pose pose9 = createPose(38, 40, Math.toRadians(180));
    private Pose pose10 = createPose(54, 90, Math.toRadians(134));

    private int currentCallback = 0;

    private PathChain path;


    private boolean unstarted;

    public Pose createPose(double x, double y, double heading) {
        return new Pose(y, -x, heading);
    }

    private boolean shootingInProgress = false;
    private Timer actionTimer = new Timer();
    private boolean needToReverse=true;
    private void shoot(){
        if(shootingInProgress) {
            robot.turret.run(-1);

            if (actionTimer.getElapsedTimeSeconds() >= 3) {
                transfer.setPower(-1);
            }
            if (actionTimer.getElapsedTimeSeconds() >= 3.25 && needToReverse) {
                robot.intake.run(-1);
                needToReverse = false;
            }
            if (actionTimer.getElapsedTimeSeconds() >= 3.45) {
                robot.intake.run(1);
            }
            if (actionTimer.getElapsedTimeSeconds()>=4.5){
                robot.intake.run(-1);
            }
            if (actionTimer.getElapsedTimeSeconds() >= 5) {  // Changed to Seconds
                robot.turret.run(-0.4);
                transfer.setPower(0);
                robot.intake.run(1);
                shootingInProgress = false;
                needToReverse = true;  // Reset for next time
                follower.resumePathFollowing();
            }
        } else {
            robot.turret.run(-0.4);
        }
    }

    public void buildPaths() {
        path = follower.pathBuilder()
                // Path 1: Start to launch position
                .addPath(new Path(new BezierLine(startPose, pose1)))
                .setLinearHeadingInterpolation(startPose.getHeading(), pose1.getHeading())
                .addParametricCallback(0.98, () -> {
                    if (!shootingInProgress) {  // Only trigger if not already shooting
                        actionTimer.resetTimer();
                        currentCallback = 1;
                    }
                })

                // Path 2: Launch to specimen pickup
                .addPath(new Path(new BezierLine(pose1, pose2)))
                .setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading())

                // Path 3: Move to wall
                .addPath(new Path(new BezierLine(pose2, pose3)))
                .setLinearHeadingInterpolation(pose2.getHeading(), pose3.getHeading())

                // Path 4: Return to launch
                .addPath(new Path(new BezierLine(pose3, pose4)))
                .setLinearHeadingInterpolation(pose3.getHeading(), pose4.getHeading())
                .addParametricCallback(0.98, () -> {
                    if (!shootingInProgress) {
                        follower.pausePathFollowing();
                        actionTimer.resetTimer();
                        shootingInProgress = true;
                        currentCallback = 2;
                    }
                })

                // Path 5: Move to second specimen
                .addPath(new Path(new BezierLine(pose4, pose5)))
                .setLinearHeadingInterpolation(pose4.getHeading(), pose5.getHeading())

                // Path 6: Move to wall
                .addPath(new Path(new BezierLine(pose5, pose6)))
                .setLinearHeadingInterpolation(pose5.getHeading(), pose6.getHeading())

                // Path 7: Return to launch
                .addPath(new Path(new BezierLine(pose6, pose7)))
                .setLinearHeadingInterpolation(pose6.getHeading(), pose7.getHeading())
                .addParametricCallback(0.98, () -> {
                    if (!shootingInProgress) {
                        follower.pausePathFollowing();
                        actionTimer.resetTimer();
                        shootingInProgress = true;
                        currentCallback = 3;
                    }
                })

                // Path 8: Move to third specimen
                .addPath(new Path(new BezierLine(pose7, pose8)))
                .setLinearHeadingInterpolation(pose7.getHeading(), pose8.getHeading())

                // Path 9: Move to wall
                .addPath(new Path(new BezierLine(pose8, pose9)))
                .setLinearHeadingInterpolation(pose8.getHeading(), pose9.getHeading())

                // Path 10: Final return to launch
                .addPath(new Path(new BezierLine(pose9, pose10)))
                .setLinearHeadingInterpolation(pose9.getHeading(), pose10.getHeading())
                .addParametricCallback(0.98, () -> {
                    if (!shootingInProgress) {
                        follower.pausePathFollowing();
                        actionTimer.resetTimer();
                        shootingInProgress = true;
                        currentCallback = 4;
                    }
                })

                .setTranslationalConstraint(5)
                .setTimeoutConstraint(750)
                .setVelocityConstraint(5)
                .build();
    }

    @Override
    public void initInternal() {
        robot = RobotImpl.getInstance(hardwareMap);
        drive = new PedroDrive(hardwareMap);
        transfer = hardwareMap.get(DcMotorEx.class, "transfer");
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
        unstarted=true;
    }

    @Override
    protected void loopInternal() {
        if(!shootingInProgress) {
            robot.intake.run(1);
        }
        // Handle shooting action
        shoot();

        follower.update();

        if (unstarted) {
            follower.followPath(path, true);
            unstarted = false;
        }

        telemetryPro.addData("x", follower.getPose().getX());
        telemetryPro.addData("y", follower.getPose().getY());
        telemetryPro.addData("Shooting", shootingInProgress);
        telemetryPro.addData("Current Callback", currentCallback);
        telemetryPro.addData("Timer", actionTimer.getElapsedTimeSeconds());
    }
}