package game.space;

import java.text.DecimalFormat;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState{
	private DecimalFormat percent;
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		percent = new DecimalFormat("##.##%");
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int arg2) throws SlickException {
		if(container.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			SpaceGame.gameRestart();
			sbg.enterState(0);
		}
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.drawString("Game Over!", 340, 280);
		g.drawString("Score: "+SpaceGame.gameScore(), 320, 300);
		g.drawString("Lasers fired: "+GameState.getLaserCtr(), 320, 320);
		g.drawString("Accuracy: "+percent.format(GameState.getAccuracy()), 320, 340);;
		
	}
	@Override
	public int getID() {
		return 1;
	}

}