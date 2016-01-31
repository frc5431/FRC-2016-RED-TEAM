
package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.libs.DriveBase;
import org.usfirst.frc.team5431.libs.Intake;
import org.usfirst.frc.team5431.libs.TurretBase;
import org.usfirst.frc.team5431.libs.Vision;
import org.usfirst.frc.team5431.map.OI;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * This is the code for red teams (There are two robots building and red is awesome)
 * Look under the libs package to see where all the main classes are split up
 * This Robot.java is just the main thread(s) that will be controlling those classes
 * Have your looksez
 */
public class Robot extends IterativeRobot {
	
	enum AutoTask{
		 Default,Custom
	};
	
    AutoTask currentAuto;
    private TurretBase turret;
    private DriveBase drive;
    private Intake intake;
    private OI oi;
    private Vision vision;

    public void robotInit() {
    	
    	turret = new TurretBase();
    	intake=new Intake();
    	drive = new DriveBase();
    	oi = new OI(); //Joystick mapping
    	vision = new Vision();
    	
       	intake.setSpeed(1);
        turret.setSpeed(0.7);
    	
        // chooser = new SendableChooser();
        // chooser.addDefault("Default Auto", defaultAuto);
        // chooser.addObject("My Auto", customAuto);
        // SmartDashboard.putData("Auto choices", chooser);
    }
    
    public void autonomousInit() {
//     	autoSelected = (String) chooser.getSelected();
// //		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
// 		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous. Effect changes based on the value of {@link #autoSelected}.
     */
    public void autonomousPeriodic() {
    	switch(currentAuto) {
    	case Custom:
        //Put custom auto code here   
            break;
    	case Default:
    	default:
    	//Put default auto code here
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	intake.checkInput(oi);
    	turret.checkInput(oi);
    	drive.checkInput(oi);
    }

    private void updateVision()
    {
    	vision.updateVals();
    	vision.updateSmartDash();
    }
    
    public void testPeriodic() {}
}
