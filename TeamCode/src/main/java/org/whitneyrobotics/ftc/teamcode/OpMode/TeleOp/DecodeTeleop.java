package org.whitneyrobotics.ftc.teamcode.OpMode.TeleOp;

import static com.sun.tools.javac.jvm.ByteCodes.error;
import static org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.RumbleEffects.endgame;
import static org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.RumbleEffects.matchEnd;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.whitneyrobotics.ftc.teamcode.Constants.Alliance;
import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.LineItem;
import org.whitneyrobotics.ftc.teamcode.Subsystems.RobotImpl;
import org.whitneyrobotics.ftc.teamcode.pedroPathing.PedroDrive;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.UnaryOperator;

@Configurable
@TeleOp(name = "1DecodeTeleop")
public class DecodeTeleop extends OpModeEx {

    RobotImpl robot;
    static double upPos=0.2;
    static double downPos=0.6;
//    private int motif;
//    private String motifTxt;

    private boolean has_rumbled = false;
    public DcMotorEx transfer;
    public int transferTargetVelocity=0;

    @Override
    public void initInternal() {
        robot = RobotImpl.getInstance(hardwareMap);
        transfer = hardwareMap.get(DcMotorEx.class, "transfer");
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
        telemetryPro.addData("drive scalar: ", robot.drive.getScalar());

        //alliance
        gamepad1.SQUARE.onPress(() -> {
            robot.switchAlliance();
            robot.drive.setStartingPose(new Pose(0,0,robot.alliance.headingAngle));
        });
        telemetryPro.addData("alliance: ", robot.alliance);

        //reverse
        if(gamepad2.CROSS.value()){
            robot.systemScalar = -1;
        } else{
            robot.systemScalar = 1;
        }

        //intake (on hold)
        if(gamepad2.RIGHT_TRIGGER.value()>0){
            robot.intake.run(robot.systemScalar);
        } else{
            robot.intake.stop();
        }

        //outtake
        gamepad2.CIRCLE.onPress(() -> {
//            robot.outtake.power = robot.ll.getSpeed(robot.alliance == Alliance.RED,0);
//            if(robot.outtake.power == 0.6){
//                robot.targetOuttakeVelocity = 1390;
//            } else if(robot.outtake.power == 0.7){
//                robot.targetOuttakeVelocity = 1570;
//            } else if(robot.outtake.power == 0.8){
//                robot.targetOuttakeVelocity = 1760;
//            } else if(robot.outtake.power == 1){
//                robot.targetOuttakeVelocity = 2190;
//            } else{
//                robot.targetOuttakeVelocity = 0;
//            }
            if(robot.targetOuttakeVelocity==0){
                robot.targetOuttakeVelocity = -robot.outtakev2;
            }else{
                robot.targetOuttakeVelocity = 0;
            }
        });
        gamepad2.TRIANGLE.onPress(() -> {
            if(robot.targetOuttakeVelocity==0) {
                robot.targetOuttakeVelocity = -robot.outtakev1;
            }else{
                robot.targetOuttakeVelocity = 0;
            }
        });
        gamepad2.DPAD_UP.onPress(()->{
            if(transferTargetVelocity==0){
                transferTargetVelocity = -1;
            }else{
                transferTargetVelocity = 0;
            }
        });
        gamepad2.DPAD_DOWN.onPress(()->{
            if(transferTargetVelocity==0){
                transferTargetVelocity = 1;
            }else{
                transferTargetVelocity = 0;
            }
        });
        transfer.setPower(transferTargetVelocity);
        robot.turret.run(robot.targetOuttakeVelocity);
        robot.turret.rotate(-gamepad2.LEFT_STICK_X.value());

        if(Math.abs(robot.targetOuttakeVelocity - robot.turret.outtake.getVelocity()) <= 20){
            telemetryPro.addData("ReACHED", true, LineItem.Color.YELLOW, LineItem.RichTextFormat.ITALICS, LineItem.RichTextFormat.BOLD);
            gamepad2.Vibrate(250);
        }
//        double error = Math.abs(robot.outtake.getVelocity() - robot.targetOuttakeVelocity);
//        telemetryPro.addData("error: ", error);
//        if (error< 40) {
//            // Logic:
//            gamepad2.Vibrate(250);
//            has_rumbled = true;
//            telemetryPro.addData("max reached", true);
//        }else{
//            has_rumbled = false;
//        }
//
//        telemetryPro.addData("RUMBLED???:",has_rumbled);
        //robot.notifier.notify_if_at_speed(gamepad2, robot.outtake.getVelocity(), robot.targetOuttakeVelocity);

//        Map<Integer, ArrayList<Double>> aprilTags = robot.ll.showAprilTags(0);
//        for(Map.Entry<Integer,ArrayList<Double>> aprilTag : aprilTags.entrySet()) {
//            ArrayList<Double> aprilTagValues = aprilTag.getValue();
//            telemetryPro.addData(String.format("AprilTag %s Values", aprilTag.getKey()), aprilTagValues);
//            telemetryPro.addData(String.format("Values to AprilTag %s", aprilTag.getKey()), robot.ll.getDepotValues(aprilTagValues.get(0).floatValue(), aprilTagValues.get(1).floatValue(), aprilTagValues.get(2).floatValue(), 0));
//        }
        telemetryPro.addData("outtake speed: ", robot.turret.outtake.getVelocity());

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
//        telemetryPro.addData("Motif Number",motif);`

        telemetryPro.addData("subsytem reverse: ", robot.systemScalar);
        telemetryPro.update();
    }
}
