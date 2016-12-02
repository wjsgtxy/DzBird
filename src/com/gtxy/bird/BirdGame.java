package com.gtxy.bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BirdGame extends JPanel {
	private Bird bird;
	private Column column1,column2;
	private Ground ground;
	public static BufferedImage background ;
	public static BufferedImage gameover;
	public static BufferedImage groundimage;
	public static BufferedImage start;
	public static BufferedImage column;
	public static BufferedImage bird0;
	
	private int score=0;
	private int state;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int GAMEOVER = 2;
	
	
	static{
		try {
			background = ImageIO.read(BirdGame.class.getResource("bg.png"));
			gameover = ImageIO.read(BirdGame.class.getResource("gameover.png"));
			groundimage = ImageIO.read(BirdGame.class.getResource("ground.png"));
			start = ImageIO.read(BirdGame.class.getResource("start.png"));
			column = ImageIO.read(BirdGame.class.getResource("column.png"));
			bird0 = ImageIO.read(BirdGame.class.getResource("0.png"));
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public BirdGame() throws IOException{
		state = START;
		bird = new Bird();
		column1 = new Column(1);
		column2 = new Column(1);
		ground = new Ground();
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(background, 0, 0, null);
		paintColum(g);
		paintGround(g);
		paintBird(g);
		paintScore(g);
		switch(state){
		case GAMEOVER:
			paintGameOver(g);
			break;
		case START:
			paintStart(g);
			break;
		}
	}
	
	private void paintStart(Graphics g){
		g.drawImage(start, 0, 0, null);
	}
	private void paintGameOver(Graphics g){
		if(state==GAMEOVER){//?
			g.drawImage(gameover, 0, 0, null);
		}
	}
	
	private void paintScore(Graphics g){
		Font font = new Font(Font.SANS_SERIF,Font.BOLD,40);
		g.setFont(font);		
		g.drawString("SCORE:"+score, 40, 60);
		g.setColor(Color.red);
		g.drawString("SCORE:"+score, 40-3, 60-3);
	}
	
	private void paintBird(Graphics g){
		Graphics2D gg = (Graphics2D)g;
		gg.rotate(-bird.alpha,bird.x,bird.y);
		g.drawImage(bird.getImage(), bird.getX()-bird.getWidth()/2, bird.getY()-bird.getHeight()/2, null);
		gg.rotate(bird.alpha,bird.x,bird.y);
		
	}
	
	private void paintGround(Graphics g ){
		g.drawImage(groundimage, ground.getX(), ground.getY(),null );
	}
	
	private void paintColum(Graphics g ){
		g.drawImage(column1.getImage(), column1.getX()-column1.getWidth()/2, column1.getY()-column1.getHeight()/2, null);
		g.drawImage(column2.getImage(), column2.getX()-column2.getWidth(), column2.getY()-column2.getHeight(), null);
	}
	
	
	public void start() throws InterruptedException{
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				try {
					switch(state){
					case GAMEOVER:
						column1 = new Column(1);
						column2 = new Column(2);
						bird = new Bird();
						score = 0;
						state = START;
						break;
					case START:
						state = RUNNING;
						break;
					case RUNNING:
						bird.flappy();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		while(true){
			switch(state){
			case START:
				bird.fly();
				break;
			case RUNNING:
				ground.step();
				column1.step();
				column2.step();
				bird.step();
				bird.fly();
				
				if(bird.x==column1.x||bird.x==column2.x){
					score++;
				}
				if(bird.hit(ground)||bird.hit(column1)||bird.hit(column2)){
					state = GAMEOVER;
				}
				break;
			}
			repaint();
			Thread.sleep(1000/30);
		}
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		JFrame frame = new JFrame("BirdGame");
		BirdGame game = new BirdGame();
		frame.add(game);
		frame.setSize(440, 670);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.start();
	}
}
