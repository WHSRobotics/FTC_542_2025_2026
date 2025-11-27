package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;
import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.RumbleEffects;

public class Notifier {
    // Variables (Assignment):
    // Tolerance:
    private static final double SPEED_TOLERANCE = 20;

    // Rumbled:
    private boolean has_rumbled = false;

    public void notify_if_at_speed(GamepadEx gamepad, double current_velocity, double target_velocity) {
        // Validation:
        if (gamepad == null) {
            return;
        }

        // Variables (Assignment):
        // Error:
        double error = Math.abs(current_velocity - target_velocity);

        // Logic:
        if (error < SPEED_TOLERANCE && !has_rumbled) {
            // Logic:
            gamepad.Vibrate(250);

            // Rumbled:
            has_rumbled = true;
        }

        if (error >= SPEED_TOLERANCE) {
            has_rumbled = false;
        }
    }
}