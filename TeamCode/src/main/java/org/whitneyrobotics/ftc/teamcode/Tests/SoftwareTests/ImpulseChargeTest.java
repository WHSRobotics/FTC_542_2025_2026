package org.whitneyrobotics.ftc.teamcode.Tests.SoftwareTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Impulse;

@TeleOp(name= "impulse charge")
public class ImpulseChargeTest extends OpModeEx {
    Impulse impulse;
    @Override
    public void initInternal() {
        impulse=new Impulse(hardwareMap);
    }

    @Override
    protected void loopInternal() {
        gamepad1.X.onPress(()->{
            impulse.charge();
        });
        gamepad1.Y.onPress(()->{
            impulse.release();
        });
    }
}
