package frc.robot.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import static frc.robot.Constants.*;

public class FalconSwerveModule extends SwerveModule {
    public FalconSwerveModule(int driveChannel, int steeringChannel, int encoderChannel) {
        this.driveMotor = new WPI_TalonFX(driveChannel, "rio");
        this.steeringMotor = new WPI_TalonFX(steeringChannel, "rio");
        // this.steeringEncoder = new WPI_CANCoder(steeringChannel, "rio");
        zeroInternalEncoder();
        configure();
    }

    public FalconSwerveModule(int driveChannel, int steeringChannel, int encoderChannel, boolean invert) {
        this.driveMotor = new WPI_TalonFX(driveChannel, "rio");
        this.steeringMotor = new WPI_TalonFX(steeringChannel, "rio");
        // this.steeringEncoder = new WPI_CANCoder(steeringChannel, "rio");
        zeroInternalEncoder();
        configure();
        getDriveMotor().setInverted(invert);
    }

    @Override
    public void updateModule(SwerveModuleState moduleState) {
        // TODO: Fix swervedrive optimization
        Rotation2d currentAngle = getInternalEncoderAngle();
        //SwerveModuleState optimzedState = SwerveModuleState.optimize(moduleState, currentAngle);
        SwerveModuleState optimzedState = moduleState;

        // Obtain swerve motors
        WPI_TalonFX driveMotor = getDriveMotor();
        WPI_TalonFX steeringMotor = getSteeringMotor();

        // Update motors in module
        driveMotor.set(ControlMode.PercentOutput,
                MathUtil.applyDeadband(moduleState.speedMetersPerSecond, 0.05D) * .1667D);
        steeringMotor.set(ControlMode.Position, (optimzedState.angle.getDegrees() / 360D) * FALCON_MK4I_STEERING_CPR);
    }

    public void zeroInternalEncoder() {
        getSteeringMotor().setSelectedSensorPosition(0D);
    }

    // Accessors and mutators

    /**
     * @deprecated No CANCoder installed (01/08/2023)
     */
    @Deprecated
    public Rotation2d getExternalEncoderAngle() {
        return Rotation2d.fromDegrees(getExternalEncoderPosition() / FALCON_CPR);
    }

    /**
     * @deprecated No CANCoder installed (01/08/2023)
     */
    @Deprecated
    public double getExternalEncoderPosition() {
        return steeringEncoder.getAbsolutePosition() * (FALCON_CPR / 360D);
    }

    public Rotation2d getInternalEncoderAngle() {
        return Rotation2d.fromDegrees(getInternalEncoderPosition() / (FALCON_CPR * 360D));
    }

    public double getInternalEncoderPosition() {
        return getSteeringMotor().getSelectedSensorPosition();
    }

    private WPI_TalonFX getDriveMotor() {
        return (WPI_TalonFX) this.driveMotor;
    }

    private WPI_TalonFX getSteeringMotor() {
        return (WPI_TalonFX) this.steeringMotor;
    }

    // Initialization methods

    /**
     * Syncronize externatal and internal encoders
     * 
     * @deprecated No CANCoder installed (01/08/2023)
     */
    @Deprecated
    protected void syncronizeInternalEncoder() {
        getSteeringMotor().setSelectedSensorPosition(getExternalEncoderPosition());
    }

    /**
     * Configure corresponding Talon FXs
     */
    protected void configure() {
        WPI_TalonFX driveMotor = getDriveMotor();
        WPI_TalonFX steeringMotor = getSteeringMotor();

        steeringMotor.setNeutralMode(NeutralMode.Coast);
        driveMotor.setNeutralMode(NeutralMode.Coast);

        // Reduce CAN packets?

        steeringMotor.config_kP(0, FALCON_STEERING_KP);
        steeringMotor.config_kI(0, FALCON_STEERING_KI);
        steeringMotor.config_kD(0, FALCON_STEERING_KD);
        steeringMotor.config_kF(0, FALCON_STEERING_KF);
    }
}
