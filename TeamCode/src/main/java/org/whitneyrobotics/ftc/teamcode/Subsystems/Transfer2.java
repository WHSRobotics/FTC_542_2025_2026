package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

public class Transfer2 {
    public CRServo t2l, t2r;

    public Transfer2(HardwareMap hardwareMap) {
        t2l = hardwareMap.get(CRServo.class, "t2l");
        t2r = hardwareMap.get(CRServo.class, "t2r");
    }

    public void run(double scalar) {
        t2l.setPower(-1 * scalar);
        t2r.setPower(1 * scalar);
    }

    public void stop(){
        t2l.setPower(0);
        t2r.setPower(0);
    }
}
