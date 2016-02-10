package org.usfirst.frc.team5431.map;

import org.usfirst.frc.team5431.robot.Robot;

/**
 * Class which defines various constants for {@link Solonoid Solonoids}
 * @author AcademyHS Robotics Team 5431
 *
 */
public class SolonoidMap {
	/**
	 * Constant which defines which {@linkplain Solonoid} controls the red aspect of the {@linkplain LED LEDs}, which is currently
	 * {@value #LED_RED}
	 */
	public static final int LED_RED;
	/**
	 * Constant which defines which {@linkplain Solonoid} controls the green aspect of the {@linkplain LED LEDs}, which is currently
	 * {@value #LED_GREEN}
	 */
	public static final int LED_GREEN;
	/**
	 * Constant which defines which {@linkplain Solonoid} controls the blue aspect of the {@linkplain LED LEDs}, which is currently
	 * {@value #LED_BLUE}
	 */
	public static final int LED_BLUE;
	
	static {
		switch (Robot.launch) {
		default:
		case RED:
		case MAYOR:
			LED_BLUE=1;
			LED_GREEN=0;
			LED_RED=2;
			break;
		case BLUE:
			LED_BLUE=1;
			LED_GREEN=0;
			LED_RED=2;
			break;
		}

	}

}
