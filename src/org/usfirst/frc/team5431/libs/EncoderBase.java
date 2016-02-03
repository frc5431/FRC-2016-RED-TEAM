package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.SensorMap;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;;

/**
 * Class which handles encoders and counters to make sure the {@linkplain TurretBase shooter} is accurate.
 * 
 * @author Team 5431
 * */
public class EncoderBase {

	private Encoder Left, Right;
	private Counter FlyLeft, FlyRight;
	private static final double distancePerPulse = ((Math.PI * 10)/28);
	private static final int samples = 7;
	
	/**
	 * Default constructor which handles {@link Encoder encoders} and {@link Counter counters}.
	 **/
	public EncoderBase() {
		this.Left = new Encoder((SensorMap.frontLeftEncoder1), (SensorMap.frontLeftEncoder2), false, EncodingType.k4X);
		this.Right = new Encoder((SensorMap.frontRightEncoder1), (SensorMap.frontRightEncoder2), false, EncodingType.k4X);
		
		this.FlyLeft = new Counter(SensorMap.FlyWheelLeftCounter);
		this.FlyRight = new Counter(SensorMap.FlyWheelRightCounter);
		
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
		return  (60/(7 * this.FlyLeft.getPeriod()));
	}
	
	/**
	 *Calculates the rotations per minute of the right flywheel.
	 * @return The RPM of the right fly wheel according to the {@link Counter counter}
	 */
	public double rightFlyRPM() {
		return  (60/(7 * this.FlyRight.getPeriod()));
	}
	
	/**
	 * @deprecated Have no idea what is going on here.
	 */
	@Deprecated
	public double[] setSpeed(double speed) {
		double[] temp = {speed, speed};
		if(this.leftFlyRPM() < this.rightFlyRPM()) {
			return temp;
		}
		return temp;
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
