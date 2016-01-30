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
	public static final int LeftFly = 4;
		/**
	 * Channel ID for the right flywheel in the shooter mechanism, which is {@value #RightFly}
	 * @see Robot
	 **/
	public static final int RightFly = 5;
	
	
}
