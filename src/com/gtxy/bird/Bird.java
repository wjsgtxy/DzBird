package com.gtxy.bird;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird extends BirdGameObject {

	private int size;
	private double g;
	private double t;
	private double v0;
	private double speed;
	private double s;
	public double alpha;
	private BufferedImage images[];
	private int index;
	
	
	public Bird() throws IOException{
		image = BirdGame.bird0;
		this.width=image.getWidth();
		this.height=image.getHeight();
		this.x=132;
		this.y=280;
		size=40;
		
		g=9;
		v0=20;
		t=0.25;
		speed=v0;
		s=0;
		alpha=0;
		
		images=new BufferedImage[8];
		for(int i=0;i<images.length;i++){
			images[i] = ImageIO.read(getClass().getResource(i+".png"));
		}
		
		index=0;
	}
	
	public void step(){
		double v0=speed;
		s=v0*t+g*t*t/2;
		y=y-(int)s;
		double v=v0-g*t;
		speed=v;
	}
	
	public void fly(){
		index++;
		image=images[(index/12)%images.length];//?%和/的优先级
	}
	
	public void flappy(){//???
		speed=v0;
	}
	
	public boolean hit(Ground ground){
		boolean isHit = this.y+size/2>ground.y;
		if(isHit){
			y=ground.y-size/2;
			alpha=-Math.PI/2;
		}
		return isHit;
	}
	
	public boolean hit(Column column){
		if(this.x>column.x-column.width/2-size/2&&x<column.x+column.width/2+size/2){
			if(y>column.y-column.gap/2+size/2&&y<column.y+column.gap-size/2){
				return false;
			}return true;
		}return false;
	}
	
	
	
	
}












