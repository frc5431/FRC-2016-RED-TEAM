package org.usfirst.frc.team5431.libs;

import java.nio.charset.Charset;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C;

/**
 * Class which handles the LED strip that goes around the robot.
 * <p>
 * Used by {@linkplain Vision the Vision class} to allow the driver to see where to turn.
 * @author AcademyHS Robotics Team 5431
 *
 */
public class LED {
	private I2C i2c;
	private int I2cPort = 0;
	
	/**
	 * Default constructor which maps the {@link Solenoid solenoids} to their respective ID.
	 */
	public LED() {
	    this.i2c = new I2C(I2C.Port.kMXP, I2cPort);
	    
	    try{if(this.i2c.addressOnly()) {this.i2c = new I2C(I2C.Port.kMXP, 0);}}
	    catch(Throwable error) {error.printStackTrace();};
	}
	
	private void parseSend(String mode, int first, int second, int third) {
		 this.parseSend(mode,  "", first, second, third);
	}
	
	private void parseSend(String mode, String config, int first, int second, int third) {
		
		StringBuilder toSend = new StringBuilder();
		toSend.append("<M:");
		toSend.append(mode);
		toSend.append(":C:");
		toSend.append(config);
		toSend.append(":R:");
		toSend.append(first);
		toSend.append(":G:");
		toSend.append(second);
		toSend.append(":B:");
		toSend.append(third);
		toSend.append(">");
		this.SendI2c(toSend.toString());
	}
	
	public void SendI2c(String toSend) {
		try {
			byte[] toSendByte = toSend.getBytes(Charset.forName("UTF-8"));
			this.i2c.writeBulk(toSendByte);
		} catch(Throwable ignored){}
	}
	
	
	public void demo() {
		this.reset();
		for(int a = 0; a < 10; a++) {
			for(int b = 0; b < 10; b++) {
				this.turnLeft(255, 255, 12, 20);
				Timer.delay((25*32)/1000);
			}
			Timer.delay(5);
			for(int b = 0; b < 10; b++) {
				this.turnRight(30, 255, 0, 30);
				Timer.delay((35*32)/1000);
			}
			Timer.delay(5);
			for(int b = 0; b < 10; b++) {
				this.forwards(255, 255, 12, 50);
				Timer.delay((105*32)/1000);
			}
			Timer.delay(5);
			for(int b = 0; b < 10; b++) {
				this.backwards(30, 255, 0, 50);
				Timer.delay((105*32)/1000);
			}
			Timer.delay(5);
			this.wholeStripRGB(255, 0, 0);
			Timer.delay(5);
		}
	} 
	
	/**
	 * Resets all of the LED lights, turning them all off.
	 */
	public void reset() {
		this.parseSend("WE", 0, 0, 0);
	}
	
	public void wholeStripRGB(int red, int green, int blue) {
		this.parseSend("WE", red, green, blue);
	}
	
	public void turnLeft(int red, int green, int blue, int speed) {
		this.parseSend("LT", String.valueOf(speed), red, green, blue);
	}
	
	public void turnRight(int red, int green, int blue, int speed) {
		this.parseSend("RT", String.valueOf(speed), red, green, blue);
	}
	
	public void forwards(int red, int green, int blue, int speed) {
		this.parseSend("FW", String.valueOf(speed), red, green, blue);
	}
	
	public void backwards(int red, int green, int blue, int speed) {
		this.parseSend("BW", String.valueOf(speed), red, green, blue);
	}
}
