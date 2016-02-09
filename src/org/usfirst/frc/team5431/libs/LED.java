package org.usfirst.frc.team5431.libs;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class LED {
	private Solenoid r,g,b;
	
	public LED() {
	    r = new Solenoid(2);
	    g = new Solenoid(0);
	    b = new Solenoid(1);
	}
	
	private void reset() {
		// Turn all LEDs off before turning them on again
		r.set(false);
		g.set(false);
		b.set(false);
	}
	
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
