import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Creates an animation of the song's frequency spectral plot
 * 
 * @author Spencer LaChance
 *
 */
@SuppressWarnings("serial")
public class SpectralPlot extends JPanel {
	
	private VisualizerModel vm;
	
	private static int FRAME_HEIGHT;
	
	/**
	 * Constructor for this animation
	 * 
	 * @param vm		The model with all of the needed data
	 * @param width		Width of the application window
	 * @param height	Height of the application window
	 */
	public SpectralPlot(VisualizerModel vm, int width, int height) {
		this.vm = vm;
		FRAME_HEIGHT = height;
	}

	/**
	 * Gets frequency spectrum data for a specific point in time from the model and then translates
	 * it into an image of the spectral plot
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int[] spectrum = vm.getFreqSpectrum();

		int x = 0;
		Random randy = new Random();
		for (int a : spectrum) {
			a *= 10;
			g.setColor(new Color(randy.nextInt(255), randy.nextInt(255), randy.nextInt(255)));
			g.fillRect(x, FRAME_HEIGHT - a, 1, a);
			x++;
		}
	}
}