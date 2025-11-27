package org.whitneyrobotics.ftc.teamcode.Tests.SubsystemTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;


@TeleOp(name = "VibratePad")

public class VibratePad extends OpModeEx {
    @Override
    public void initInternal() {

    }

    @Override
    protected void loopInternal() {
        gamepad1.Vibrate(200000);
    }
}
