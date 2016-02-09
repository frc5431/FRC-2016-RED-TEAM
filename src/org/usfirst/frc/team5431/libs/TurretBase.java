package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.OI;
import org.usfirst.frc.team5431.robot.Robot;
import org.usfirst.frc.team5431.map.MotorMap;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Class that represents the shooter turret on the robot.
 * <p>
 * Team 5431's turret is a fixed turret. It has 2 flywheels to launch the ball,
 * and a series of rods and axles to intake. This class specifies the speeds and
 * code for the 2 flywheels in the front.
 * <p>
 * This class contains a toggle for whether it is shooting.
 * <p>
 * The {@linkplain CANTalon motor ID} for the left flywheel is
 * {@value MotorMap#LEFT_FLY}. The right flywheel is {@value MotorMap#RIGHT_FLY}.
 * 
 * 
 * @see Robot
 * @see MotorMap
 * @author AcademyHS Robotics Team 5431
 **/
public class TurretBase {

	private CANTalon Left, Right;
	//true because it is inverted at the start. it won't actually start running
	private boolean running = true;
	private int pastbutton = 0;
	private double speed = 0.7, motorspeed = 0;

	/**
	 * Default constructor for {@code TurretBase}. Binds the
	 * {@linkplain CANTalon left motor} to {@value MotorMap#LEFT_FLY} and the
	 * {@linkplain CANTalon right motor} to {@value MotorMap#RIGHT_FLY}.
	 */
	public TurretBase() {
		this.Left = new CANTalon(MotorMap.LEFT_FLY);
		this.Right = new CANTalon(MotorMap.RIGHT_FLY);

		this.Left.enable();
		this.Right.enable();

		this.Left.clearStickyFaults();
		this.Right.clearStickyFaults();

	}

	/**
	 * Shoots with the current internal motor speed value.
	 * 
	 * @see #getSpeed(double)
	 */
	public void shoot() {
		this.Left.set(motorspeed);
		this.Right.set(-motorspeed);
	}

	/**
	 * Shoots at max power
	 */
	public void shootMax() {
		setMotorSpeed(1);
		shoot();
	}

	/**
	 * Shoots at half power
	 */
	public void shootHalf() {
		setMotorSpeed(0.5);
		shoot();
	}

	/**
	 * Stops shooting
	 */
	public void stopShoot() {
		setMotorSpeed(0);
		shoot();
	}

	/**
	 * Checks whether the {@link Robot robot} is currently shooting.
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Takes an {@link OI} and changes the speed values based on the
	 * input. Also shoots if it is toggled.
	 * 
	 * @param map
	 */
	public void checkInput(OI map) {
		if ((map.isShooting() ? 0 : 1) > pastbutton) {
			if (running) {
				setMotorSpeed(0);
			} else {
				setMotorSpeed(speed);
			}
			shoot();
		}
		pastbutton = map.isShooting() ? 0 : 1;
	}

	/**
	 * Sets the speed at which the {@link Robot robot} will shoot when
	 * {@link #shoot()} is called.
	 * 
	 * @param Speed
	 *            for the motor
	 */
	private void setMotorSpeed(double d) {
		motorspeed = d;
		running = d > 0;
	}

	public void setSpeed(double d) {
		speed = d;
	}

	/**
	 * Returns the current speed to shoot at
	 * 
	 * @return the speed, as specified by {@link #setSpeed(double)}
	 */
	public double getSpeed() {
		return speed;
	}
}
