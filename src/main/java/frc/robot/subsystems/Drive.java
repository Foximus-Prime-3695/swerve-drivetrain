// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.swerve.FalconSwerveModule;
import frc.robot.swerve.SwerveModule;

import static edu.wpi.first.math.MathUtil.applyDeadband;
import static frc.robot.util.FloatingPoint.*;
import static frc.robot.Constants.*;

public class Drive extends SubsystemBase {
  // Swerve modules
  private SwerveModule[] modules;
  // Swerve kinematics
  private double trackWidth, wheelbase;
  private Translation2d[] moduleLocations;
  private SwerveDriveKinematics kinematics;

  private static Drive instance;

  private Drive() {
    this(TRACK_WIDTH, WHEELBASE);
  }

  private Drive(double trackWidth, double wheelbase) {
    initializeModules();
    initializeKinematics(trackWidth, wheelbase);
  }

  public static Drive getInstance() {
    if (instance == null)
      instance = new Drive();
    return instance;
  }

  public static Drive getInstace(double trackWidth, double wheelbase) {
    if (instance == null || compareDouble(instance.trackWidth, trackWidth)
        || compareDouble(instance.wheelbase, wheelbase))
      instance = new Drive(trackWidth, wheelbase);
    return instance;
  }

  @Override
  public void periodic() {
  }

  public void driveRelative(CommandXboxController controller) {
    double lateral = applyDeadband(controller.getLeftX(), DEADBAND);
    double vertical = applyDeadband(controller.getLeftY(), DEADBAND);
    double rotational = applyDeadband(controller.getRightX(), DEADBAND);
    driveRelative(lateral, vertical, rotational);
  }

  public void driveRelative(double lateral, double vertical, double rotational) {
    ChassisSpeeds speeds = new ChassisSpeeds(vertical, lateral, rotational);
    SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);
    updateModules(moduleStates);
  }

  private void updateModules(SwerveModuleState[] moduleStates) {
    if (modules.length != 4 || moduleStates.length != 4)
      DriverStation.reportWarning("Invalid number of swerve drive modules", false);

    // Update modules
    for (int i = 0; i < modules.length; ++i)
      modules[i].updateModule(moduleStates[i]);
  }

  public void zeroModules() {
    for (SwerveModule module : modules)
      module.zeroInternalEncoder();
  }

  /**
   * Initialize swerve drive modules
   */
  private void initializeModules() {
    this.modules = new SwerveModule[4];
    this.modules[0] = new FalconSwerveModule(FRONT_LEFT_DRIVE_CHANNEL, FRONT_LEFT_TURNING_CHANNEL, 0, false);
    this.modules[1] = new FalconSwerveModule(FRONT_RIGHT_DRIVE_CHANNEL, FRONT_RIGHT_TURNING_CHANNEL, 0, true);
    this.modules[2] = new FalconSwerveModule(BACK_LEFT_DRIVE_CHANNEL, BACK_LEFT_TURNING_CHANNEL, 0, false);
    this.modules[3] = new FalconSwerveModule(BACK_RIGHT_DRIVE_CHANNEL, BACK_RIGHT_TURNING_CHANNEL, 0, true);
  }

  /**
   * Initalize swerve drive kinematics
   */
  private void initializeKinematics(double trackWidth, double wheelbase) {
    if (trackWidth <= 0 || wheelbase <= 0)
      DriverStation.reportWarning("Invalid track width and/or wheelbase", false);

    // Calculate offsets
    this.trackWidth = trackWidth;
    this.wheelbase = wheelbase;
    double xOffset = trackWidth / 2;
    double yOffset = wheelbase / 2;

    // Position swerve drive modules
    this.moduleLocations = new Translation2d[4];
    this.moduleLocations[0] = new Translation2d(-yOffset, xOffset); // Front Left
    this.moduleLocations[1] = new Translation2d(-yOffset, -xOffset); // Front Right
    this.moduleLocations[2] = new Translation2d(yOffset, xOffset); // Back Left
    this.moduleLocations[3] = new Translation2d(yOffset, -xOffset); // Back Right

    // Initialize kinematics
    this.kinematics = new SwerveDriveKinematics(moduleLocations);
  }
}
