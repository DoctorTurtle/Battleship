package battleship;

import battleship.Grid;
import battleship.Token;

public class Player {
	protected Grid grid;
	protected int color;
	protected Token current;

	Player(int c) {
		color = c;
	}

	public void setGrid(Grid g) {
		grid = g;
	}
	
	public int getColor() {
		return color;
	}

	public void mouseMoved(int i) {
		
	}

	public void mouseClicked() {
		
	}

	public void makeMove() {
		
	}

	public void setToken(Token c) {
		current = c;
	}
}