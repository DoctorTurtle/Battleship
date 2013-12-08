package battleship;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Grid 
{
	public static final int CELL_SIZE = 64;
	public static final int TOP_MARGIN = 64;
	public static final int WIN_LENGTH = 4;
	private Integer winColor = null;
	
	private Player[] players;
	private int width, height, turn;
//	private Token current;
//	private Token[][] grid;
//	private Stack<Token> playHistory = new Stack<Token>();
//	private Stack<Token> undoHistory = new Stack<Token>();
	private ArrayList<Integer> fullColumns = new ArrayList<Integer>();
	private ArrayList<Integer> aiColors = new ArrayList<Integer>();
	private int lastMouseCell;
	private Image image;
	
	public Grid(Player[] p) {
		this(p, 7, 6);
	}
	public Grid(Player[] p, int w, int h) {		
		players = p;
		for (Player player : players) {
			player.setGrid(this);
//			if (player instanceof AIPlayer) {
//				aiColors.add(player.getColor());
//			}
		}
		width = w;
		height = h;
		lastMouseCell = w / 2;
//		grid = new Token[width][height];
		turn = 0;
		skipTurns(0);
		makeMove();
		// Add a listener event to receive mouse actions
//		addMouseListener(new MouseAdapter() { 
//			public void mouseClicked(MouseEvent e) {
//				if (!current.dropped()) {
//					players[turn].mouseClicked();					
//				}
//			}
//		});
//		addMouseMotionListener(new MouseMotionAdapter() {
//			public void mouseMoved(MouseEvent e) {
//				lastMouseCell = e.getX() / CELL_SIZE;
//				if (!current.dropped()) {
//					players[turn].mouseMoved(lastMouseCell);
//				}
//			}
//		});
		try {                
			image = ImageIO.read(new File("Board.png"));
		} catch (IOException e) {}
		
		// Set the preferred size of the component
//		setPreferredSize(new Dimension(w * CELL_SIZE, h * CELL_SIZE + TOP_MARGIN));
		
//		new Thread(this).start();
	}
	public void skipTurns(int delta) {
		turn = ((turn + delta) % players.length + players.length) % players.length;
//		current = new Token(this, players[turn].getColor());
//		players[turn].setToken(current);
	}


	public void makeMove() {
		players[turn].makeMove();
		players[turn].mouseMoved(lastMouseCell);
	}

	/**
	 * @return a list of all the players
	 */
	public List<Player> getPlayers() {
		return Arrays.asList(players);
	}
	
	/**
	 * @return a list of full column x values
	 */
	public ArrayList<Integer> getFullColumns() {
		return fullColumns;
	}

	/**
	 * @return a list of empty column x values
	 */
	public ArrayList<Integer> getEmptyColumns() {
		ArrayList<Integer> empty = new ArrayList<Integer>(getGridWidth());
		for (int i = 0; i < getGridWidth(); i++) {
			if (!fullColumns.contains(i)) {
				empty.add(i);
			}
		}
		return empty;
	}
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
	public int getGridWidth() {
		return width;
	}
	
	/**
	 * @return the height of the Grid
	 */
	public int getGridHeight() {
		return height;
	}
	
	/**
	 * Loop through the Grid object to search for chains of four tokens.
	 * 
	 * @return the color which has the connect four or null if no connect four exists
	 */
	
}
