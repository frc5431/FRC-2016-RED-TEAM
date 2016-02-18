package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.OI;
import org.usfirst.frc.team5431.robot.Robot;
import org.usfirst.frc.team5431.map.MotorMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Class that handles tank drive.
 * 
 * @author AcademyHS Robotics Team 5431
 * 
 */
public class DriveBase {

	private CANTalon rearleft, frontleft, rearright, frontright;

	private RobotDrive drive;
	
	private static int robotWidth = 23 + 1/8;

	/**
	 * Default constructor
	 * 
	 * @see #DriveBase(boolean b)
	 */
	public DriveBase() {
		this(false);
	}

	/**
	 * Construtor with an option to have brakemode enabled
	 * 
	 * @param brakeMode
	 *            Whether to enable brakemode on the {@linkplain CANTalon
	 *            motors}
	 */
	public DriveBase(boolean brakeMode) {
		this.rearleft = new CANTalon(MotorMap.REAR_LEFT);
		this.frontleft = new CANTalon(MotorMap.FRONT_LEFT);
		this.rearright = new CANTalon(MotorMap.REAR_RIGHT);
		this.frontright = new CANTalon(MotorMap.FRONT_RIGHT);

		this.rearleft.enable();
		this.frontleft.enable();
		this.rearright.enable();
		this.frontright.enable();

		/*
		 * if (Robot.launch==Robot.LaunchType.BLUE) {
		  frontright.setInverted(true); frontleft.setInverted(true);
		 * rearleft.setInverted(true); }
		 */
		this.rearleft.clearStickyFaults();
		this.frontleft.clearStickyFaults();
		this.rearright.clearStickyFaults();
		this.frontright.clearStickyFaults();

		this.rearleft.enableBrakeMode(brakeMode);
		this.frontleft.enableBrakeMode(brakeMode);
		this.frontright.enableBrakeMode(brakeMode);
		this.rearright.enableBrakeMode(brakeMode);

		this.drive = new RobotDrive(this.frontleft, this.rearleft, this.frontright, this.rearright);
	}

	/**
	 * Uses {@linkplain RobotDrive#tankDrive(double l, double r) tank drive} to
	 * drive.
	 * 
	 * @param left
	 *            Value of the left joystick, where -1 is the lowest, 0 is the
	 *            center, and 1 is the highest.
	 * @param right
	 *            Value of the right joystick, where -1 is the lowest, 0 is the
	 *            center, and 1 is the highest.
	 */
	public void drive(double left, double right) {
		//if (Robot.launch==Robot.LaunchType.BLUE) {
			//drive.tankDrive(left * 0.7, right * 0.7);
		//} else
			drive.tankDrive(right, left);
	}

	/**
	 * Automagically drives straight
	 */
	public void auto_driveStraight(double distance, double speed, double curve) { //Why do you have curve? Liave, you need to document!
		Robot.encoder.resetDrive();

		double left = 0;
		double right = 0;

		while (((left = Robot.encoder.LeftDistance()) < distance)
				&& ((right = Robot.encoder.RightDistance()) < distance)) {
			if (left < right - 0.1) {
				drive.drive(speed + curve, speed - curve);
			} else if (left > right + 0.1) {
				drive.drive(speed - curve, speed + curve);
			} else {
				drive.drive(speed, speed);
			}
		}
		drive.drive(0, 0);
	}
	
	/**
	 * Encoder-based turning with input in degrees and speed.
	 * @param degrees 
	 * 					From 0 - 180 for left and 0 to -180 for right
	 * @param speed
	 * 					Speed that robot turns, from -1 to 1.
	 * @param curve
	 * 					How much to speed up a side if one side is going a pulse faster.
	 * 					Should be extremely small (adds to motor value for a side).
	 */
	public void auto_driveTurn(double degrees, double speed, double curve){
		Robot.encoder.resetDrive();
		double left = 0;
		double right = 0;
		double leftDistance = 0;
		double rightDistance = 0;
						 //We aren't doing straight in this function are we? How am I going to find distance from just degrees (which is 0). 
		if(degrees != 0){ //This is to make sure that even if build team programs, they won't kill themselves immediately.
			if(degrees < 0){
				leftDistance = (1/2 * degrees) * robotWidth / 360;   //degrees negates left for us (why type more?)
				rightDistance = (1/2 * -degrees) * robotWidth / 360; //Negative because right will need to be positive
			}
			else{
				leftDistance = (1/2 * -degrees) * robotWidth / 360;	//Negating because left needs to go backward.
				rightDistance = (1/2 * degrees) * robotWidth / 360;
			}
			//Lets just do copy and paste here, shall we? You don't mind - right, David?
			while (((left = Robot.encoder.LeftDistance()) < leftDistance)
					&& ((right = Robot.encoder.RightDistance()) < rightDistance)) {
				if (left < right - 0.1) {
					drive.drive(speed + curve, speed - curve);
				} else if (left > right + 0.1) {
					drive.drive(speed - curve, speed + curve);
				} else {
					drive.drive(speed, speed);
				}
			}
			drive.drive(0, 0);
		}
		//else I would return something (we need to make a list of error codes and not have any void functions . . .)
	}
	/*
	 * Make the joystick inputs curved for a natural dead zone(No jumping) And
	 * also allow smaller more precise movements
	 */
	private double exp(double Speed) {
		return Speed/1.1;//(0.46 * Math.pow(Speed, 3)) + (0.5 * Speed);
	}

	/**
	 * Checks input and drives based on an {@linkplain OI OI}
	 * 
	 * @param map
	 *            Current operator interface.
	 */
	public void checkInput(OI map) {
		this.drive(exp(map.getDriveLeftYAxis()), exp(map.getDriveRightYAxis()));
	}

}
