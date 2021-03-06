package shipSearch;

public class Ship {
	int x,y;
	Map map;
	private static Clcg4 unigen;
	//public Wind wind;
	//double timeToStay;
	
	public Ship(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Ship(Map map, Clcg4 gen){
		this.unigen = gen;
        //this.wind = wind;
        this.map = map;
    		this.x = (int) Math.floor(unigen.nextValue(1) * 4);
    		this.y = (int) Math.floor(unigen.nextValue(2) * 4);
//    		System.out.print("shipX:");
//    		System.out.print(this.x);
//    		System.out.print("shipY:");
//    		System.out.println(this.y);

	}
	public void move(Wind wind){
		int tempX,tempY;
		double probAX = 0, probBX = 0, probCX = 0;
		double probAY = 0, probBY = 0, probCY = 0;
		int windX = wind.getDirectionX();
		int windY = wind.getDirectionY();
		
		if(windX == 1){
			if(x == map.row){
				probAX = 1;	 //right
				probBX = 1;//stay
				probCX = 1.0/6;//left
			}else if(x == 0){
				probAX = 1;
				probBX = 1.0/3;
				probCX = 0.0;
			}else{
				probAX = 1;
				probBX = 1.0/3;
				probCX = 1.0/6;
			}
		}else if(windX == 0){
			if(x == map.row){
				probAX = 1;	 //right
				probBX = 1;//stay
				probCX = 1.0/3;//left
			}else if(x == 0){
				probAX = 1;
				probBX = 2.0/3;
				probCX = 0;
			}else{
				probAX = 1;
				probBX = 2.0/3;
				probCX = 1.0/3;
			}
		}else if(windX == -1){
			if(x == map.row){
				probAX = 1;	 //right
				probBX = 1;//stay
				probCX = 2.0/3;//left
			}else if(x == 0){
				probAX = 1;
				probBX = 5.0/6;
				probCX = 0;
			}else{
				probAX = 1;
				probBX = 5.0/6;
				probCX = 4.0/6;
			}
		}
		
		
		if(windY == 1){
			if(y == map.row){
				probAY = 1;	 //right
				probBY = 1;//stay
				probCY = 1.0/6;//left
			}else if(y == 0){
				probAY = 1;
				probBY = 1.0/3;
				probCY = 0;
			}else{
				probAY = 1;
				probBY = 1.0/3;
				probCY = 1.0/6;
			}
		}else if(windY == 0){
			if(y == map.row){
				probAY = 1;	 //right
				probBY = 1;//stay
				probCY = 1.0/3;//left
			}else if(y == 0){
				probAY = 1;
				probBY = 2.0/3;
				probCY = 0;
			}else{
				probAY = 1;
				probBY = 2.0/3;
				probCY = 1.0/3;
			}
		}else if(windY == -1){
			if(y == map.row){
				probAY = 1;	 //right
				probBY = 1;//stay
				probCY = 2.0/3;//left
			}else if(y == 0){
				probAY = 1;
				probBY = 5.0/6;
				probCY = 0;
			}else{
				probAY = 1;
				probBY = 5.0/6;
				probCY = 4.0/6;
			}
		}

		
		
		do{
			double moveX = unigen.nextValue(1);
			double moveY = unigen.nextValue(2);	
			if( moveX < probCX){
				tempX = this.x - 1;
			}else if(moveX < probBX){
				tempX = this.x;
			}else{
				tempX = this.x + 1;
			}
			if( moveY < probCY){
				tempY = this.y - 1;
			}else if(moveY < probBY){
				tempY = this.y;
			}else{
				tempY = this.y + 1;
			}	
		}while(!map.inMap(tempX, tempY));
		
		this.x = tempX;
		this.y = tempY;
//		System.out.print("shipX:");
//		System.out.print(this.x);
//		System.out.print("shipY:");
//		System.out.println(this.y);
	}
	
	public double generateHoldingTime(){
		//how to calculate holding time
		double holdingTime = 0.5 * Math.pow(unigen.nextValue(3), -0.5);
		//System.out.println(holdingTime);
		return holdingTime;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
