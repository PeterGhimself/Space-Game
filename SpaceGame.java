package game.space;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SpaceGame extends StateBasedGame{
	private static GameState gameState;
	
	public SpaceGame(){
		super("Wizard Game");
	}	
	
	public static void main(String[] args){
		try{
			AppGameContainer app = new AppGameContainer(new SpaceGame());
			app.setDisplayMode(900, 563, false);
			app.setAlwaysRender(true);
			app.start();
			app.setTargetFrameRate(60);
			//makes sure the FPS rate is synchronized with the monitor rate
			app.setVSync(true);
			app.setTitle("Wizard Game");
		}
		catch(SlickException e){
			e.printStackTrace();
		}
	}
	public void initStatesList(GameContainer container) throws SlickException {
		gameState = new GameState();
		this.addState(gameState);
		this.addState(new GameOverState());
	}
	public static void gameRestart() throws SlickException{
		gameState.resetGame();
	}
	public static int gameScore(){
		return gameState.getGameScore();
	}

}
