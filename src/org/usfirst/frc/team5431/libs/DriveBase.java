package org.usfirst.frc.team5431.libs;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveBase {
	
	private CANTalon frontLeft, rearLeft, frontRight, rearRight;
	
	private RobotDrive drive;
	
	public DriveBase()
	{
		this.frontLeft = new CANTalon(2);
		this.rearLeft = new CANTalon(3);
		this.frontRight = new CANTalon(4);
		this.rearRight = new CANTalon(5);
		this.drive = new RobotDrive(this.frontLeft, this.rearLeft, this.frontRight, this.rearRight);
	}
	
	public void forward(double left, double right)
	{
		drive.tankDrive(left, right);
	}
	
}
