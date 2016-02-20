package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.SensorMap;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Class which handles encoders and counters to make sure the {@linkplain TurretBase shooter} is accurate.
 * 
 * @author AcademyHS Robotics Team 5431
 * */
public class EncoderBase {

	private Encoder Left, Right;
	private Counter FlyLeft, FlyRight;
	private static final double distancePerPulse = ((Math.PI * 10)/360.0);
	private static final int samples = 7;
	
	/**
	 * Default constructor which handles {@link Encoder encoders} and {@link Counter counters}.
	 **/
	public EncoderBase() {
		this.Left = new Encoder((SensorMap.LEFT_ENCODER_1), (SensorMap.LEFT_ENCODER_2), true, EncodingType.k4X);
		this.Right = new Encoder((SensorMap.RIGHT_ENCODER_1), (SensorMap.RIGHT_ENCODER_2), false, EncodingType.k4X);
		
		this.FlyLeft = new Counter(SensorMap.FLY_WHEEL_LEFT_COUNTER);
		this.FlyRight = new Counter(SensorMap.FLY_WHEEL_RIGHT_COUNTER);
		
        this.Left.setDistancePerPulse(distancePerPulse);
        this.Right.setDistancePerPulse(distancePerPulse);
        
        this.Left.setSamplesToAverage(samples);
        this.Right.setSamplesToAverage(samples);
        
        this.FlyLeft.setSamplesToAverage(samples);
        this.FlyRight.setSamplesToAverage(samples);
	}
	
	/**
	 * Resets the {@link Counter counters} and {@link Encoder encoders}.
	 * 
	 */
	public void resetModules() {
        this.Left.reset();
        this.Right.reset();
        this.FlyLeft.reset();
        this.FlyRight.reset();
	}
	
	/**
	 *Calculates the rotations per minute of the left flywheel.
	 * @return The RPM of the left fly wheel according to the {@link Counter counter}
	 */
	public double leftFlyRPM() {
		return  (60/(360 * this.FlyLeft.getPeriod()));
	}
	
	/**
	 *Calculates the rotations per minute of the right flywheel.
	 * @return The RPM of the right fly wheel according to the {@link Counter counter}
	 */
	public double rightFlyRPM() {
		return  (60/(360 * this.FlyRight.getPeriod()));
	}

	
	/**
	 * Resets the distance on the {@linkplain Counter counters}.
	 */
	public void resetDrive() {
		this.Left.reset();
		this.Right.reset();
	}
	
	/**
	 * Calculates the distance the left flywheel moved since the last time {@link #resetDrive()} was called.
	 * 
	 * @return Distance the left flywheel went according to the {@link Counter counters}.
	 */
	public double LeftDistance() {
		return this.Left.getDistance();
	}
	
	/**
	 * Calculates the distance the left flywheel moved since the last time {@link #resetDrive()} was called.
	 * 
	 * @return Distance the left flywheel went according to the {@link Counter counters}.
	 */
	public double RightDistance() {
		return this.Right.getDistance();
	}
	
	
}


