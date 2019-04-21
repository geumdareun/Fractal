import java.util.ArrayList;

public class Edge
{
	Point p1, p2;
	
	public Edge(Point p1, Point p2)
	{
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Edge copy()
	{
		return new Edge(p1.copy(), p2.copy());
	}
	
	public Point weightedPosition(double ratio)
	{
		return new Point(ratio*(p2.x-p1.x)+p1.x, ratio*(p2.y-p1.y)+p1.y);
	}
	
	public Edge shrinkOnCopy(double ratio)
	{
		return new Edge(p1.copy(), weightedPosition(ratio));
	}
	
	public void rebase(Point basePoint) //CHECK
	{
		//p2.translate(basePoint.y-p1.x, basePoint.y-p1.y);
		p2.translate(p1.x-basePoint.x, p1.y-basePoint.y);
		p1.set(basePoint.x, basePoint.y);
	}
	
	public void rotate(double theta)
	{
		rotate(p1.copy(), theta);
	}
	
	public void rotate(Point basePoint, double theta)
	{
		p1.rotate(basePoint, theta);
		p2.rotate(basePoint, theta);
	}
	
	public static ArrayList<Edge> rootsOfUnity(int n)
	{
		return rootsOfUnity(n, 1, 0);
	}
	
	public static ArrayList<Edge> rootsOfUnity(int n, double scale)
	{
		return rootsOfUnity(n, scale, 0);
	}
	
	public static ArrayList<Edge> rootsOfUnity(int n, double scale, double angleOffset)
	{
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for(int i=0;i<n;i++)
		{
			double theta = 2.0*Math.PI/n;
			double x = Math.cos(theta * i + angleOffset) * scale;
			double y = Math.sin(theta * i + angleOffset) * scale;
			edges.add(new Edge(Point.origin(), new Point(x, y)));
		}
		return edges;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s-%s", p1, p2);
	}
}
