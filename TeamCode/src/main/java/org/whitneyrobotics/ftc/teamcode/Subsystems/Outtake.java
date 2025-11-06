package org.whitneyrobotics.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

public class Outtake {
    public DcMotorEx outtakeMotor;

    public Outtake(HardwareMap hardwareMap) {
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtake");
    }

    public void run(double scalar) {
        if (outtakeMotor.getPower() == 0) {
            outtakeMotor.setPower(1 * scalar);
        } else {
            outtakeMotor.setPower(0);
        }
    }

    public void runSlow(double scalar){
        if(outtakeMotor.getPower() == 0){
            outtakeMotor.setPower(0.6 * scalar);
        } else{
            outtakeMotor.setPower(0);
        }
    }

    public double getPower(){
        return outtakeMotor.getPower();
    }

    public void runJoystick(double power){
        outtakeMotor.setPower(power);
    }
}