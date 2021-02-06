package moe.pgnhd.networksnakes2.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import moe.pgnhd.networksnakes2.controller.GameBoard;

public class Application extends JFrame {
	private static final long serialVersionUID = 6298672140039182740L;
	
	public static void main(String[] args) {
		// swing needs to do the UI setup on it's own Thread
		EventQueue.invokeLater(() -> {
			// the application window (similar to JFrame)
			Application gameWindow = new Application();
			
			// Board with logic
			GameBoard board = new GameBoard();
			// JPanel which draws graphics
			DrawPanel drawPanel = new DrawPanel(board);
			// Attached to the drawPanel & has reference to it for callbacks
			KeyHandler keyHandler = new KeyHandler(board);
			
			drawPanel.addKeyListener(keyHandler);
			
			gameWindow.add(drawPanel);
			gameWindow.setVisible(true);
			gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gameWindow.setTitle("NetworkSnakes 2");
			gameWindow.setResizable(false);
			gameWindow.pack();
			gameWindow.setVisible(true);
			gameWindow.setAlwaysOnTop(true);
			
			drawPanel.requestFocusInWindow(); // has to be done after the window is visible

			
		});
	}
	
}
