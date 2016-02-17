package org.usfirst.frc.team5431.map;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Class which contains all of the user input, including a {@linkplain Joystick
 * joystick}. Classes will ask for it to get the input at that time. The methods update real-time.
 * 
 * @author AcademyHS Robotics Team 5431
 * @see Robot
 */ 
public final class OI {
	/**
	 * Constant which defines an button or joystick ID
	 */
	public static final int 
			JOYSTICK_DRIVE = 0, 
			JOYSTICK_GUN = 0, 
			DRIVE_LEFT_Y = 1, 
			DRIVE_RIGHT_Y = 5,
			GUN_TRIGGER = 1, 
			DRIVE_INTAKE = 2,
			DRIVE_INTAKE_BACKWARDS=3,
			DRIVE_SOL_IN = 2,
			DRIVE_SOL_OUT = 3,
			AUTO_GUN_TRIGGER = 6;
	
	private final Joystick drive, gun;
	private final JoystickButton intake, shoot, solIn, solOut, intakeback, autoShoot;
	

	/**
	 * Default constructor which creates all of the input objects
	 */
	public OI() {
		drive = new Joystick(JOYSTICK_DRIVE);
		gun = new Joystick(JOYSTICK_GUN);
		
		shoot = new JoystickButton(gun, GUN_TRIGGER);
		intake = new JoystickButton(drive, DRIVE_INTAKE);
		intakeback = new JoystickButton(drive,DRIVE_INTAKE_BACKWARDS);
		autoShoot = new JoystickButton(gun, AUTO_GUN_TRIGGER);
		
		solIn = new JoystickButton(drive, DRIVE_SOL_IN);
		solOut = new JoystickButton(drive, DRIVE_SOL_OUT);
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
	
	public boolean isAutoShoot() {
		return autoShoot.get();
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
	
	public boolean isRevIntaking() {
		return intakeback.get();
	}

	/**
	 * Checks the left Y axis on the driving joystick.
	 * 
	 * @return Value of the left Y axis, from -1 to 1.
	 * @see #getDriveController
	 * */
	public double getDriveLeftYAxis() {
		return drive.getRawAxis(DRIVE_LEFT_Y);
	}

	/**
	 * Checks the right Y axis on the driving joystick.
	 * 
	 * @return Value of the right Y axis, from -1 to 1.
	 * @see #getDriveController
	 * */
	public double getDriveRightYAxis() {
		return drive.getRawAxis(DRIVE_RIGHT_Y);
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
	 * @return Whether {@JoystickButton the button} bound to the ID of {@value #DRIVE_SOL_IN} is down
	 * */
	public boolean isSolIn() {
		return solIn.get();
	}
	
	public boolean isIntakingBackwards(){
		return intakeback.get();
	}

	/* Not needed now
	public double getZAxis() {
		return joy.getRawAxis(2);
	}

	public double getZRotateAxis() {
		return joy.getRawAxis(3);
	}*/
}
