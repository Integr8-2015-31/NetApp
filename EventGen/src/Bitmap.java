

import java.util.Arrays;

public class Bitmap {

	public int width;
	public int height;
	public byte parts[];
	
	public int GetWidth() {
		return width;
	}
	
	public int GetHeight() {
		return height;
	}
	

	
	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		parts = new byte[width * height * 4];
	}
	
	public void clear(byte shade) {
		Arrays.fill(parts, shade);
	}
	
	public void drawLine(double x1, double y1, double x2, double y2, Color c) {
		double slope = (y1 - y2)/(x1-x2);
		double b = -slope * x2 + y2;
		System.out.println(slope);
		for(double x=x1;x<x2;x=x+.01f) {
			DrawMedPixel((int)x,(int)(slope * x + b), (byte)0, c);

		}
	}
	
	public void DrawPixel(int x, int y, byte a, byte b, byte g, byte r) throws java.lang.ArrayIndexOutOfBoundsException {
		
		
		boolean inBounds = true;
		if(x <= 0 || x >= width || y <= 0 || y >= height) {
			inBounds = false;
		}

		 if(inBounds) {
			 int index = (y * width + x) * 4;
				parts[index] = a;
				parts[index + 1] = b;
				parts[index + 2] = g;
				parts[index + 3] = r;
		 }
		
	}
	
public void DrawPixel(int x, int y, byte a, Color c) throws java.lang.ArrayIndexOutOfBoundsException {
		y = height - y;
	//	x = x + width/2;
	//	y = y + height/2;
	
		boolean inBounds = true;
		if(x <= 0 || x >= width || y <= 0 || y >= height) {
			inBounds = false;
		}

		 if(inBounds) {
			 int index = (y * width + x) * 4;
				parts[index] = a;
				parts[index + 1] = (byte) (Color.getB(c)); //+ (1 - a) * parts[index + 1]); 
				parts[index + 2] = (byte) (Color.getG(c)); //+ (1 - a) * parts[index + 2]); 
				parts[index + 3] = (byte) (Color.getR(c)); //+ (1 - a) * parts[index + 3]); 
		 }
		
	}
		
	
	public void DrawMedPixel(int x, int Y, byte a, Color c) throws java.lang.ArrayIndexOutOfBoundsException{
		int y =  Y;
		DrawPixel(x,y,a,c);
		DrawPixel(x-1,y,a,c);
		DrawPixel(x+1,y,a,c);
		DrawPixel(x,y-1,a,c);
		DrawPixel(x,y+1,a,c);
		DrawPixel(x,y+2,a,c);
		DrawPixel(x-1,y+1,a,c);
		DrawPixel(x+1,y+1,a,c);
		DrawPixel(x-2,y,a,c);
		DrawPixel(x+2,y,a,c);
		DrawPixel(x-1,y-1,a,c);
		DrawPixel(x+1,y-1,a,c);
		DrawPixel(x,y-2,a,c);
	}
	
	public void DrawLargePixel(int x, int Y, byte a, Color c) throws java.lang.ArrayIndexOutOfBoundsException{
		int y =  Y;
		DrawMedPixel(x,y,a,c);
		DrawMedPixel(x-2,y,a,c);
		DrawMedPixel(x+2,y,a,c);
		DrawMedPixel(x,y-2,a,c);
		DrawMedPixel(x,y+2,a,c);
		DrawMedPixel(x,y+4,a,c);
		DrawMedPixel(x-2,y+2,a,c);
		DrawMedPixel(x+2,y+2,a,c);
		DrawMedPixel(x-4,y,a,c);
		DrawMedPixel(x+4,y,a,c);
		DrawMedPixel(x-2,y-2,a,c);
		DrawMedPixel(x+2,y-2,a,c);
		DrawPixel(x,y-4,a,c);
	}
	
	
	public void CopyToByteArray(byte[] b) {
		for(int i = 0; i < width * height; i++) {
			b[i * 3] = parts[i * 4 + 1];
			b[i * 3 + 1] = parts[i * 4 + 2];
			b[i * 3 + 2] = parts[i * 4 + 3];
		}
	}
}

