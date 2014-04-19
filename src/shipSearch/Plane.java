package shipSearch;

public class Plane {
	int x,y;
	int index;
	Map map;
	private static Clcg4 unigen;
	private static Clcg4 unigen2;
	//double timeToStay;
	public Plane(int x, int y, int index){
		this.x = x;
		this.y = y;
		this.index = index;
	}
	public Plane(int index, Map map){
        unigen = new Clcg4();
        unigen.initDefault();
        unigen2 = new Clcg4();
        unigen2.initDefault();
        this.map = map;
        do{
    		this.x = (int) Math.floor(unigen.nextValue(1) * 4);
    		this.y = (int) Math.floor(unigen2.nextValue(2) * 4);
        }
        while(map.planeInArea(this.x, this.y));
		this.index = index;
	}
	public void move(){
		
	}
	public double generateHoldingTime(){
		return 0;
	}
}
