package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

public class Intake {
    public DcMotorEx intakeMotor;

    public Intake(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
    }

    public void run(GamepadEx gp){
        gp.CIRCLE.onPress(() -> {
            if(intakeMotor.getPower() == 0){
                intakeMotor.setPower(-1);
            } else {
                intakeMotor.setPower(0);
            }
        });
    }
}
