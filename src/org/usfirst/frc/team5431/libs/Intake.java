package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.OI;
import org.usfirst.frc.team5431.map.SensorMap;
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
 * @author AcademyHS Robotics Team 5431
 *
 */
public class Intake {
	private final CANTalon top,bot;
	//true because it is inverted at the start. it won't actually start running
	private boolean running = true;
	private boolean limitState = false;
	private static DigitalInput boulderLimit;
	private int pastbutton = 0;
	private double motorspeed = 0;
	/**
	 * Default constructor for {@code TurretBase}. Binds the
	 * {@linkplain CANTalon top motor} to {@value MotorMap#INTAKE} and the
	 * {@linkplain CANTalon bot motor} to {@value MotorMap#INTAKE_BOT}.
	 */
	public Intake() {
		this.top = new CANTalon(MotorMap.INTAKE);
		this.bot = new CANTalon(MotorMap.UNUSED);
		
		this.bot.enable();
		this.top.enable();
		//top.setInverted(true);
		//bot.setInverted(true);

		this.top.clearStickyFaults();
		this.bot.clearStickyFaults();
		
		//this.top.setInverted(true);
		
		this.top.enableBrakeMode(true);
		this.bot.enableBrakeMode(true);
		
		boulderLimit = new DigitalInput(SensorMap.INTAKE_LIMIT);
		Robot.table.putNumber("intake max", MotorMap.DEFAULT_INTAKE_SPEED);
		//rightLimit = new DigitalInput(8);
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
		//this is the code for the toggle
		limitState = (!boulderLimit.get()); //|| !rightLimit.get()); //Reverses boulderLimit
		
		Robot.table.putBoolean("boulder", limitState);
		if(limitState && !map.isIntaking()) {
			setMotorSpeed(0);
		} else if(limitState && map.isIntaking()) {
			setMotorSpeed(Robot.table.getNumber("intake max", MotorMap.DEFAULT_INTAKE_SPEED));
		}
		
		if ((map.isIntaking() ? 0 : 1) > pastbutton) {
			if (running) {
				setMotorSpeed(0);
			} else {
				setMotorSpeed(Robot.table.getNumber("intake max", MotorMap.DEFAULT_INTAKE_SPEED));
			}
		}
		pastbutton = map.isIntaking() ? 0 : 1;
		if(map.isIntakingBackwards() && !running){
			setMotorSpeed(-Robot.table.getNumber("intake max", MotorMap.DEFAULT_INTAKE_SPEED));
			
		}
		intake();
		
		//toggle gun
//		if ((map.isIntaking() ? 0 : 1) > pastbutton) {
//			if (running) {
//				setMotorSpeed(0);
//			} else if(!boulderLimit.get()) {
//				setMotorSpeed(speed);
//			}
//			intake();
//		}
//		pastbutton = map.isIntaking() ? 0 : 1;
	}

	/**
	 * Sets the speed for the motors to spin at.
	 * 
	 * @param d
	 *            new speed
	 */
	private void setMotorSpeed(double d) {
		motorspeed = d;
		running= d!=0;
		Robot.table.putBoolean("intake", running);
	}

}


class GetLimit extends Thread {
	
	@Override
	public void run() {
		//Intake.
	}
}