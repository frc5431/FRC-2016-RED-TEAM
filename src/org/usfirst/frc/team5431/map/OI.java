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
	 * Constant which defines an button or joystick ID
	 */
	public static final int 
			JOYSTICK_DRIVE = 0, 
			JOYSTICK_GUN = 0, 
			driveLY = 1, 
			driveRY = 4,
			gunTrigger = 1, 
			driveIntake = 1,
			driveSolIn = 2,
			driveSolOut = 3;
	
	private final Joystick drive, gun;
	private final JoystickButton intake, shoot, solIn, solOut;
	

	/**
	 * Default constructor which creates all of the input objects
	 */
	public OI() {
		drive = new Joystick(JOYSTICK_DRIVE);
		gun = new Joystick(JOYSTICK_GUN);
		
		shoot = new JoystickButton(gun, gunTrigger);
		intake = new JoystickButton(drive, driveIntake);
		
		solIn = new JoystickButton(drive, driveSolIn);
		solOut = new JoystickButton(drive, driveSolOut);
	}

	/**
	 * Returns the controlling {@linkplain Joystick joystick} which controls {@linkplain DriveBase driving}.
	 * 
	 * @return the connected joystick
	 */
	public Joystick getDriveController() {
		return drive;
	}
	
	/**
	 * Returns the controlling {@linkplain Joystick joystick} which controls the {@linkplain TurretBase turret}.
	 * 
	 * @return the connected joystick
	 **/
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

	/**
	 * Checks the left Y axis on the driving joystick.
	 * 
	 * @return Value of the left Y axis, from -1 to 1.
	 * @see #getDriveController
	 * */
	public double getDriveLeftYAxis() {
		return drive.getRawAxis(driveLY);
	}

	/**
	 * Checks the right Y axis on the driving joystick.
	 * 
	 * @return Value of the right Y axis, from -1 to 1.
	 * @see #getDriveController
	 * */
	public double getDriveRightYAxis() {
		return drive.getRawAxis(driveRY);
	}
	
	/**
	 * Checks to see if the button to have the solonoid outut is down.
	 * @return Whether {@JoystickButton the button} bound to the ID of {@value #driveSolOUt} is down
	 * */
	public boolean isSolOut() {
		return solOut.get();
	}
	
	/**
	 * Checks to see if the button to have the solonoid input is down.
	 * @return Whether {@JoystickButton the button} bound to the ID of {@value #driveSolIn} is down
	 * */
	public boolean isSolIn() {
		return solIn.get();
	}

	/* Not needed now
	public double getZAxis() {
		return joy.getRawAxis(2);
	}

	public double getZRotateAxis() {
		return joy.getRawAxis(3);
	}*/
}
