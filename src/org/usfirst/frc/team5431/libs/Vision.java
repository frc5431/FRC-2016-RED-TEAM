/**
 * 
 */
package org.usfirst.frc.team5431.libs;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Team5431
 *
 */

//Private class to calculate items based on camera position(Liav can you do better docs on this?)
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
			minDistance = 90,
			maxDistance = 144,
			leftTrig = -10,
			rightTrig = 10;
			
	
	public double DistanceCalc(double pixelsFromTop) {
		return (33.8569 * Math.pow(1.007, pixelsFromTop)); //Make sure you pre test these values
	}
	
	public double fromCenter(double half, double current) {
		return current - half;
	}
	
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
    public boolean withIn(double num, double lower, double upper) {
    	return ((num >= lower) && (num <= upper));
    }
	
}

public class Vision {

	private static NetworkTable grip;
	private static Maths math;
	private final double[] defaults = {0};
	
	//Holders for updates
	public static double[]
			areas = {0},
			distances = {0},
			fromCenters = {0},
			holeSolids = {0};
			
	
	public Vision() {
		grip = NetworkTable.getTable("GRIP/vision");
		math = new Maths();
	}
	
	public void updateVals() {
		areas = this.area();
		distances = this.distance();
		fromCenters = this.fromCenter(Maths.screenHalf);
		holeSolids = this.solidity();
	}
	
	public void updateSmartDash() {
		this.updateSmartDash(0, 0);
	}
	
	public double[] updateSmartDash(double readyVal, double offVal) {
		
		double[] toReturn = {0};
		
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
			
			if((forback == 0) && (lefight == 0)) {
				SmartDashboard.putString("FIRE", "YES FIRE!");
				SmartDashboard.putString("PULL", "YES FIRE!");	
				toReturn[0] = readyVal;
				toReturn[1] = 0;
				toReturn[2] = 0;
			} else {
				SmartDashboard.putString("PULL", ((forback == 0) ? "" : (forback == 1) ? "Drive Back!" : "Drive Forward!")); //Display to the dashboard
				SmartDashboard.putString("FIRE", ((lefight == 0) ? "" : (lefight == 1) ? "Turn Left!" : "Turn Right!")); //Display to the dashboard
				toReturn[0] = ((readyVal+offVal)/2) - 0.05;
				toReturn[1] = lefight;
				toReturn[2] = forback;
			}
		} else {
			SmartDashboard.putString("FIRE", "HOLE NOT FOUND!");
			toReturn[0] = offVal;
			toReturn[1] = 0;
			toReturn[2] = 0;
		}
		
		return toReturn;
		
	}
	
	public void stop() {
		NetworkTable.shutdown(); //Shutdown table so we don't get dumb error
	}
	
	public double[] getX() {
		final double holesY[] = grip.getNumberArray("centerX", this.defaults);
		return (this.mult(holesY)) ? holesY : this.defaults; //Same for all below return array 0 if a problem
	}
	
	public double[] getY() {
		final double holesX[] = grip.getNumberArray("centerY", this.defaults);
		return (this.mult(holesX)) ? holesX : this.defaults;
	}
	
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
    private boolean mult(double[] multi) {
    	try  {
    		return (multi[0] != 0 && multi.length >= 1); //If no error in array (Null) then return array
    	}
    	catch(Exception ignored) {
    		return false; //If error then return false to 
    	}
    }
	
	public double[] area() { //Get area for each object
		final double areas[] = grip.getNumberArray("area", this.defaults);
		return (this.mult(areas)) ? areas : this.defaults;
	}
	
	public double[] solidity() { //Get solidity of object 0-100
		final double solidities[] = grip.getNumberArray("solidity", this.defaults);
		return (this.mult(solidities)) ? solidities : this.defaults;
	}
	
}
