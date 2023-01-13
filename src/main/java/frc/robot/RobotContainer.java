// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.ManualCommandDrive;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private static final Drive drive = Drive.getInstance();

  public static final CommandXboxController driver = new CommandXboxController(Constants.DRIVER_PORT);
  public static final CommandXboxController operator = new CommandXboxController(Constants.OPERATOR_PORT);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureBindings();
    configureShuffleboad();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    drive.setDefaultCommand(new ManualCommandDrive(Drive.getInstance()));
  }

  private void configureShuffleboad() {
    SmartDashboard.putData("Zero swerve drive", new InstantCommand(() -> drive.zeroModules()));
    SmartDashboard.putData("Drive forward", new InstantCommand(() -> drive.driveRelative(0, 1D, 0)));
    SmartDashboard.putData("Drive sideways", new InstantCommand(() -> drive.driveRelative(1D, 0, 0)));
    SmartDashboard.putData("Rotate in place", new InstantCommand(() -> drive.driveRelative(0, 0D, 1D)));
    SmartDashboard.putData("Drive northeast", new InstantCommand(() -> drive.driveRelative(1D, 1D, 0)));
    SmartDashboard.putData("Disable drive", new InstantCommand(() -> drive.driveRelative(0D, 0D, 0)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
