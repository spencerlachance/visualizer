import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private VisualizerModel vm;

	private static int FRAME_WIDTH = 1000;
	private static int FRAME_HEIGHT = 950;

	/**
	 * Constructor for the view
	 * 
	 * @param vm	The model that contains all of the data behind the visualizer
	 */
	public VisualizerView(VisualizerModel vm) {
		super();
		this.vm = vm;

		this.setTitle("Spencer's Visualizer");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());
		visualizerPanel = new Circles(vm, FRAME_HEIGHT, FRAME_WIDTH);
		visualizerPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.add(visualizerPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		this.add(buttonPanel, BorderLayout.SOUTH);
		playButton = new JButton("►");
		buttonPanel.add(playButton);

		String[] options = {"1. Spectral Plot", "2. Circles"};
		JComboBox<String> designChooser = new JComboBox<>(options);
		designChooser.addActionListener(new dropDownListener());
		buttonPanel.add(designChooser);

		timer = new Timer(10, this);
		timer.setInitialDelay(0);

		this.pack();
		this.setVisible(true);
	}

	/**
	 * What happens every timer tick
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.repaint();
	}

	/**
	 * Called by the controller to set itself as the action listener for the button
	 * 
	 * @param al	The action listener (controller)
	 */
	public void setButtonListener(ActionListener al) {
		playButton.addActionListener(al);
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
	public void changePanel(int choice) {
		pausePlayView(false);
		this.remove(visualizerPanel);
		switch (choice) {
		case 1:
			visualizerPanel = new SpectralPlot(vm, FRAME_WIDTH, FRAME_HEIGHT);
			break;
		case 2:
			visualizerPanel = new Circles(vm, FRAME_WIDTH, FRAME_HEIGHT);
			break;
		}
		visualizerPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.add(visualizerPanel);
		pausePlayView(true);
	}

	/**
	 * A listener for the drop-down menu
	 * 
	 * @author Spencer LaChance
	 *
	 */
	private class dropDownListener implements ActionListener {

		/**
		 * What happens when the user selects an option from the drop-down
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			char choice = ((String) ((JComboBox) e.getSource()).getSelectedItem()).charAt(0);
			changePanel(Character.getNumericValue(choice));
		}
	}
}
