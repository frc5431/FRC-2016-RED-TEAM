package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.SolonoidMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.I2C;

/**
 * Class which handles the LED strip that goes around the robot.
 * <p>
 * Used by {@linkplain Vision the Vision class} to allow the driver to see where to turn.
 * @author AcademyHS Robotics Team 5431
 *
 */
public class LED {
	private Solenoid r,g,b;
	private I2C serial;
	
	/**
	 * Default constructor which maps the {@link Solenoid solenoids} to their respective ID.
	 */
	public LED() {
	    //r = new Solenoid(SolonoidMap.LED_RED);
	    //g = new Solenoid(SolonoidMap.LED_GREEN);
	    //b = new Solenoid(SolonoidMap.LED_BLUE);
	    
	    serial = new I2C(I2C.Port.kMXP, 0);
	}
	
	/**
	 * Resets all of the LED lights, turning them all off.
	 */
	public void reset() {
		// Turn all LEDs off before turning them on again
		//r.set(false);
		//g.set(false);
		//b.set(false);
	    byte[] o = "OKKKK".getBytes();
	    byte[] t = {};
	    serial.transaction(o, o.length, t, 1);
	}
	
	public boolean parseSend(String mode, int first, int second, int third) {
		return this.parseSend(mode,  "", first, second, third);
	}
	
	private boolean parseSend(String mode, String config, int first, int second, int third) {
		
		StringBuilder toSend = new StringBuilder();
		
		toSend.append("<<<::MODE::");
		toSend.append(mode);
		toSend.append("::CONFIG::");
		toSend.append(config);
		toSend.append("::R::");
		toSend.append(first);
		toSend.append("::G::");
		toSend.append(second);
		toSend.append("::B::");
		toSend.append(third);
		toSend.append(">>>");
		
		boolean gb = false;
		
		try {
			gb = (this.SendI2C(toSend.toString()) == "G");
		} catch(Throwable problem) {
			problem.printStackTrace();
		}
		
		return gb;
	}
	
	public String SendI2C(String toSend) {
		byte[] toSendByte = String.valueOf(toSend + "$").getBytes();
		byte[] toRecv = {};
		
		serial.transaction(toSendByte, toSendByte.length, toRecv, 1);
		
		return toRecv.toString();
	}
	
	/**
	 * Makes the led's a different color based on a string.
	 * <p>
	 * <table>
  <col width="50%"/>
  <col width="50%"/>
  <thead>
    <tr><th>Input String</th><th>Output LED Color</th></tr>
  <thead>
  <tbody>
     <tr><td>r or red</td><td>Red</td></tr>
          <tr><td>b or blue</td><td>Blue</td></tr>
     <tr><td>g or green</td><td>Green</td></tr>
     <tr><td>c or cyan</td><td>Cyan</td></tr>
     <tr><td>p or purple</td><td>Purple</td></tr>
     <tr><td>y or yellow</td><td>Yellow</td></tr>
     <tr><td>w or white</td><td>White</td></tr>

  </tbody>
</table>
	 * 
	 * 
	 * @param color String that specifies which color the LED will be based on the above table.
	 */
	public void LEDFromColor(String color) {
		
		reset();

		switch (color) {
			case "b":
			case "blue":
				b.set(true);
				break;
				
			case "g":
			case "green":
				g.set(true);
				break;
				
			case "r":
			case "red":
				r.set(true);
				break;
				
			case "c":
			case "cyan":
				b.set(true);
				g.set(true);
				break;
			
			case "y":
			case "yellow":
				// r + g
				r.set(true);
				g.set(true);
				break;
			
			case "p":
			case "purple":
				// r + b
				r.set(true);
				b.set(true);
				break;
				
			case "w":
			case "white":
				r.set(true);
				g.set(true);
				b.set(true);
				break;
		}
	}
	
	/**
	 * Demonstrates this class's ability to control LED by going through 7 different combinations.
	 * <p>
	 * It goes through:
	 * <ol>
	 * <li>
	 * Red
	 * </li>
	 * <li>
	 * Blue
	 * </li>
	 * <li>
	 * Green
	 * </li>
	 * <li>
	 * Cyan
	 * </li>
	 * <li>
	 * Purple
	 * </li>
	 * <li>
	 * Yellow
	 * </li>
	 * <li>
	 * White
	 * </li>
	 * </ol>
	 * <p>
	 * <b>This method blocks until the demo is complete</b>
	 */
	public void demo() {
		this.LEDFromColor("r");
		Timer.delay(2);
		this.LEDFromColor("b");
		Timer.delay(2);
		this.LEDFromColor("g");
		Timer.delay(2);
		this.LEDFromColor("c");
		Timer.delay(2);
		this.LEDFromColor("p");
		Timer.delay(2);
		this.LEDFromColor("y");
		Timer.delay(2);
		this.LEDFromColor("w");
		Timer.delay(2);
		reset();
	}
	
	public void wholeStripRGB(int red, int green, int blue) {
		SmartDashboard.putBoolean("LED_FAILURE:", this.parseSend("WHOLE", red, green, blue));
	}
	
	public void turnLeft(int red, int green, int blue, int speed) {
		this.parseSend("LEFT", String.valueOf(speed), red, green, blue);
	}
	
	public void turnRight(int red, int green, int blue, int speed) {
		this.parseSend("RIGHT", String.valueOf(speed), red, green, blue);
	}
}
