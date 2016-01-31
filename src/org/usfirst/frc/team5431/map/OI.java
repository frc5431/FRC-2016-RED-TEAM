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
	public static final int JOYSTICK = 0;
	private  final Joystick joy;
	private final JoystickButton intake, shoot;

	/**
	 * Default constructor which creates all of the input objects
	 */
	public OI() {
		joy = new Joystick(JOYSTICK);
		shoot = new JoystickButton(joy, 1);
		intake = new JoystickButton(joy, 2);
	}

	/**
	 * Returns the controlling joystick.
	 * 
	 * @return the connected joystick
	 */
	public Joystick getController() {
		return joy;
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

	public double getXAxis() {
		return joy.getRawAxis(0);
	}

	public double getYAxis() {
		return joy.getRawAxis(1);
	}

	public double getZAxis() {
		return joy.getRawAxis(2);
	}

	public double getZRotateAxis() {
		return joy.getRawAxis(3);
	}
}
