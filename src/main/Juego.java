package main;


import javax.swing.SwingUtilities;

public class Juego {
	public static void main(String[] args) {
		// Pasamos la tarea de crear al JFrame al EDT
		SwingUtilities.invokeLater(
				new Runnable() {
					@Override
					public void run() {
						new MainFrame();						
					}
				}
			);
	}
}
