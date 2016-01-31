package org.usfirst.frc.team5431.map;

/**
 * Class that defines the ID's for the motors.
 * 
 * @see CANTalon
 **/
public final class MotorMap {
	//to prevent instantiation
	private MotorMap(){}
	
	/**
	 * Channel ID for the left flywheel in the shooter mechanism, which is {@value #LeftFly}
	 * @see Robot
	 **/
	public static final int LeftFly = 9;
		/**
	 * Channel ID for the right flywheel in the shooter mechanism, which is {@value #RightFly}
	 * @see Robot
	 **/
	public static final int RightFly = 5;
	/**
 * Channel ID for the top intake motor in the shooter mechanism, which is {@value #IntakeTop}
 * @see Robot
 **/
	public static final int IntakeTop = 6;
	/**
 * Channel ID for the bottom intake motor in the shooter mechanism, which is {@value #IntakeBot}
 * @see Robot
 **/
	public static final int IntakeBot=7;
	/**
 * Channel ID for the rear right wheel on the drive base, which is {@value #RearRight}
 * @see Robot
 **/
	public static final int RearRight = 3;
	/**
 * Channel ID for the rear left wheel on the drive base, which is {@value #RearLeft}
 * @see Robot
 **/
	public static final int RearLeft = 2;
	/**
 * Channel ID for the front right wheel on the drive base, which is {@value #FrontRight}
 * @see Robot
 **/
	public static final int FrontRight = 8;
	/**
 * Channel ID for the front left wheel on the drive base, which is {@value #FrontLeft}
 * @see Robot
 **/
	public static final int FrontLeft =4;

}
