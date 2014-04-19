package shipSearch;

public class Ship {
	int x,y;
	Map map;
	private static Clcg4 unigen;
	private static Clcg4 unigen2;
	public Wind wind;
	//double timeToStay;
	public Ship(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Ship(Map map, Wind wind){
        unigen = new Clcg4();
        unigen.initDefault();
        unigen2 = new Clcg4();
        unigen2.initDefault();
        this.wind = wind;
        this.map = map;
        do{
    		this.x = (int) Math.floor(unigen.nextValue(1) * 4);
    		this.y = (int) Math.floor(unigen2.nextValue(2) * 4);
        }
        while(map.planeInArea(this.x, this.y));
	}
	public void move(){
		
	}
	public double generateHoldingTime(){
		return 0;
	}
}
