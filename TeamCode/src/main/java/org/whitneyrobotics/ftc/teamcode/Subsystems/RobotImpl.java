package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.pedropathing.geometry.Pose;

import org.whitneyrobotics.ftc.teamcode.Constants.Alliance;
import org.whitneyrobotics.ftc.teamcode.pedroPathing.PedroDrive;

public class RobotImpl {

    public static Pose poseMemory = new Pose(0, 0, 0);
    private static RobotImpl instance = null;
    public Alliance alliance = Alliance.RED;
    //devices
    public PedroDrive drive;
    public Intake intake;

    private RobotImpl(HardwareMap hardwareMap) {
        drive = new PedroDrive(hardwareMap);
        //devices
        intake = new Intake(hardwareMap);
    }

    public void switchAlliance(){
        alliance = alliance == Alliance.RED ? Alliance.BLUE : Alliance.RED;
    }

    public void teleOpInit(){
        Pose poseMemory = drive.getPose();
    }

    public static RobotImpl getInstance(){
        return instance;
    }

    public static RobotImpl getInstance(HardwareMap hardwareMap){
        init(hardwareMap);
        return instance;
    }

    public static void init(HardwareMap hardwareMap) {
        instance = new RobotImpl(hardwareMap);
    }

//    public void update(){
//        //update devices
//    }
}
