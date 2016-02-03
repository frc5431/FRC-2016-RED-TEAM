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
		this(false);
	}
	
	public DriveBase(boolean brakeMode)
	{
		this.rearleft = new CANTalon(MotorMap.RearLeft);
		this.frontleft = new CANTalon(MotorMap.FrontLeft);
		this.rearright = new CANTalon(MotorMap.RearRight);
		this.frontright = new CANTalon(MotorMap.FrontRight);
		
		this.rearleft.enable();
		this.frontleft.enable();
		this.rearright.enable();
		this.frontright.enable();
		
		this.rearleft.clearStickyFaults();
		this.frontleft.clearStickyFaults();
		this.rearright.clearStickyFaults();
		this.frontright.clearStickyFaults();
		
		this.rearleft.enableBrakeMode(brakeMode);
		this.frontleft.enableBrakeMode(brakeMode);
		this.frontright.enableBrakeMode(brakeMode);
		this.rearright.enableBrakeMode(brakeMode);
		
		this.drive = new RobotDrive(this.frontleft, this.rearleft, this.frontright, this.rearright);
	}
	
	public void drive(double left, double right)
	{
		drive.tankDrive(left, right);
	}
	
	public void checkInput(OI map){
		this.drive(map.getDriveLeftYAxis(),map.getDriveRightYAxis());
	}
	
}
