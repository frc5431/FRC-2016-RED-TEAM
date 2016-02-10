package org.usfirst.frc.team5431.map;

import org.usfirst.frc.team5431.robot.Robot;

/**
 * Class which defines various constants for {@link Encoder Encoders} and {@link Counter Counters}
 * @author AcademyHS Robotics Team 5431
 *
 */
public class SensorMap {
	
	/**
	 * Constant which defines which sensor ID on the RoboRIO belongs to the sensor.
	 */
	public static final int FRONT_RIGHT_ENCODER_1,
				FRONT_RIGHT_ENCODER_2,
				FRONT_LEFT_ENCODER_1 ,
				FRONT_LEFT_ENCODER_2 ,
				REAR_RIGHT_ENCODER_1 ,
				REAR_RIGHT_ENCODER_2 ,
				REAR_LEFT_ENCODER_1 ,
				REAR_LEFT_ENCODER_2 ,
				FLY_WHEEL_RIGHT_COUNTER ,
				FLY_WHEEL_LEFT_COUNTER ,
				INTAKE_LIMIT;
	
	
	
	static {
		switch (Robot.launch) {
		default:
		case RED:
		case MAYOR:
			FRONT_RIGHT_ENCODER_1=0;
			FRONT_RIGHT_ENCODER_2=1;
			FRONT_LEFT_ENCODER_1=2;
			FRONT_LEFT_ENCODER_2=3;
			REAR_RIGHT_ENCODER_1=4;
			REAR_RIGHT_ENCODER_2=5;
			REAR_LEFT_ENCODER_1=6;
			REAR_LEFT_ENCODER_2=7;
			FLY_WHEEL_RIGHT_COUNTER=8;
			FLY_WHEEL_LEFT_COUNTER=9;
			INTAKE_LIMIT=10;
			break;
		case BLUE:
			FRONT_RIGHT_ENCODER_1=0;
			FRONT_RIGHT_ENCODER_2=1;
			FRONT_LEFT_ENCODER_1=2;
			FRONT_LEFT_ENCODER_2=3;
			REAR_RIGHT_ENCODER_1=4;
			REAR_RIGHT_ENCODER_2=5;
			REAR_LEFT_ENCODER_1=6;
			REAR_LEFT_ENCODER_2=7;
			FLY_WHEEL_RIGHT_COUNTER=8;
			FLY_WHEEL_LEFT_COUNTER=9;
			INTAKE_LIMIT=10;
			break;
		}

	}

}
