/**
 * 
 */
package org.usfirst.frc.team5431.libs;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

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
	private static final double 
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
	
	public 
	
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
	
}

public class Vision {

	private static NetworkTable grip;
	private static Maths math;
	private final double[] defaults = {0};
	
	public Vision() {
		grip = NetworkTable.getTable("GRIP/vision");
		math = new Maths();
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
	
	//See if number is within two other numbers
    public boolean withIn(double num, double lower, double upper) {
    	return ((num >= lower) && (num <= upper));
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
