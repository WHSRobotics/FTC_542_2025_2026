package org.whitneyrobotics.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;
import org.whitneyrobotics.ftc.teamcode.Libraries.Controllers.ControlConstants;
import org.whitneyrobotics.ftc.teamcode.Libraries.Controllers.PIDController;
import org.whitneyrobotics.ftc.teamcode.Libraries.Motion.MotionProfileTrapezoidal;
import org.whitneyrobotics.ftc.teamcode.Libraries.Utilities.NanoStopwatch;
public class OuttakePID {
    private PIDController controller;
    private MotionProfileTrapezoidal motionProfile;
    public static double p = 3450, i = 0, d = 150;
    public static double f = 0;
    public static double V_MAX = 2350; // max velocity in degrees/s
    public static double A_MAX = 700; // max acceleration in degrees/s^2
    private static final double ticksInDegrees = 700 / 180.0;
    private int initialPosition;
    private NanoStopwatch stopwatch;
    public DcMotorEx outtakeMotor;
    public double power = 0.8;
    public double v1 = 1390;
    public double v2 = 1670;
    public boolean isOn = false;

    // Add target velocity tracking
    private double targetVelocity = 0;
    private double startingVelocity = 0; // Track where we started from

    public double outputToMotor=0;
    public double publicError=0;
    public double publicProfile=0;
    public double publicDesVel=0;
    public double publicCurrentVel=0;
    public double publicReadTime=0;
    public double publicGoal=0;
    public double publicMotionProfileTime=0;
    public double publicChange=0;
    public double publicReadVelFromSet=0;
    public double publicReadGoalAtSet=0;
    public double publicPidOutput=0;
    private boolean firstUpdate=false;
    public OuttakePID(HardwareMap hardwareMap) {
        controller = new PIDController(new ControlConstants(p, i, d));
        motionProfile = new MotionProfileTrapezoidal(V_MAX, A_MAX);
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtake");
        stopwatch = new NanoStopwatch();
    }
    public void run(double scalar, double power) {
        targetVelocity = 0; // Disable PID mode
        if (outtakeMotor.getPower() == 0) {
            outtakeMotor.setPower(power * scalar);
        } else {
            outtakeMotor.setPower(0);
        }
    }
    public void runVelocity(double scalar, double velocity){
        targetVelocity = 0; // Disable PID mode
        if(!isOn){
            outtakeMotor.setVelocity(velocity * scalar);
            isOn = true;
        } else{
            outtakeMotor.setVelocity(0);
            isOn = false;
        }
    }
    public void resetEncoders(){
        outtakeMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }
    public double getVelocity(){
        return outtakeMotor.getVelocity();
    }
    public void setPower(double power){
        targetVelocity = 0; // Disable PID mode
        outtakeMotor.setPower(power);
    }
    public void setTargetVelocity(double velocity){
        startingVelocity = outtakeMotor.getVelocity(); // FIX: Store in startingVelocity
        double velocityChange = -1*velocity - startingVelocity; // FIX: Use startingVelocity
        publicChange=velocityChange;
        publicReadVelFromSet=velocity;
        targetVelocity = velocity;
        stopwatch.reset();
        controller = new PIDController(new ControlConstants(p, i, d));
        motionProfile.setGoal(velocityChange);
        publicReadGoalAtSet=motionProfile.getGoal();
        firstUpdate=true;
    }

    public double getTargetVelocity(){
        return targetVelocity;
    }

    public double getStartingVelocity(){
        return startingVelocity;
    }

    public void update() {
        motionProfile.setGoal(publicChange);
        if (targetVelocity == 0) {
            outtakeMotor.setVelocity(0);
            return;
        }

        double elapsedTime = stopwatch.seconds();
        double profileVelocity = motionProfile.velocityAt(elapsedTime);
        double currentVel = -1*outtakeMotor.getVelocity();

        // FIX: Simple math using stored startingVelocity
        double desiredVel = targetVelocity;

        double error = desiredVel - currentVel;
        controller.calculate(error);
        double pidOutput = controller.getOutput();
        double ff = desiredVel * f;

        outputToMotor=pidOutput;
        publicError=error;
        publicDesVel=desiredVel;
        publicProfile=profileVelocity;
        publicCurrentVel=currentVel;
        publicReadTime=elapsedTime;
        publicGoal=motionProfile.getGoal();
        publicMotionProfileTime=motionProfile.getDuration();
        publicPidOutput=pidOutput;

        outtakeMotor.setVelocity(currentVel+pidOutput);
    }

    // Function to update PID constants
    public void updatePIDConstants(double newP, double newI, double newD, double newF) {
        p = newP;
        i = newI;
        d = newD;
        f = newF;
        controller = new PIDController(new ControlConstants(p, i, d));
    }

    // Function to update motion profile constants
    public void updateMotionProfile(double newVMax, double newAMax) {
        V_MAX = newVMax;
        A_MAX = newAMax;
        motionProfile = new MotionProfileTrapezoidal(V_MAX, A_MAX);
    }
}