
package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.libs.DriveBase;
import org.usfirst.frc.team5431.libs.Intake;
import org.usfirst.frc.team5431.libs.TurretBase;
import org.usfirst.frc.team5431.map.InputMap;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	enum AutoTask{
		 Default,Custom
	};
    AutoTask currentAuto;
    private TurretBase turret;
    private  DriveBase drive;
    private Intake intake;
    private InputMap input;
    //SendableChooser chooser;
    public void robotInit() {
    	turret = new TurretBase();
    	intake=new Intake();
    	drive = new DriveBase();
    	input = new InputMap();
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
    	intake.checkInput(input);
    	turret.checkInput(input);
    	drive.checkInput(input);
    }

    public void testPeriodic() {}
}
