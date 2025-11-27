package org.whitneyrobotics.ftc.teamcode.Tests.SubsystemTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.whitneyrobotics.ftc.teamcode.Subsystems.Outtake;
import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;

@TeleOp(name = "OuttakeVelocityTest")
public class OuttakeVelocityTest extends OpModeEx {
    Outtake outtake;

    @Override
    public void initInternal() {
        outtake = new Outtake(hardwareMap);
    }

    @Override
    protected void loopInternal() {
        gamepad2.CIRCLE.onPress(() -> {
            outtake.runVelocity(1, 1390);
        });
        telemetryPro.addData("velocity: ", outtake.getVelocity());
    }
}
