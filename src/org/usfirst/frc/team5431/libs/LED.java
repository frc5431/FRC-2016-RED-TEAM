package org.usfirst.frc.team5431.libs;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * Class which handles the LED strip that goes around the robot.
 * <p>
 * Used by {@linkplain Vision the Vision class} to allow the driver to see where to turn.
 * @author AcademyHS Robotics
 *
 */
public class LED {
	private Solenoid r,g,b;
	
	/**
	 * Default constructor which maps the {@link Solenoid solenoids} to their respective ID.
	 */
	public LED() {
	    r = new Solenoid(2);
	    g = new Solenoid(0);
	    b = new Solenoid(1);
	}
	
	/**
	 * Resets all of the LED lights, turning them all off.
	 */
	private void reset() {
		// Turn all LEDs off before turning them on again
		r.set(false);
		g.set(false);
		b.set(false);
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
}
