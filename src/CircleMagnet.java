import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CircleMagnet {
	
	protected static int WINDOW_WIDTH = 948;
	protected static int WINDOW_HEIGHT = 522;
	protected static int MAX_ENTITIES = 300;
	protected static int STARTING_ENTITIES = 100;
	protected static boolean RightMouseDown = false;
	protected static boolean LeftMouseDown = false;
	protected static boolean ShiftDown = false;
	private static BufferedImage image = null;
	public static final String DIR = System.getProperty("user.dir");
	private static long oldTime = 0;
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Circle Magnet");
        window.setContentPane(new ContentPanel());
        window.setSize(1000, 560);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
		
	}
	
	public static class ContentPanel extends JPanel implements FocusListener, MouseListener, KeyListener,  ComponentListener {

		private static final long serialVersionUID = 1L;
		private ArrayList<Circle> circleList = new ArrayList<Circle>();
		private int[] mousePos = new int[2];
		
		public ContentPanel() {
			
			try {

				image = ImageIO.read(new File(DIR + "\\resources\\java.png"));
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
			setBackground(Color.BLACK);
			
			addFocusListener(this);
			addMouseListener(this);
			addKeyListener(this);
			addComponentListener(this);
			
			for (int i = 0; i < STARTING_ENTITIES; i++) {

				circleList.add(new Circle());

			}
			
		}
		
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			
			if (hasFocus())	{
				
				mousePos = getMouseInWindow();
				
				if (LeftMouseDown == true && ShiftDown == true && oldTime + 50 < System.currentTimeMillis() && circleList.size() < MAX_ENTITIES) {
					
					//System.out.println(mousePos[0] + " | " + mousePos[1]);
					circleList.add(new Circle(mousePos[0], mousePos[1]));
					//System.out.println(circleList.get(circleList.size() - 1).distance);//circleList.get(circleList.size()-5).posX + " | " + circleList.get(circleList.size()-5).posY);
					oldTime = System.currentTimeMillis();
					
				}
				
				for (int i = 0; i < circleList.size(); i++) {
						
					Circle circle = circleList.get(i);
					circle.update(mousePos);
					g.setColor(circle.color);
					g.fillOval((int)circle.posX - 5, (int)circle.posY - 5, 10, 10);
					
				}

				g.setColor(Color.WHITE);
				g.drawString("Amount of circles: " + circleList.size(),7,20);
				repaint();
				
			}
		
			else {
			
				g.setColor(Color.WHITE);
				g.drawImage(image, (WINDOW_WIDTH - image.getWidth())/2, (WINDOW_HEIGHT - image.getHeight())/2, this);
				g.drawString("Click to Activate.", WINDOW_WIDTH/2 - 40, WINDOW_HEIGHT - 50);
			
			}
			
		}
		
		public void focusGained(FocusEvent e) {
			
			repaint();
			
		}
		
		public void focusLost(FocusEvent e) {
			
			repaint();
			
		}
		
		public void mousePressed(MouseEvent e) {
			
			if (e.getButton() == MouseEvent.BUTTON3) {
				
				RightMouseDown = true;
				
			} else if (e.getButton() == MouseEvent.BUTTON1) {
				
				LeftMouseDown = true;
				
			}
			
			requestFocus();
			
		}
		
		public void mouseReleased(MouseEvent e) {
			
			if (e.getButton() == MouseEvent.BUTTON3) {
					
				RightMouseDown = false;
				
			} else if (e.getButton() == MouseEvent.BUTTON1) {
				
				LeftMouseDown = false;
				
			}
			
		}

		public void keyPressed(KeyEvent e) {
			
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				
				ShiftDown = true;
				
			}
			
		}

		public void keyReleased(KeyEvent e) {
			
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				
				ShiftDown = false;
				
			}
			
		}

		@Override
		public void componentResized(ComponentEvent e) {
			
			WINDOW_HEIGHT = e.getComponent().getHeight();
			WINDOW_WIDTH = e.getComponent().getWidth();
			
		}
		
		public int[] getMouseInWindow() {

			int[] output = new int[2];
			
			output[0] = (int)(MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().getX());
			output[1] = (int)(MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().getY());
			
			output[0] = clampInt(WINDOW_WIDTH, output[0], 0);
			output[1] = clampInt(WINDOW_HEIGHT, output[1], 0);
			
			return output;
			
		}
		
		@Override
		public void keyTyped(KeyEvent e) {}
		
		public void mouseEntered(MouseEvent e) {}
		
		public void mouseExited(MouseEvent e) {}
		
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void componentHidden(ComponentEvent e) {}

		@Override
		public void componentMoved(ComponentEvent e) {}

		@Override
		public void componentShown(ComponentEvent e) {}
			
	}
	
	public static int clampInt(int a, int b, int c) {
		
		return Math.max(c, Math.min(a, b));
		
	}
	
}
