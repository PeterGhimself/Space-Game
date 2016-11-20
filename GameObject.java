package game.space;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class GameObject {
	private Image img;
	private Rectangle rekt;
	private Circle circ;
	
	public GameObject(Image img, int x, int y){
		this.img = img;
		int height = img.getHeight();
		int width = img.getWidth();
		rekt = new Rectangle(x, y, width, height);
	}
	
	public void moveY(float num, int delta){
		rekt.setCenterY(rekt.getCenterY()+num*delta);
	}
	public void moveX(float num, int delta){
		rekt.setCenterX(rekt.getCenterX()+num*delta);
	}
	public int getBotY(){
		return Math.round(rekt.getMaxY());
	}
	public void setBotY(float num){
		rekt.setY(num-rekt.getHeight());
	}
	public int getTopY(){
		return Math.round(rekt.getMinY());
	}
	public void setTopY(float num){
		rekt.setY(num);
	}
	public int getLeftX(){
		return Math.round(rekt.getMinX());
	}
	public void setLeftX(float num){
		rekt.setX(num);
	}
	public int getRightX(){
		return Math.round(rekt.getMaxX());
	}
	public void setRightX(float num){
		rekt.setX(num-rekt.getWidth());
	}
	public int getCenterY(){
		return (int) rekt.getCenterY();
	}
	public int getCenterX(){
		return (int) rekt.getCenterX();
	}
	public void setCenterX(float num){
		rekt.setX(num);
	}
	public void setCenterY(float num){
		rekt.setCenterY(num);
	}
	public Rectangle getRekt(){
		return rekt;
	}
	public Circle getCirc(){
		return circ;
	}
	public Image getImage(){
		return img;
	}
	public void Collide(Circle c){
		if(rekt.intersects(getCirc())){
			rekt.subtract(getCirc());
		}
	}
}
