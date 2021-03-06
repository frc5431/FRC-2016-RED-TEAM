package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.OI;
import org.usfirst.frc.team5431.robot.Robot;
import org.usfirst.frc.team5431.map.MotorMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private double  motorspeed = 0;
	public static volatile double toSpeed = 0.5;

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
		Left.setInverted(true);
		Right.setInverted(true);

		this.Left.clearStickyFaults();
		this.Right.clearStickyFaults();
		
		Robot.table.putNumber("turret max", MotorMap.DEFAULT_FLYWHEEL_SPEED);
		new updateThread().start();

	}

	/**
	 * Shoots with the current internal motor speed value.
	 * 
	 * @see #getSpeed(double)
	 */
	public void shoot() {
		Robot.table.putBoolean("turret", motorspeed>0);
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
				setMotorSpeed(Robot.table.getNumber("turret max",MotorMap.DEFAULT_FLYWHEEL_SPEED));
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
	public void setMotorSpeed(double d) {
		motorspeed = d;
		running = d > 0;
	}
}

class updateThread extends Thread {
	
	public void run() {
		while(true) {
			TurretBase.toSpeed = SmartDashboard.getNumber("TURRET SPEED", MotorMap.DEFAULT_FLYWHEEL_SPEED);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
