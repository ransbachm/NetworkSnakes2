package moe.pgnhd.networksnakes2.view;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import moe.pgnhd.networksnakes2.controller.GameBoard;

public class KeyHandler implements KeyListener {
	
	// https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
	
	private final GameBoard board;
	
	public KeyHandler(GameBoard board) {
		this.board = board;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// translates the char to a direction
		Point newDirection = KeyBind.valueOf((e.getKeyChar() + "").toUpperCase()).direction;
		board.lastDirection = newDirection;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}

}
