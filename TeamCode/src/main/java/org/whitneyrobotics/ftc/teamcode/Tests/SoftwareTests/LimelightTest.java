package org.whitneyrobotics.ftc.teamcode.Tests.SoftwareTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.LimelightImpl;

import java.util.ArrayList;
import java.util.Map;

@TeleOp(name="llteleop")
public class LimelightTest extends OpModeEx {
    LimelightImpl ll;

    @Override
    public void initInternal() {
        ll=new LimelightImpl(hardwareMap);
    }

    @Override
    protected void loopInternal() {
        Map<Integer, ArrayList<Double>> aprilTags = ll.showAprilTags(0);
        for(Map.Entry<Integer,ArrayList<Double>> aprilTag : aprilTags.entrySet()){
            ArrayList<Double> aprilTagValues=aprilTag.getValue();
            telemetryPro.addData(String.format("AprilTag %s Values", aprilTag.getKey()),aprilTagValues);
            telemetryPro.addData(String.format("Values to AprilTag %s",aprilTag.getKey()),ll.getDepotValues(aprilTagValues.get(0).floatValue(),aprilTagValues.get(1).floatValue(),aprilTagValues.get(2).floatValue(),0));
        }
//        telemetryPro.addData("Speed",ll.getSpeed(true,15));
    }
}