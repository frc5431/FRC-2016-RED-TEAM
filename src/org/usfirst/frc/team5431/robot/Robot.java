
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

	public static enum LaunchType {
		RED, BLUE, MAYOR, ID;
	}

	// Better than strings
	enum AutoTask {
		AutoShoot, StandStill
	};

	AutoTask currentAuto;
	SendableChooser auton_select;
	private TurretBase turret;
	private DriveBase drive;
	private Intake intake;
	private OI oi;
	private LED led;

	private boolean runOnce = false; // Don't mess with please
	private double ledTime;
	
	/**
	 * Holder array whose value is changed by other threads.
	 */
	public static volatile double[] autoAimVals = { 0, 0, 0 }; // Make sure the
																// other thread
																// can see the
																// vals
	
	
	public static volatile EncoderBase encoder; //Encoder class to access other threads
	
	
	/**
	 * Holder value whose value is changed by other threads.
	 */
	public static volatile double onTarget = 0.7, // Value to shoot at target
			offTarget = 0.2; // Value to idle flyWheels

	/**
	 * Method called once in a {@linkplain Robot robot's} lifetime.
	 */
	public void robotInit() {
		if (launch != LaunchType.ID) {
			runOnce = true;
			
			encoder = new EncoderBase();
			turret = new TurretBase();
			intake = new Intake();
			drive = new DriveBase();
			oi = new OI(); // Joystick mapping
			//boulderLimit = new DigitalInput(SensorMap.INTAKE_LIMIT);
			// pneumatic = new PneumaticBase();

			intake.setSpeed(1);
			turret.setSpeed(0.73);

			auton_select = new SendableChooser();
			auton_select.addDefault("AutoShoot Lowbar", AutoTask.AutoShoot);
			auton_select.addObject("StandStill", AutoTask.StandStill);

			// pneumatic.startCompressor();

			SmartDashboard.putData("Auto choices", auton_select);

			// Start vision thread
			new VisionThread().start();
			led = new LED(); 
			Timer.delay(1);
			ledTime = 0;

		}
		runOnce = true;
		
		encoder = new EncoderBase();
		turret = new TurretBase();
		intake = new Intake();
		drive = new DriveBase();
		oi = new OI(); // Joystick mapping
		//boulderLimit = new DigitalInput(SensorMap.INTAKE_LIMIT);
		// pneumatic = new PneumaticBase();

		intake.setSpeed(1);
		turret.setSpeed(0.73);

		auton_select = new SendableChooser();
		auton_select.addDefault("AutoShoot Lowbar", AutoTask.AutoShoot);
		auton_select.addObject("StandStill", AutoTask.StandStill);

		// pneumatic.startCompressor();

		SmartDashboard.putData("Auto choices", auton_select);

		// Start vision thread
		new VisionThread().start();
		led = new LED(); 
		Timer.delay(1);
		ledTime = 0;
	}

	/**
	 * Method called once at the start of autonomous mode.
	 */
	public void autonomousInit() {
		currentAuto = (AutoTask) auton_select.getSelected();
		SmartDashboard.putString("Auto Selected: ", currentAuto.toString());
		led.demo();
		//led.reset();
		//SmartDashboard.putString("SERIAL", led.SendSerial("READY"));
		//led.demo();
	}

	/** 
	 * Autonomously drives under the low bar.
	 */
	public void lowbarMode() {
		// Drive 15 feet
		this.auto_driveStraight(156, 0.5, 0.05); // Distance (in), speed
		// (0-1), curve(0-0.1)

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
		case AutoShoot:
			if (runOnce) {
				this.lowbarMode();
				runOnce = false;
			}
			break;
		case StandStill:
		default:
			Timer.delay(0.01);
			break;
		}
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
		if (launch != LaunchType.ID) {
			ledTime += Timer.getFPGATimestamp() - ledTime;
			SmartDashboard.putNumber("Time On", ledTime);
			if (launch != LaunchType.BLUE) {
				intake.checkInput(oi);
				turret.checkInput(oi);
			}
			drive.checkInput(oi);
		}
		// Timer.delay(1);
		// led.SendI2C("VAL");
		// pneumatic.checkInput(oi);
	}

	/**
	 * 
	 * Called when peridioc is disabled
	 */
	public void disabledPeriodic() {
		runOnce = true;
	}

	/**
	 * Automagically drives straight
	 */
	private void auto_driveStraight(double distance, double speed, double curve) {
		encoder.resetDrive();

		double left = 0;
		double right = 0;

		while (((left = encoder.LeftDistance()) < distance) && ((right = encoder.RightDistance()) < distance)) {
			if (left < right) {
				drive.drive(speed + curve, speed - curve);
			} else if (right > left) {
				drive.drive(speed - curve, speed + curve);
			} else {
				drive.drive(speed, speed);
			}
		}
		drive.drive(0, 0);
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
	private static double onTarget, offTarget;

	/**
	 * Default constructor.
	 */
	public VisionThread() {
		try {
			vision = new Vision();
			onTarget = Robot.onTarget;
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
				Robot.autoAimVals = vision.updateSmartDash(onTarget, offTarget);
				Thread.sleep(100);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
