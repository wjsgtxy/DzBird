package com.gtxy.bird;

public class Ground extends BirdGameObject {

	public Ground(){
		image=BirdGame.groundimage;
		this.width=image.getWidth();
		this.height=image.getHeight();
		x=0;
		y=500;
	}
	public void step(){
		x--;
		if(x==-109){
			x=0;
		}
	}
}
