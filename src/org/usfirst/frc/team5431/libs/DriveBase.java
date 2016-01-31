package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.InputMap;
import org.usfirst.frc.team5431.map.MotorMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;


public class DriveBase {
	
	private CANTalon frontLeft, rearLeft, frontRight, rearRight;
	
	private RobotDrive drive;
	
	public DriveBase()
	{
		this.frontLeft = new CANTalon(MotorMap.FrontLeft);
		this.rearLeft = new CANTalon(MotorMap.RearLeft);
		this.frontRight = new CANTalon(MotorMap.FrontRight);
		this.rearRight = new CANTalon(MotorMap.RearRight);
		this.drive = new RobotDrive(this.frontLeft, this.rearLeft, this.frontRight, this.rearRight);
	}
	
	private void drive(double left, double right)
	{
		drive.tankDrive(left, right);
	}
	
	public void checkInput(InputMap map){
		drive(map.getYAxis(),map.getZRotateAxis());
	}
	
}
