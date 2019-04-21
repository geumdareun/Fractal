import java.awt.Color;

public class Palette
{
	private static final int COLOR_COUNT = 8;
	public static final int LOOP_SIZE = 2040;
	
	private static int colorIndex(int n)
	{
		return (n/255)%COLOR_COUNT;
	}
	
	private static int intensity(int n)
	{
		return n%255;
	}
	
	public static Color rainbow(int n)
	{
		int INDEX = colorIndex(n);
		int T = intensity(n);
		int r = 0, g = 0, b = 0;
		switch(INDEX)
		{
		case 0:
			r = T;     g = 0;     b = 0;     break;
		case 1:
			r = 255;   g = T;     b = 0;     break;
		case 2:
			r = 255-T; g = 255;   b = 0;     break;
		case 3:
			r = 0;     g = 255;   b = T;     break;
		case 4:
			r = 0;     g = 255-T; b = 255;   break;
		case 5:
			r = T;     g = 0;     b = 255;   break;
		case 6:
			r = 255;   g = 0;     b = 255-T; break;
		case 7:
			r = 255-T; g = 0;     b = 0;     break;
		}
		return new Color(r, g, b);
		//return new Color(255-r, 255-g, 255-b).darker();
	}
}
