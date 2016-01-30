package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.MotorMap;

import edu.wpi.first.wpilibj.CANTalon;

public class TurretBase {

	private static CANTalon Left, Right;
	
	public TurretBase() 
	{
		this.Left = new CANTalon(MotorMap.LeftFly);
		this.Right = new CANTalon(MotorMap.RightFly);
	}
	
	public void setShoot(double speed)
	{
		this.Left.set(-speed);
		this.Right.set(-speed);
	}
	
}
