package org.whitneyrobotics.ftc.teamcode.OpMode;

import static org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.RumbleEffects.endgame;
import static org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.RumbleEffects.matchEnd;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Extensions.TelemetryPro.LineItem;
import org.whitneyrobotics.ftc.teamcode.Subsystems.RobotImpl;

import java.util.function.UnaryOperator;

@TeleOp(name = "work pls")
public class DecodeTeleop extends OpModeEx {

    RobotImpl robot;
    private final UnaryOperator<Float> scalingFunctionDefault = x -> (float)Math.pow(x, 3);

    @Override
    public void initInternal() {
        robot = RobotImpl.getInstance(hardwareMap);
        gamepad1.SQUARE.onPress(robot::switchAlliance);
        gamepad1.START.onPress(() -> {
            robot.drive.startDrive();
        });
        robot.drive.initialize();
        robot.teleOpInit();
        setupNotifications();
    }

    void setupNotifications() {
        addTemporalCallback(resolve -> {
            gamepad1.getEncapsulatedGamepad().runRumbleEffect(endgame);
            gamepad2.getEncapsulatedGamepad().runRumbleEffect(endgame);
            telemetryPro.addLine("Endgame approaching soon!", LineItem.Color.ROBOTICS, LineItem.RichTextFormat.BOLD).persistent();
            resolve.accept(!gamepad1.getEncapsulatedGamepad().isRumbling() &&
                    gamepad2.getEncapsulatedGamepad().isRumbling());
        }, 85000);

        addTemporalCallback(resolve -> {
            telemetryPro.removeLineByCaption("Endgame approaching soon!");
            resolve.accept(true);
        },90000);

        addTemporalCallback(resolve -> {
            //playSound("matchend",100f);
            gamepad1.getEncapsulatedGamepad().runRumbleEffect(matchEnd);
            gamepad2.getEncapsulatedGamepad().runRumbleEffect(matchEnd);
            telemetryPro.addLine("Match ends in 5 seconds!", LineItem.Color.FUCHSIA, LineItem.RichTextFormat.BOLD).persistent();
            resolve.accept(!gamepad1.getEncapsulatedGamepad().isRumbling() &&
                    gamepad2.getEncapsulatedGamepad().isRumbling());
        }, 113000);

        addTemporalCallback(resolve -> {
            //playSound("slay",100f);
            telemetryPro.removeLineByCaption("Match ends in 5 seconds!");
            telemetryPro.addLine("Match has ended.", LineItem.Color.LIME, LineItem.RichTextFormat.ITALICS).persistent();
            resolve.accept(true);
        },120000);
    }

    @Override
    public void start() {
        robot.drive.startDrive();
    }

    @Override
    protected void loopInternal() {
        //drive
        gamepad1.BUMPER_RIGHT.onPress(() -> robot.drive.driveMode());
        robot.drive.update(gamepad1);
        //deal w scaling later bc im too lazy rn
        UnaryOperator<Float> scaling = scalingFunctionDefault;
        if(gamepad1.BUMPER_LEFT.value()) scaling = x -> x/2;
        //add telemetry for alliance later im too lazy rn
        telemetryPro.addData("robotCentric: ", robot.drive.getDriveMode());
        telemetryPro.addData("x: ", robot.drive.getPose().getX());
        telemetryPro.addData("y: ", robot.drive.getPose().getY());
        telemetryPro.addData("heading: ", robot.drive.getPose().getHeading());

        // OUTTAKE (christopher):
        gamepad1.TRIANGLE.onPress(() -> {
            robot.outtake.transfer();
        });
        gamepad1.X.onPress(() -> {
            robot.outtake.flicker();
        });
        gamepad1.CIRCLE.onPress(() -> {
            robot.outtake.toggle_outtake_power();
        });

        //update devices.......... :c

        //intake
        robot.intake.run(gamepad2);
        telemetryPro.update();
    }
}
