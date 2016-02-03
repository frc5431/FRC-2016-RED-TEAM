package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.OI;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class PneumaticBase {

	private Compressor comp;
	private DoubleSolenoid test;
	
	public PneumaticBase() {
		comp = new Compressor();
		test = new DoubleSolenoid(4, 5);
	}
	
	public void startCompressor() {
		comp.setClosedLoopControl(true);
		comp.start();
	}
	
	public void solIn() {
		test.set(DoubleSolenoid.Value.kForward);
	}
	
	public void solOut() {
		test.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void checkInput(OI map) {
		if(map.isSolIn()) {
			this.solIn();
		} else if(map.isSolOut()) {
			this.solOut();
		}
	}
	
}
