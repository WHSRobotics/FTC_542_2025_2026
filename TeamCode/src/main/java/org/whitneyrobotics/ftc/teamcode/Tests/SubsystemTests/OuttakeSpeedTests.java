package org.whitneyrobotics.ftc.teamcode.Tests.SubsystemTests;
//
//import com.pedropathing.geometry.Pose;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
//import org.whitneyrobotics.ftc.teamcode.Subsystems.Outtake;
//import org.whitneyrobotics.ftc.teamcode.Subsystems.RobotImpl;
//import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//
//
//
//@TeleOp(name = "OuttakeSpeedTests")
//public class OuttakeSpeedTests extends OpModeEx {
//
//    private RobotImpl robot;
//    private double outtakePower;
//
//
//    @Override
//    public void initInternal() {
//        robot = RobotImpl.getInstance(hardwareMap);
//        robot.drive.setStartingPose(new Pose(0,0,robot.alliance.headingAngle));
//        telemetryPro.addLine("Robot is ready");
//    }
//
//    @Override
//    public void start(){
//        robot.drive.startDrive();
//        robot.drive.driveMode();
//    }
//
//    @Override
//    protected void loopInternal() {
//        // drive
//        robot.drive.update(gamepad1);
//
//        // outtake: raw input from right joystick
////        double manualPower = -gamepad1.RIGHT_STICK_Y.value();
//        // outtake: presets
//        gamepad1.SQUARE.onPress(() -> {
//            outtakePower = 0.6;
//        });
//        gamepad1.TRIANGLE.onPress(() ->{
//            outtakePower = 0.7;
//        });
//        gamepad1.CIRCLE.onPress(() ->{
//            outtakePower = 0.8;
//        });
//        gamepad1.CROSS.onPress(() ->{
//            outtakePower = 0.9;
//        });
//        gamepad1.BUMPER_RIGHT.onPress(() ->{
//            outtakePower = 0;
//            robot.outtake.setPower(outtakePower);
//        });
//
//        if(gamepad1.DPAD_DOWN.value()){
//            robot.outtake.setPower(outtakePower);
//        }
//
//        telemetryPro.addData("Outtake Power", outtakePower);
//        telemetryPro.addData("Outtake Velocity: ", robot.outtake.getVelocity() + "ticks per second");
//
//        //transfer
//        if(gamepad2.LEFT_TRIGGER.value()>0){
//            robot.transfer.run(robot.systemScalar);
//        } else{
//            robot.transfer.stop();
//        }
//
//        //transfer2
//        if(gamepad2.DPAD_UP.value()){
//            robot.transfer2.run(robot.systemScalar);------------------------------------------------
//        } else{
//            robot.transfer2.stop();
//        }
//
//        //intake (on hold)
//        if(gamepad2.RIGHT_TRIGGER.value()>0){
//            robot.intake.run(robot.systemScalar);
//        } else{
//            robot.intake.stop();
//        }
//
//        telemetryPro.update();
//    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------}