package shipSearch;

public class Wind {
	public int[] direction;
	
	public Wind()
	{
		direction = changeDirection();
	}
	
	public double generateHoldingTime(){
		return 0;
	}
	public int[] changeDirection(){
		int[] a = {1,2};
		return a;	
	}
}
