package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.MotorMap;

import edu.wpi.first.wpilibj.CANTalon;

public class TurretBase {

	private CANTalon Left, Right;
	
	public TurretBase() 
	{
		this.Left = new CANTalon(MotorMap.LeftFly);
		this.Right = new CANTalon(MotorMap.RightFly);
		
		this.Left.enable();
		this.Right.enable();
		
		this.Left.setInverted(true);
		this.Right.setInverted(true);
		
		this.Left.clearStickyFaults();
		this.Right.clearStickyFaults();
	}
	
	public void setShoot(double speed)
	{
		this.Left.set(-speed);
		this.Right.set(-speed);
	}
	
}

