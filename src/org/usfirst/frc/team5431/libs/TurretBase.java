package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.MotorMap;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Class that represents the shooter turret on the robot.
 * <p>
 * Team 5431's turret is a fixed turret. It has 2 flywheels to launch the ball, and a series of rods and axles to intake.
 * This class specifies the speeds and code for the 2 flywheels in the front.
 * <p>
 * The {@linkplain CANTalon motor ID} for the left flywheel is {@value MotorMap#LeftFly}. The right flywheel is {@value MotorMap#RightFly}.
 * @see Robot
 * @see MotorMap
 **/
public class TurretBase {

	private CANTalon Left, Right;
	
		/**
	 * Default constructer for {@code TurretBase}. Binds the {@linkplain CANTalon left motor} to {@value MotorMap#LeftFly} and the {@linkplain CANTalon right motor} to {@value MotorMap#RightFly}.
	 * */
	public TurretBase() 
	{
		this.Left = new CANTalon(MotorMap.LeftFly);
		this.Right = new CANTalon(MotorMap.RightFly);
		
		this.Left.enable();
		this.Right.enable();
		
		this.Left.setInverted(true);
		this.Right.setInverted(true);
		
		this.Left.clearStickyFaults();
		this.Right.clearStickyFaults();
	}
	
		/**
	 * Sends the command to the {@linkplain CANTalon flywheels} to spin at a specified speed.
	* @param speed the percent voltage to send to the flywheels. 1.0 is max voltage, and 0.0 is no power.
	 **/
	public void setShoot(double speed)
	{
		this.Left.set(-speed);
		this.Right.set(-speed);
	}
	
	/**
	 * Overloads {@link #setShoot(double speed)} with a parameter of 1.0, or max speed.
	 **/
	public void shootMax(){
		setShoot(1);
	}
	
		/**
	 * Overloads {@link #setShoot(double speed)} with a parameter of 0.5, or half speed.
	 **/
	public void shootHalf(){
		setShoot(0.5);
	}
	
		/**
	 * Overloads {@link #setShoot(double speed)} with a parameter of 0.0, or stop it.
	 **/
	public void stopShoot(){
		setShoot(0);
	}

	
}

