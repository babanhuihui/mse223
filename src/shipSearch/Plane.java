package shipSearch;

public class Plane {
	int x,y;
	int index;
	Map map;
	private static Clcg4 unigenX;
	private static Clcg4 unigenY;
	private static Clcg4 unigenT;
	//double timeToStay;
	public Plane(int x, int y, int index){
		unigenX = new Clcg4();
	    unigenX.initDefault();
	    unigenY = new Clcg4();
	    unigenY.initDefault();
	    unigenT = new Clcg4();
	    unigenT.initDefault();
		this.x = x;
		this.y = y;
		this.index = index;
	}
	
	public Plane(int index, Map map){
        unigenX = new Clcg4();
        unigenX.initDefault();
        unigenY = new Clcg4();
        unigenY.initDefault();
        unigenT = new Clcg4();
        unigenT.initDefault();
        this.map = map;
        this.index = index;
        do{
    		this.x = (int) Math.floor(unigenX.nextValue(1) * 4);
    		this.y = (int) Math.floor(unigenY.nextValue(2) * 4);
        }
        while(map.planeInArea(this.x, this.y));
        map.initialPlane(x, y, index);	
	}
	
	public void move(){
		//int tempX = this.x, tempY = this.y;
		int tempX,tempY;
		do{
			double moveX = unigenX.nextValue(1);
			double moveY = unigenY.nextValue(2);	
			if( moveX < 1/3){
				tempX = this.x + 1;
			}else if(moveX < 2/3){
				tempX = this.x;
			}else{
				tempX = this.x - 1;
			}
			if( moveY < 1/3){
				tempY = this.y + 1;
			}else if(moveY < 2/3){
				tempY = this.y;
			}else{
				tempY = this.y - 1;
			}	
		}while(map.planeInArea(this.x, this.y) || !map.inMap(tempX, tempY));
		this.x = tempX;
		this.y = tempY;
		map.setPlane(this.x, this.y, this);
	}
	
	public double generateHoldingTime(){
		double holdingTime = unigenT.nextValue(3) * 0.25 + 1;
		return holdingTime;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getIndex(){
		return index;
	}
}
