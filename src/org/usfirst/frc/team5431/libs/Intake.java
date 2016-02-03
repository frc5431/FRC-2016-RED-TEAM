package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.OI;
import org.usfirst.frc.team5431.robot.Robot;
import org.usfirst.frc.team5431.map.MotorMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Intake class for team 5431's intake mechanism. Works hand in hand with the
 * {@linkplain TurretBase shooter}.
 * <p>
 * Contains a toggle for the intake, which is specified by the
 * {@link #checkInput(OI)} method.
 * 
 * @author AcademyHS Robotics
 *
 */
public class Intake {
	private final CANTalon top, bot;
	//true because it is inverted at the start. it won't actually start running
	private boolean running = true;
	private static DigitalInput boulderLimit;
	private int pastbutton = 0;
	private double speed = 0.7, motorspeed = 0;

	/**
	 * Default constructor for {@code TurretBase}. Binds the
	 * {@linkplain CANTalon top motor} to {@value MotorMap#IntakeTop} and the
	 * {@linkplain CANTalon bot motor} to {@value MotorMap#IntakeBot}.
	 */
	public Intake() {
		this.top = new CANTalon(MotorMap.IntakeTop);
		this.bot = new CANTalon(MotorMap.IntakeBot);

		this.top.enable();
		this.bot.enable();

		this.top.clearStickyFaults();
		this.bot.clearStickyFaults();
		
		this.top.setInverted(true);
		
		this.top.enableBrakeMode(true);
		this.bot.enableBrakeMode(true);
		
		boulderLimit = Robot.boulderLimit;
	}

	/**
	 * Intakes with the current internal motor speed value.
	 * 
	 * @see #getSpeed(double)
	 */
	public void intake() {
		this.top.set(motorspeed);
		this.bot.set(motorspeed);
	}

	/**
	 * Makes it intake at max speed
	 */
	public void intakeMax() {
		setMotorSpeed(1);
		intake();
	}

	/**
	 * Makes it intake at half speed
	 */
	public void intakeHalf() {
		setMotorSpeed(0.5);
		intake();
	}

	/**
	 * Stops the intake.
	 */
	public void stopIntake() {
		setMotorSpeed(0);
		intake();
	}

	/**
	 * Checks whether the {@link Robot robot} is currently intaking.
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Takes an {@link OI} and changes the speed values based on the
	 * input. Also intakes if it is toggled.
	 * 
	 * @param map
	 */
	public void checkInput(OI map) {
		//this is the code for the toggle.
		if ((map.isIntaking() ? 0 : 1) > pastbutton) {
			if (running && !boulderLimit.get()) {
				setMotorSpeed(0);
			} else if(!boulderLimit.get()) {
				setMotorSpeed(speed);
			}
			intake();
		}
		pastbutton = map.isIntaking() ? 0 : 1;
		
		//toggle gun
		if ((map.isIntaking() ? 0 : 1) > pastbutton) {
			if (running) {
				setMotorSpeed(0);
			} else if(!boulderLimit.get()) {
				setMotorSpeed(speed);
			}
			intake();
		}
		pastbutton = map.isIntaking() ? 0 : 1;
	}

	/**
	 * Sets the speed for the motors to spin at.
	 * 
	 * @param d
	 *            new speed
	 */
	private void setMotorSpeed(double d) {
		motorspeed = d;
		running = d > 0;
	}

	/**
	 * Sets the speed at which the {@link Robot robot} will intake at when it is
	 * toggled
	 * 
	 * @param Speed
	 *            for the motor
	 */
	public void setSpeed(double d) {
		speed = d;
	}

	/**
	 * Returns the current speed to intake at
	 * 
	 * @return the speed, as specified by {@link #setSpeed(double)}
	 */
	public double getSpeed() {
		return speed;
	}

}
