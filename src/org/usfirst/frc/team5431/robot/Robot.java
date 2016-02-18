
package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.libs.DriveBase;
import org.usfirst.frc.team5431.libs.EncoderBase;
import org.usfirst.frc.team5431.libs.Intake;
import org.usfirst.frc.team5431.libs.LED;
import org.usfirst.frc.team5431.libs.TurretBase;
import org.usfirst.frc.team5431.libs.Vision;
import org.usfirst.frc.team5431.map.OI;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
/**
 * This is the code for red team of Team 5431(There are two robots building and
 * red is awesome.) Look under the libs package to see where all the main
 * classes are split up. This Robot.java is just the main thread(s) that will be
 * controlling those classes.
 * 
 * @author AcademyHS Robotics Team 5431
 */
public class Robot extends IterativeRobot {

	public static LaunchType launch = LaunchType.RED;
	public static enum LaunchType{
		RED,BLUE,MAYOR()
	}

	// Better than strings
	enum AutoTask {
		AutoShootLowbar, AutoShootCenter, StandStill
	};

	AutoTask currentAuto;
	SendableChooser auton_select;
	private TurretBase turret;
	private DriveBase drive;
	private Intake intake;
	private OI oi;
	private boolean runOnce = false; // Don't mess with please
	private double ledTime;
	public static NetworkTable table;
	
	/**
	 * Holder array whose value is changed by other threads.
	 */
	public static volatile double[] autoAimVals = { 0, 0, 0 }; // Make sure the
																// other thread
																// can see the
																// vals
	
	public static volatile double[] encoderVals = {0, 0, 0, 0}; //L Fly, R Fly, Left, Right
	
	
	public static volatile EncoderBase encoder; //Encoder class to access other threads
	public static volatile LED led; //Multi-thread access variable
	
	
	/**
	 * Holder value whose value is changed by other threads.
	 */
	public static volatile double offTarget = 0.2; // Value to idle flyWheels

	/**
	 * Method called once in a {@linkplain Robot robot's} lifetime.
	 */
	public void robotInit() {
		runOnce = true;
		
		encoder = new EncoderBase();
		turret = new TurretBase();
		intake = new Intake();
		drive = new DriveBase();
		oi = new OI(); // Joystick mapping
		led = new LED(); 

		//intake.setSpeed(1);
		//turret.setSpeed(0.73);
		
		auton_select = new SendableChooser();
		auton_select.addObject("AutoShoot Lowbar", AutoTask.AutoShootLowbar);
		auton_select.addDefault("AutoShoot Center4", AutoTask.AutoShootCenter);
		auton_select.addObject("StandStill", AutoTask.StandStill);

		// pneumatic.startCompressor();

		SmartDashboard.putData("Auto choices", auton_select);

		// Start vision thread
		//new VisionThread().start();
		new EncoderThread().start();
		Timer.delay(1);
		ledTime = 0;
		table = NetworkTable.getTable("5431");
	}

	/**
	 * Method called once at the start of autonomous mode.
	 */
	public void autonomousInit() {
		currentAuto = (AutoTask) auton_select.getSelected();
		SmartDashboard.putString("Auto Selected: ", currentAuto.toString());
		led.reset();
		//SmartDashboard.putString("SERIAL", led.SendSerial("READY"));
		//led.demo();
		
	}

	/** 
	 * Autonomously drives under the low bar.
	 * Assumes that we <u>begin in front of lowbar</u>
	 */
	public void lowbarMode() {
		// Drive 15 feet
		drive.auto_driveStraight(156, 0.5, 0.05); // Distance (in), speed (0-1), curve(0-0.1)
		drive.auto_driveTurn(-40, 0.5, 0.05);

	}
	
	/**
	 * Autonomously drives forward (for the other 4 besides lowbar)
	 * Assumes that we begin in front of a defense <b>other</b> than:
	 * <li>Cheval de Frise</li><li>Portcullis</li><li>Sally Port</li>
	 */
	public void centerMode(){
		drive.auto_driveStraight(144, 0.5, 0.05);
	}
	
	public void autoShoot() {
		if(autoAimVals[1] == 1) {
			drive.drive(-0.3, 0.3);
		} else if(autoAimVals[1] == 2) {
			drive.drive(0.3, -0.3);
		} else if(autoAimVals[1] == 5){
			drive.drive(0, 0);
		} else {
			drive.drive(0, 0);
		}
	}

	/**
	 * This function is called periodically during autonomous. Effect changes
	 * based on the value of {@link #autoSelected}.
	 */
	public void autonomousPeriodic() {
		//vision.updateVals();
		SmartDashboard.putNumber("Run TIME", ledTime);
		ledTime += 1;
		Timer.delay(2);

		switch (currentAuto) {
		case AutoShootLowbar:
			if (runOnce) {
				this.lowbarMode();
				runOnce = false;
			}
			break;
		case AutoShootCenter:
			if(runOnce){
				this.centerMode();
				runOnce = false;
			}
			break;
		case StandStill:
		default:
			Timer.delay(0.01);
			break;
		}
		
		//DriverStation.getInstance().
	}

	/**
	 * Runs once at the beginning of teleoperated control.
	 */
	public void teleopInit() {
		runOnce = true;
		ledTime = 0;
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		table.putBoolean("connection", true);
		ledTime += Timer.getFPGATimestamp() - ledTime;
		SmartDashboard.putNumber("Time On", ledTime);
		if (launch!=LaunchType.BLUE) {
			intake.checkInput(oi);
			turret.checkInput(oi);
		}
		drive.checkInput(oi);
		
	}

	/**
	 * 
	 * Called when peridioc is disabled
	 */
	public void disabledPeriodic() {
		runOnce = true;
		Robot.encoder.resetDrive();
	}

	/**
	 * @deprecated unused
	 */
	@Deprecated
	public void testPeriodic() {}
}

/**
 * Thread which handles vision and fills in some public holder values.
 */
class VisionThread extends Thread {

	private Vision vision;
	private static double offTarget;

	/**
	 * Default constructor.
	 */
	public VisionThread() {
		try {
			vision = new Vision();
			offTarget = Robot.offTarget;
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inherit-javadoc}
	 */
	@Override
	public void run() {
		try {
			while (true) {
				vision.updateVals();
				Robot.autoAimVals = vision.updateSmartDash(offTarget);
				Thread.sleep(100);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}

class EncoderThread extends Thread {
	
	private EncoderBase encoder;
	
	public EncoderThread() {
		encoder = Robot.encoder;
	}
	
	@Override
	public void run() {
		while(true) {
			Robot.encoderVals[0] = encoder.leftFlyRPM();
			Robot.encoderVals[1] = encoder.rightFlyRPM();
			Robot.encoderVals[2] = encoder.LeftDistance();
			Robot.encoderVals[3] = encoder.RightDistance();
			
			SmartDashboard.putNumber("LEFT-WHEEL", Robot.encoderVals[2]);
			SmartDashboard.putNumber("RIGHT-WHEEL", Robot.encoderVals[3]);
			SmartDashboard.putNumber("FLY-LEFT-WHEEL", Robot.encoderVals[0]);
			SmartDashboard.putNumber("FLY-RIGHT-WHEEL", Robot.encoderVals[1]);
			
			try {Thread.sleep(10);} catch (InterruptedException ignored) {}
		}
	}
}
