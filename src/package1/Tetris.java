package package1;

import javax.swing.JFrame;

public class Tetris {

	public static void main(String[] args) {
		JFrame frame=new JFrame();
		frame.setSize(510,685);
		frame.setTitle("TETRIS");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Panel());
		
		frame.setVisible(true);
		

	}

}
