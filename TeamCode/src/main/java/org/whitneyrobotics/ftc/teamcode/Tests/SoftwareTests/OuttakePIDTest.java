package org.whitneyrobotics.ftc.teamcode.Tests.SoftwareTests;
//
//import com.bylazar.configurables.annotations.Configurable;
//import com.bylazar.configurables.annotations.IgnoreConfigurable;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import org.whitneyrobotics.ftc.teamcode.Subsystems.OuttakePID;
//
//@Configurable
//@TeleOp(name = "OuttakePID Test", group = "Test")
//public class OuttakePIDTest extends OpMode {
//
//    private OuttakePID outtake;
//
//    public static double p = 0.005;
//    public static double i = 0.0;
//    public static double d = 0.00028;
//    public static double f = 0.0;
//
//    public static double vMax = 2350;
//    public static double aMax = 700;
//
//
//    public static double velocity1 = 1390;
//    public static double velocity2 = 1670;
//    public static double customVelocity = 1500;
//
//
//    @Override
//    public void init() {
//        outtake = new OuttakePID(hardwareMap);
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.addLine("Controls:");
//        telemetry.addLine("A: Set velocity to v1 (1390)");
//        telemetry.addLine("B: Set velocity to v2 (1670)");
//        telemetry.addLine("X: Custom velocity (1500)");
//        telemetry.addLine("Y: Stop velocity control");
//        telemetry.addLine("Left Bumper: Reset encoders");
//        telemetry.addLine("Right Bumper: Manual power mode");
//        telemetry.update();
//    }
//
//    @Override
//    public void loop() {
//        // Update PID constants from dashboard panels
//
//        // Always call update for PID control
//        outtake.update();
//
//        // Button controls
//        if (gamepad1.a) {
//            outtake.setTargetVelocity(velocity1);
//            telemetry.addData("Action", "Set target velocity to V1: " + velocity1);
//        }
//
//        if (gamepad1.b) {
//            outtake.setTargetVelocity(velocity2);
//            telemetry.addData("Action", "Set target velocity to V2: " + velocity2);
//        }
//
//        if (gamepad1.x) {
//            outtake.setTargetVelocity(customVelocity);
//            telemetry.addData("Action", "Set target velocity to CUSTOM: " + customVelocity);
//        }
//
//        if (gamepad1.y) {
//            outtake.setTargetVelocity(0); // Stop
//            telemetry.addData("Action", "Stopping velocity control");
//        }
//
//        if (gamepad1.left_bumper) {
//            outtake.resetEncoders();
//            telemetry.addData("Action", "Reset encoders");
//        }
//
//        // Manual power control with right stick
//        if (gamepad1.right_bumper) {
//            double power = -gamepad1.right_stick_y * 0.8 * 1800; // Negative for correct direction
//            outtake.outtakeMotor.setVelocity(power);
//            telemetry.addData("Action", "Manual power: " + power);
//        }
//
//        // Test built-in velocity control
//        if (gamepad1.dpad_up) {
//            outtake.runVelocity(1.0, 1000);
//            telemetry.addData("Action", "Built-in velocity control: 1000");
//        }
//
//        if (gamepad1.dpad_down) {
//            outtake.run(1.0, 0.5);
//            telemetry.addData("Action", "Power control: 0.5");
//        }
//
//        // Telemetry
//        telemetry.addData("Current Velocity", "%.1f ticks/sec", outtake.getVelocity());
//        telemetry.addData("Motor Power", "%.3f", outtake.outtakeMotor.getPower());
//        telemetry.addData("Is On", outtake.isOn);
//
//        // PID tuning values from panels
//        telemetry.addLine();
//        telemetry.addData("PID Values", "P=%.4f, I=%.4f, D=%.6f",
//                p, i, d);
//        telemetry.addData("Feedforward", "F=%.6f", f);
//        telemetry.addData("Motion Profile", "V_MAX=%.0f, A_MAX=%.0f",
//                vMax, aMax);
//        telemetry.addData("Target velocity",outtake.getTargetVelocity());
//        telemetry.addData("Starting velocity",outtake.getStartingVelocity());
//        telemetry.addData("Output to Motor",outtake.outputToMotor);
//        telemetry.addData("Error",outtake.publicError);
//        telemetry.addData("Desired Velocity",outtake.publicDesVel);
//        telemetry.addData("Current Read Velocity",outtake.publicCurrentVel);
//        telemetry.addData("Motion Profile",outtake.publicProfile);
//        telemetry.addData("Elapsed Time",outtake.publicReadTime);
//        telemetry.addData("Motion Profile Goal",outtake.publicGoal);
//        telemetry.addData("Motion Profile Time",outtake.publicMotionProfileTime);
//        telemetry.addData("Motion Profile Goal at Set Target Velocity",outtake.publicReadGoalAtSet);
//        telemetry.addData("Target Velocity at Set Target Velocity",outtake.publicReadVelFromSet);
//        telemetry.addData("Velocity Change",outtake.publicChange);
//        telemetry.addData("PID Output",outtake.publicPidOutput);
//        telemetry.update();
//    }
//
//    @Override
//    public void stop() {
//        outtake.setPower(0);
//    }
//}