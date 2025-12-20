package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Impulse {
    private DcMotorEx impulse;
    public int launchedFinalPos=100;
    public boolean isLaunching;
    public Impulse(HardwareMap hardwareMap){
        impulse=hardwareMap.get(DcMotorEx.class,"impulse");
        impulse.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        impulse.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        impulse.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        impulse.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void charge(){
        impulse.setTargetPosition(launchedFinalPos);
        impulse.setPower(-1);
    }
    public void release(){
        impulse.setPower(0);
    }
    public void launch(){
        impulse.setTargetPosition(-1*launchedFinalPos);
        impulse.setPower(-1);
        isLaunching=true;
    }
    public void returnToZero(){
        impulse.setTargetPosition(0);
        impulse.setPower(1);
        isLaunching=false;
    }
    public boolean fullyLaunched(){
        return (!(impulse.isBusy()) && isLaunching);
    }
}
