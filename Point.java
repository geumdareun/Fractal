
public class Point
{
	double x, y;
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public static Point origin()
	{
		return new Point(0,0);
	}
	
	public Point copy()
	{
		return new Point(x,y);
	}
	
	public void set(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void rotate(Point basePoint, double theta)
	{
		double c = Math.cos(theta);
		double s = Math.sin(theta);
		x -= basePoint.x;
		y -= basePoint.y;
		double newX = c*x+s*y;
		double newY = s*x-c*y;
		x = newX;
		y = newY;
		x += basePoint.x;
		y += basePoint.y;
	}
	
	public void translate(double dx, double dy)
	{
		x+=dx;
		y+=dy;
	}
	
	@Override
	public String toString()
	{
		return String.format("(%f,%f)", x, y);
	}
}
