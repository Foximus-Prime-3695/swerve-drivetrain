// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final int DRIVER_PORT = 0;
  public static final int OPERATOR_PORT = 1;
  public static final double DEADBAND = 0.1D;
  
  public static final int FRONT_LEFT_DRIVE_CHANNEL = 8;
  public static final int FRONT_LEFT_TURNING_CHANNEL = 7;
  public static final int FRONT_RIGHT_DRIVE_CHANNEL = 2;
  public static final int FRONT_RIGHT_TURNING_CHANNEL = 1;
  public static final int BACK_LEFT_DRIVE_CHANNEL = 6;
  public static final int BACK_LEFT_TURNING_CHANNEL = 5;
  public static final int BACK_RIGHT_DRIVE_CHANNEL = 4;
  public static final int BACK_RIGHT_TURNING_CHANNEL = 3;

  public static final double TRACK_WIDTH = 0.6096D;
  public static final double WHEELBASE = 0.6096D;
  public static final double MK4I_STEERING_RATIO = 150D / 7D;

  public static final double FALCON_CPR = 2048D;
  public static final double FALCON_MK4I_STEERING_CPR = FALCON_CPR * MK4I_STEERING_RATIO;
  public static final double FALCON_STEERING_KP = 0.125D;
  public static final double FALCON_STEERING_KI = 0D;
  public static final double FALCON_STEERING_KD = 0.5D;
  public static final double FALCON_STEERING_KF = 0D;

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;

  }
}
