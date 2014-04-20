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
    
    public static void main(String[] args) {
        gEstimatorA = new Estimator(1.96,"95%", "##.####"); // 95% CI       
        unigen = new Clcg4();
        unigen.initDefault();
   
        df1 = new DecimalFormat("###.###");
        debug = 0;
        trial = false;
        ci = false;
        String arg;
        int i=0;
        epsilon = 0.005;
        double valA;
        
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
        if (!trial) {
            unigen.initGenerator(1, unigen.NewSeed);
        
        }
        
        //run the simulation repetitions and collect stats
        for (int rep = 1; rep <= numReps; rep++) {
            valA = doRepA();
            if (debug >= 1) {
                System.out.print("Repetition "+rep+": "+df1.format(valA)+"       ");
            }
            if (debug >=1) System.out.println();
            if (debug >=2) System.out.println();
        }
        
        // print results
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
                    + gEstimatorA.numberOfTrialsNeeded(epsilon,false));
        }
        
    }
    /*
     * Please Program here
     */
    static double doRepA() {
    	boolean found = false;
        double val = 0;
        Map map = new Map(4, 4);
		Plane plane1 = new Plane(1, map);
		Plane plane2 = new Plane(2, map);
		Ship ship = new Ship(map);
		Wind wind = new Wind();
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
		
		//Make the transition
		while(!found){
			Clock minClock = Clock.findMinTime(timeChain);
			
			//Depending on the clock, making the relevant transitions
			switch(minClock.type){
			case Plane1Clock:
				break;
			case Plane2Clock:
				break;
			case ShipClock:
				break;
			case WindClock:
				break;
			case SearchClock:
				break;
			}
			
			//Updating the clocks readings.
			
		}
		
        if (debug >= 2) System.out.println();
        gEstimatorA.processNextValue(val);
        return val;
    }
//	public static void main(int argc, char[] argv){
//		Map map = new Map(4, 4);
//		Plane plane1 = new Plane(1, map);
//		Plane plane2 = new Plane(2, map);
//		int[] a = {1,2};
//		System.out.print("gaga");
//	}
}
