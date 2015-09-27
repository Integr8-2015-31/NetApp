
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;




public class Main {

	static int time = 0;
	private final static int width = 600;
	private final static int height = 1000;
	private final static int sleepTime = 1000;
	
	public static void main(String[] args) {
		BufferedImage img3 = null;
		BufferedImage img2 = null;
		BufferedImage img1 = null;
		
		try {
			File f= new File("res\\V3.png");
			System.out.println(f.getAbsolutePath());
			img3 = ImageIO.read(new File("res\\V3.png"));
			img2 = ImageIO.read(new File("res\\V2.png"));
			img1 = ImageIO.read(new File("res\\V1.png"));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		CanvasDisplay display = new CanvasDisplay(width,height,"", (byte) 0);
		Bitmap map = display.GetBitmap();
		
//		CanvasDisplay graph = new CanvasDisplay(1000,800,"Graph", (byte) 0);
//		Bitmap grapher = graph.GetBitmap();
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		
		byte[] data = ((DataBufferByte)(image.getRaster().getDataBuffer())).getData();
		
		g.setFont(new Font("Jokerman", Font.PLAIN, 35));
		g.setColor(Color.BLUE);
		
		ArrayList<Event> events = new ArrayList<Event>();
		
		int Last_I_amount = 0;
		int Last_C_amount = 0;
		int Last_L_amount = 0;
		//grapher.clear((byte)(0xff));
		
		while(true) {
		map.clear((byte)(0x00));
		Arrays.fill(data, (byte)(0x00));
		
		
		double eventChance = Math.random();						//create event
		if(eventChance < .02d) {  
			events.add(new Event("LongTerm", (int) (Math.random() * 100) + 200));
			
		} else if(eventChance < .15d) {
			events.add(new Event("Critical", (int) (Math.random() * 20) + 10));
			
		} else if(eventChance < .35d) {
			events.add(new Event("Immediate", (int) (Math.random() * 7) + 3));
		}
		
		for(Event e:events) {					// update event times
			e.time = e.time - 1;
		}
		
		for(int i=0; i<events.size();i++) {   // remove events that are over
			Event e = events.get(i);
			//System.out.println(e.type);
			if(e.time < 1) {
				events.remove(i);
			}
		}
		g.setColor(Color.green);
		g.drawString("Time: " + time, 100, 75);
		int counter = 100;
		
		int I_amount = 0;
		int C_amount = 0;
		int L_amount = 0;
		
		for(Event e:events) {

			counter = counter + 80;
			  													//print out all events
			//g.drawRect(0, 0, width, height);


			//System.out.println(e.type + "  " + e.time);
			switch(e.type) {
			case "Immediate":  g.setColor(Color.RED); I_amount++; break;
			case "Critical":  g.setColor(Color.YELLOW); C_amount++; break;
			case "LongTerm":  g.setColor(Color.BLUE);L_amount++;  break;
			}
			//g.drawString(e.type + "   " + e.time, 100, counter);
			
			switch(e.type) {
			case "Immediate":  g.drawImage(img3,100,counter,71,71,null); break;
			case "Critical":  g.drawImage(img2,100,counter,75,75,null); break;
			case "LongTerm":  g.drawImage(img1,100,counter,75,75,null); break;
			}
			
//			System.out.println(L_amount + " " + C_amount + " " + I_amount + " ");
			
			
//			grapher.DrawMedPixel(time, L_amount * 80, (byte) 0, com.kevin.graphics.canvas.Color.purple);
//			grapher.DrawMedPixel(time, C_amount * 80, (byte) 0, com.kevin.graphics.canvas.Color.yellow);
//			grapher.DrawMedPixel(time, I_amount * 80, (byte) 0, com.kevin.graphics.canvas.Color.red);
			
			
			g.drawString("" + e.time, 190, counter + 37);
			
		}
//		grapher.drawLine(time - 1, (double)(Last_I_amount * 160), time,(double)( I_amount * 160), com.kevin.graphics.canvas.Color.red);
//		grapher.drawLine(time - 1,(double) (Last_C_amount * 160), time,(double)( C_amount * 160), com.kevin.graphics.canvas.Color.yellow);
//		grapher.drawLine(time - 1,(double)( Last_L_amount * 160), time,(double)( L_amount * 160), com.kevin.graphics.canvas.Color.purple);
		
		
		
		Last_I_amount = I_amount;
		Last_C_amount = C_amount;
		Last_L_amount = L_amount;
		
		for(int x = 0; x < width; x++) {
			for(int y =0;y<height;y++) {
				int index = (y * width + x) * 3;
				map.DrawPixel(x, y, (byte) 0, data[index], data[index + 1], data[index + 2]);
			}
		}
		
	
		display.SwapBuffers();
		//graph.SwapBuffers();
	
		System.out.println();
		time=time+1;
		
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
	}
}
