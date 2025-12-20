package org.whitneyrobotics.ftc.teamcode.Tests.SoftwareTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Impulse;

@TeleOp(name="impulse")
public class ImpulseTest extends OpModeEx {

    Impulse impulse;
    @Override
    public void initInternal() {
        impulse=new Impulse(hardwareMap);
    }

    @Override
    protected void loopInternal() {
        gamepad1.X.onPress(()->{
            impulse.launch();
        });
        if(impulse.fullyLaunched()){
            impulse.returnToZero();
        }
    }
}
