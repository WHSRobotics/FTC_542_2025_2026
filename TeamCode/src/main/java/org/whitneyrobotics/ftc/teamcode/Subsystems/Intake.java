package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

public class Intake {
    public DcMotorEx intakeMotor;
    public boolean buttonToggle = true;

    public Intake(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
    }

    public void run(GamepadEx gp){
        if(buttonToggle){
            buttonMode();
        } else{
           joystickMode(-gp.LEFT_STICK_Y.value());
        }
    }

    public void changeMode(){
        buttonToggle = !buttonToggle;
    }

    public void buttonMode(){
        if(intakeMotor.getPower() == 1){
            intakeMotor.setPower(0);
        } else {
            intakeMotor.setPower(1);
        }
    }

    public void joystickMode(double power){
        if(power<0){power = 0;}
        intakeMotor.setPower(power);
    }
}
