package battleship;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Stack;

import Battleship.Player;
import Battleship.Token;


public class Grid 
{
	public static final int CELL_SIZE = 64;
	public static final int TOP_MARGIN = 64;
	public static final int WIN_LENGTH = 4;
	private Integer winColor = null;
	
	private Player[] players;
	private int width, height, turn;
	private Token current;
	private Token[][] grid;
	private Stack<Token> playHistory = new Stack<Token>();
	private Stack<Token> undoHistory = new Stack<Token>();
	private ArrayList<Integer> fullColumns = new ArrayList<Integer>();
	private ArrayList<Integer> aiColors = new ArrayList<Integer>();
	private int lastMouseCell;
	private Image image;
	public void drawLines(Graphics2D g) {
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.black);
		
		for(int x = 0; x <= width; x++) {
			g.drawLine(x * CELL_SIZE, CELL_SIZE, x * CELL_SIZE, height * CELL_SIZE + CELL_SIZE);
		}
		
		for(int y = 0; y <= height; y++) {
			g.drawLine(0, y * CELL_SIZE + CELL_SIZE, width * CELL_SIZE, y * CELL_SIZE + CELL_SIZE);
		} 
	}
}
