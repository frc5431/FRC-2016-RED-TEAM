package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.SensorMap;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;;

public class EncoderBase {

	private Encoder Left, Right;
	private Counter FlyLeft, FlyRight;
	private static final double distancePerPulse = ((Math.PI * 10)/28);
	private static final int samples = 7;
	
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
	
	public void resetModules() {
        this.Left.reset();
        this.Right.reset();
        this.FlyLeft.reset();
        this.FlyRight.reset();
	}
	
	public double leftFlyRPM() {
		return  (60/(7 * this.FlyLeft.getPeriod()));
	}
	
	public double rightFlyRPM() {
		return  (60/(7 * this.FlyRight.getPeriod()));
	}
	
	public double[] setSpeed(double speed) {
		double[] temp = {speed, speed};
		if(this.leftFlyRPM() < this.rightFlyRPM()) {
			return temp;
		}
		return temp;
	}
	
	public void resetDrive() {
		this.Left.reset();
		this.Right.reset();
	}
	
	public double LeftDistance() {
		return this.Left.getDistance();
	}
	
	public double RightDistance() {
		return this.Right.getDistance();
	}
	
	
}
