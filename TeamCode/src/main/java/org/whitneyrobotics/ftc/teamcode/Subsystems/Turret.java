package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.whitneyrobotics.ftc.teamcode.Libraries.Controllers.ControlConstants;
import org.whitneyrobotics.ftc.teamcode.Libraries.Controllers.PIDController;

import java.util.ArrayList;

public class Turret {
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(3450, 0, 150, 0);

    public DcMotorEx rotate;
    public DcMotorEx outtakeMotor;

    private ControlConstants pidConstants;
    private PIDController pid;
    public LimelightImpl ll;

    public Turret(HardwareMap hardwareMap,double p,double i,double d){
        rotate=hardwareMap.get(DcMotorEx.class,"rotate");
        outtakeMotor=hardwareMap.get(DcMotorEx.class,"outtakeMotor");
        pidConstants = new ControlConstants(p, i, d);
        pid=new PIDController(pidConstants);
        ll=new LimelightImpl(hardwareMap);
//        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
//            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
//        }
//
//        // RUE limits max motor speed to 85% by default
//        // Raise that limit to 100%
//        MotorConfigurationType motorConfigurationType = outtakeMotor.getMotorType().clone();
//        motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
//        outtakeMotor.setMotorType(motorConfigurationType);
//
//        // Turn on RUN_USING_ENCODER
//        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        // Set PIDF Coefficients with voltage compensated feedforward value
//        outtakeMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(
//                MOTOR_VELO_PID.p, MOTOR_VELO_PID.i, MOTOR_VELO_PID.d,
//                MOTOR_VELO_PID.f * 12 / hardwareMap.voltageSensor.iterator().next().getVoltage()
//        ));
    }
    public void run(double velocity){
        outtakeMotor.setPower(velocity);
    }
    public void rotate(double power){
        rotate.setPower(power);
    }
    public void update(){
        double tx=ll.getShootingAngle(true);

        double error = 0 - tx;

        pid.calculate(error);

        double power =  Math.abs(error) < 0.5 ? 0 : pid.getOutput();;


        rotate.setPower(power);
    }
}
