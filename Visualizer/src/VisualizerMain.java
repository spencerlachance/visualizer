import java.util.Scanner;

/**
 * The class that gets the input file path from the user and then runs the visualizer with it
 * 
 * @author Spencer LaChance
 *
 */
public class VisualizerMain {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter file path:");
		String path = scan.nextLine();
		scan.close();
		new VisualizerController(path);
	}
}