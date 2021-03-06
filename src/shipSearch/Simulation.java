package shipSearch;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class Simulation {
	
	private static Clcg4 unigen;
	private static Estimator gEstimatorA;
    private static int debug;
    private static boolean trial, ci;
    private static int numReps;
    private static double epsilon;
    private static DecimalFormat df1; 
    private static int count;
    
    public static void main(String[] args) {
        //gEstimatorA = new Estimator(1.96,"95%", "##.####"); // 95% CI       
        gEstimatorA = new Estimator(2.33,"98%", "##.####"); // 95% CI       
        unigen = new Clcg4();
        unigen.initDefault();
   
        df1 = new DecimalFormat("###.###");
        debug = 0;
        trial = false;
        ci = false;
        String arg;
        int i=0;
        epsilon = 0.01;
        double valA;
        count = 0;
        // parse command line arguments
        if (args.length < 1) {
          System.out.println("usage: Simulation n [-dm -t -i]");
          return;
        }
        numReps = Integer.parseInt(args[i++]);
        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];
            if (arg.equals("-t")) {
                trial = true;
            }
            else if (arg.equals("-i")) {
                ci = true;
            }
            else if (arg.startsWith("-d")) {
                debug = Integer.parseInt(arg.substring(2));
            }
            else {
               System.out.println("bad command line arg: "+arg);
               return; 
            }
        }

        //re-initialize generator for production runs
//        if (!trial) {
//            unigen.initGenerator(1, unigen.NewSeed);
//        
//        }
//        
        //run the simulation repetitions and collect stats
        for (int rep = 1; rep <= numReps; rep++) {
            valA = doRepA();
            if (debug >= 1) {
                System.out.print("Repetition "+rep+": "+df1.format(valA)+"       ");
            }
            if (debug >=1) System.out.println();
            if (debug >=2) System.out.println();
        }
        
        // print result
        System.out.print("the count is");
        System.out.println(count);
        System.out.print("Prob is ");
        System.out.println(count/500.0);
        System.out.print("Average income InvestorA: "+df1.format(gEstimatorA.mean())+"     ");
        if (ci){
        	System.out.println();
            System.out.print("A with ");
            gEstimatorA.printConfidenceInterval();
            System.out.println();
        }
        else System.out.println();
        if (trial) {
            System.out.println("Est. # of repetitions for +/- "+epsilon+" accuracy: "
                    + gEstimatorA.numberOfTrialsNeeded(epsilon,true));
        }
        
    }
    /*
     * Please Program here
     */
    static double doRepA() {
    	boolean found = false;
        Wind wind = new Wind(1.5,unigen);
        Map map = new Map(4, 4);
		Plane plane1 = new Plane(1, map,unigen);
		Plane plane2 = new Plane(2, map, unigen);
		Ship ship = new Ship(map, unigen);
		double totalTime = 0;
		
		//array of c1,c2,c3,c4,c5
		ArrayList<Clock> timeChain = new ArrayList<Clock>();
		//Add in the 5 clocks
		Clock planeClock = new Clock(Clock.ClockType.Plane1Clock, plane1.generateHoldingTime());
		timeChain.add(planeClock);
		Clock plane2Clock = new Clock(Clock.ClockType.Plane2Clock, plane2.generateHoldingTime());
		timeChain.add(plane2Clock);
		Clock shipClock = new Clock(Clock.ClockType.ShipClock, ship.generateHoldingTime());
		timeChain.add(shipClock);
		Clock windClock = new Clock(Clock.ClockType.WindClock, wind.generateHoldingTime());
		timeChain.add(windClock);
		Clock searchClock = new Clock(Clock.ClockType.SearchClock, -1);
		timeChain.add(searchClock);
		
		Plane pendingPlane = null;
		//Make the transition
		while(!found){
			Clock minClock = Clock.findMinTime(timeChain);
			//System.out.println("minClock" + minClock.type);
			double timeElapsed = minClock.time;
			//Depending on the clock, making the relevant transitions
			switch(minClock.type){
			case Plane1Clock:
				//System.out.println("plane1");
				plane1.move();
				//System.out.println("plane1step1");
				pendingPlane = checkIfInSameArea(ship, plane1, plane2,searchClock);
				minClock.time = plane1.generateHoldingTime();
				break;
			case Plane2Clock:
				//System.out.println("plane2");
				plane2.move();
				//System.out.println("plane2step2");
				pendingPlane = checkIfInSameArea(ship, plane1, plane2, searchClock);
				minClock.time = plane2.generateHoldingTime();
				break;
			case ShipClock:
				System.out.println("ship");
				ship.move(wind);
				System.out.println("shipstep2");
				pendingPlane = checkIfInSameArea(ship, plane1, plane2, searchClock);
				minClock.time = ship.generateHoldingTime();
				break;
			case WindClock:
				wind.changeDirection();
				minClock.time = wind.generateHoldingTime();
				break;
			case SearchClock:
				System.out.println("in same block now");
				//i don't understand your pending plane mechanism here
				//found = checkIfFound(pendingPlane, ship);
				found = checkIfFound(pendingPlane, ship, searchClock) || checkIfFoundDirectly(ship, plane1, plane2, searchClock);
				if(!found){
					minClock.time = -1;
				}
				break;
			}
			//Updating the clocks readings.
			
			for (int i = 0; i < timeChain.size(); i ++){
				Clock clock = timeChain.get(i);
				if (clock.time != -1){
					clock.time -= timeElapsed;
				}
			}
			//updating the state
			
			//TODO updating the total time
			totalTime += timeElapsed;
			//System.out.println(totalTime);
			
		}
		if(totalTime < 24){
			count++;
		}
        if (debug >= 2) System.out.println();
        //System.out.println(totalTime);
        gEstimatorA.processNextValue(totalTime);
        //gEstimatorA.processNextValue(searchClock.time);
        return totalTime;
    }

	private static Plane checkIfInSameArea(Ship ship, Plane plane1, Plane plane2, Clock searchClock) {
		// TODO Auto-generated method stub
		if (ship.x == plane1.x && ship.y == plane1.y){
			searchClock.time =  generateSearchingTime();
			return plane1;
		}else if (ship.x == plane2.y && ship.y == plane2.y){
			searchClock.time = generateSearchingTime();
			return plane2;
		}
		return null;
	}

	private static boolean checkIfFoundDirectly(Ship ship, Plane plane1, Plane plane2, Clock searchClock) {
		// TODO Auto-generated method stub
		if (ship.x == plane1.x && ship.y == plane1.y){
			searchClock.time =  generateSearchingTime();
			return true;
		}else if (ship.x == plane2.y && ship.y == plane2.y){
			searchClock.time =  generateSearchingTime();
			return true;
		}
		return false;
	}
	private static boolean checkIfFound(Plane pendingPlane, Ship ship, Clock searchClock) {
		// TODO check if the ship is already found
		if (pendingPlane != null){
			if (pendingPlane.x == ship.x && pendingPlane.y == ship.y){
				searchClock.time =  generateSearchingTime();
				return true;
			}
		}
		pendingPlane = null;
		return false;
	}
	
	public static double generateSearchingTime(){
		Clcg4 unigen = new Clcg4();
	    unigen.initDefault();
		double u = unigen.nextValue(3);
		if (u <= 0.5){
			return Math.sqrt(2*u);
		}else
		{
			return 2-Math.sqrt(2 - 2*u);
		}
	}
}
