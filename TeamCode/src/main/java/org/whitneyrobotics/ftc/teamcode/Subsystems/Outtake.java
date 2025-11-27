package org.whitneyrobotics.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

public class Outtake {
    public DcMotorEx outtakeMotor;
    public double power = 0.8;
    public double v1 = 1390;
    public double v2 = 1670;
    public boolean isOn = false;

    public Outtake(HardwareMap hardwareMap) {
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtake");
    }

    public void run(double scalar, double power) {
        if (outtakeMotor.getPower() == 0) {
            outtakeMotor.setPower(power * scalar);
        } else {
            outtakeMotor.setPower(0);
        }
    }

    public void runVelocity(double scalar, double velocity){
        if(!isOn){
            outtakeMotor.setVelocity(velocity * scalar);
            isOn = true;
        } else{
            outtakeMotor.setVelocity(0);
            isOn = false;
        }
    }


    public void resetEncoders(){
        outtakeMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public double getVelocity(){
        return outtakeMotor.getVelocity();
    }

    public void setPower(double power){
        outtakeMotor.setPower(power);
    }

    public void setVelocity(double velocity){
        outtakeMotor.setVelocity(velocity);
    }
}