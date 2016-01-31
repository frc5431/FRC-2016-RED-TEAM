package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.OI;
import org.usfirst.frc.team5431.map.MotorMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;


public class DriveBase {
	
	private CANTalon rearleft, frontleft, rearright, frontright;
	
	private RobotDrive drive;
	
	public DriveBase()
	{
		this.rearleft = new CANTalon(MotorMap.RearLeft);
		this.frontleft = new CANTalon(MotorMap.FrontLeft);
		this.rearright = new CANTalon(MotorMap.RearRight);
		this.frontright = new CANTalon(MotorMap.FrontRight);
		this.drive = new RobotDrive(this.rearleft, this.frontleft, this.rearright, this.frontright);
	}
	
	private void drive(double left, double right)
	{
//		left1.set(left);
//		left2.set(left);
//		right1.set(right);
//		right2.set(right);
		drive.tankDrive(left, right);
		//drive.tankDrive(left, right);
	}
	
	public void checkInput(OI map){
		drive(map.getYAxis(),map.getZRotateAxis());
	}
	
}
