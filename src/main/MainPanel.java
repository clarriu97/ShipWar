package main;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	private BufferedImage buffer;
	private Model model;
	private Timer timer;
	
	
	public MainPanel() {
		buffer = new BufferedImage(G.WIDTH, G.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		model = new Model(buffer.createGraphics());			//Le pido al buffer que haga de builder e instancie un graphics
		//Lo crea el buffer, el dueño es el modelo y luego ya lo usa todo quisqui, naves, estrellas, enemigos, etc.
		addKeyListener(new KeyController());				//para el teclado
		addMouseMotionListener(new MouseController());		//para el ratón
		setFocusable(true);									//Cuando pinche sobre la ventana las teclas llegarán y se lo pasaré al listener
		timer = new Timer();
		timer.scheduleAtFixedRate(new PaintTask(), 0, Math.round(1000.0/G.FPS));
		// Ocultar el raton
		try {
			Image image = ImageIO.read(new File("sprites/cursor.png"));
			Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0,0), "img");		//Singleton, obtengo la referencia
			//de tooktit con get, es único
			setCursor(c);
		} catch (IOException e) { }

	}
	
	
	@Override
	public void paint(Graphics g) {
		// Recibimos mensaje de PAINT cada x milisegundos
		model.updateAndDraw();					// Fase 1: Actualizar modelo y draw buffer
		g.drawImage(buffer, 0, 0, null);		// Fase 2: transferir buffer al JPanel
		Toolkit.getDefaultToolkit().sync();		// Fase 3: sync display, nos aseguramos que el panel se vea en pantalla
	}

	// -----------------------------------------------------------------------
	
	private class PaintTask extends TimerTask {
		@Override
		// Método invocado por el hilo del timer
		public void run() {
			repaint();		// Solicitud de paint a la cola de mensajes
		}
	}
	
	// -----------------------------------------------------------------------
	
	private class KeyController extends KeyAdapter { 
		private List<Integer> keys;
		private int code;
		private boolean pressed;
		
		public KeyController(){
			keys = new ArrayList<Integer>();
			code = 0;
			pressed = false;
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if (keys.contains(e.getKeyCode())) { return; }	// Evitar rafagas
			
			pressed = true;
			code = e.getKeyCode();
			keys.add(code);
			procesar();
		}
		
		@Override
		public void keyReleased(KeyEvent e){
			pressed = false;
			code = e.getKeyCode();
			keys.remove(new Integer(code));			//Quitame el int en el que ponga 5, no el int que este en la posicion 5
			procesar();
		}
		
	
		private void procesar() {
			if (pressed && code == KeyEvent.VK_SPACE) {
				model.shipShoot();
			}
			if (pressed && code >= KeyEvent.VK_1 && code <= KeyEvent.VK_8) {
				model.play(code - KeyEvent.VK_1 + 1);
			}
		}
	}
	



	private class MouseController extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			model.setShipTarget(new Vector(e.getX(), e.getY()));
		}
	}



}


