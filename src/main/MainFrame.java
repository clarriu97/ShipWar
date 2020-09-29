package main;


import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public MainFrame() {
		super("Guerra de Naves");									//Se puede cambiar el nombre del panel
		setDefaultCloseOperation(EXIT_ON_CLOSE);		//Hace que en vez de solamente tirar el objeto a la basura, 
		//la ventana se va a la basura y EL PROGRAMA NO SIGUE, naturalmente deber�a seguir si un objeto es null
		setResizable(false);
		setContentPane(new MainPanel());
		setSize(new Dimension(G.WIDTH + G.EXTRA_WIDTH, G.HEIGHT + G.EXTRA_HEIGHT));		//Son extras para que EL JUEGO tenga eso de ancho y alto, 
		//pero si se ejecuta en otro SO pueden cambiar
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}
