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
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		double[] spectrum = vm.getFreqSpectrum();

		Random randy = new Random();
		int a;
		for (int i = 1; i <= spectrum.length; i++) {
			a = (int) (10 * spectrum[i - 1]);
			g.setColor(new Color(randy.nextInt(255), randy.nextInt(255), randy.nextInt(255)));
			g.fillRect(i, FRAME_HEIGHT - a, 1, a);
		}
	}
}