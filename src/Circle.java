import java.awt.Color;
import java.util.*;

public class Circle {

	private static final double ATTRACT_DISTANCE = CircleMagnet.WINDOW_WIDTH*0.85;
	private static final double REPEL_DISTANCE = CircleMagnet.WINDOW_WIDTH*0.5;
	private static final double FRICTION = 0.999;
	
	private Random generator = new Random();
	public final Color color = new Color(generator.nextFloat()*0.8f + 0.2f, generator.nextFloat()*0.8f + 0.2f, generator.nextFloat()*0.8f + 0.2f, generator.nextFloat()*0.4f + 0.6f);
	
	private static double mouseX = 0;
	private static double mouseY = 0;
	
	protected double posX;
	protected double posY;
	private double deltaX;
	private double deltaY;
	private double maxDeltaX = 0;
	private double maxDeltaY = 0;
	protected double distance = 0;

	public Circle() {
		
		this.posX = CircleMagnet.WINDOW_WIDTH/2;
		this.posY = CircleMagnet.WINDOW_HEIGHT/2;
		System.out.println(CircleMagnet.WINDOW_WIDTH/2 + " | " + CircleMagnet.WINDOW_HEIGHT/2);
		double factorDelta = generator.nextDouble();
		this.deltaX = Math.sin(factorDelta*Math.PI*2)*0.75;
		this.deltaY = Math.cos(factorDelta*Math.PI*2)*0.75;
		
	}
	
	public Circle(int x, int y) {
		
		double factorDelta = generator.nextDouble();
		this.posX = x + Math.sin(factorDelta*Math.PI*2)*0.01;
		this.posY = y + Math.cos(factorDelta*Math.PI*2)*0.01;
		this.deltaX = Math.sin(factorDelta*Math.PI*2)*0.75;
		this.deltaY = Math.cos(factorDelta*Math.PI*2)*0.75;
		
	}

	public void update(int[] mousePos) {
		
		mouseX = mousePos[0];
		mouseY = mousePos[1];
		
		distance = distBetweenVectors(posX, posY, mouseX, mouseY);
		
		maxDeltaX = posX - mouseX;
		maxDeltaY = posY - mouseY;
		maxDeltaX /= distance*500;
		maxDeltaY /= distance*500;
		
		if (CircleMagnet.LeftMouseDown == true) {
			
			if (distance < ATTRACT_DISTANCE)
		
			{
			
				double modifier = (1 - (distance/ATTRACT_DISTANCE))*CircleMagnet.WINDOW_WIDTH*0.0014;
				deltaX += maxDeltaX*modifier;
				deltaY += maxDeltaY*modifier;
			
			}
		
		}
		
		if (CircleMagnet.RightMouseDown == true) {
				
			if (distance < REPEL_DISTANCE) {
					
				double modifier = (1 - (distance/REPEL_DISTANCE))*CircleMagnet.WINDOW_WIDTH*0.002;
				deltaX -= maxDeltaX*modifier;
				deltaY -= maxDeltaY*modifier;
					
			}
				
		}
		
		/*if ((deltaX + deltaY)/2 < 0.001) {
			
			deltaX += Math.random()*0.0625 - 0.03125;
			deltaY += Math.random()*0.0625 - 0.03125;
			
		}*/
		
		deltaX *= FRICTION;
		deltaY *= FRICTION;
		
		posX -= deltaX;
		posY -= deltaY;
		
		if (posX > CircleMagnet.WINDOW_WIDTH - 5) {
			
			posX = CircleMagnet.WINDOW_WIDTH - 5;
			deltaX *= -1;
			
		}
		
		else if (posX < 5) {
			
			posX = 5;
			deltaX *= -1;
		
		}
		
		if (posY > CircleMagnet.WINDOW_HEIGHT - 5) {
		
			posY = CircleMagnet.WINDOW_HEIGHT - 5;
			deltaY *= -1;
		
		}
		
		else if (posY < 5) {
		
			posY = 5;
			deltaY *= -1;
		
		}
		
	}

	private static double distBetweenVectors(double a, double b, double c, double d) {

		return Math.sqrt(Math.pow(c - a, 2.0) + Math.pow(d - b, 2.0));
	
	}

	public void setPos(int[] pos) {
		
		System.out.println((double) pos[0] + " | " + (double) pos[1]);
		this.posX = (double) pos[0];
		this.posY = (double) pos[1];
	
	}
	
}