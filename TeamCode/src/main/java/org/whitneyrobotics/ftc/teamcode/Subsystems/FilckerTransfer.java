package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FilckerTransfer {
    public Servo transfer;
    FilckerTransfer(HardwareMap hardwareMap){
        transfer= hardwareMap.get(Servo.class,"transfer");
    }
    public void up(double pos){
        transfer.setPosition(pos);
    }
    public void down(double pos){
        transfer.setPosition(pos);
    }
}
