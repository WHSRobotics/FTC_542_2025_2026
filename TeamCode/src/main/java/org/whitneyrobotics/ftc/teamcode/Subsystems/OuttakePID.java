package org.whitneyrobotics.ftc.teamcode.Subsystems;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;
import org.whitneyrobotics.ftc.teamcode.Libraries.Controllers.ControlConstants;
import org.whitneyrobotics.ftc.teamcode.Libraries.Controllers.PIDController;
import org.whitneyrobotics.ftc.teamcode.Libraries.Motion.MotionProfileTrapezoidal;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.NanoStopwatch;
public class OuttakePID {
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(3450, 0, 150, 0);

    public DcMotorEx outtakeMotor;
    
    public double v1 = 1390;
    public double v2 = 1670;

    //    double lastTicks;
//    double currentTicks;
//
//    double wheelCircumference=7238.2294;
//    double totalTicks=537.7;
//    double lastTime;
//    double currentTime;
    public OuttakePID(HardwareMap hardwareMap) {
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtake");

        // Reverse as appropriate
        // outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Turns on bulk reading
        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        // RUE limits max motor speed to 85% by default
        // Raise that limit to 100%
        MotorConfigurationType motorConfigurationType = outtakeMotor.getMotorType().clone();
        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
        outtakeMotor.setMotorType(motorConfigurationType);

        // Turn on RUN_USING_ENCODER
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set PIDF Coefficients with voltage compensated feedforward value
        outtakeMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(
                MOTOR_VELO_PID.p, MOTOR_VELO_PID.i, MOTOR_VELO_PID.d,
                MOTOR_VELO_PID.f * 12 / hardwareMap.voltageSensor.iterator().next().getVoltage()
        ));
    }
    public void run(double velocity){
        outtakeMotor.setVelocity(velocity);
    }

}