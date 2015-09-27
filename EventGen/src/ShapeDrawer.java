

import java.util.Arrays;

public class ShapeDrawer extends Bitmap {

	private float[] Xmin,Xmax;
	Color[][] color;
	private byte background;
	
	public ShapeDrawer(int width, int height,byte background) {
		super(width, height);
		Xmin = new float[height];
		Xmax = new float[height];
		color = new Color[height][width];
		this.background = background;
			}
	
	public void addLine(int Y,float Xmin, float Xmax) {
		addLine(Y,Xmin,Xmax,Color.black);
	}
	
	
	
	public void addLine(int Y,float Xmin, float Xmax,Color c) {
		try {
		if(this.Xmin[Y] == this.Xmax[Y] && this.Xmin[Y] == 0) {
		
		this.Xmin[Y] = Xmin;
		this.Xmax[Y] = Xmax;
		for(int i = 0; i < width; i ++) {
		this.color[Y][i] = c;
		}
		} else {
			if(Xmin > this.Xmax[Y]) { //new line is on the right, not touching
				for(int i = (int) this.Xmax[Y];i < Xmin; i ++) {
				this.color[Y][i] = Color.white;
				}
				for(int i = (int) Xmin;i < Xmax; i ++) {
				this.color[Y][i] = c;
				}
				this.Xmax[Y] = Xmax;
			} else 	if(Xmax < this.Xmin[Y]) { //new line is on the left, not touching
				for(int i = (int) Xmin;i < Xmax; i ++) {
				this.color[Y][i] = c;
				}				
				for(int i = (int) Xmax;i < this.Xmax[Y]; i ++) {
				this.color[Y][i] = Color.white;
				}
				this.Xmin[Y] = Xmin;
		}
		}
		}catch (java.lang.ArrayIndexOutOfBoundsException e) {
			
		}
		
	
	}
		
	
	public void fillShape(int Ymax, int Ymin) {
		if(Ymin < 0) {
			Ymin = 0;
		}
		if(Ymax > height) {
			Ymax = height;
		}
		 for(int i = Ymin; i < Ymax; i++) {
			 if(Xmin[i] == Xmax[i]) {
				// System.out.println("Continued at y = " + i);
				 continue;
			 }
			 
			 for(float j = Xmin[i];j < Xmax[i];j++) {
				 if(j<0 || j > width || i < 0 || i > height) {
					 continue;
				 }
				 DrawPixel((int) j,i,(byte)0x00,color[(int) i][(int) j]);
			 }
			 
		 }
	}
	
	public void fillShapeWithBitmap(Bitmap b) {
//		float Yratio = 255f/height;
//		float Xratio = 255f/width;
		 for(int i = 0; i < height; i++) {
			 if(Xmin[i] == Xmax[i]) {
				// System.out.println("Continued at y = " + i);
				 continue;
			 }
			 
			 for(float j = Xmin[i];j < Xmax[i];j++) {
				 int index = (int) ((i * width + j) * 4);
				 DrawPixel((int) j,i,(byte)0x00,b.parts[index+1],b.parts[index+2],b.parts[index+3]);
			 }
			 
		 }
	}
	
	public void fillShapeWithPattern(byte b,float size) {
		float Yratio = height/size;
		float Xratio = width/size;
		 for(int i = 0; i < height; i++) {
			 if(Xmin[i] == Xmax[i]) {
				// System.out.println("Continued at y = " + i);
				 continue;
			 }
			 
			 for(float j = Xmin[i];j < Xmax[i];j++) {
				 //int index = (int) ((i * width + j) * 4);
				 DrawPixel((int) j,i,(byte)0x00,(byte)(i * Xratio),(byte)(j * Yratio), b);
			 }
			 
		 }
	}
	
	public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		drawTriangle(x1,y1,x2,y2,x3,y3,Color.black);
	}
	
	
	public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3,Color c) {
		
//		DrawMedPixel(x1,y1,(byte)(0x00),Color.red);
//		DrawMedPixel(x2,y2,(byte)(0x00),Color.purple);
//		DrawMedPixel(x3,y3,(byte)(0x00),Color.green);
		
		int topX,topY,botX,botY,midX,midY;
		boolean right = false;
		if(y1 < y2 && y1< y3) {
			botX = x1;
			botY = y1;
		} else if(y2 < y1 && y2 < y3) {
			botX = x2;
			botY = y2;
		} else {
			botX = x3;
			botY = y3;
		}
		
		if(y1 > y2 && y1 > y3) {
			topX = x1;
			topY = y1;
		} else if(y2 > y1 && y2 > y3) {
			topX = x2;
			topY = y2;
		} else {
			topX = x3;
			topY = y3;
		}
		
		if(y1 < topY && y1 > botY) {
			midX = x1;
			midY = y1;
		} else 	if(y2 < topY && y2 > botY) {
			midX = x2;
			midY = y2;
		} else {
			midX = x3;
			midY = y3;
		}
		
		int midpoint = (topX + botX)/2;
		
		if (midX <= midpoint) {
			right = false;
		} else {
			right = true;
		}
		
		DrawMedPixel(topX,topY, (byte) 1, Color.purple);
		DrawMedPixel(midX,midY, (byte) 1, Color.black);
		DrawMedPixel(botX,botY, (byte) 1, Color.orange);
		if(right) {
			
			double STM = (float)(topX - midX)/(float)(topY - midY);
			double STB = (float)(botX - topX) / (float)(botY - topY);
			float XI1 = topX;
			float XI2 = topX;
			for(int i = topY;i>midY;i--) {
				addLine(i,XI1,XI2,c);
				XI1 = (float) (-STB + XI1);
				XI2 = (float) (-STM + XI2);
				
			}
			float SMB = (float)(midX - botX)/(float)	(midY - botY);
			float XI3 = midX;
			for(int i = midY;i>botY;i--) {
				addLine(i,XI1,XI3,c);
				XI3 = (float) (-SMB + XI3);
				XI1 = (float) (-STB + XI1);
			}
		} else {
			
			double STM = (float)(topX - midX)/(float)(topY - midY);
			double STB = (float)(botX - topX) / (float)(botY - topY);
			float XI1 = topX;
			float XI2 = topX;
			for(int i = topY;i>midY;i--) {
				addLine(i,XI2,XI1,c);
				XI1 = (float) (-STB + XI1);
				XI2 = (float) (-STM + XI2);
			}
			float SMB = (float)(midX - botX)/(float)(midY - botY);
			float XI3 = midX;
			for(int i = midY;i>botY;i--) {
				addLine(i,XI3,XI1,c);
				XI3 = (float) (-SMB + XI3);
				XI1 = (float) (-STB + XI1);
			}

			
		}
		
	}
	
	public void draw() {
		for(float i = 0;i<height;i=i + .1f) {
			DrawPixel((int) Xmin[(int) i],(int)i,(byte)(0xFF),Color.black);
			DrawPixel((int) Xmax[(int) i],(int)i,(byte)(0xFF),Color.black);
		}
	}
	
	public void fillShape() {
		 fillShape(height,0);
	}
	
	public void clear() {
		Arrays.fill(Xmin, 0);
		Arrays.fill(Xmax, 0);
		super.clear(background);
	}
	
	

}
