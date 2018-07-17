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
	private VisualizerView vv;
	private boolean state; // true = playing, false = paused

	/**
	 * Constructor for the controller
	 * 
	 * @param filePath		the path to the file used in the visualizer
	 */
	public VisualizerController(String filePath) {
		try {
			asr = new AudioSampleReader(new File(filePath));
			vm = new VisualizerModel(asr);
			state = false;
			asr.createClip();
			vv = new VisualizerView(vm);
			vv.setButtonListener(this);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * What happens when the user toggles the play/pause button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (state) {
			asr.stopClip();
			state = false;
		}
		else {
			asr.startClip();
			state = true;
		}
		vv.pausePlayView(state);
	}
}
