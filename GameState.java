package game.space;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameState extends BasicGameState{
	
	private ArrayList<Circle> balls;
	private ArrayList<Projectile> lasers;
	private Circle mouseBall;
	private int timePassed, atkSpeed;
	private Random random;
	private int gameScore, scoreTemp;
	private int lives, level, deltaTest;
	private Image backGround = null;
	private Character spaceShip;
	private final int SPACESHIP_X_INITIAL = 425, SPACESHIP_Y_INITIAL = 500;
	private final float STARTING_SPEED = 0.3f;
	
	public static int laserCtr;
	public static double accuracy;
	public static float movementSpeed; 
	
	
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		resetGame();
		random = new Random();
		backGround = new Image("data/deepSpace.png");
		movementSpeed = STARTING_SPEED;
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		
		if(deltaTest == 60){
			deltaTest = 0;
			System.out.println(delta);
			try{Thread.sleep(50);} catch(Exception e) {}
			System.out.println(delta);
			System.out.println("------");
		}
		
		Input input = container.getInput();
		
		//controls for spaceShip
		if(input.isKeyDown(Input.KEY_W)){
			spaceShip.moveY(-movementSpeed, delta);
		}
		if(input.isKeyDown(Input.KEY_S)){
			spaceShip.moveY(movementSpeed, delta);
		}
		if(input.isKeyDown(Input.KEY_A)){
			spaceShip.moveX(-movementSpeed, delta);
		}
		if(input.isKeyDown(Input.KEY_D)){
			spaceShip.moveX(movementSpeed, delta);
		}
		
		atkSpeed += delta;
		
		if(input.isKeyDown(Input.KEY_SPACE)){
			if(atkSpeed > (200-(level-1)*6)){
				lasers.add(new Projectile(new Image("data/CyanSquare.gif"), spaceShip.getCenterX()-5, spaceShip.getCenterY()-5));
				laserCtr++;
				atkSpeed = 0;
			}
		}
//		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
//			for(int i = 0; (mouseBall.getCenterY() < -5 || mouseBall.getCenterY() > 568) || mouseBall.getCenterX() < -5 || mouseBall.getCenterX() > 905; i++){
//				laser.setCenterY(mouseBall.getCenterY()+(mouseBall.getCenterY()-spaceShip.getCenterY()));
//				laser.setCenterX(mouseBall.getCenterX()+(mouseBall.getCenterX()-spaceShip.getCenterY()));
//			}	
//		}
		//bounds the spaceShip
		if((spaceShip.getBotY() >= 563)){
			spaceShip.setBotY(563);
		}
		if(spaceShip.getTopY() <= 0){
			spaceShip.setTopY(0);
		}
		if(spaceShip.getLeftX() <= 0){
			spaceShip.setLeftX(0);
		}
		if(spaceShip.getRightX() >= 900){
			spaceShip.setRightX(900);
		}
		mouseBall.setCenterX(input.getMouseX());
		mouseBall.setCenterY(input.getMouseY());
		
		timePassed += delta;
		
		accuracy = ((double)gameScore)/((laserCtr));
		
		movementSpeed = STARTING_SPEED+((level-1)/10.0f);
		
		deltaTest++;
	
		
		if(scoreTemp == 10 ){
			scoreTemp = 0;
			level++;
		}
		
		if(timePassed > 400*(4-level*0.13)){
			timePassed = 0;
			balls.add(new Circle(200 + random.nextInt(500), 0 + -1*random.nextInt(100) , 10));
		}
			
		for(Circle c : balls){
			c.setCenterY(c.getCenterY()+delta/5f);
		}
		
		for(int i = balls.size()-1; i >= 0; i--){
			Circle c = balls.get(i);
			if(c.getCenterY() > 610){
				balls.remove(i);
				lives--;
			}else if(c.intersects(spaceShip.getRekt())){
				balls.remove(i);
			}
		}
		for(Projectile laser : lasers){
			laser.setCenterY(laser.getCenterY()-delta*2/5f);
		}
		for(int i = lasers.size()-1; i >= 0; i--){
			Projectile laser = lasers.get(i);
			if(laser.getCenterY() < -5){
				lasers.remove(i);
//				System.out.println("laser size: ");
			}
			for(int j = balls.size()-1; j >= 0; j--)
			if(laser.getRekt().intersects(balls.get(j))){
				balls.remove(j);
				lasers.remove(i);
				gameScore++;
				scoreTemp++;
			}
		}
		if(lives <= 0 || level == 31){
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
	}
	
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		backGround.draw();
		spaceShip.getImage().draw(spaceShip.getLeftX(), spaceShip.getTopY());
		for(Projectile laser : lasers){
			laser.getImage().draw(laser.getLeftX(), laser.getTopY());
		}
		g.setColor(Color.green);
		g.fill(mouseBall);
		
		
		g.setColor(Color.red);
		for(Circle c : balls){
			g.fill(c);
		}
		g.setColor(Color.white);
		g.drawString("Level: "+level, 20, 50);
		g.drawString("Score: "+gameScore, 20, 70);
		g.drawString("Lasers fired: "+GameState.getLaserCtr(), 20, 90);
		g.drawString("Lives: "+lives, 20, 500);
		g.drawString("Movement Speed: "+GameState.getMovementSpeed(), 20, 520);
	}
	public int getGameScore(){
		return gameScore;
	}

	@Override
	public int getID() {
		return 0;
	}
	public void resetGame() throws SlickException{
		lives = 10;
		laserCtr = 0;
		gameScore = 0;
		level = 1;
		balls = new ArrayList<Circle>();
		lasers = new ArrayList<Projectile>();
		mouseBall = new Circle(0, 0, 10);
		timePassed = 0;
		spaceShip = new Character(new Image("data/speedShip.png"), SPACESHIP_X_INITIAL, SPACESHIP_Y_INITIAL);
	}
	public static int getLaserCtr(){
		return laserCtr;
	}
	public static double getAccuracy(){
		return accuracy;
	}
	public static float getMovementSpeed(){
		return movementSpeed;
	}
}
