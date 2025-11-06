package org.whitneyrobotics.ftc.teamcode.OpMode.TeleOp;

import static org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.RumbleEffects.endgame;
import static org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.RumbleEffects.matchEnd;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.LineItem;
import org.whitneyrobotics.ftc.teamcode.Subsystems.RobotImpl;
import org.whitneyrobotics.ftc.teamcode.pedroPathing.PedroDrive;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.UnaryOperator;

@TeleOp(name = "1DecodeTeleop")
public class DecodeTeleop extends OpModeEx {

    RobotImpl robot;
//    private int motif;
//    private String motifTxt;

    @Override
    public void initInternal() {
        robot = RobotImpl.getInstance(hardwareMap);
        robot.drive.setStartingPose(new Pose(0,0,robot.alliance.headingAngle));

//        motifTxt="None";
//        motif=-1;
        robot.teleOpInit();
        setupNotifications();
    }

    void setupNotifications() {
        addTemporalCallback(resolve -> {
            gamepad1.getEncapsulatedGamepad().runRumbleEffect(endgame);
            gamepad2.getEncapsulatedGamepad().runRumbleEffect(endgame);
            telemetryPro.addLine("Endgame approaching soon!", LineItem.Color.ROBOTICS, LineItem.RichTextFormat.BOLD).persistent();
            resolve.accept(!gamepad1.getEncapsulatedGamepad().isRumbling() &&
                    gamepad2.getEncapsulatedGamepad().isRumbling());
        }, 85000);

        addTemporalCallback(resolve -> {
            telemetryPro.removeLineByCaption("Endgame approaching soon!");
            resolve.accept(true);
        },90000);

        addTemporalCallback(resolve -> {
            //playSound("matchend",100f);
            gamepad1.getEncapsulatedGamepad().runRumbleEffect(matchEnd);
            gamepad2.getEncapsulatedGamepad().runRumbleEffect(matchEnd);
            telemetryPro.addLine("Match ends in 5 seconds!", LineItem.Color.FUCHSIA, LineItem.RichTextFormat.BOLD).persistent();
            resolve.accept(!gamepad1.getEncapsulatedGamepad().isRumbling() &&
                    gamepad2.getEncapsulatedGamepad().isRumbling());
        }, 113000);

        addTemporalCallback(resolve -> {
            //playSound("slay",100f);
            telemetryPro.removeLineByCaption("Match ends in 5 seconds!");
            telemetryPro.addLine("Match has ended.", LineItem.Color.LIME, LineItem.RichTextFormat.ITALICS).persistent();
            resolve.accept(true);
        },120000);
    }

    @Override
    public void start() {
        robot.drive.startDrive();
    }

    @Override
    protected void loopInternal() {
        //drive
        gamepad1.BUMPER_RIGHT.onPress(() -> robot.drive.driveMode());
        gamepad1.BUMPER_LEFT.onPress(() -> robot.drive.changeScalar());
        robot.drive.update(gamepad1);
        telemetryPro.addData("robotCentric: ", robot.drive.getDriveMode());
//        telemetryPro.addData("x: ", robot.drive.getPose().getX());
//        telemetryPro.addData("y: ", robot.drive.getPose().getY());
//        telemetryPro.addData("heading: ", robot.drive.getPose().getHeading());
        telemetryPro.addData("drive scalar: ", robot.drive.getScalar());
        telemetryPro.addData("alliance: ", robot.alliance);

        gamepad1.SQUARE.onPress(() -> {
            robot.switchAlliance();
            robot.drive.setStartingPose(new Pose(0,0,robot.alliance.headingAngle));
        });

        //subsystems
        //reverse
        if(gamepad2.CROSS.value()){
            robot.systemScalar = -1;
        } else{
            robot.systemScalar = 1;
        }

        //transfer
        if(gamepad2.LEFT_TRIGGER.value()>0){
            robot.transfer.run(robot.systemScalar);
        } else{
            robot.transfer.stop();
        }

        //compression (on hold)
//        if (gamepad2.CROSS.value()) {
//            robot.comp.run();
//        } else{
//            robot.comp.stop();
//        }

        //intake (on hold)
        if(gamepad2.RIGHT_TRIGGER.value()>0){
            robot.intake.run(robot.systemScalar);
        } else{
            robot.intake.stop();
        }

        //outtake
        gamepad2.CIRCLE.onPress(() -> {
            robot.outtake.run(robot.systemScalar);
        });
        gamepad2.TRIANGLE.onPress(() -> {
            robot.outtake.runSlow(robot.systemScalar);
        });
//        if(gamepad2.LEFT_STICK_Y.value()!=0){
//            robot.outtake.runJoystick(gamepad2.LEFT_STICK_Y.value());
//        }
        telemetryPro.addData("outtake speed: ", robot.outtake.getPower());
//        robot.ll.showAprilTags(0);
//        Map<Integer, ArrayList<Double>> aprilTags = robot.ll.showAprilTags(0);
//        for(Map.Entry<Integer,ArrayList<Double>> aprilTag : aprilTags.entrySet()){
//            ArrayList<Double> aprilTagValues=aprilTag.getValue();
//            telemetryPro.addData(String.format("Polar Coordinates to AprilTag %s",aprilTag.getKey()),robot.ll.getDepotValues(aprilTagValues.get(0).floatValue(),aprilTagValues.get(2).floatValue(),0));
//        }
//        if(aprilTags.containsKey(21)){
//            motif=0;
//            motifTxt="GPP";
//        }else if(aprilTags.containsKey(22)){
//            motif=1;
//            motifTxt="PGP";
//        }else if(aprilTags.containsKey(23)) {
//            motif = 2;
//            motifTxt="PPG";
//        }
//        telemetryPro.addData("Motif",motifTxt);
//        telemetryPro.addData("Motif Number",motif);
        telemetryPro.update();
    }
}
