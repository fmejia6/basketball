/*
 * Michaela Borzechowski
 * Flavio Mejia Morelli
 * Softwarepraktikum 2015 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Basketball extends JFrame{
	
	//Initialisierung der Variablen:
	int height=600;
	int width=800;
	int x=width/2;
	int y = height/2-height/4;
	int vy=1;
	int r=50;
	int vx=14;
	int vxOriginal=vx;
	JPanel panel;
	int drawHand=0;
	
	Color[] colors={Color.green, Color.BLUE, Color.black, Color.red,Color.magenta};
	Color currentColor = randomColor();
	
	/*Der Konstruktor erstellt den Frame und das Panel (mit der entsprechenden
	 * paint Methode und einem Keylistener zum dribbeln)
	 */
	public Basketball(){
		
		// Konfiguration
		setTitle("Basketball");
		setSize(width, height);
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent keyEvent) {
		        // Die Methode interpretiert gedrückte Tasten
		 
		        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER){ 
		        	System.out.println("ENTER GDRÜCKT");
		        	/*
		        	 * Wenn Enter gedrueckt wird, wird der Ball gedribbelt
					 * wenn die Geschwindigkeit dabei nicht zu gross wird
		        	 */
		        	if(Math.abs(vy)*2<height){
		        		//Die GEschwindigkeit wird dabei verdoppelt
		        		vy=Math.abs(vy)*2;
		        	}
		        	
		        	//und wir setzen drawHand, damit auf der GUI die Hand angezeigt wird
		        	drawHand=5;
		        }
			}
		});
		
		panel=new JPanel(){
			public void paint(Graphics g) {
				super.paint(g);
				
				g.setColor(currentColor);
				g.fillOval(x, y, r, r);
				
				//Wenn wir gerade dribbeln, wir hier zusaetzlich eine Hand gemalt
				if(drawHand!=0){
					g.setColor(Color.orange);
					g.fillRect(x-2,y-2,50,10);
					drawHand=drawHand-1;
					
				}
				
			}
			
		};
		
		this.setContentPane(panel);
		
		JLabel label=new JLabel("Druecke Enter zum dribbeln :)");
		panel.add(label);
		
		// um Fenster zu Schliessen:
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				event.getWindow().dispose();
				System.exit(0);
			}
		});

		setVisible(true);
		jump();
		
	}
	
	/*
	 * Diese Methode simuliert das Springen des Balles.
	 */
	public void jump(){
		
		while(true){
	
			y=y+vy;
			if(vy<0){
				vy=vy+2;
			}else{
				//Wenn wir nach oben springen, werden wir langsamer als beim Fallen:
				vy=vy+1;
			}
			
			
			if(y>=height-2*r || y <= r){
				vy=-vy;
			}
			
			
			//Aenderung/Einhaltung der Richtung:
			x=x+vx;
			//Wenn wir an den rechten oder linken Rand stossen:
			if(x+r>=width||x<=r){
				//Dann wechseln wir die Farbe des Balles
				currentColor=randomColor();
				//Und aendern die Richtung
				vx=-vx;
										
			}
	
			panel.repaint();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//Diese Methode gibt zufaellig eine Farbe aus der Liste colors zurueck.
	public Color randomColor(){
		int r = (int)( Math.random()*colors.length);
		return colors[r];
	}
	
	
	
	public static void main(String[] args) {
		new Basketball();
		
	}

}
