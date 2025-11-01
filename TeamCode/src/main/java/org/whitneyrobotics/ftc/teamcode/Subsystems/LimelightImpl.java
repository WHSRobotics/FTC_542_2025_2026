package org.whitneyrobotics.ftc.teamcode.Subsystems;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LimelightImpl {
    Limelight3A limelight;
    public LimelightImpl(HardwareMap hardwareMap){
        limelight=hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(60);
        limelight.start();
    }
    //current apriltag pipeline is 0
    public Map<Integer, ArrayList<Double>> showAprilTags(int aprilTagPipeline){
        limelight.pipelineSwitch(aprilTagPipeline);
        LLResult result = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
        Map<Integer, ArrayList<Double>> aprilTags=new HashMap<Integer,ArrayList<Double>>();
        for (LLResultTypes.FiducialResult fiducial : fiducials) {
            ArrayList<Double> addList=new ArrayList<Double>();
            addList.add(fiducial.getTargetXDegrees());
            addList.add(fiducial.getTargetYDegrees());
            addList.add(fiducial.getTargetArea());
            aprilTags.put(fiducial.getFiducialId(),addList);
        }
        return aprilTags;
    }
    public LLResult getColorBlobOutput(int colorPipeLine) {
        limelight.pipelineSwitch(colorPipeLine);
        LLResult result = limelight.getLatestResult();
        return result;
    }
    public ArrayList<Float> getDepotValues(float tx, float td, float camAngle){
        ArrayList<Float> values=new ArrayList<>();
        float convFactor=(float) (6.33/Math.sqrt(td*307200));
        float robotDistToDepotStraight=(float) (0.5*(240*convFactor+320*convFactor)*Math.tan(Math.toRadians(65.875)));
        float robotDistToDepot=(float) (robotDistToDepotStraight/Math.cos(Math.toRadians(tx)));
        values.add(tx);
        values.add(robotDistToDepot);
        return values;
    }
    public ArrayList<Float> getDepotVector(float tx,float ty,float td,float camAngle,float camOffsetFromCenter, float camHeight){
        ArrayList<Float> values=new ArrayList<>();
        float camDistToScore= (float) (79.1587-41*Math.log(td));
        float robotDistToDepot= (float) (camDistToScore*Math.cos(camAngle));
        values.add(robotDistToDepot);
        values.add(tx+camOffsetFromCenter);
        values.add(ty+camHeight);
        return values;
    }
}
