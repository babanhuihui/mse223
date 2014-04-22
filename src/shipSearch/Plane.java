package shipSearch;

public class Plane {
	int x,y;
	int index;
	Map map;
	private static Clcg4 unigen;

	//double timeToStay;
	public Plane(int x, int y, int index){
		
		this.x = x;
		this.y = y;
		this.index = index;
	}
	
	public Plane(int index, Map map, Clcg4 gen){
		this.unigen = gen;
        this.map = map;
        this.index = index;
        do{
    		this.x = (int) Math.floor(unigen.nextValue(1) * 4);
    		this.y = (int) Math.floor(unigen.nextValue(2) * 4);
        }
        while(map.planeInArea(this.x, this.y));
        map.initialPlane(x, y, index);	
//        System.out.print("planeX:");
//		System.out.print(this.x);
//		System.out.print("planeY:");
//		System.out.println(this.y);
	}
	
	public void move(){
		//int tempX = this.x, tempY = this.y;
		int tempX,tempY;
		do{
			double moveX = unigen.nextValue(1);
			double moveY = unigen.nextValue(2);
			// we wonder if the probability of the plane movement should be change
			if( moveX < 1.0/3){
				tempX = this.x + 1;
			}else if(moveX < 2.0/3){
				tempX = this.x;
			}else{
				tempX = this.x - 1;
			}
			if( moveY < 1.0/3){
				tempY = this.y + 1;
			}else if(moveY < 2.0/3){
				tempY = this.y;
			}else{
				tempY = this.y - 1;
			}	
			//System.out.println("temp: " + tempX + " , " + tempY);
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}while( !map.inMap(tempX, tempY) || map.planeInArea(tempX, tempY));
		int swapX = this.x;
		this.x = tempX;
		int swapY = this.y;
		this.y = tempY;
		map.setPlane(swapX, swapY, this);
//		System.out.print("planeX:");
//		System.out.print(this.x);
//		System.out.print("planeY:");
//		System.out.println(this.y);
	}
	
	
	public void move(Wind wind){
		int tempX,tempY;
		double probAX = 0, probBX = 0, probCX = 0;
		double probAY = 0, probBY = 0, probCY = 0;
		int windX = wind.getDirectionX();
		int windY = wind.getDirectionY();
		
		if(windX == 1){
				probAX = 1;
				probBX = 1.0/3;
				probCX = 1.0/6;
		}else if(windX == 0){
				probAX = 1;
				probBX = 2.0/3;
				probCX = 1.0/3;
		}else if(windX == -1){
				probAX = 1;
				probBX = 5.0/6;
				probCX = 4.0/6;
		}
		
		
		if(windY == 1){
				probAY = 1;
				probBY = 1.0/3;
				probCY = 1.0/6;
		}else if(windY == 0){
				probAY = 1;
				probBY = 2.0/3;
				probCY = 1.0/3;
		}else if(windY == -1){
				probAY = 1;
				probBY = 5.0/6;
				probCY = 4.0/6;
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
		}while(!map.inMap(tempX, tempY) || map.planeInArea(tempX, tempY));
		
		this.x = tempX;
		this.y = tempY;
//		System.out.print("shipX:");
//		System.out.print(this.x);
//		System.out.print("shipY:");
//		System.out.println(this.y);
	}
	
	
	public double generateHoldingTime(){
		double holdingTime = unigen.nextValue(3) * 0.25 + 1;
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
