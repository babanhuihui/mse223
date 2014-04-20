package shipSearch;

import java.util.ArrayList;

public class Wind {
	double lamuda;
	//public ArrayList<Integer> direction;
	public int x;
	public int y;
	private static Clcg4 unigenX;
	private static Clcg4 unigenY;
	private static Clcg4 unigenT;
	
	public Wind(double lamuda)
	{
		unigenX = new Clcg4();
	    unigenX.initDefault();
	    unigenY = new Clcg4();
	    unigenY.initDefault();
	    unigenT = new Clcg4();
	    unigenT.initDefault();
	    this.lamuda = lamuda;
		//direction = changeDirection();
	    changeDirection();
	}
	
	public Wind()
	{
		unigenX = new Clcg4();
	    unigenX.initDefault();
	    unigenY = new Clcg4();
	    unigenY.initDefault();
	    unigenT = new Clcg4();
	    unigenT.initDefault();
	    this.lamuda = 1;
		//direction = changeDirection();
	    changeDirection();
	}
	
	public double generateHoldingTime(){
		double x = unigenT.nextValue(3);
		double holdingTime = lamuda * Math.exp(-lamuda * x);
		return holdingTime;
	}
	/*
	 * 
	 
	public ArrayList<Integer> changeDirection(){
		ArrayList<Integer> direction = new ArrayList<Integer>();
		
		int directionX,directionY;
		double moveX = unigenX.nextValue(1);
		double moveY = unigenY.nextValue(2);	
		if( moveX < 1/3){
			directionX = 1;
		}else if(moveX < 2/3){
			directionX = 0;
		}else{
			directionX = -1;
		}
		if( moveY < 1/3){
			directionY = 1;
		}else if(moveY < 2/3){
			directionY = 0;
		}else{
			directionY = -1;
		}
		
		direction.add(directionX);
		direction.add(directionY);
		return direction;	
	}*/
	public void changeDirection(){
		changeDirectionX();
		changeDirectionY();
	}
	
	public void changeDirectionX(){
		int directionX;
		double moveX = unigenX.nextValue(1);
		if( moveX < 1.0/3){
			directionX = 1;
		}else if(moveX < 2.0/3){
			directionX = 0;
		}else{
			directionX = -1;
		}
		x = directionX;
	}
	
	public void changeDirectionY(){
		int directionY;
		double moveY = unigenY.nextValue(1);
		if( moveY < 1.0/3){
			directionY = 1;
		}else if(moveY < 2.0/3){
			directionY = 0;
		}else{
			directionY = -1;
		}
		y = directionY;
	}
	
	public int getDirectionX(){
		return x;
	}
	public int getDirectionY(){
		return y;
	}
	
}
