package com.gtxy.bird;

import java.util.Random;

public class Column extends BirdGameObject {

	public int gap ;
	public int distance;
	
	public Column(int n){
		image = BirdGame.column;
		this.width = image.getWidth();
		this.height = image.getHeight();
		gap = 144;
		distance = 245;
		x = 550+(n-1)*distance;
		y = new Random().nextInt(128)+132;
	}
	
	public void step(){
		x--;
		if(x==-width/2){
			x=distance*2-width/2;
			y=new Random().nextInt(128)+132;
		}
	}
}
