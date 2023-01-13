// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

public class FloatingPoint {
    public static boolean compareDouble(double x, double y) {
        if (x == y)
            return true;
        return Math.abs(x - y) < getEpsilon(x, y);
    }

    public static double getEpsilon(double x, double y) {
        return Math.ulp(Math.max(x, y));
    }
}
