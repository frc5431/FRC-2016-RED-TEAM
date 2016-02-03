package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.map.OI;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Class that handles the pnuematics of the lift mechanism
 * */
public class PneumaticBase {

	private Compressor comp;
	private DoubleSolenoid test;
	
	/**
	 * Default constructor which sets up the {@linkplain Compressor compressor} and {@linkplain DoubleSolenoid solenoid}.
	 * */
	public PneumaticBase() {
		comp = new Compressor();
		test = new DoubleSolenoid(4, 5);
	}
	
	/**
	 * Starts compressing the air
	 * */
	public void startCompressor() {
		comp.setClosedLoopControl(true);
		comp.start();
	}
	
		/**
	 * Makes the {@linkplain DoubleSolenoid solenoid} input.
	 * */
	public void solIn() {
		test.set(DoubleSolenoid.Value.kForward);
	}
	
	/**
	 * Makes the {@linkplain DoubleSolenoid solenoid} output.
	 * */
	public void solOut() {
		test.set(DoubleSolenoid.Value.kReverse);
	}
	
	/**
	 * Moves the {@linkplain DoubleSolenoid solenoid} based on the input from the {@link OI}.
	 * @param map Operator control map
	 * */
	public void checkInput(OI map) {
		if(map.isSolIn()) {
			this.solIn();
		} else if(map.isSolOut()) {
			this.solOut();
		}
	}
	
}
