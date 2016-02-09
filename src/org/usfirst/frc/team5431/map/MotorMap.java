package org.usfirst.frc.team5431.map;

/**
 * Class that defines the ID's for the motors.
 * @author AcademyHS Robotics Team 5431
 * @see CANTalon
 **/
public final class MotorMap {
	//to prevent instantiation
	private MotorMap(){}
	
	/**
	 * Channel ID for the left flywheel in the shooter mechanism, which is {@value #LEFT_FLY}
	 * @see Robot
	 **/
	public static final int LEFT_FLY = 9;
		/**
	 * Channel ID for the right flywheel in the shooter mechanism, which is {@value #RIGHT_FLY}
	 * @see Robot
	 **/
	public static final int RIGHT_FLY = 5;
	/**
 * Channel ID for the top intake motor in the shooter mechanism, which is {@value #INTAKE_TOP}
 * @see Robot
 **/
	public static final int INTAKE_TOP = 6;
	/**
 * Channel ID for the bottom intake motor in the shooter mechanism, which is {@value #INTAKE_BOT}
 * @see Robot
 **/
	public static final int INTAKE_BOT=7;
	/**
 * Channel ID for the front right wheel on the drive base, which is {@value #FRONT_RIGHT}
 * @see Robot
 **/
	public static final int FRONT_RIGHT = 3;
	/**
 * Channel ID for the front left wheel on the drive base, which is {@value #FRONT_LEFT}
 * @see Robot
 **/
	public static final int FRONT_LEFT = 2;
	/**
 * Channel ID for the rear right wheel on the drive base, which is {@value #REAR_RIGHT}
 * @see Robot
 **/
	public static final int REAR_RIGHT = 8;
	/**
 * Channel ID for the rear left wheel on the drive base, which is {@value #REAR_LEFT}
 * @see Robot
 **/
	public static final int REAR_LEFT =4;

}
