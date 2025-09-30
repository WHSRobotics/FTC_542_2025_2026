//package org.firstinspires.ftc.teamcode.Tests.FeatureTests;
//
//import static org.firstinspires.ftc.teamcode.Constants.Alliance.*;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.Constants.Alliance;
//import org.OpModeEx.Extensions.teamcode.ftc.whitneyrobotics.OpModeEx;
//import org.TelemetryPro.Extensions.teamcode.ftc.whitneyrobotics.LineItem;
//import org.firstinspires.ftc.teamcode.Subsystems.AllianceSensor;
//import org.Tests.teamcode.ftc.whitneyrobotics.Test;
//
//@Test(name="Smart Marker Test", description="Press A or use the magnetic limit switches to automatically detect and switch to an alliance. Press B to clear telemetry.")
//@TeleOp(name="Smart Marker Test", group="Feature Tests")
//public class SmartMarkers extends OpModeEx {
//    AllianceSensor allianceSensor;
//    public static Alliance alliance = RED;
//
//    @Override
//    public void initInternal() {
//        allianceSensor = new AllianceSensor(hardwareMap);
//        alliance = allianceSensor.isRedAlliance() ? RED : BLUE;
//        telemetryPro.addData("Initial Alliance", alliance.name(), alliance == RED ? LineItem.Color.RED : LineItem.Color.BLUE).persistent();
//        allianceSensor.onChange(isRed -> {
//            alliance = isRed ? RED : BLUE;
//            telemetryPro.addData("Alliance changed to", alliance.name(), alliance == RED ? LineItem.Color.RED : LineItem.Color.BLUE).persistent();
//            playSound("chime");
//        });
//        gamepad1.A.onPress(() -> {
//            alliance = alliance == RED ? BLUE : RED;
//            telemetryPro.addData("Alliance manually changed to", alliance.name(), alliance == RED ? LineItem.Color.RED : LineItem.Color.BLUE, LineItem.RichTextFormat.ITALICS).persistent();
//        });
//        gamepad1.B.onPress(telemetryPro::clear);
//    }
//
//    @Override
//    public void initInternalLoop(){
//        allianceSensor.update();
//    }
//
//    @Override
//    protected void loopInternal() {
//        allianceSensor.update();
//    }
//}
