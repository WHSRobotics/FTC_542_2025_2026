package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class Turret {
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(3450, 0, 150, 0);

    public DcMotorEx rotate;
    public DcMotorEx outtakeMotor;
    public Servo hood;
    enum Levels {
        ONE(0),
        TWO(0.1),
        THREE(0.2);

        public final double pos;
        Levels(double pos){
            this.pos = pos;
        }
    }

    public Turret(HardwareMap hardwareMap){
        rotate=hardwareMap.get(DcMotorEx.class,"rotate");
        outtakeMotor=hardwareMap.get(DcMotorEx.class,"outtakeMotor");
        hood=hardwareMap.get(Servo.class,"hood");

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
    public void rotate(double power){
        rotate.setPower(power);
    }
    public void hood(){
        hood.equals()
    }
}
