package frc.robot.swerve;

import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public abstract class SwerveModule {
    protected MotorController driveMotor;
    protected MotorController steeringMotor;
    protected CANCoder steeringEncoder;

    public abstract void updateModule(SwerveModuleState moduleState);

    public abstract void zeroInternalEncoder();

    public abstract Rotation2d getExternalEncoderAngle();

    public abstract double getExternalEncoderPosition();

    public abstract Rotation2d getInternalEncoderAngle();

    public abstract double getInternalEncoderPosition();

    protected abstract void syncronizeInternalEncoder();

    protected abstract void configure();

}
