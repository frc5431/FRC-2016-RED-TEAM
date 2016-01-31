package org.usfirst.frc.team5431.map;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Class which contains all of the user input, including a {@linkplain Joystick
 * joystick}. Classes will ask for it to get the input at that time. The methods update real-time.
 * 
 * @author AcademyHS Robotics
 * @see Robot
 */ 
public final class OI {
	/**
	 * Defines that the joystick channel is {@value #JOYSTICK}.
	 */
	public static final int 
			JOYSTICK_DRIVE = 0, 
			JOYSTICK_GUN = 0, 
			driveLY = 1, 
			driveRY = 4,
			gunTrigger = 1, 
			driveIntake = 1;
	
	private final Joystick drive, gun;
	private final JoystickButton intake, shoot;
	

	/**
	 * Default constructor which creates all of the input objects
	 */
	public OI() {
		drive = new Joystick(JOYSTICK_DRIVE);
		gun = new Joystick(JOYSTICK_GUN);
		
		shoot = new JoystickButton(gun, gunTrigger);
		intake = new JoystickButton(drive, driveIntake);
	}

	/**
	 * Returns the controlling joystick.
	 * 
	 * @return the connected joystick
	 */
	public Joystick getDriveController() {
		return drive;
	}
	
	public Joystick getGunController() {
		return gun;
	}

	/**
	 * Check the {@linkplain JoystickButton button} for shooting is down, and
	 * returns it.
	 * 
	 * @return whether the button specifying the robot to shoot is down or not.
	 * @see TurretBase
	 */
	public boolean isShooting() {
		return shoot.get();
	}

	/**
	 * Check the {@linkplain JoystickButton button} for the intake is down, and
	 * returns it.
	 * 
	 * @return whether the button specifying the robot to intake is down or not.
	 * @see Intake
	 */
	public boolean isIntaking() {
		return intake.get();
	}

	public double getDriveLeftYAxis() {
		return drive.getRawAxis(driveLY);
	}

	public double getDriveRightYAxis() {
		return drive.getRawAxis(driveRY);
	}

	/* Not needed now
	public double getZAxis() {
		return joy.getRawAxis(2);
	}

	public double getZRotateAxis() {
		return joy.getRawAxis(3);
	}*/
}
