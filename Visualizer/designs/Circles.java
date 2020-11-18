package designs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import src.VisualizerModel;

/**
 * Creates an visual representation of the song using circles of varying sizes
 * and colors
 * 
 * @author Spencer LaChance
 *
 */
@SuppressWarnings("serial")
public class Circles extends VisualizerDesign {

	public Circles(VisualizerModel vm, int width, int height) {
		super(vm, width, height);
	}

	/**
	 * Gets frequency spectrum data for a specific point in time from the model and
	 * then translates it into an image of circles that represent various
	 * frequencies
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		double[] spectrum = vm.getFreqSpectrum();
		Random randy = new Random();

		int a;
		for (int i = 0; i < spectrum.length; i += 4) {
			g.setColor(new Color(randy.nextInt(255), randy.nextInt(255), randy.nextInt(255)));
			a = (int) (3 * spectrum[i]);
			g.fillOval(FRAME_WIDTH / 2 - a / 2, FRAME_HEIGHT / 2 - a / 2, a, a);
		}
	}
}