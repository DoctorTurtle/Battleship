package battleship;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private BufferedImage image;
	public ImagePanel(String url) {
		try {                
			image = ImageIO.read(new File(url));
		} catch (IOException e) {}
		setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);    
	}
}