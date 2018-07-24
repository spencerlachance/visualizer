import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The controller for the visualizer that communicates between the user, view, and model
 * 
 * @author Spencer LaChance
 *
 */
public class VisualizerController implements ActionListener {

	private AudioSampleReader asr;
	private VisualizerModel vm;
	private boolean state; // true = playing, false = paused

	/**
	 * Constructor for the controller
	 * 
	 * @param filePath		the path to the file used in the visualizer
	 */
	public VisualizerController(File f) {
		try {
			asr = new AudioSampleReader(f);
			vm = new VisualizerModel(asr);
			state = false;
			asr.createClip();
			start();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Starts the animation
	 */
	public void start() {
		asr.startClip();
		state = true;
	}

	/**
	 * What happens when the user toggles the play/pause button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("PLAY/PAUSE")) {
			if (state) {
				asr.stopClip();
				state = false;
			}
			else {
				asr.startClip();
				state = true;
			}
		}
		else { // "FILE CHOSEN"
			asr.closeClip();
		}
	}

	/**
	 * @return	The model with all of the data for the visualizer
	 */
	public VisualizerModel getModel() {
		return vm;
	}
}
