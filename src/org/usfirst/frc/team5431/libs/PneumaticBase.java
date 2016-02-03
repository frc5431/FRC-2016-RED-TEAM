package org.usfirst.frc.team5431.libs;

import edu.wpi.first.wpilibj.Compressor;

public class PneumaticBase {

	private Compressor comp;
	
	public PneumaticBase() {
		comp = new Compressor();
	}
	
	public void startCompressor() {
		comp.setClosedLoopControl(true);
		comp.start();
	}
	
}
