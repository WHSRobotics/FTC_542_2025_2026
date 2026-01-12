package org.whitneyrobotics.ftc.teamcode.Tests.SoftwareTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;

import java.util.ArrayList;

@TeleOp(name="indiv motor")
public class IndivMotor extends OpModeEx {
    public DcMotorEx fr;
    public DcMotorEx fl;
    public DcMotorEx br;
    public DcMotorEx bl;
    public ArrayList<DcMotorEx> motors;
    public ArrayList<String> motorNames;
    public int index;
    @Override
    public void initInternal() {
        fr=hardwareMap.get(DcMotorEx.class,"fr");
        fl=hardwareMap.get(DcMotorEx.class,"fl");
        br=hardwareMap.get(DcMotorEx.class,"br");
        bl=hardwareMap.get(DcMotorEx.class,"bl");
        motors.add(fr);
        motors.add(fl);
        motors.add(br);
        motors.add(bl);
        motorNames.add("fr");
        motorNames.add("fl");
        motorNames.add("br");
        motorNames.add("bl");
        index=0;
    }

    @Override
    protected void loopInternal() {
        gamepad1.BUMPER_RIGHT.onPress(()->{
            index=(index+1)%3;
        });
        gamepad1.DPAD_UP.onPress(()->{
            motors.get(index).setPower(1);
        });
        gamepad1.DPAD_DOWN.onPress(()->{
            motors.get(index).setPower(-1);
        });
        telemetryPro.addData("motor",motorNames.get(index));
    }
}
