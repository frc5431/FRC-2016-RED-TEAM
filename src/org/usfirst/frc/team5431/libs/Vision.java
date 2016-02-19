/**
 * 
 */
package org.usfirst.frc.team5431.libs;

import org.usfirst.frc.team5431.robot.Robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Private class to calculate items based on camera position(Liav can you do better docs on this?) (sure)

/**
 * Private inline class for {@link Vision} with various helper methods.
 * @author AcademyHS Robotics Team 5431
 *
 */
class Maths {
	
	//Choose hole options (Total should be 1.0)
	private static final double 
			areaNum = 0.2,
			distNum = 0.2,
			solidNum = 0.4,
			fromNum = 0.2;
	
	//Distances and resolution values
	public static final double 
			screenHalf = 170,
			minDistance = 77,
			maxDistance = 127,
			leftTrig = -13,
			rightTrig = 13;
			
	/**
	 * Calculates the distance of a location
	 * <p>
	 * <b>Make sure to pretest the values</b>
	 * 
	 * @param pixelsFromTop Number of pixels from the top
	 * @return The distance from the hole
	 * */
	public double DistanceCalc(double pixelsFromTop) {
		return (47.0943) * Math.pow(1.0072, pixelsFromTop); //return (33.8569 * Math.pow(1.007, pixelsFromTop)); //Make sure you pre test these values
	}
	
	public double SpeedCalc(double distanceFromTower) {
		return distanceFromTower;
	}
	
	/**
	 * Checks the distance of a location from the center of the camera
	 * @param half Center of the camera, in pixels
	 * @param current Location to check
	 * @return Distance from the center of the camera, in pixels. Negative values means it's to the left. 
	 * */
	public double fromCenter(double half, double current) {
		return current - half;
	}
	
	/**
	 * Returns which hole to use based on various info
	 * @param areas Array with the area of each hole, where each index refers to a hole (array[0]=hole 0)
	 * @param distances Array with the distance of each hole from the camera, where each index refers to a hole (array[0]=hole 0)
	 * @param solidity  Array with the solidity of each hole, where each index refers to a hole (array[0]=hole 0)
	 * @param fromCenter Array with the distance of each hole from the center of the camera in pixels, where each index refers to a hole (array[0]=hole 0.) Negative means to the left.
	* @return Hole ID based on the parameters. If 666 is returned, no hole was found.
	 * */
	public int chooseHole(double[] areas, double[] distances, double[] solidity, double[] fromCenter)
    {	
		int amount = areas.length;
		
    	try
    	{
    		double holes[] = {0}; //Don't mess
    		double largest = 0; //Don't mess
    		int current = 0; //Don't mess
		
    		//If any of the values are negative make sure that they are positive
	    	for(int now = 0; now < amount; now++) {
	    		areas[now] = (areas[now] < 0) ? -(areas[now]) : areas[now];
	    		distances[now] = (distances[now] < 0) ? -(distances[now]) : distances[now];
	    		solidity[now] = (solidity[now] < 0) ? -(solidity[now]) : solidity[now];
	    		fromCenter[now] = (fromCenter[now] < 0) ? -(fromCenter[now]) : fromCenter[now];
	    	}
	    	
	    	//Calculate the values by multiplying the max values
	    	for(int now = 0; now < amount; now++) {
	    		holes[now] = ((areas[now]/2000) * areaNum) + ((1-(distances[now]/maxDistance)) * distNum)
	    		+ ((solidity[now]/100) * solidNum) + ((fromCenter[now]/screenHalf) * fromNum);
	    	}
	    	
	    	//See which hole was the largest and add that to the current hole
	    	for(int now = 0; now < holes.length; now++) {
	    		if(holes[now] > largest) {
	    			largest = holes[now];
	    			current = now;
	    		}
	    	}
	    	return current;
    	}
    	catch(Exception ignored) {
    		return 666; //Return 666 which means none found
    	}
    }
	
	//See if number is within two other numbers
	/**
	 * Checks to see if a number is within two other numbers
	 * @param num Number to compare
	 * @param lower Lower bound
	 * @param upper Upper bound
	 * @return Whether num is within lower and upper bounds.
	 * */
    public boolean withIn(double num, double lower, double upper) {
    	return ((num >= lower) && (num <= upper));
    }
	
}

/**
 * Class which calculates where to shoot based on various factors, and stores it in a {@link NetowrkTable}.
 * <p>
 * Additionally, adjusts the {@linkplain LED LEDs} based on when you can shoot.
 * 
 * @see TurretBase
 * @author AcademyHS Robotics Team 5431
 * */
public class Vision {

	private static NetworkTable grip;
	private static Maths math;
	//private static LED led;
	private final double[] defaults = {0};
	private int count = 0;
	
	//Holders for updates
	/**
	 * Holder variable for updates
	 * */
	public static double[]
			areas = {0},
			distances = {0},
			fromCenters = {0},
			holeSolids = {0};
			
	
	/**
	 * Default constructor
	 * */
	public Vision() {
		grip = NetworkTable.getTable("GRIP/vision");
		math = new Maths();
		//led = new LED();
	}
	
	/**
	 * Updates holder values for the holes.
	 * @see #areas
	 * @see #distances
	 * @see #fromCenters
	 * @see #holeSolids
	 * */
	public void updateVals() {
		areas = this.area();
		distances = this.distance();
		fromCenters = this.fromCenter(Maths.screenHalf);
		holeSolids = this.solidity();
	}
	
	/**
	 * Updates the {@linkplain SmartDashboard} with the new values.
	 * 
	 * @return An array of values, which says what speed to set the motors of
	 * @see #updateSmartDash(double)
	 * */
	
	public double[] updateSmartDash() {
		return this.updateSmartDash(0.0);
	}
	
	/**
	 * Updates the {@linkplain SmartDashboard} with the new values.
	 * 
	 * @param offVal Value to set if it is off
	 * @return An array of values, where each value says if it is ready to shoot. A 1 size array of 0 means it failed or not ready to shoot.
	 * @see #updateVals()
	 * */
	public double[] updateSmartDash(double offVal) {
		
		SmartDashboard.putNumber("UPDATE VALUE", count);
		
		count += 1;
		
		double toReturn[] = {0, 0, 0};
		
		int toShoot = math.chooseHole(areas, distances, holeSolids, fromCenters); //Chooses an object to shoot at(Method below)
		SmartDashboard.putNumber("Hole Num:", toShoot); //Display to dashboard what to shoot at
		
		if(toShoot != 666) {//Don't shoot at nothing (THE DEVIL)
			double tempCenter = this.fromCenter(Maths.screenHalf)[toShoot]; //Temp center values
			
			//Display values to SmartDashboard!
			SmartDashboard.putNumber("Hole area:", areas[toShoot]);
			SmartDashboard.putNumber("Distance:", distances[toShoot]);
			SmartDashboard.putNumber("From Center:", tempCenter);
			SmartDashboard.putNumber("Solidity:", holeSolids[toShoot]);
			
			int forback = (math.withIn(distances[toShoot], Maths.minDistance, Maths.maxDistance)) ? 0 : 
					(distances[toShoot] < Maths.minDistance) ? 1 : 2; //Get which direction to drive
					
			int lefight = (math.withIn(tempCenter, Maths.leftTrig, Maths.rightTrig)) ? 0 :
					(tempCenter < Maths.leftTrig) ? 1 : 2; //Amount to turn the turrent
			
			double readyVal = math.SpeedCalc(distances[toShoot]);
			
			if((forback == 0) && (lefight == 0)) {
				SmartDashboard.putString("FIRE", "YES FIRE!");
				SmartDashboard.putString("PULL", "YES FIRE!");	
				//Robot.led.wholeStripRGB(255, 0, 0);
				toReturn[0] = readyVal;
				toReturn[1] = 0;
				toReturn[2] = 0;
			} else {
				String pulling = "";
				String firing = "";
				if (forback == 1) {
					pulling = "Drive Back!";
					//Robot.led.backwards(0, 0, 255, 60);
				}else if(forback == 2) {
					pulling = "Drive Forward!";
					//Robot.led.forwards(0, 255, 255, 60);
				}
				
				if(lefight == 1) {
					firing = "Turn Left!";
					//Robot.led.turnLeft(255, 135, 0, 65);
				} else if(lefight == 2) {
					firing = "Turn Right!";
					//Robot.led.turnRight(255, 135, 0, 65);
				}
				SmartDashboard.putString("PULL", pulling);
				SmartDashboard.putString("FIRE", firing);
				
				toReturn[0] = offVal;
				toReturn[1] = lefight;
				toReturn[2] = forback;
				
			}
		} else {
			SmartDashboard.putString("FIRE", "HOLE NOT FOUND!");
			//Robot.led.wholeStripRGB(120, 140, 120);
			toReturn[0] = offVal;
			toReturn[1] = 5;
			toReturn[2] = 5;
		}
		
		return toReturn;
		
	}
	
	/**
	 * Stops the {@linkplain NetworkTable} safely, to stop any errors.
	 * */
	public void stop() {
		NetworkTable.shutdown(); //Shutdown table so we don't get dumb error
	}
	
	/**
	 * Gets the X value of all the holes from the {@link NetworkTable}
	 * 
	 * @return Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.
	 * */
	public double[] getX() {
		final double holesY[] = grip.getNumberArray("centerX", this.defaults);
		return (this.mult(holesY)) ? holesY : this.defaults; //Same for all below return array 0 if a problem
	}
	
	/**
	 * Gets the Y value of all the holes from the {@link NetworkTable}
	 * 
	 	 * @return Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.

	 * */
	public double[] getY() {
		final double holesX[] = grip.getNumberArray("centerY", this.defaults);
		return (this.mult(holesX)) ? holesX : this.defaults;
	}
	
	/**
	 * Gets the distance value of all the holes from the {@link NetworkTable}
	 * @return Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.
	 * */
	public double[] distance() {
		final double objects[] = 
				this.getX(), 
				distances[] = {0};
		int num = 0;
		for(double object : objects) { //Get distance for each object
			distances[num] = math.DistanceCalc(object); //Change based on regression
			num++;
		}
		return distances;
	}
	
	/**
	 * Gets the distance from the center of the screen in pixels of all the holes from the {@link NetworkTable}
	 * 
	 * @param HalfSize Center of the camera vision in pixels
	 * @return Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.

	 * */
	public double[] fromCenter(double HalfSize) {
		final double objects[] = 
				this.getX(),
				distances[] = {0};
		int num = 0;
		for(double object : objects) { //Find distance from each object from the center
			distances[num] = math.fromCenter(HalfSize, object);
			num++;
		}
		return distances;
	}
	
    //check if returned array is good
    /**
     * Checks an array to make sure it is valid
     * @return whether the array is good and valid.
     * @param multi Array to validate
     * */
    private boolean mult(double[] multi) {
    	try  {
    		return (multi[0] != 0 && multi.length >= 1); //If no error in array (Null) then return array
    	}
    	catch(Exception ignored) {
    		return false; //If error then return false to 
    	}
    }
	
	/**
	 * Gets the area of all the holes from the {@link NetworkTable}

	 * @param Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.

	 * */
	public double[] area() { //Get area for each object
		final double areas[] = grip.getNumberArray("area", this.defaults);
		return (this.mult(areas)) ? areas : this.defaults;
	}
	
	/**
	 * Gets the solidity of all the holes from the {@link NetworkTable}

	 	 * @param Array of all the hole values, where the index is the hole ID. If an array of 0 is returned, a problem occured.

	 * */
	public double[] solidity() { //Get solidity of object 0-100
		final double solidities[] = grip.getNumberArray("solidity", this.defaults);
		return (this.mult(solidities)) ? solidities : this.defaults;
	}
	
}
