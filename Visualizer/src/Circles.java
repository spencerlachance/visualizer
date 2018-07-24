import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Creates an visual representation of the song using circles of varying sizes and colors
 * 
 * @author Spencer LaChance
 *
 */
@SuppressWarnings("serial")
public class Circles extends JPanel {
	
	private VisualizerModel vm;
	
	private static int FRAME_HEIGHT;
	private static int FRAME_WIDTH;
	
	/**
	 * Constructor for this animation
	 * 
	 * @param vm		The model with all of the needed data
	 * @param width		Width of the application window
	 * @param height	Height of the application window
	 */
	public Circles(VisualizerModel vm, int height, int width) {
		this.vm = vm;
		FRAME_HEIGHT = height;
		FRAME_WIDTH = width;
	}

	/**
	 * Gets frequency spectrum data for a specific point in time from the model and then translates
	 * it into an image of circles that represent various frequencies
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		double[] spectrum = vm.getFreqSpectrum();
		Random randy = new Random();

		int a;
		for (int i = 0; i < spectrum.length; i += 4) {
			g.setColor(new Color(randy.nextInt(255), randy.nextInt(255), randy.nextInt(255)));
			a = (int) (6 * spectrum[i]);
			g.fillOval(FRAME_WIDTH / 2 - a / 2, FRAME_HEIGHT / 2 - a / 2, a, a);
		}
	}
}