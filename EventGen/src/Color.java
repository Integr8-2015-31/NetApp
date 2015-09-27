

public enum Color {
	red,orange,yellow,green,blue,purple,white,black,grey;
	
	public static byte getB(Color c) {
		int b;
		switch(c) {
		case red: b = 0;
		break;
		case orange: b = 0;
		break;
		case yellow: b = 0;
		break;
		case green: b = 0;
		break;
		case blue: b = 255;
		break;
		case purple: b = 238;
		break;
		case white: b =  255;
		break;
		case black: b = 0;
		break;
		case grey: b = 130;
		break;
		default: b = 0;
		break;
		}
		return (byte)(b);
	}
	
	public static byte getR(Color c) {
		int r;
		switch(c) {
		case red: r = 255;
		break;
		case orange: r = 255;
		break;
		case yellow: r = 242;
		break;
		case green: r = 0;
		break;
		case blue: r = 0;
		break;
		case purple: r = 224;
		break;
		case white: r =  255;
		break;
		case black: r = 0;
		break;
		case grey: r = 130;
		break;
		default: r = 0;
		break;
		}
		return (byte)(r);
	}
	
	public static byte getG(Color c) {
		int g;
		switch(c) {
		case red: g = 0;
		break;
		case orange: g = 110;
		break;
		case yellow: g = 225;
		break;
		case green: g = 255;
		break;
		case blue: g = 0;
		break;
		case purple: g = 0;
		break;
		case white: g =  255;
		break;
		case black: g = 0;
		break;
		case grey: g = 130;
		break;
		default: g = 0;
		break;
		}
		return (byte)(g);
	}
}
