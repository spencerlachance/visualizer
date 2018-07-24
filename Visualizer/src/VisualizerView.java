import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

/**
 * Displays the visualizer animation
 * 
 * @author Spencer LaChance
 *
 */
public class VisualizerView extends JFrame implements ActionListener {

	private Timer timer;
	private JButton playButton;
	private JPanel visualizerPanel;
	private JFileChooser chooser;
	private JButton fileButton;
	private int currentDesign; //corresponds to the numbers in the drop-down
	private VisualizerModel vm;
	private boolean state;
	private File f;

	private static int FRAME_WIDTH = 1000;
	private static int FRAME_HEIGHT = 950;

	/**
	 * Constructor for the view
	 * 
	 * @param vm	The model that contains all of the data behind the visualizer
	 */
	public VisualizerView() {
		super();
		currentDesign = 1;
		state = false;

		this.setTitle("Spencer's Visualizer");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());	
		visualizerPanel = new TitleCard();
		visualizerPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.add(visualizerPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		this.add(buttonPanel, BorderLayout.SOUTH);

		chooser = new JFileChooser();
		fileButton = new JButton("Choose File");
		fileButton.setActionCommand("FILE CHOSEN");
		fileButton.addActionListener(this);
		buttonPanel.add(fileButton);

		playButton = new JButton("►");
		playButton.setActionCommand("PLAY/PAUSE");
		playButton.addActionListener(this);
		buttonPanel.add(playButton);

		String[] options = {"1. Circles", "2. Spectral Plot", "3. Central Spectrum"};
		JComboBox<String> designChooser = new JComboBox<>(options);
		designChooser.setActionCommand("DESIGN CHANGE");
		designChooser.addActionListener(this);
		buttonPanel.add(designChooser);

		this.pack();
		this.setVisible(true);
	}

	/**
	 * Starts the animation
	 * 
	 * @param vm	The model with the data needed to visually represent the audio
	 */
	public void start(VisualizerModel vm) {
		timer = new Timer(10, this);
		timer.setInitialDelay(0);
		timer.setActionCommand("TICK");
		this.vm = vm;
		selectDesign(currentDesign);
		timer.restart();
		playButton.setText("❚❚");
		state = true;
	}

	/**
	 * What happens every timer tick, the Choose File button is pressed, or a design is selected
	 * from the drop-down
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "TICK":
			this.repaint();
			break;
		case "FILE CHOSEN":
			chooser.showOpenDialog(this);
			f = chooser.getSelectedFile();
			VisualizerController vc = new VisualizerController(f);
			playButton.addActionListener(vc);
			fileButton.addActionListener(vc);
			start(vc.getModel());
			break;
		case "DESIGN CHANGE":
			char choice = ((String) ((JComboBox) e.getSource()).getSelectedItem()).charAt(0);
			currentDesign = Character.getNumericValue(choice);
			selectDesign(currentDesign);
			break;
		case "PLAY/PAUSE":
			if (!(f == null)) {
				if (state) {
					timer.stop();
					playButton.setText("►");
					state = false;
				}
				else {
					timer.start();
					playButton.setText("❚❚");
					state = true;
				}
			}
		}
	}

	/**
	 * How the view reacts to the play button being toggled
	 * 
	 * @param state		The current state of the animation after the button is pressed 
	 * 					(true = playing, false = paused)
	 */
	public void pausePlayView(boolean state) {
		if (state) {
			timer.start();
			playButton.setText("❚❚");
		}
		else {
			timer.stop();
			playButton.setText("►");
		}
	}

	/**
	 * Changes the type of animation displayed based on what the user selects from the drop-down
	 * 
	 * @param choice	A number corresponding to an animation type
	 */
	public void selectDesign(int choice) {
		pausePlayView(false);
		this.remove(visualizerPanel);
		switch (choice) {
		case 1:
			visualizerPanel = new Circles(vm, FRAME_WIDTH, FRAME_HEIGHT);
			break;
		case 2:
			visualizerPanel = new SpectralPlot(vm, FRAME_WIDTH, FRAME_HEIGHT);
			break;
		case 3:
			visualizerPanel = new CentralSpectrum(vm, FRAME_WIDTH, FRAME_HEIGHT);
		}
		visualizerPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.add(visualizerPanel);
		pausePlayView(true);
	}
	
	private class TitleCard extends JPanel {
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawString("Click the button below and choose a WAV, AU, or AIFF/AIFF-C file.",
					FRAME_WIDTH / 2 - 200, FRAME_HEIGHT / 2);
		}
	}
}