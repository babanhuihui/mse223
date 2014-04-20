package shipSearch;

import java.util.ArrayList;

public class Clock {
	public ClockType type;
	public double time;

	/**
	 * @param args
	 */
	public enum ClockType 
	{
		Plane1Clock, Plane2Clock, ShipClock, WindClock, SearchClock
	}
	public Clock(ClockType type, double time){
		this.type = type;
		this.time = time;
	}
	
	public static Clock findMinTime(ArrayList<Clock> clocks){
		if(clocks.size() == 0)
		{
			return null;
		}
		Clock minClock = clocks.get(0);
		for (int i= 0; i < clocks.size(); i++){
			if (clocks.get(i).time != -1 && clocks.get(i).time < minClock.time){
				minClock = clocks.get(i);
			}
		}
		return minClock;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
