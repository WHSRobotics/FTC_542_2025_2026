package org.whitneyrobotics.ftc.teamcode.Tests.SubsystemTests;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Turret;
@Configurable
@TeleOp(name="turret test")
public class TurretTest extends OpModeEx {
    private Turret turret;
    private int v1=2000;
    private int v2=4000;
    public static double kP=1;
    public static double kI=0;
    public static double kD=0;
    private double targetVelocity=0;
    @Override
    public void initInternal() {
        turret=new Turret(hardwareMap,kP,kI,kD);
    }

    @Override
    protected void loopInternal() {
        gamepad1.CIRCLE.onPress(()->{
            targetVelocity=0.5;
        });
        gamepad1.TRIANGLE.onPress(()->{
            targetVelocity=1;
        });
        gamepad1.DPAD_DOWN.onPress(()->{
            targetVelocity=0;
        });
        turret.run(targetVelocity);
        turret.update();
//        turret.rotate(gamepad1.LEFT_STICK_X.value());
    }
}
